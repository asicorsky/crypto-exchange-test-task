let NAME_MAPPING = {
    sell: 'Sells',
    buy: 'Buys'
};
$(document).ready(function () {

    $.get("/view/buy", function (buys) {
        populate("buy", buys);
        $.get("/view/sell", function (sells) {
            populate("sell", sells);
            let socket = new SockJS('/ws');
            let stomp = Stomp.over(socket);
            stomp.connect({}, function (frame) {
                stomp.subscribe("/appeared", function (data) {
                    let body = JSON.parse(data.body);
                    let tbody = $($(".loader[data-type=" + body.side + "] .loader-table tbody")[0]);
                    let html = row(body);
                    if (body.previousUid && body.previousUid.length > 0) {
                        tbody.prepend(html);
                    } else {
                        tbody.append(html);
                    }
                });
                stomp.subscribe("/removed", function (uid) {
                    $(".loader-table tr[data-uid=" + uid + "]").remove();
                })
            });
        });
    });
});

function populate(type, views) {

    let tbody = $($(".loader[data-type=" + type + "] .loader-table tbody")[0]);
    $.each(views, function (idx, view) {
        let html = row(view);
        tbody.append(html);
    });
    $(".loader[data-type=" + type + "] span").text(NAME_MAPPING[type]);
}

function row(view) {

    let html = "<tr data-uid=" + view.uid + ">";
    html += "<td>" + view.price + "</td>";
    html += "<td>" + view.size + "</td>"
    html += "<td>" + view.summary + "</td>"
    html += "</tr>";
    return html;
}
