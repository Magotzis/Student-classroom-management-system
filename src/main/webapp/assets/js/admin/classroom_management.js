var table;
$(function(){
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
        "ajax": "../../classroom/findClassroomByPage.do",
        "columns": [
            {
                "sClass": "text-center",
                "data": "id",
                "render": function (data, type, row, meta) {
                    return '<input type="checkbox"  class="checkchild"  value='+row.id+' />';
                },
                "bSortable": false
            },
            { "data": "cno" },
            { "data": "cnumber" },
            { "data": "cdevice" },
            { "data": "cplace" }
        ],
        "dom": "<'row'<'col-xs-6'i><'col-xs-6'l>r>" +
        "t" + "<'#mytool.col-xs-12''>" +
        "<'row'<'col-xs-4'><'col-xs-6'p>>",
        initComplete: function () {
            $("#mytool").append('<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addModal">添加</button>&nbsp');
            $("#mytool").append('<button id="edit" type="button" class="btn btn-primary btn-sm">修改</button>&nbsp');
            $("#mytool").append("<button id='export' type='button' class='btn btn-primary btn-sm'>导出excel</button>&nbsp");
            $("#mytool").append('<button id="delete" type="button" class="btn btn-danger btn-sm">删除</button>&nbsp');
            $("#delete").on("click", deleteData);
            $("#add_save").on("click",addData);
            $("#edit").on("click",editOption);
            $("#edit_save").on("click",editData);
            $("#export").on("click",function(){
            	window.location.href= '../../classroom/exportClassroomExcel.do';
            });
        }

    });

    //全选
    $("#checkAll").click(function () {
        var check = $(this).prop("checked");
        $(".checkchild").prop("checked", check);
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
                url: "../../classroom/deleteClassroom.do",
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
        url: "../../classroom/addOrUpdateClassroom.do",
        data: {
            cno : $("#add_cno").val(),
            cnumber : $("#add_cnumber").val(),
            cdevice : $("#add_cdevice").val(),
            cplace : $("#add_cplace").val()
        },
        success: function(data){
            $("#add_cno").val('');
            $("#add_cnumber").val('');
            $("#add_cdevice").val('');
            $("#add_cplace").val('');
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
             url: "../../classroom/findClassroomById.do",
             data: {
                 id : id
             },
             success: function(data){
                 $("#edit_id").val(data.data.id);
                 $("#edit_cno").val(data.data.cno);
                 $("#edit_cnumber").val(data.data.cnumber);
                 $("#edit_cdevice").val(data.data.cdevice);
                 $("#edit_cplace").val(data.data.cplace);
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
        url: "../../classroom/addOrUpdateClassroom.do",
        data: {
            id : $("#edit_id").val(),
            cno : $("#edit_cno").val(),
            cnumber : $("#edit_cnumber").val(),
            cdevice : $("#edit_cdevice").val(),
            cplace : $("#edit_cplace").val()
        },
        success: function(data){
            $("#edit_id").val('');
            $("#edit_cno").val('');
            $("#edit_cnumber").val('');
            $("#edit_cdevice").val('');
            $("#edit_cplace").val('');
            $("#editModal").modal('hide');
            bootbox.alert(data.info);
            table.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
        } ,
        dataType: 'json'
    });
}