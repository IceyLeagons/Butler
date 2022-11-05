<script lang="ts">
    import { onMount } from "svelte";
    import { fly } from "svelte/transition";
    import { currentUser, logOut } from "../api/user";

    let userDiv;
    let menuDiv; // I could implement DFS to containsChild, but this is easier

    let opened = false;
    let currentTheme = document.body.classList.contains("dark-theme")
        ? "Dark"
        : "Light";

    function toggleTheme(e) {
        e.preventDefault();

        document.body.classList.toggle("dark-theme");
        currentTheme = document.body.classList.contains("dark-theme")
            ? "Dark"
            : "Light";
    }

    function toggleOpen() {
        opened = !opened;
    }

    onMount(() => {
        document.addEventListener("click", (e) => {
            if (!opened) return;
            if (containsChild(userDiv, e.target)) return;
            if (containsChild(menuDiv, e.target)) return;

            toggleOpen();
        });
    });

    function containsChild(parent, child): Boolean {
        let children = parent.children;
        for (let i = 0; i < children.length; i++) {
            if (children[i] === child) return true;
        }

        return false;
    }
</script>

<div class="user" bind:this={userDiv}>
    <!-- svelte-ignore a11y-click-events-have-key-events -->
    <img
            class="avatar"
            src={$currentUser.avatar}
            alt="avatar.png"
            on:click={toggleOpen}
    />

    {#if opened}
        <div
                class="menu drop-shadow"
                bind:this={menuDiv}
                transition:fly={{ x: -50, duration: 500 }}
        >
            <a href="/profile" class="menu-item"><i class="fa-solid fa-user icon" /> Profile</a>

            <a href="/settings" class="menu-item"><i class="fa-solid fa-hammer icon" /> Settings</a>
            <a href="http://localhost:8080/link?client_id=dc783715-5e6e-4146-a7bd-7384af6928c4&state=87u92384957n8345vih&redirect_uri=http://localhost:8081/login&token={$currentUser.token}" class="menu-item"><i class="fa-solid fa-plug icon" /> Link</a>

            <!-- svelte-ignore a11y-missing-attribute -->
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <a class="menu-item" on:click={toggleTheme}
            ><i class="fa-solid fa-circle-half-stroke icon" /> Theme: {currentTheme}</a
            >

            <!-- svelte-ignore a11y-missing-attribute -->
            <!-- svelte-ignore a11y-click-events-have-key-events -->
            <!-- svelte-ignore a11y-invalid-attribute -->
            <a href="#" class="menu-item logout" on:click={logOut}
            ><i class="fa-solid fa-arrow-right-from-bracket icon" /> Log Out</a
            >
        </div>
    {/if}
</div>

<style lang="scss">



  .user {
    position: relative;
  }

  .avatar {
    vertical-align: middle;
    width: 3rem;
    aspect-ratio: 1;
    border-radius: 50%;

    cursor: pointer;
  }
  .menu {
    position: absolute;

    top: 0rem;
    left: 5rem;

    width: 200px;

    overflow: hidden;

    border-left: solid 4px var(--primary-color);
    background-color: var(--card-bg);

    display: flex;
    flex-direction: column;
    justify-content: flex-start;

    border-radius: 10px;

    .icon {
      margin-right: 1rem;
    }

    .menu-item {
      z-index: 0;
      padding: 1rem;
      cursor: pointer;
      transition: 0.3s;

      &:hover {
        color: var(--text-on-primary);
        background-color: var(--primary-color);
      }

      &.logout:hover {
        background-color: red;
      }
    }
  }
</style>
