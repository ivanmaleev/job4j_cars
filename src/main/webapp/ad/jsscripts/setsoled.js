function setSold(checkbox) {
    let id = checkbox.id;
    $.ajax({
        type: 'POST',
        data: {'id': id},
        url: 'http://localhost:8080/job4j_cars/ad/setsoled.do',
    }).done(function () {
    }).fail(function (err) {
        console.log(err);
    });
}