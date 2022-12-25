<%--
  Created by IntelliJ IDEA.
  User: jiruichang
  Date: 2022/12/22
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
    <form id = "formId" class="layui-form layui-form-pane" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">姓名</label>
            <div class="layui-input-block">
                <input type="text" name="name" autocomplete="off" placeholder="请输入姓名" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-block">
                <input type="text" name="password" lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">考试科目</label>
            <div class="layui-input-block">
                <input type="text" name="obj" lay-verify="required" placeholder="请输入考试科目" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">监考地点</label>
            <div class="layui-input-block">
                <input type="text" name="loc" lay-verify="required" placeholder="请输入监考地点" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">类型</label>
            <div class="layui-input-block">
                <input type="radio" name="type" value="1" title="管理员" checked="">
                <input type="radio" name="type" value="2" title="教师">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" type="button" onclick="submitForm()" type="button">添加</button>
                <button class="layui-btn" type="reset">重置</button>
            </div>

        </div>
    </form>
    <script>
        layui.use(['table','form'],function (){
            var form  = layui.form;
            var table = layui.form;
        });

            function submitForm(){
                $.post(
                    '${path}/user?method=add',
                    $('#formId').serialize(), //{'name':'zhangsan','age':23,'gender':'nan'}
                    function (jsonResult){
                        console.log(jsonResult)

                        if(jsonResult.ok){
                            layer.msg(
                                '添加成功',
                                {icon: 1, time: 1000},
                                function (){//弹出消息1000毫秒之后发出这个消息
                                    //获得当前弹窗的index
                                    var index = parent.layer.getFrameIndex(window.name);  //先得到当前iframe层的索引
                                    parent.layer.close(index);  //执行关闭
                                    parent.location.reload();  //刷新父级页面
                                }
                            )
                        }

                    },
                    'json'

                );
            }

    </script>
</body>
</html>
