<script lang="ts">


    import {getNews} from "../api/newsApi.js";
</script>
{#await getNews()}
    <p>Fetching...</p>
{:then news}
    {#each news as data}
        {#each data.items as item}
            <a href={item.link} class="new">
                <div class="left">
                    <img class="sourceImage" src={data.imageUrl}/>
                    <h1 class="sourceTitle">{item.title}</h1>
                    <h2 class="newContent">{item.content}</h2>
                </div>
                <h3 class="publish">( {item.publish.toLocaleDateString()} {item.publish.toLocaleTimeString()} )</h3>
            </a>
        {/each}
    {/each}
{/await}

<style lang="scss">
  .new {
    display: flex;

    padding-block: 1rem;
    padding-inline: 1rem;

    gap: 1rem;
    justify-content: space-between;

    justify-items: center;
    align-items: center;

    .sourceTitle {
      font-size: 1.5rem;
    }

    .left {
      display: flex;
      align-items: center;

      gap: 1rem;
      max-width: 100%;
    }

    .publish {
      width: 12rem;
    }

    .publish {
      font-size: 1rem;
    }

    &:hover {
      background-color: #EFEFEF;
    }

    .newContent {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      max-width: 50%;
    }

    .sourceImage {
      max-width: 5rem;
    }
  }
</style>