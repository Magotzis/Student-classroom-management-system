var lessonTable;
var classroomTable;
var lessonFlag = true;
$(function(){

    $("#select").on("click",function(){
        tabDialog("#tabModal");
    });

    $("#empty_search").on("click",function(){
        initClassroom();
    });

    $("#search").on("click",function(){
        initLesson();
    });

});

function initLesson(){
    var lno = $("#lno").val();
    if (lno == null || lno == ''){
        bootbox.alert("请先选择课程！");
        return;
    }
    if (lessonFlag){
        // 初始化表格
        lessonTable = $("#lessonTables").DataTable({
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
                "url" : "../../lesson/findArrangementByPage.do",
                "data" : {
                    lno : $("#lno").val()
                },
                "type": 'POST',
                "dataType" : 'json'
            },
            "columns": [
                { "data": "lno" },
                { "data": "lname" },
                { "data": "lcredit" },
                { "data": "gtime" },
                {
                    "sClass": "text-center",
                    data : null,
                    "render": function (data, type, row, meta) {
                        return "<a href=javascript:void(classroomDialog('"+row.id+"'));  class='delete btn btn-default btn-xs'  ><i class='fa fa-ok'></i> 申请改期</a>";
                    },
                    "bSortable": false
                }
            ],
            "dom": "<'row'<'col-xs-6'i><'col-xs-6'l>r>" +
            "t" + "<'#mytool.col-xs-12''>" +
            "<'row'<'col-xs-4'><'col-xs-6'p>>"

        });
        lessonFlag = false;
    }else {
        lessonTable.ajax.reload();
    }
}

function initClassroom(){
    // 初始化表格
    classroomTable = $("#classroomTables").DataTable({
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
                cnumber : 0,
                gtime : $("#week").val() + $("#time").val()
            },
            "type": 'POST',
            "dataType" : 'json'
        },
        "columns": [
            { "data": "cno" },
            { "data": "cnumber" },
            { "data": "cdevice" },
            { "data": "cplace" },
            {
                "sClass": "text-center",
                data : null,
                "render": function (data, type, row, meta) {
                    return "<a href=javascript:void(apply('"+ row.cno +"'));  class='delete btn btn-default btn-xs'  ><i class='fa fa-ok'></i> 申请</a>";
                },
                "bSortable": false
            }
        ],
        "dom": "<'row'<'col-xs-6'i><'col-xs-6'l>r>" +
        "t" + "<'#mytool.col-xs-12''>" +
        "<'row'<'col-xs-4'><'col-xs-6'p>>"

    });
}

/**
 *	显示tab选择弹出框
 */
function tabDialog(dialogId){
    /*查出该老师所有的课程信息*/
    $.ajax({
        type: 'POST',
        url: "../../lesson/findLessonListByTeacher.do",
        data: {

        },
        success: function(data){
           var length = data.data.length;
            if (length < 0){
                $("#lesson").html("无教师课程数据可选择！");
            }else{
                var html = "";
                for (var i = 0 ;i < length ; i++){
                    html += "<a onclick=tabOnClick('"+ data.data[i].lno+"','"+data.data[i].lname+"')>"+data.data[i].lname+"&nbsp</a>"
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
function tabOnClick(lno,lname) {
    $("#lno").val(lno);
    $("#lname").val(lname);
    $("#tabModal").modal("hide");
}

/**
 * 空教室查询弹框
 */
function classroomDialog(id){
    $("#id").val(id);
    $("#classroomModal").modal("show");
}

/*安排课程信息*/
function apply(cno){
    var gtime = $("#week").val() + $("#time").val();
    bootbox.confirm("是否改期申请为"+gtime+"的"+cno ,function(result){
        if (result){
            $.ajax({
                type: 'POST',
                url : "../../schooltime/addSchoolTime.do",
                data :{
                    lno: $("#lno").val(),
                    cno : cno,
                    gtime : gtime,
                    state : 'N',
                    apply : 'T',
                    oldId : $("#id").val()
                },
                success : function(data){
                    bootbox.alert(data.info);
                    $("#classroomModal").modal("hide");
                    classroomTable.destroy();
                },
                dataType : 'json'
            });
        }
    });
}