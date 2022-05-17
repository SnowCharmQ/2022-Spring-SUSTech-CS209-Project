// 自调用函数
(function () {
    // 封装函数
    var setFont = function () {
        // 获取html元素
        var html = document.documentElement;
        // var html = document.querySelector('html');
        // 获取宽度
        var width = html.clientWidth;
        // 如果小于1024，那么就按1024
        if (width < 1024){
            width = 1024;
        }
        // 如果大于1920，那么就按1920
        if (width > 1920) {
            width = 1920;
        }
        // 计算
        var fontSize = width / 80 + 'px';
        // 设置给html
        html.style.fontSize = fontSize;
    }
    setFont();
    // onresize：改变大小事件
    window.onresize = function () {
        setFont();
    }

})();

(function () {

    $('.monitor').on('click', '.tabs a', function () {
        // 点击谁给谁加类名，其他去除类名
        $(this).addClass('active').siblings().removeClass('active');
        // 把对应的content显示，其他的隐藏
        var index = $(this).attr('data-index');
        // 显示
        $('.content').eq(index).show().siblings('.content').hide();

    });


    // 滚动复制一份
    $('.monitor .marquee').each(function () {
        // 拿到了marquee里面的所有row
        var rows = $(this).children().clone();
        // 追加进去
        $(this).append(rows);
        // 父.append(子)==>子.appendTo(父)
        // $('ul').append($('<li>li</li>'));==>$('<li>li</li>').appendTo('ul');
    });
})();






