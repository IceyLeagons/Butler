<script lang="ts">
    import logo from "../assets/logo.png";
    import pages from "../pages/pages";
    import { activeRoute } from "../router/Router.svelte";
    import User from "./User.svelte";
</script>


    <div class="nav-container">
        <nav class="navigation drop-shadow">
            <div class="brand">
                <img class="logo" src={logo}/>
            </div>
            <div class="items">
                {#each pages as page}
                    <a href={page.href} class="nav-item desktop {($activeRoute.path === page.href) ? "selected" : "" }"><i class={page.icon}></i> {page.name} </a>
                    <a href={page.href} class="nav-item mobile {($activeRoute.path === page.href) ? "selected" : "" }"><i class={page.icon}></i></a>
                {/each}

            </div>
            <User />
        </nav>
    </div>


<style lang="scss" global>

  .nav-container {
    display: flex;
    justify-content: center;
  }

  .logo {
    margin-block: 1rem;
    width: 80%;
  }

  .brand {
    display: flex;
    align-content: center;

    width: 100%;
    max-width: 10rem;
  }

  .navigation {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;

    gap: 3rem;

    width: auto;
    height: 3rem;

    margin: 2rem;
    padding-block: 1.3rem;
    padding-inline: 2rem;

    border-radius: 20px;

    background-color: var(--nav-bg);

    .items {
      display: flex;
      flex-direction: row;
      gap: 2rem;
      width: 100%;

      i {
        margin-right: .5rem;
      }
    }

    a {
      font-size: 1.3rem;
    }
  }

  .nav-item.mobile {
    display: none;
  }

  .nav-item, .nav-item::before {
    transition: background-color, color .2s;
  }

  .selected, .nav-item:hover {
    position: relative;
    color: white;
    background-color: var(--primary-color);
    z-index: 2;

    &::before {
      content: ' ';

      position: absolute;

      width: calc(100% + 1rem);
      height: 100%;

      left: -.5rem;
      top: -50%;
      z-index: -1;
      border-radius: 0px 0px 5px 5px;

      padding-block: 1.6rem;
      border-bottom: solid 3px var(--primary-color-darken);

      background-color: var(--primary-color);

    }
  }

  @media (max-width: 800px) {
    .brand {
      display: none;
      width: 0px;
    }
  }

  @media (max-width: 1155px) {
    .logo {
      width: 100%;
    }

    .navigation {
      display: flex;

      .items {
        width: 80%;
      }
    }

    .selected, .nav-item:hover {
      &::before {
        left: -.75rem;
      }
    }

    .nav-item.mobile {
      display: unset;
    }

    .nav-item.desktop {
      display: none;
    }
  }
</style>