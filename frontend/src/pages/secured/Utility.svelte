<script lang="ts">
    import Appliance from "../../components/Appliance.svelte";
    import getAppliances from "../../api/smartHomeApi.js";
    import currentUser from "../../api/user";
    import Map from "../../components/Map.svelte";
    import getPublicWifis from "../../api/publicWifiApi.js";

    $: token = ($currentUser) ? $currentUser.token : ""
</script>

<div class="page">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="appliances">
                    {#await getAppliances(token)}
                        Fetching appliances...
                    {:then appliances}
                        {#each appliances as appliance}
                            <Appliance data={appliance} />
                        {/each}
                    {/await}
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <div class="card" style="width: 1000px; height: 435px;">
                <h1>Public WIFIs</h1>
               {#await getPublicWifis(token)}
                    Fetching public wifi information
                {:then wifis}
                    <Map wifis={wifis} />
                {/await}

            </div>
        </div>
    </div>
</div>

<style>
    .appliances {
        display: flex;
        flex-direction: column;
        gap: 1.5rem;
    }
</style>