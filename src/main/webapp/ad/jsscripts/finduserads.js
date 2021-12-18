function findAds(userid) {
    var viewtype = 'userads';
    $.ajax({
        type: 'GET',
        data: {
            'viewtype': viewtype,
            'userid': userid
        },
        url: 'http://localhost:8080/job4j_cars/ad/userads.do',
        dataType: 'json'
    }).done(function (ads) {
        let adsEl = document.getElementById('adsEl');
        var newadsEl = document.createElement('tbody');
        newadsEl.setAttribute('id', 'adsEl');
        adsEl.parentNode.replaceChild(newadsEl, adsEl);
        for (var ad of ads) {
            var tr = document.createElement('tr');
            var th = document.createElement('th');
            th.setAttribute('scope', 'row');
            let text = document.createTextNode(ad.id);
            th.append(text);
            tr.append(th);

            var td = document.createElement('td');
            text = document.createTextNode(ad.car.carBrand.name);
            td.append(text);
            tr.appendChild(td);

            td = document.createElement('td');
            text = document.createTextNode(ad.car.bodytype);
            td.append(text);
            tr.appendChild(td);

            td = document.createElement('td');
            img = document.createElement('img');
            img.setAttribute('src', '/job4j_cars/download?filename=' + ad.car.photoPath);
            img.setAttribute('width', '100px');
            img.setAttribute('height', '100px');
            td.append(img);
            tr.appendChild(td);

            td = document.createElement('td');
            href = document.createElement('a');
            href.setAttribute('href', '/job4j_cars/ad/uploadphoto.do?id=' + ad.id);
            text = document.createTextNode('Загрузить');
            href.append(text);
            td.append(href);
            tr.appendChild(td);

            td = document.createElement('td');
            text = document.createTextNode(ad.description);
            td.append(text);
            tr.appendChild(td);

            td = document.createElement('td');
            text = document.createTextNode(ad.created);
            td.append(text);
            tr.appendChild(td);

            td = document.createElement('td');
            var checkbox = document.createElement('input');
            checkbox.setAttribute('type', 'checkbox');
            checkbox.setAttribute('id', ad.id);
            checkbox.setAttribute('name', ad.id);
            checkbox.setAttribute('onchange', 'setSold(this)');
            if (ad.sold) {
                checkbox.setAttribute('checked', true);
            }
            td.append(checkbox);
            tr.appendChild(td);

            td = document.createElement('td');
            href = document.createElement('a');
            href.setAttribute('href', '/job4j_cars/ad/edit.jsp?id=' + ad.id);
            td.append(href);
            img = document.createElement('i');
            img.setAttribute('class', 'fa fa-edit mr-3');
            href.append(img);
            tr.appendChild(td);

            $('#adsEl').append($(tr));
        }
    }).fail(function (err) {
        console.log(err);
    });
}