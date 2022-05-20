function selectSpringBootQuestion() {
    $.ajax({
        url: "/springboot/springboot-question",
        type: 'POST',
        data: $("#springboot-question").serialize(),
        dataType: "json",
        success: function (json) {
            var ele = $(".springboot-panel")
            ele.empty()
            if (json.state === 200) {
                var head = '<div class="head">' +
                    '        <span class="question-title">Question</span>' +
                    '        <span class="date-title">Date</span>' +
                    '        <span class="answers-title">Answers</span>' +
                    '        <span class="views-title">Views</span>' +
                    '        <span class="link-title">Link</span>' +
                    '    </div>' +
                    '    <div class="spacing">none</div>'
                ele.append(head)
                var list = json.data;
                for (var i = 0; i < list.length; i++) {
                    var temp = list[i];
                    var row = '<div class="row">' +
                        '        <span class="question">' + temp.question + '</span>' +
                        '        <span class="date">' + temp.date + '</span>' +
                        '        <span class="views">' + temp.views + '</span>' +
                        '        <span class="answers">' + temp.answers + '</span>' +
                        '        <a href="' + temp.href + '"' +
                        '           class="link">The Link To This QUESTION</a>' +
                        '    </div>'
                    ele.append(row)
                }
            } else if (json.state === 220) {
                ele.append("<h2>Sorry! No Data Was Found!</h2>")
            }
        }
    })
}

function selectMyBatisQuestion() {
    $.ajax({
        url: "/mybatis/mybatis-question",
        type: 'POST',
        data: $("#mybatis-question").serialize(),
        dataType: "json",
        success: function (json) {
            var ele = $(".mybatis-panel")
            ele.empty()
            if (json.state === 200) {
                var head = '<div class="head">' +
                    '        <span class="question-title">Question</span>' +
                    '        <span class="date-title">Date</span>' +
                    '        <span class="answers-title">Answers</span>' +
                    '        <span class="views-title">Views</span>' +
                    '        <span class="link-title">Link</span>' +
                    '    </div>' +
                    '    <div class="spacing">none</div>'
                ele.append(head)
                var list = json.data;
                for (var i = 0; i < list.length; i++) {
                    var temp = list[i];
                    var row = '<div class="row">' +
                        '        <span class="question">' + temp.question + '</span>' +
                        '        <span class="date">' + temp.date + '</span>' +
                        '        <span class="views">' + temp.views + '</span>' +
                        '        <span class="answers">' + temp.answers + '</span>' +
                        '        <a href="' + temp.href + '"' +
                        '           class="link">The Link To This QUESTION</a>' +
                        '    </div>'
                    ele.append(row)
                }
            } else if (json.state === 220) {
                ele.append("<h2>Sorry! No Data Was Found!</h2>")
            }
        }
    })
}