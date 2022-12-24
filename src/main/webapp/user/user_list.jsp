<%--
  Created by IntelliJ IDEA.
  User: jiruichang
  Date: 2022/12/20
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<%--    右侧工具条--%>
    <script type="text/html" id="barDemo">
        <c:if test="${user.type==1}">
            <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
        </c:if>
    </script>
<%--    顶部工具条--%>
    <script type="text/html" id="toolbarDemo">
    <c:if test="${user.type==1}">
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-sm"  lay-event="add">添加</button>
                <button class="layui-btn layui-btn-sm" lay-event="deleteAll">批量删除</button>
            </div>
    </c:if>
    </script>
    <div class="demoTable">
        搜索名字：
        <div class="layui-inline">
            <input class="layui-input" name="name" id="nameId" autocomplete="off">
        </div>
        搜索邮箱：
        <div class="layui-inline">
            <input class="layui-input" name="email" id="emailId" autocomplete="off">
        </div>
        搜索手机：
        <div class="layui-inline">
            <input class="layui-input" name="phone" id="phoneId" autocomplete="off">
        </div>
        <button class="layui-btn" data-type="reload">搜索</button>
        <br>
        开始时间：
        <div class="layui-inline">
            <input type="text" class="layui-input" id="beginDateId" placeholder="yyyy-MM-dd HH:mm:ss">
        </div>
        结束时间：
        <div class="layui-inline">
            <input type="text" class="layui-input" id="endDateId" placeholder="yyyy-MM-dd HH:mm:ss">
        </div>

    </div>

    <table class="layui-hide" id="test" lay-filter="test"></table>
    <script src="//res/layui/dist/layui.js" charset="utf-8"></script>
    <script>
        layui.use(['table','laydate'], function(){
            var table = layui.table;
            var laydate = layui.laydate

            //日期时间选择器
            laydate.render({
                elem: '#beginDateId'
                ,type: 'datetime'
            });
            //日期时间选择器
            laydate.render({
                elem: '#endDateId'
                ,type: 'datetime'
            });


            //方法级渲染
            table.render({
                elem: '#test'
                ,url: '${path}/user?method=selectByPage'
                ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
                ,cols: [[
                    {checkbox: true, fixed: true}
                    ,{field:'id', title: 'ID', sort: true, fixed: true}
                    ,{field:'name', title: '用户名'}
                    ,{field:'type', title: '管理员类型'}
                    ,{field:'password', title: '密码'}
                    ,{field:'email', title: '邮箱'}
                    ,{field:'phone', title: '电话'}
                    ,{field:'status', title: '状态'}
                    ,{field:'gmtCreate', title: '创建时间',templet:"<div>{{!d.gmtCreate.time?'':layui.util.toDateString(d.gmtCreate.time,'yyyy-MM-dd HH:mm:ss')}}</div>"}
                    ,{field:'gmtModified', title: '更新时间',templet:"<div>{{!d.gmtCreate.time?'':layui.util.toDateString(d.gmtCreate.time,'yyyy-MM-dd HH:mm:ss')}}</div>"}
                    ,{field:'phone', title: '操作', toolbar :'#barDemo'}
                ]]
                ,id: 'tableId'
                ,page: true
            });

            //头工具栏事件
            table.on('toolbar(test)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id);
                switch(obj.event){
                    case 'add':
                        //location.href = "${path}/user/user_add.jsp"
                        layer.open({
                            type: 2,
                            area: ['700px','400px'],
                            content: '${path}/user/user_add.jsp'

                        })
                        break;
                    case 'deleteAll':

                        var data = checkStatus.data;
                        // {avatar: '', deleted: 0, email: '123', gmtCreate: null, gmtModified: null, …}, {…}, {…}, {…}, {…}, {…}]
                        layer.msg('选中了：'+ data.length + ' 个');
                        var idArray = new Array();
                        for (var i = 0; i < data.length; i++) {
                            idArray.push(data[i].id);
                        }
                        //[14,15] ->'14,15'
                        var ids = idArray.join(',');
                        layer.confirm('真的删除行么', function(index){
                            //异步请求，局部刷新
                            $.post(
                                '${path}/user?method=deleteAll',
                                {'ids':ids},
                                function (jsonResult){
                                    console.log(jsonResult);
                                    if(jsonResult.ok){
                                        // mylayer.okMsg(jsonResult.msg)
                                        mylayer.okMsg('批量删除成功')
                                        //删除之后，刷新表格展示最新的数据
                                        table.reload('tableId');
                                    }else{
                                        mylayer.errorMsg(jsonResult.msg)
                                        mylayer.okMsg('批量删除失败')
                                    }
                                },
                                'json'
                            );
                            layer.close(index);
                        });
                        break;
                    case 'isAll':
                        layer.msg(checkStatus.isAll ? '全选': '未全选');
                        break;

                    //自定义头工具栏右侧图标 - 提示
                    case 'LAYTABLE_TIPS':
                        layer.alert('这是工具栏右侧自定义的一个图标按钮');
                        break;
                };
            });

            //监听右侧工具条
            table.on('tool(test)', function(obj){
                var data = obj.data;
                if(obj.event === 'del'){
                    layer.confirm('真的删除行么', function(index){
                        //异步请求，局部刷新
                        $.post(
                          '${path}/user?method=deleteById',
                            {'id':data.id},
                            function (jsonResult){
                              console.log(jsonResult);
                              if(jsonResult.ok){
                                  mylayer.okMsg('删除成功')
                                  //删除之后，刷新表格展示最新的数据
                                  table.reload('tableId');
                              }else{
                                  mylayer.errorMsg('删除失败')
                              }
                            },
                            'json',
                        );
                        layer.close(index);
                    });
                } else if(obj.event === 'edit'){
                    layer.open({
                        type: 2,
                        area: ['700px','400px'],
                        //content: '${path}/user/user_add.jsp'
                        content: '${path}/user?method=getUserUpdatePage&id='+ data.id

                    })
                }
            });

            var $ = layui.$, active = {
                reload: function(){
                    var demoReload = $('#demoReload');

                    //执行重载
                    table.reload('tableId', {
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                           name : $('#nameId').val(),
                           email : $('#emailId').val(),
                           phone : $('#phoneId').val(),
                           beginDate : $('#beginDateId').val(),
                           endDate : $('#endDateId').val(),
                        }
                    });
                }
            };

            $('.demoTable .layui-btn').on('click', function(){
                var type = $(this).data('type');
                active[type] ? active[type].call(this) : '';
            });
        });
    </script>




