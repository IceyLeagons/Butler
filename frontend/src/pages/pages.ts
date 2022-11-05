import Home from "./secured/Home.svelte";
import Entertainment from "./secured/Entertainment.svelte";

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
        name: "Shopping / Food",
        href: "/foodAndShopping",
        icon: "fa-solid fa-cart-shopping",
        component: Home
    },
    {
        name: "Transport",
        href: "/transport",
        icon: "fa-solid fa-bus",
        component: Home
    },
    {
        name: "Utility",
        href: "/utility",
        icon: "fa-solid fa-star",
        component: Home
    }
];

export default pages;