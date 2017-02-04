$(function(){

    // 初始化表格
    $("#applyTables").DataTable({
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
        "ajax": "../../schooltime/findApplyByPage.do",
        "columns": [
            { "data": "lno" },
            { "data": "cno" },
            { "data": "gtime" },
            {
                data : "state",
                "render": function (data, type, row, meta) {
                    if (row.state == "N"){
                        return '<p style="color: #2a6496;font-size:15px;">等待审核</p>';
                    }else if (row.state == 'P'){
                        return '<p style="color: red;font-size:15px;">申请失败</p>';
                    }else if (row.state == 'T'){
                        return '<p style="color: green;font-size:15px;">申请成功</p>';
                    }
                },
                "bSortable": false
            }
        ],
        "dom": "<'row'<'col-xs-6'i><'col-xs-6'l>r>" +
        "t" + "<'#mytool.col-xs-12''>" +
        "<'row'<'col-xs-4'><'col-xs-6'p>>"
    });

});
