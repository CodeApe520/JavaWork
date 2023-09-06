<%--
  Created by IntelliJ IDEA.
  User: jiruichang
  Date: 2022/12/22
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="../header.jsp"%>
</head>
<body>
<%--右侧工具条--%>
<script type="text/html" id="barDemo">
    <c:if test="${user.type == 1}">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </c:if>
</script>
<%--顶部工具条--%>
<script type="text/html" id="toolbarDemo">
    <c:if test="${user.type == 1}">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add">添加</button>
        <button class="layui-btn layui-btn-sm" lay-event="deleteAll">批量删除</button>
    </div>
    </c:if>
</script>

<script type="text/html" id="typeTemplet">
    {{#     if (d.type==1) {            }}
    <span class="layui-badge layui-bg-orange">管理员</span>
    {{#     } else if (d.type==2) {     }}
    <span class="layui-badge layui-bg-green">督导</span>
    {{#     } else if (d.type==3) {     }}
    <span class="layui-badge layui-bg-green">教师</span>
    {{#     } else if (d.type==4) {     }}
    <span class="layui-badge layui-bg-green">学生</span>
    {{#     }                           }}
</script>

<script type="text/html" id="statusTemplet">
    <!-- 这里的 checked 的状态只是演示 -->
    <input type="checkbox" name="status" value="{{d.id}}" lay-skin="switch" lay-text="可用|不可用" lay-filter="satusLayFilter" {{ d.status == 1 ? 'checked' : '' }}>
</script>

<div class="demoTable">
    用户名：
    <div class="layui-inline">
        <input class="layui-input" name="name" id="nameId" autocomplete="off">
    </div>
    手机号：
    <div class="layui-inline">
        <input class="layui-input" name="phone" id="phoneId" autocomplete="off">
    </div>
    邮箱：
    <div class="layui-inline">
        <input class="layui-input" name="email" id="emailId" autocomplete="off">
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
    账号类型：
    <div class="layui-inline">
        <select id="typeId" name="type">
            <option value="">--类型--</option>
            <option value="1">管理员</option>
            <option value="2">督导</option>
            <option value="3">教师</option>
            <option value="4">学生</option>
        </select>
    </div>
</div>
<table class="layui-hide" id="test" lay-filter="test"></table>

<script>
    layui.use(['table','laydate','form'], function(){
        var table = layui.table;
        var laydate = layui.laydate;
        var form = layui.form;

        //日期时间选择器
        laydate.render({
            elem: '#beginDateId'
            ,type: 'datetime'
        });
        laydate.render({
            elem: '#endDateId'
            ,type: 'datetime'
        });

        //table 方法级渲染
        table.render({
            elem: '#test'
            ,url: '${path}/user?method=selectByPage'
            ,toolbar: '#toolbarDemo' //开启头部工具栏，并为其绑定左侧模板
            ,cols: [[
                {checkbox: true, fixed: true}
                ,{field:'id', title: 'ID', sort: true}
                ,{field:'name', title: '用户名'}
                ,{field:'type', title: '账号类型',templet: '#typeTemplet'}
                ,{field:'password', title: '密码'}
                ,{field:'phone', title: '手机号'}
                ,{field:'email', title: '邮箱'}
                ,{field:'status', title: '状态',templet: '#statusTemplet'}
                ,{field:'gmtCreate', title: '创建时间',templet:"<div>{{!d.gmtCreate.time?'':layui.util.toDateString(d.gmtCreate.time, 'yyyy-MM-dd HH:mm:ss')}}</div>"}
                ,{field:'', title: '操作', toolbar: '#barDemo'}
            ]]
            ,id: 'tableId'
            ,page: true,
        });

        //监听性别操作
        form.on('switch(satusLayFilter)', function(email){
            //layer.tips(this.value + ' ' + this.name + '：'+ email.elem.checked, email.othis);
            console.log(this.value + ' ' + this.name + '：'+ email.elem.checked);
            var status = email.elem.checked == true ? 1 : 0;
            $.post(
                '${path}/user?method=updateStatus',
                {'id':this.value, 'status': status},
                function(jsonResult) {
                    console.log(jsonResult);
                    if (jsonResult.ok) {
                        mylayer.okMsg('状态修改成功')
                    } else {
                        mylayer.errorMsg('状态修改失败')
                    }
                },
                'json'
            );
        });

        //头工具栏事件
        table.on('toolbar(test)', function(email){
            var checkStatus = table.checkStatus(email.config.id);
            switch(email.event){
                case 'add':
                    // phoneation.href = '${path}/user/user_add.jsp';
                    layer.open({
                        type: 2,
                        area: ['700px', '400px'],
                        content: '${path}/user/user_add.jsp'
                    });
                    break;
                case 'deleteAll':
                    var data = checkStatus.data;
                    //[{avatar: '', deleted: 0, phone: '12', gmtCreate: null, gmtModified: null, …}, {avatar: '', deleted: 0, phone: '13', gmtCreate: null, gmtModified: null, …}]
                    layer.msg('选中了：'+ data.length + ' 个');
                    var idArray = new Array();
                    for (var i = 0; i < data.length; i++) {
                        idArray.push(data[i].id);
                    }
                    //[14,15] -> '14,15'
                    var ids = idArray.join(',');
                    layer.confirm('真的删除行么', function(index){
                        $.post(
                            '${path}/user?method=deleteAll',
                            {'ids':ids},
                            function(jsonResult) {
                                console.log(jsonResult);
                                if (jsonResult.ok) {
                                    //mylayer.okMsg(jsonResult.msg)
                                    mylayer.okMsg('批量删除成功')
                                    //删除之后刷新表格展示最新的数据
                                    table.reload('tableId');
                                } else {
                                    //mylayer.errorMsg(jsonResult.msg);
                                    mylayer.errorMsg('批量删除失败')
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
        table.on('tool(test)', function(email){
            var data = email.data;
            if(email.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.post(
                        '${path}/user?method=deleteById',
                        {'id':data.id},
                        function(jsonResult) {
                            console.log(jsonResult);
                            if (jsonResult.ok) {
                                mylayer.okMsg('删除成功')
                                //删除之后刷新表格展示最新的数据
                                table.reload('tableId');
                            } else {
                                mylayer.errorMsg('删除失败');
                            }
                        },
                        'json'
                    );

                    layer.close(index);
                });
            } else if(email.event === 'edit'){
                layer.open({
                    type: 2,
                    area: ['700px', '400px'],
                    // content: '${path}/user/user_update.jsp'
                    content: '${path}/user?method=getUserUpdatePage&id=' + data.id
                });
            }
        });

        var $ = layui.$, active = {
            reload: function(){
                //执行重载
                table.reload('tableId', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    ,where: {
                        name: $('#nameId').val(),
                        phone: $('#phoneId').val(),
                        email: $('#emailId').val(),
                        type: $('#typeId').val(),
                        beginDate: $('#beginDateId').val(),
                        endDate: $('#endDateId').val(),
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






<%--
   <table class="table table-striped table-bordered table-hover table-condensed">
        <tr>
            <td>ID</td>
            <td>名字</td>
            <td>密码</td>
            <td>邮箱</td>
            <td>考试科目</td>
            <td>删除</td>
        </tr>
        <c:forEach items="${list}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.password}</td>
                <td>${user.phone}</td>
                <td>${user.email}</td>
                &lt;%&ndash;<td><a href="${path}/user?method=deleteById&id=${user.id}">删除</a></td>&ndash;%&gt;
                <td><a href="javascript:deleteById(${user.id})">删除</a></td>
            </tr>
        </c:forEach>
    </table>

    <script>
        function deleteById(id) {
            var isDelete = confirm('您确认要删除么？');
            if (isDelete) {
                phoneation.href = "${path}/user?method=deleteById&id=" + id;
            }
        }
    </script>--%>
</body>
</html>
