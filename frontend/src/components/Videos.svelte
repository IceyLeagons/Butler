<script lang="ts">

import getRecommendedVideos from "../api/youtubeApi.js";
</script>

<div class="videos">
    {#await getRecommendedVideos()}
        Fetching videos...
    {:then videos}
        {#each videos as video}
            <a href={video.url} class="video">
                <img class="thumbnail" src={video.thumbnail} />
                <h1 class="title">{video.name}</h1>
                <h2 class="uploader">by {video.uploader}</h2>
            </a>
        {/each}
    {/await}
</div>
<style lang="scss">
    .videos {
      margin-top: 1rem;
      display: flex;
      flex-direction: column;
      gap: 1rem;

      max-height: 300px;
      overflow-y: auto;

      .video {
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