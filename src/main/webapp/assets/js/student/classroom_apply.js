var flag = true;
var table;
$(function(){

    $("#search").on("click",function(){
        initTable();
    });
});

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
                        return "<a href=javascript:void(apply('"+ row.cno +"'));  class='delete btn btn-default btn-xs'  ><i class='fa fa-ok'></i> 申请</a>";
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
function apply(cno){
    var gtime = $("#week").val() + $("#time").val();
    bootbox.confirm("是否申请"+gtime+"的"+cno ,function(result){
       if (result){
           $.ajax({
               type: 'POST',
               url : "../../schooltime/addSchoolTime.do",
               data :{
                   cno : cno,
                   gtime : gtime,
                   state : 'N',
                   apply : 'S'
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