var table;
$(function(){
    // 初始化表格
    table = $("#applyTables").DataTable({
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
            "url" : "../../schooltime/findTeacherApprovedByPage.do",
            "data" : {
                apply : "T"
            },
            "type": 'POST',
            "dataType" : 'json'
        },
        "columns": [
            { "data": "lname" },
            { "data": "cno" },
            { "data": "gtime" },
            { "data": "tname" },
            {
                "sClass": "text-center",
                "data": null,
                "render": function (data, type, row, meta) {
                    var html =  "<button type='button' class='btn btn-primary btn-sm' onclick=approved('"+row.id+"','"+row.oldId+"','T')>通过</button>&nbsp";
                    html +=  "<button type='button' class='btn btn-primary btn-sm' onclick=approved('"+row.id+"','"+row.oldId+"','P')>拒绝</button>&nbsp";
                    return html;
                },
                "bSortable": false
            }
        ],
        "dom": "<'row'<'col-xs-6'i><'col-xs-6'l>r>" +
        "t" + "<'#mytool.col-xs-12''>" +
        "<'row'<'col-xs-4'><'col-xs-6'p>>"

    });

});

/*审批结果*/
function approved(id,oldId,state){
    $.ajax({
        type: 'POST',
        url : "../../schooltime/approvedSchoolTime.do",
        data :{
            id : id,
            oldId : oldId,
            state : state,
            apply : 'T'
        },
        success : function(data){
            bootbox.alert(data.info);
            table.ajax.reload(null, false); // 刷新表格数据，分页信息不会重置
        },
        dataType : 'json'
    });
}