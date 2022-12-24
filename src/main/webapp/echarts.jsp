<%--
  Created by IntelliJ IDEA.
  User: Gao
  Date: 2022/12/21
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@ include file="header.jsp"%>
    <!-- 引入刚刚下载的 ECharts 文件 -->
    <script src="${path}/static/echarts.min.js"></script>
</head>
<body>
<!-- 为 ECharts 准备一个定义了宽高的 DOM -->
<div id="main1" style="width: 600px;height:400px;"></div>
<div id="main2" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('main1'));
    var myChart2 = echarts.init(document.getElementById('main2'));

    $.post(
        '${path}/dept?method=selectDeptCount',
        function(jsonResult) {
            console.log(jsonResult.data);
            var array = jsonResult.data;

            //柱状图
            // [{name: '客房部', value: 1},{name: 'IT部', value: 1}]
            var xArray = new Array();
            var yArray = new Array();
            for (var i = 0; i < array.length; i++) {
                xArray.push(array[i].name);
                yArray.push(array[i].value);
            }
            // 指定图表的配置项和数据
            var option1 = {
                title: {
                    text: 'ECharts 入门示例'
                },
                tooltip: {},
                legend: {
                    data: ['销量']
                },
                xAxis: {
                    data: xArray
                },
                yAxis: {},
                series: [
                    {
                        name: '销量',
                        type: 'bar',
                        data: yArray
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart1.setOption(option1);

            //饼状图
            var option2 = {
                title: {
                    text: 'Referer of a Website',
                    subtext: 'Fake Data',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: '50%',
                        data: array,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };

            myChart2.setOption(option2);
        },
        'json'
    );

</script>
</body>
</html>
