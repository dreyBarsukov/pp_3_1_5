async function getUserInfo(){

    let response = await fetch("http://localhost:7072/api/user");
    let json = await response.json();

    let table = document.querySelector(".UserInfo")
    let text = `
                    <tr>
                        <td>${json.id}</td>
                        <td>${json.firstName}</td>
                        <td>${json.lastName}</td>
                        <td>${json.email}</td>
                        <td>${json.rolesToString}</td>
                    </tr>`

    let nav = document.querySelector(".number1")
    nav.innerHTML = `${json.email}`+ " with role: " + `${json.rolesToString}`
    table.innerHTML = text
}
getUserInfo()