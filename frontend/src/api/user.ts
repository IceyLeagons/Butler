import { writable, type Writable } from "svelte/store";
import { redirect } from "../router/Router.svelte";

export type User = {
    firstName: string;
    avatar: string;
    token: string;
}

async function fetchUserInformation(token: string): Promise<User> {
    return new Promise(async (resolve) => {
       let response = await fetch("/api/me", {
           headers: {
               "Authentication": "Bearer " + token
           }
       });

       let json = await response.json();

       resolve({
           firstName: json["firstName"],
           avatar: json["gravatarUrl"],
           token
       })
    });
}

function checkSavedCredentialsAndLogIn() {
    let user = window.sessionStorage.getItem("user")
    if (user) {
        login(JSON.parse(user) as User);
    }
}

function logOut(e) {
    currentUser.set(undefined);
    window.sessionStorage.removeItem("user");
    redirect("/login");
}

function login(user: User) {
    currentUser.set(user);
    window.sessionStorage.setItem("user", JSON.stringify(user));
    redirect("/");
}

const currentUser: Writable<User> = writable({firstName: "Tamás", avatar: "https://gravatar.com/avatar/4a95f5bbba1cecd1dc777bf39a2b7b50?s=500", token:
        "ddd"}); //

export {currentUser, logOut, login, checkSavedCredentialsAndLogIn, fetchUserInformation}
export default currentUser;