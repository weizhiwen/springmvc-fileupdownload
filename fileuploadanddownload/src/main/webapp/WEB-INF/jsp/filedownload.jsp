<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>文件上传与下载</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.12.4.min.js"></script>
    <script>
        $(function(){
            var targer = $("#main")
            $.ajax({
                url: "fileList",
                dataType: "json",
                success: function (data) {
                    data = JSON.parse(data)
                    for (var i in data) {
                        var a = $("<a></a><br>").text(data[i].substring(data[i].indexOf("_")+1))
                        a.attr("href", "${pageContext.request.contextPath}/download?filename="+encodeURIComponent(data[i]))
                        targer.append(a)
                    }
                }
            })
        })
    </script>
</head>
<body>
    <div id="main" style="width:500px; margin: 0 auto;">
    </div>
</body>
</html>