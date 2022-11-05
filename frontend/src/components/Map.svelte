<script>
    import * as L from "leaflet";
    import {onMount} from "svelte";

    export let wifis = [];

    let mapContainer;

    function createMap(container, lat, long) {
        let map = L.map(container).setView([lat, long], 15);
        L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
            maxZoom: 20,
            attribution:
                '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        }).addTo(map);

        var icon = L.icon({
            iconUrl: "https://unpkg.com/leaflet@1.6.0/dist/images/marker-icon.png",
            iconSize: [25, 41],
            iconAnchor: [41, 10]
        })

        for (let i = 0; i < wifis.length; i++) {
            let wifi = wifis[i];
            L.marker([wifi.lat, wifi.lng], {
                title: wifi.ssid,
                icon: icon
            }).addTo(map);
        }
    }

    onMount(() => {
        navigator.geolocation.getCurrentPosition((data) => {
            let lat = data.coords.latitude;
            let long = data.coords.longitude;
            createMap(mapContainer, lat, long);
        });
    });
</script>

<svelte:head>
    <!-- In the REPL you need to do this. In a normal Svelte app, use a CSS Rollup plugin and import it from the leaflet package. -->
    <link
            crossorigin=""
            href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
            integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
            rel="stylesheet"
    />
</svelte:head>

<div bind:this={mapContainer} id="container" style="height:400px; width:100%"/>