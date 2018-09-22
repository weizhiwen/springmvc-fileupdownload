<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>文件上传与下载</title>
    <noscript>
        <style>
            #main {
                display: none !important;
            }
        </style>
        <p align="center">您的浏览器禁止了JS，请先启动脚本</p>
    </noscript>
    <script>
        function check() {
            var file = document.getElementById("file");
            if (file.value == "") {
                alert("上传的文件为空")
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <div id="main" style="width:500px; margin: 0 auto;">
        <span style="color:red;">${msg}</span>
        <form action="" method="post" enctype="multipart/form-data" onsubmit="return check()">
            <input type="file" name="file" id="file" multiple="multiple"><br>
            <input type="submit" value="上传">
        </form>
    </div>
</body>
</html>