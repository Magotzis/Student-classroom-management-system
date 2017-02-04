<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>教室信息管理系统</title>
    <!-- Bootstrap Styles-->
    <link href="../../assets/css/bootstrap.css" rel="stylesheet"/>
    <!-- FontAwesome Styles-->
    <link href="../../assets/css/font-awesome.css" rel="stylesheet"/>
    <!-- Custom Styles-->
    <link href="../../assets/css/custom-styles.css" rel="stylesheet"/>
    <!-- Google Fonts-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
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
                    <a href="lesson_apply.jsp"><i class="fa fa-dashboard"></i> 课程改期申请</a>
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
                        欢迎回来！
                    </h1>

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
<!-- Metis Menu Js -->
<script src="../../assets/js/jquery.metisMenu.js"></script>
<!-- Custom Js -->
<script src="../../assets/js/custom-scripts.js"></script>


</body>
</html>
