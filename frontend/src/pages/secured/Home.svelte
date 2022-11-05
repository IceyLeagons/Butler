<script lang="ts">
    import currentUser from "../../api/user";
    import { getCurrentDaytime } from "../../utils/daytime";

    import { onMount } from "svelte";
    import getWeather from "../../api/weather/weatherApi";
    import getCalendarData from "../../api/calendarApi";
    import News from "../../components/News.svelte";
    import getRandomFact from "../../api/randomFactsApi.js";
    import RequestCalendar from "../../components/RequestCalendar.svelte";

    let weather = undefined;
    let daytime = getCurrentDaytime();

    $: token = ($currentUser) ? $currentUser.token : ""

    onMount(() => {
        navigator.geolocation.getCurrentPosition((data) => {
            let lat = data.coords.latitude;
            let long = data.coords.longitude;

            getWeather(lat, long).then((data) => {
                weather = data;
            })
        });
    })
</script>

<div class="page">
    <div class="row" style="width: 100%;">
        <div class="col upper">
            <div class="card greeting">
                <div class="row">
                    <h1 class="title">{daytime.greeting}, {$currentUser.firstName}!</h1>
                    <img class="graphic" src={daytime.icon} alt="icon" />
                </div>
            </div>
        </div>
        <div class="col upper">
            <div class="card greeting">
                <div class="row">
                    {#if weather !== undefined}
                        <h1 class="title">Currently it's<br /> {weather.weatherCode.displayName}</h1>
                        <img class="graphic" src={weather.weatherCode.icon} alt="icon" />
                    {:else}
                        <h1>No Weather Data</h1>
                    {/if}
                </div>
            </div>
        </div>
        <div class="col upper">
            <div class="card greeting">
                <div class="col">
                    <h1 class="title">Did you know, that </h1>
                    {#await getRandomFact()}
                        ...
                    {:then fact}
                        <h2 style="max-width: 100%; text-overflow: ellipsis;">{fact}</h2>
                    {/await}
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col calendar-container">
            <div class="calendar card">
                {#await getCalendarData(token)}
                    Fetching calendar data...
                {:then events}
                    <RequestCalendar {events} />
                {/await}
            </div>
        </div>
        <div class="col news-container">
            <div class="news card">
                <h1>News</h1>
                <div class="news-content">
                    <News />
                </div>
            </div>
        </div>
    </div>
</div>

<style lang="scss">
  .upper {
    width: 100% !important;
  }

  .calendar-container {
    width: 100%;
  }

  .news-container {
    width: 100%;
  }

  .news-content {
    display: flex;
    flex-direction: column;
    gap: .5rem;

    overflow-y: auto;
    max-height: 90%;
    width: 100%;
  }

  .news {
    display: flex;
    flex-direction: column;
    gap: 1rem;

    overflow-y: hidden;
    max-width: 100%;
    overflow-x: auto;
    max-height: 500px;
  }

  .calendar {
    width: 100%;
    height: 31rem;
    flex-grow: 1;
    margin: 0 auto;
    max-width: 900px;
  }



  .clock {
    width: 100%;
    max-width: 32rem;
    max-height: 11rem;
  }

  .title {
    font-size: 2.2rem;
  }




</style>
