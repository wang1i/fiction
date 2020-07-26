
$(".wait").click(function () {
    layui.use('layer', function(){
        var layer = layui.layer;

        layer.msg('该功能尚未开发', {icon: 5});
    });
});