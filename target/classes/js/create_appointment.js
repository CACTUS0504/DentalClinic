/*
const form = document.getElementById('addingRevForm');
var token = sessionStorage.getItem('token');
var header = 'Bearer_' + token;
var authorId;

if(token){
    fetch('/api/v1/users/getUsername?token=' + token, {
            method: 'GET',
            headers: {
                      'Authorization': header
                    }
    }).then(response => {
        if (response.ok) { // Если запрос успешен

            return response.json(); // Парсим ответ в формате JSON
        } else {
            alert("Invalid Data ^-^");
        }
    }).then(data => {
          // Сохраняем полученный токен в localStorage или sessionStorage
          //document.cookie = `token=${data.token}`
          authorId = data.id;


    }).catch(error => {
        console.error(error);
    });

} else {
    location.href = '/api/v1/auth/login';
}

if (form != null) {
    form.addEventListener('submit', (event) => {
        event.preventDefault(); // Отменяем отправку формы по умолчанию

        const name = form.querySelector('#name').value;
        const details = form.querySelector('#details').value;

        // Отправляем асинхронный POST-запрос на сервер для авторизации
        fetch('/reviews/', {
            method: 'POST',
            body: JSON.stringify({
                name,
                details,
                authorId
            }),
            headers: {
                    'Content-Type': 'application/json',
                    'Authorization': header
            }
        }).then(response => {

            if (response.ok) { // Если запрос успешен

                alert("Your review was saved!");
                location.href="/reviews/";
            } else {
                alert("Invalid Data ^-^");
            }
        }).catch(error => {
            console.error(error);
        });
    });
}*/