<script lang="ts">
    import currentUser from "../api/user";
    import getPlaces from "../api/placesApi.js";

    $: token = ($currentUser) ? $currentUser.token : "";
</script>

<div class="places">
    {#await getPlaces(token)}
        Fetching videos...
    {:then places}
        {#if places.length === 0}
            <h1>It looks like there are no fun activities near you :( </h1>
        {:else}
            {#each places as place}
                <div class="place">
                    <h1 class="title">{place.address}</h1>
                    <h2 class="type">{place.type}</h2>
                </div>
            {/each}
        {/if}
    {/await}
</div>
<style lang="scss">
  .places {
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    gap: 1rem;

    max-height: 300px;
    overflow-y: auto;

    .place {
      display: flex;
      align-items: center;
      gap: 1rem;

      padding: .5rem;
      transition: .3s;

      background-color: #EFEFEF;
    }
  }
</style>