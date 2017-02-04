var flag = true;
var table;
$(function(){

    $("#select").on("click",function(){
        tabDialog("#tabModal");
    });

    $("#search").on("click",function(){
        initTable();
    });
});

/**
 *	显示tab选择弹出框
 */
function tabDialog(dialogId){
    /*查出所有老师信息*/
    $.ajax({
        type: 'POST',
        url: "../../lesson/findLessonByList.do",
        data: {
        },
        success: function(data){
            var length = data.data.length;
            if (length < 0){
                $("#teacher").html("无课程数据可选择！");
            }else{
                var html = "";
                for (var i = 0 ;i < length ; i++){
                    html += "<a onclick=tabOnClick('"+ data.data[i].id+"','"+ data.data[i].lno+"','"+data.data[i].lname+"')>"+data.data[i].lname+"&nbsp</a>"
                }
                $("#lesson").html(html);
            }
        } ,
        dataType: 'json'
    });
    $(dialogId).modal("show");
}

/**
 * 点击tab里标签的触发事件
 */
function tabOnClick(id,lno,lname) {
    var prefix = $("#prefix").html();
    $("#id").val(id);
    $("#lno").val(lno);
    $("#lname").val(lname);
    $("#tabModal").modal("hide");
}

function initTable(){
    var cnumber = $("#cnumber").val();
    if (cnumber == null || cnumber == ''){
        bootbox.alert("请填写座位数");
        return;
    }
    if (flag){
        // 初始化表格
        table = $("#classroomTables").DataTable({
            "processing": true,
            "serverSide": true,
            "pageLength": 5,
            "lengthMenu": [ [5,10, 25, 50, 1000], [5,10, 25, 50, "All"] ],
            "language": {
                "emptyTable": "表格中没有数据~",
                "info": "显示 _START_ 到 _END_ 条数据 共 _TOTAL_ 条数据",
                "infoEmpty": "没有数据~",
                "infoFiltered": "(在 _MAX_ 条数据中查询)",
                "lengthMenu": "显示 _MENU_ 条数据",
                "search": "查询:",
                "zeroRecords": "没有找到对应的数据",
                "paginate": {
                    "previous":"上一页",
                    "next": "下一页",
                    "last": "末页",
                    "first": "首页"
                }
            },
            "ajax": {
                "url" : "../../classroom/findFreeClassroomByPage.do",
                "data" : {
                    lno : $("#lno").val(),
                    cdevice : $("#cdevice").val(),
                    cnumber : $("#cnumber").val(),
                    gtime : $("#week").val() + $("#time").val()
                },
                "type": 'POST',
                "dataType" : 'json'
            },
            "columns": [
                { "data": "cno" },
                { "data": "cnumber" },
                { "data": "cdevice" },
                {
                    "sClass": "text-center",
                    data : null,
                    "render": function (data, type, row, meta) {
                        return "<a href=javascript:void(arrangement('"+ row.cno +"'));  class='delete btn btn-default btn-xs'  ><i class='fa fa-ok'></i> 安排</a>";
                    },
                    "bSortable": false
                },
            ],
            "dom": "<'row'<'col-xs-6'i><'col-xs-6'l>r>" +
            "t" + "<'#mytool.col-xs-12''>" +
            "<'row'<'col-xs-4'><'col-xs-6'p>>"
        });
        flag = false;
    }else {
        table.ajax.reload();
    }
}

/*安排课程信息*/
function arrangement(cno){
    var lno = $("#lno").val();
    var lname = $("#lname").val();
    var gtime = $("#week").val() + $("#time").val();
    if (lno == null || lno ==''){
        bootbox.alert("请先选择课程！");
        return;
    }
    bootbox.confirm("是否安排"+lname+"到"+gtime,function(result){
       if (result){
           $.ajax({
               type: 'POST',
               url : "../../schooltime/addSchoolTime.do",
               data :{
                   lno : lno,
                   cno : cno,
                   gtime : gtime,
                   state : 'T'
               },
               success : function(data){
                   bootbox.alert(data.info);
                   table.ajax.reload(null, false);
               },
               dataType : 'json'
           });
       }
    });
}