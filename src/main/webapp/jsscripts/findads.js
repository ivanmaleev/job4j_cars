function findAds() {
    let viewtypeEl = document.getElementsByName('viewtype');
    var viewtype = 'unsold';
    for (var i = 0; i < viewtypeEl.length; i++) {
        if (viewtypeEl[i].checked == true) {
            viewtype = viewtypeEl[i].id;
            break;
        }
    }
    $.ajax({
        type: 'GET',
        data: {
            'viewtype': viewtype
        },
        url: 'http://localhost:8080/job4j_cars/ads.do',
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
            //text = document.createTextNode(ad.car.photoPath);
            td.append(img);
            tr.appendChild(td);

            td = document.createElement('td');
            text = document.createTextNode(ad.description);
            td.append(text);
            tr.appendChild(td);

            td = document.createElement('td');
            text = document.createTextNode(ad.created);
            td.append(text);
            tr.appendChild(td);

            $('#adsEl').append($(tr));
        }
    }).fail(function (err) {
        console.log(err);
    });
}