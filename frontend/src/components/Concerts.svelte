<script lang="ts">
    import currentUser from "../api/user";
    import getConcerts from "../api/concertApi.js";

    $: token = ($currentUser) ? $currentUser.token : "";
</script>

<div class="concerts">
    {#await getConcerts(token)}
        Fetching videos...
    {:then concerts}
        {#each concerts as concert}
            <div class="concert">
                <h1 class="title">{concert.name}</h1>
            </div>
        {/each}
    {/await}
</div>
<style lang="scss">
  .concerts {
    margin-top: 1rem;
    display: flex;
    flex-direction: column;
    gap: 1rem;

    max-height: 300px;
    overflow-y: auto;

    .concert {
      display: flex;
      align-items: center;
      gap: 1rem;

      padding: .5rem;
      transition: .3s;

      &:hover {
        background-color: #EFEFEF;
      }
    }
  }
</style>