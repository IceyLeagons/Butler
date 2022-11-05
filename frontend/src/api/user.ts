import { writable, type Writable } from "svelte/store";
import { redirect } from "../router/Router.svelte";

export type User = {
    firstName: string;
    avatar: string;
    token: string;
    expires: number;
}

async function fetchUserInformation(token: string): Promise<User> {
    return new Promise(async (resolve) => {
       let response = await fetch("/api/me", {
           method: "GET",
           credentials: 'include',
           headers: {
               'Authorization': 'Bearer ' + token
           }
       });

       let json = await response.json();

       resolve({
           firstName: json["firstName"],
           avatar: json["gravatarUrl"],
           token: token,
           expires: new Date().getTime() + 1000 * 60 * 60
       })
    });
}

function checkSavedCredentialsAndLogIn() {
    let user = window.sessionStorage.getItem("user")
    if (user) {
        let obj = JSON.parse(user) as User
        if (new Date().getTime() >= obj.expires) {
            logOut();
        }
        login(obj);
    }
}

function logOut() {
    currentUser.set(undefined);
    window.sessionStorage.removeItem("user");
    redirect("/login");
}

function login(user: User) {
    currentUser.set(user);
    window.sessionStorage.setItem("user", JSON.stringify(user));
    redirect("/");
}

const currentUser: Writable<User> = writable(undefined); //

export {currentUser, logOut, login, checkSavedCredentialsAndLogIn, fetchUserInformation}
export default currentUser;