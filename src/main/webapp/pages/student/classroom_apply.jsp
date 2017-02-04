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
                    <a class="active-menu" href="#"><i class="fa fa-dashboard"></i> 空课室查询申请</a>
                </li>

                <li>
                    <a href="apply_result.jsp"><i class="fa fa-dashboard"></i> 申请结果查询</a>
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
                        空课室申请
                    </h1>

                    <div class="col-md-12">
                        <h3 class="page-header">
                            选择教室
                        </h3>
                        <div class="col-xs-12">
                            <div class="col-xs-3">
                                <input type="text" class="form-control" id="cdevice" placeholder="教室类别">
                            </div>
                            <div class="col-xs-3">
                                <input type="number" class="form-control" id="cnumber" placeholder="座位数>=">
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <div class="col-xs-3">
                                <select id="week" class="form-control">
                                    <option value="周一">周一</option>
                                    <option value="周二">周二</option>
                                    <option value="周三">周三</option>
                                    <option value="周四">周四</option>
                                    <option value="周五">周五</option>
                                    <option value="周六">周六</option>
                                    <option value="周日">周日</option>
                                </select>
                            </div>
                            <div class="col-xs-3">
                                <select id="time" class="form-control">
                                    <option value="第1、2节">第1、2节</option>
                                    <option value="第3、4节">第3、4节</option>
                                    <option value="第5节">第5节</option>
                                    <option value="第6、7节">第6、7节</option>
                                    <option value="第8、9节">第8、9节</option>
                                    <option value="第10、11，12节">第10、11、12节</option>
                                </select>
                            </div>
                            <div class="col-xs-2">
                                <button class="btn btn-primary btn-sm" type="button" id="search">空教室查询</button>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                课程一览表
                            </div>
                            <div class="panel-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped" id="classroomTables">
                                        <thead>
                                        <tr>
                                            <th>课室名</th>
                                            <th>座位数</th>
                                            <th>课室设备</th>
                                            <th>申请</th>
                                        </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!--End Advanced Tables -->
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
<script src="../../assets/js/student/classroom_apply.js"></script>


</body>
</html>
