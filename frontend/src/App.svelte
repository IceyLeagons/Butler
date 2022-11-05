<script>
    import Navigation from "./components/Navigation.svelte";

    import pages from "./pages/pages";
    import currentUser, { checkSavedCredentialsAndLogIn, login } from "./api/user";

    import Router, { redirect } from "./router/Router.svelte";
    import NotFound from "./router/NotFound.svelte";
    import Route from "./router/Route.svelte";

    import Login from "./pages/Login.svelte";
    import { onMount } from "svelte";

    onMount(() => {
        checkSavedCredentialsAndLogIn();
    });

    $: loggedIn = $currentUser !== undefined;

    const loginMiddleware = (ctx, next) => {
        if (!loggedIn) {
            redirect("/login");
            return;
        }

        next();
    }

    const loggedInMiddleware = (ctx, next) => {
        if (loggedIn) {
            redirect("/");
            return;
        }
        next();
    }
</script>

{#if loggedIn}
    <Navigation />
{/if}

<main class="page-content">
    <Router>
        <Route path="/login" component={Login} middleware={[loggedInMiddleware]} />

        {#each pages as page}
            <Route path={page.href} component={page.component} middleware={[loginMiddleware]}/>
        {/each}

        <NotFound>
            <h1>Page not found</h1>
        </NotFound>
    </Router>
</main>

<style lang="scss">
  .page-content {
    margin-top: 3rem;
    width: 90%;
    margin-inline: auto;
  }
</style>