<script lang="ts">
    import boredSvg from "../../assets/bored.svg";
    import Videos from "../../components/Videos.svelte";
    import Concerts from "../../components/Concerts.svelte";
    import Places from "../../components/Places.svelte";
</script>

<div class="page">
    <div class="row">
        <div class="col">
            <div class="card greeting">
                <div class="row">
                    <h1 class="title">Are you feeling bored? </h1>
                    <img class="graphic" src={boredSvg} alt="icon" style="width: 40%;" />
                </div>
            </div>
        </div>
        <div class="col">
            <div class="card">
                <h1 class="title">Activities</h1>
                <Places />
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col youtube-container">
            <div class="card youtube">
                <h1>These videos might cheer you up!</h1>
                <Videos />
            </div>
        </div>
        <div class="col concert-container">
            <div class="card seatgeek">
                <h1>Concerts you might like</h1>
                <Concerts />
            </div>
        </div>
    </div>
</div>




<style lang="scss">
    .youtube-container {
      width: 50%;
    }
</style>