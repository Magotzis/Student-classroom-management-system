var table;
$(function(){
    // 初始化表格
    table = $("#lessonTables").DataTable({
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
        "ajax": "../../lesson/findLessonByPage.do",
        "columns": [
            {
                "sClass": "text-center",
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<input type="checkbox"  class="checkchild"  value='+row.id+' />';
                },
                "bSortable": false
            },
            { "data": "lno" },
            { "data": "lname" },
            { "data": "lcredit" },
            { "data": "tname" },
            { "data": "tno" },
            { "data": "tsex" },
            { "data": "ttitle" }
        ],
        "dom": "<'row'<'col-xs-6'i><'col-xs-6'l>r>" +
        "t" + "<'#mytool.col-xs-12''>" +
        "<'row'<'col-xs-4'><'col-xs-6'p>>",
        initComplete: function () {
            $("#mytool").append('<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addModal">添加</button>&nbsp');
            $("#mytool").append('<button id="edit" type="button" class="btn btn-primary btn-sm">修改</button>&nbsp');
            $("#mytool").append('<button id="export" type="button" class="btn btn-primary btn-sm">导出excel</button>&nbsp');
            $("#mytool").append('<button id="delete" type="button" class="btn btn-danger btn-sm">删除</button>&nbsp');
            $("#delete").on("click", deleteData);
            $("#add_save").on("click",addData);
            $("#edit").on("click",editOption);
            $("#edit_save").on("click",editData);
            $("#export").on("click",function(){
            	window.location.href= '../../lesson/exportLessonExcel.do';
            });
        }

    });

    //全选
    $("#checkAll").click(function () {
        var check = $(this).prop("checked");
        $(".checkchild").prop("checked", check);
    });

    $("#add_select").on("click",function(){
        $("#prefix").html("add");
        tabDialog("#tabModal");
    });
    $("#edit_select").on("click",function(){
        $("#prefix").html("edit");
        tabDialog("#tabModal");
    });

});


/**
 * 删除数据
 */
function deleteData() {
    bootbox.confirm("确定删除所选记录吗?",function(result){
        if(result){
            var id = document.getElementsByClassName("checkchild");
            var value = new Array();
            for(var i = 0; i < id.length; i++){
                if(id[i].checked)
                    value.push(id[i].value);
            }
            $.ajax({
                type: 'POST',
                url: "../../lesson/deleteLesson.do",
                data: {
                    ids : value.toString()
                },
                success: function(data){
                    bootbox.alert(data.info);
                    table.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
                } ,
                dataType: 'json'
            });
        }
    });
}

/*
新增课室
 */
function addData(){
    $.ajax({
        type: 'POST',
        url: "../../lesson/addOrUpdateLesson.do",
        data: {
            lno : $("#add_lno").val(),
            lname : $("#add_lname").val(),
            lcredit : $("#add_lcredit").val(),
            tno : $("#add_tno").val()
        },
        success: function(data){
            $("#add_lno").val('');
            $("#add_lname").val('');
            $("#add_lcredit").val('');
            $("#add_tno").val('');
            $("#add_tname").val('');
            bootbox.alert(data.info);
            table.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
        } ,
        dataType: 'json'
    });
}

/*编辑前的处理*/
function editOption(){
     if ($(".checkchild:checked").length > 1)
     {
     bootbox.alert("一次只能修改一条数据");
     }else{
         var id = $(".checkchild:checked").val();
         $.ajax({
             type: 'POST',
             url: "../../lesson/findLessonById.do",
             data: {
                 id : id
             },
             success: function(data){
                 $("#edit_id").val(data.data.id);
                 $("#edit_lno").val(data.data.lno);
                 $("#edit_lname").val(data.data.lname);
                 $("#edit_lcredit").val(data.data.lcredit);
                 $("#edit_tno").val(data.data.tno);
                 $("#edit_tname").val(data.data.tname);
                 $("#editModal").modal('show');
             } ,
             dataType: 'json'
         });
     }
}

/*编辑课室*/
function editData(){
    $.ajax({
        type: 'POST',
        url: "../../lesson/addOrUpdateLesson.do",
        data: {
            id : $("#edit_id").val(),
            lno : $("#edit_lno").val(),
            lname : $("#edit_lname").val(),
            lcredit : $("#edit_lcredit").val(),
            tno : $("#edit_tno").val()
        },
        success: function(data){
            $("#edit_id").val('');
            $("#edit_lno").val('');
            $("#edit_lname").val('');
            $("#edit_lcredit").val('');
            $("#edit_tno").val('');
            $("#edit_tname").val('');
            $("#editModal").modal('hide');
            bootbox.alert(data.info);
            table.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
        } ,
        dataType: 'json'
    });
}

/**
 *	显示tab选择弹出框
 */
function tabDialog(dialogId){
    /*查出所有老师信息*/
    $.ajax({
        type: 'POST',
        url: "../../teacher/getTeacherByList.do",
        data: {

        },
        success: function(data){
           var length = data.data.length;
            if (length < 0){
                $("#teacher").html("无教师数据可选择！");
            }else{
                var html = "";
                for (var i = 0 ;i < length ; i++){
                    html += "<a onclick=tabOnClick('"+ data.data[i].tno+"','"+data.data[i].tname+"')>"+data.data[i].tname+"&nbsp</a>"
                }
                $("#teacher").html(html);
            }
        } ,
        dataType: 'json'
    });
    $(dialogId).modal("show");
}

/**
 * 点击tab里标签的触发事件
 */
function tabOnClick(tno,tname) {
    var prefix = $("#prefix").html();
    $("#" + prefix + "_tno").val(tno);
    $("#" + prefix + "_tname").val(tname);
    $("#tabModal").modal("hide");
}