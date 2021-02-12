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
                    let currentRows = tbody.find("tr[data-uid=" + body.uid + "]");
                    if (currentRows.length) {
                        let currentRow = $(currentRows[0]);
                        currentRow.find(".size").text(body.size);
                        currentRow.find(".summary").text(body.summary);
                    } else {
                        let html = row(body);
                        if (body.previousUid && body.previousUid.length > 0) {
                            tbody.find("tr[data-uid=" + body.previousUid + "]").after($(html));
                        } else {
                            tbody.prepend(html);
                        }
                    }
                });
                stomp.subscribe("/removed", function (data) {
                    $(".loader-table tr[data-uid=" + data.body + "]").remove();
                });
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
    html += "<td class='price'>" + view.price + "</td>";
    html += "<td class='size'>" + view.size + "</td>"
    html += "<td class='summary'>" + view.summary + "</td>"
    html += "</tr>";
    return html;
}
