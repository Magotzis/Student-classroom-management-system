<!DOCTYPE html>
<html>
<head>
    <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>教室信息管理系统</title>
    <!-- Bootstrap Styles-->
    <link href="../../assets/css/bootstrap.css" rel="stylesheet"/>
    <!-- FontAwesome Styles-->
    <link href="../../assets/css/font-awesome.css" rel="stylesheet"/>
    <!-- Custom Styles-->
    <link href="../../assets/css/custom-styles.css" rel="stylesheet"/>
</head>
<body>
<div id="wrapper">

    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">教室管理系统</a>
        </div>

        <ul class="nav navbar-top-links navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" aria-expanded="false">
                    <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="../../common/logout.do"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
    </nav>
    <!--/. NAV TOP  -->

    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li>
                    <a class="active-menu" href="#"><i class="fa fa-sitemap"></i>教室管理</a>
                </li>

                <li>
                    <a href="#"><i class="fa fa-sitemap"></i>课程信息<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="lesson_management.jsp">课程管理</a>
                        </li>
                        <li>
                            <a href="lesson_arrangement.jsp">课程安排</a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="#"><i class="fa fa-sitemap"></i>申请处理<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="student_apply.jsp">学生教室申请</a>
                        </li>
                        <li>
                            <a href="teacher_apply.jsp">教师课程改期申请</a>
                        </li>
                    </ul>
                </li>
            </ul>

        </div>

    </nav>
    <!-- /. NAV SIDE  -->

    <div id="page-wrapper">
        <div id="page-inner">
            <div class="row">
                <div class="col-md-12">
                    <!-- page begin -->
                    <h1 class="page-header">
                        教室管理
                    </h1>

                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                教室一览表
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped" id="classroomTables">
                                        <thead>
                                        <tr>
                                            <th>
                                                <input type="checkbox" id="checkAll" />
                                            </th>
                                            <th>课室名</th>
                                            <th>座位数</th>
                                            <th>课室设备</th>
                                            <th>课室位置</th>
                                        </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!--End Advanced Tables -->
                    </div>

                    <!--addModal -->
                    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="addLabel">新增课室</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="add_cno" placeholder="课室名">
                                    </div>
                                    <div class="form-group">
                                        <input type="number" class="form-control" id="add_cnumber" placeholder="座位数">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="add_cdevice" placeholder="课室设备">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="add_cplace" placeholder="课室位置">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" id="add_save">添加</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!--editModal -->
                    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="editLabel">修改课室信息</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="form-group hide">
                                        <input type="text" class="form-control" id="edit_id" placeholder="id">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="edit_cno" placeholder="课室名">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="edit_cnumber" placeholder="座位数">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="edit_cdevice" placeholder="课室设备">
                                    </div>
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="edit_cplace" placeholder="课室位置">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" id="edit_save">保存</button>
                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- page end -->
                </div>
            </div>
            <!-- /. ROW  -->
        </div>
        <!-- /. PAGE INNER  -->
        <div id="footer" align="center">
            <footer><p><b>CopyRight@ 2016. </b>All right reserved.</p></footer>
        </div>
    </div>
    <!-- /. PAGE WRAPPER  -->
</div>
<!-- /. WRAPPER  -->


<!-- JS Scripts-->
<!-- jQuery Js -->
<script src="../../assets/js/jquery-1.10.2.js"></script>
<!-- Bootstrap Js -->
<script src="../../assets/js/bootstrap.min.js"></script>
<script src="../../assets/js/bootbox.js"></script>
<!-- Metis Menu Js -->
<script src="../../assets/js/jquery.metisMenu.js"></script>
<!-- DATA TABLE SCRIPTS -->
<script src="../../assets/js/dataTables/jquery.dataTables.js"></script>
<script src="../../assets/js/dataTables/dataTables.bootstrap.js"></script>
<!-- Custom Js -->
<script src="../../assets/js/custom-scripts.js"></script>
<script src="../../assets/js/admin/classroom_management.js"></script>



</body>
</html>
