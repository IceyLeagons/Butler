<script lang="ts" context="module">
    import { writable } from 'svelte/store';

    const routes: IRoute[] = [];
    interface IRoute {
        path: string;
        component?: any;
        middleware: any[];
        params?: any;
    }

    export function register(route: IRoute) {
        routes[route.path] = route
    }

    export function redirect(path: string) {
        page.redirect(path);
    }

    export const activeRoute: any = writable({}); //type of this should be Writable although it is not exported ??? so we're using "any" yolo


</script>

<script lang="ts">
    import { onMount, onDestroy } from "svelte";
    import page from "page";

    const last = (route) => {
        return function (ctx) {
            $activeRoute = { ...route, params: ctx.params }
        }
    }

    const setupPage = () => {
        console.log("Setting up Router.");
        Object.keys(routes).forEach((path) => {
            const route = routes[path];
            page(path, ...route.middleware, last(route))
        })

        console.log("Router set up!")
        page.start();
    }

    onMount(setupPage);
    onDestroy(page.stop);
</script>

<slot />