<%--    <table class="table table-striped table-bordered table-hover table-condensed">--%>
<%--        <tr>--%>
<%--            <td>Id</td>--%>
<%--            <td>名字</td>--%>
<%--            <td>密码</td>--%>
<%--            <td>邮箱</td>--%>
<%--            <td>电话</td>--%>
<%--            <td>删除</td>--%>
<%--        </tr>--%>
<%--        <c:forEach items="${list}" var="user">--%>
<%--            <tr>--%>
<%--                <td>${user.id}</td>--%>
<%--                <td>${user.name}</td>--%>
<%--                <td>${user.password}</td>--%>
<%--                <td>${user.email}</td>--%>
<%--                <td>${user.phone}</td>--%>
<%--&lt;%&ndash;                <td><a href="${path}/user?method=deleteById&id=${user.id}">删除</a> </td>&ndash;%&gt;--%>
<%--                <td><a href="javascript:deleteById(${user.id})">删除</a> </td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>

<%--    </table>--%>

<%--    <script>--%>
<%--        function deleteById(id){--%>
<%--            var isDeleted = confirm('您确定要删除吗');--%>
<%--            if (isDeleted){--%>
<%--                location.href = "${path}/user?method=deleteById&id=" + id;--%>
<%--            }--%>
<%--        }--%>
<%--    </script>--%>
</body>
</html>
