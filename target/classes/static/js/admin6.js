async function allUsers() {

    let response = await fetch("http://localhost:7072/api/admin/users");
    let json = await response.json();

    let table = document.querySelector(".allUsersInfo")
    let textTable = ``

    function test(users) {
        for (const user of users) {
            textTable += `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.rolesToString}</td>
                        <td>
                            <button id="Edit" class="btn btn-primary" type="button"
                            data-toggle="modal" data-target="#editModal"
                            onclick="editModal(${user.id})">Edit
                            </button>
                        </td>
                        <td>
                            <button id="Delete" class="btn btn-danger" type="button"
                            data-toggle="modal" data-target="#deleteModal"
                            onclick="deleteModal(${user.id})">Delete
                            </button>
                        </td>
                        
                        <td>
                    </tr>`
        }
    }

    test(json)
    table.innerHTML = textTable
    getRoles("rolesEdit")
    getRoles("rolesNew")
}

function getRoles(name) {
    const userRoles = document.getElementById(`${name}`)
    const selectRoles = (roles) => {
        userRoles.size = roles.length
        const options = roles

        for (let i = 0; i < options.length; i++) {
            let roleName = options[i].name
            let el = document.createElement("option")
            el.textContent = roleName
            el.value = roleName
            userRoles.appendChild(el)
        }
    }

    fetch(`http://localhost:7072/api/admin/roles`)
        .then((response) => response.json())
        .then((data) => selectRoles(data))
}

async function editModal(id) {
    let response22 = await fetch(`http://localhost:7072/api/admin/users/user?id=${id}`);
    let user = await response22.json();

    document.getElementById('idEdit').value = `${user.id}`;
    document.getElementById('firstNameEdit').value = `${user.firstName}`;
    document.getElementById('lastNameEdit').value = `${user.lastName}`;
    document.getElementById('emailEdit').value = `${user.email}`;
    document.getElementById('passwordEdit').value = `${user.password}`;
}

async function deleteModal(id) {
    let response2 = await fetch(`http://localhost:7072/api/admin/users/user?id=${id}`);
    let user = await response2.json();

    document.getElementById('idDelete').value = `${user.id}`;
    document.getElementById('firstNameDelete').value = `${user.firstName}`;
    document.getElementById('lastNameDelete').value = `${user.lastName}`;
    document.getElementById('emailDelete').value = `${user.email}`;
    document.getElementById('passwordDelete').value = `${user.password}`;

}

async function CreateEditModal() {
    let idEdit = document.getElementById('idEdit').value;
    let firstNameEdit = document.getElementById('firstNameEdit').value;
    let lastNameEdit = document.getElementById('lastNameEdit').value;
    let emailEdit = document.getElementById('emailEdit').value;
    let passwordEdit = document.getElementById('passwordEdit').value;

    const userRoleSelect = document.getElementById("rolesEdit")
    let selectedValues = [...userRoleSelect.options]
        .filter((x) => x.selected)
        .map((x) => x.value)

    console.log(selectedValues)

    let userEdit = {
        id: idEdit,
        firstName: firstNameEdit,
        lastName: lastNameEdit,
        email: emailEdit,
        password: passwordEdit,
        roles: selectedValues
    }
    let response = await fetch(`http://localhost:7072/api/admin/users/update?id=${idEdit}&ids=${selectedValues}`, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(userEdit)
    });
    allUsers();
}

async function CreateDeleteModal() {
    let idDelete = document.getElementById('idDelete').value;
    await fetch(`http://localhost:7072/api/admin/users/delete?id=${idDelete}`, {
        method: 'DELETE'
    });
    allUsers();
}

async function addNewUser() {
    let firstNameNew = document.getElementById('firstNameNew').value;
    let lastNameNew = document.getElementById('lastNameNew').value;
    let passwordNew = document.getElementById('passwordNew').value;
    let emailNew = document.getElementById('emailNew').value;

    const userRoleSelect = document.getElementById("rolesNew")
    let rolesNewVal = [...userRoleSelect.options]
        .filter((x) => x.selected)
        .map((x) => x.value)

    let userNew = {
        firstName: firstNameNew,
        lastName: lastNameNew,
        email: emailNew,
        password: passwordNew,
        roles: rolesNewVal
    }
    console.log(userNew)
    await fetch(`http://localhost:7072/api/admin/users/new?ids=${rolesNewVal}`, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(userNew)
    });
    allUsers()
}

async function getAdminInfo() {

    let response2 = await fetch("http://localhost:7072/api/user");
    let json = await response2.json();

    let table1 = document.querySelector(".AdminInfo")
    let text = `
                    <tr>
                        <td>${json.id}</td>
                        <td>${json.firstName}</td>
                        <td>${json.lastName}</td>
                        <td>${json.email}</td>
                        <td>${json.rolesToString}</td>
                    </tr>`

    let nav = document.querySelector(".number2")
    nav.innerHTML = `${json.email}` + " with role: " + `${json.rolesToString}`
    table1.innerHTML = text
}

getAdminInfo()
allUsers()