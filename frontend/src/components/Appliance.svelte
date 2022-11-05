<script lang="ts">
    import type {SmartAppliance} from "../api/smartHomeApi";

    export let data: SmartAppliance
</script>

<div class="appliance">
    <img class="img" src={data.imageUrl} />
    <h1 class="name">{data.name}</h1>

    {#if data.controlType === "toggle"}
        <input type="checkbox" />
    {/if}
    {#if data.controlType === "slider"}
        <input type="range" />
    {/if}
    {#if data.controlType === "colorwheel"}
        <input type="color" />
    {/if}
</div>
<style>
    .img {
        width: 50px;
        aspect-ratio: 1;
    }

    .appliance {
        display: flex;
        flex-direction: row;
        gap: 1rem;
    }
</style>