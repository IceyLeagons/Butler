<script lang="ts">
    import { register, activeRoute } from './Router.svelte';

    export let path: string = '/';
    export let component: any = undefined;
    export let middleware: any[] = [];

    let params = {};

    register({path: path, component: component, middleware: middleware});
    
    $: if ($activeRoute.path === path) {
        params = $activeRoute.params;
    }
</script>

{#if $activeRoute.path === path}
  <!-- if component passed in ignore slot property -->
  {#if $activeRoute.component}
    <!-- passing custom properties and page.js extracted params -->
    <svelte:component
      this="{$activeRoute.component}"
      {...$$restProps}
      {...params}
    />
  {:else}
    <!-- expose params on the route via let:params -->
    <slot {params} />
  {/if}
{/if}
