function login() {
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    
    let user = {
        "username": username,
        "password": password
    }
    let xhttp = new XMLHttpRequest();
    
    xhttp.onload = (resp) => {
            
        if(xhttp.status === 200){
            let role = JSON.parse(xhttp.getResponseHeader("user")).role;
            if(role === 1){
                window.location = './employee-dash.html'; 
            }
            else{
                window.location = './finance-manager-dash.html';
            }
        }
        else {
            alert('invalid credentials');
        }
    }
    xhttp.open('POST', '../users/login');
    
    xhttp.send(JSON.stringify(user));
}

function showRegister() {
    window.location = './register.html';
}

function register() {
    let username = document.getElementById('usernameR').value;
    let password = document.getElementById('passwordR').value;
    let firstname = document.getElementById('firstnameR').value;
    let lastname = document.getElementById('lastnameR').value;
    let email = document.getElementById('emailR').value;
    let role = document.getElementById('roleR').value;
    let roleId;
    
    if(role === 'Employee'){
        roleId = 1;
    }
    else{
        roleId = 2;
    }
    
    let newUser = {
        "username": username,
        "password": password,
        "firstName": firstname,
        "lastName": lastname,
        "email": email,
        "role": roleId
    }
    
    let xhttp = new XMLHttpRequest();
    xhttp.onload = (resp) => {
        if(xhttp.status === 200){
            window.location = './employee-dash.html';
        }
        else{
            alert('Please try again')
        }
    }
    xhttp.open('POST', '../users/register');
    xhttp.send(JSON.stringify(newUser));
}