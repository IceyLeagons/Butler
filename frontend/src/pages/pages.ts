import Home from "./secured/Home.svelte";
import Entertainment from "./secured/Entertainment.svelte";
import Utility from "./secured/Utility.svelte";

export type Page = {
    name: string;
    href: string;
    icon: string;
    component: any;
};

const pages: Page[] = [
    {
        name: "Home",
        href: "/",
        icon: "fa-solid fa-home",
        component: Home
    },
    {
        name: "Entertainment",
        href: "/entertainment",
        icon: "fa-solid fa-masks-theater",
        component: Entertainment
    },
    {
        name: "Utility",
        href: "/utility",
        icon: "fa-solid fa-star",
        component: Utility
    }
];

export default pages;