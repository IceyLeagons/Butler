import clearkSky from "../../assets/weather/clear_sky.svg";
import cloudy from "../../assets/weather/cloudy.svg";
import fog from "../../assets/weather/fog-or-car.svg";
import rainOver from "../../assets/weather/rain_over.svg";
import thunderstorm from "../../assets/weather/thunderstorm.svg";
import freezingRain from "../../assets/weather/freezing_rain.svg";
import rain2 from "../../assets/weather/rain2.svg"; // more intense
import rain from "../../assets/weather/raining.svg";
import snow from "../../assets/weather/snowing.svg";
import wind from "../../assets/weather/wind.svg";

export type WeatherCode = {
    id: number;
    displayName: string;
    icon: string;
}

const weatherCodes: WeatherCode[] = [
    {
        id: 0,
        displayName: "Clear Sky",
        icon: clearkSky
    },
    {
        id: 1,
        displayName: "Mainly Clear",
        icon: clearkSky
    },
    {
        id: 2,
        displayName: "Partly Cloudy",
        icon: cloudy
    },
    {
        id: 3,
        displayName: "Cloudy",
        icon: cloudy
    },
    {
        id: 45,
        displayName: "Fog",
        icon: fog
    },
    {
        id: 45,
        displayName: "Depositing Rime Fog",
        icon: fog
    },
    {
        id: 51,
        displayName: "Light Drizzle",
        icon: wind
    },
    {
        id: 53,
        displayName: "Moderate Drizzle",
        icon: wind
    },
    {
        id: 55,
        displayName: "Dense Drizzle",
        icon: wind
    },
    {
        id: 56,
        displayName: "Freezing Light Drizzle",
        icon: wind
    },
    {
        id: 57,
        displayName: "Freezing Dense Drizzle",
        icon: wind
    },
    {
        id: 61,
        displayName: "Slightly Raining",
        icon: rain
    },
    {
        id: 63,
        displayName: "Moderate Rain",
        icon: rain
    },
    {
        id: 65,
        displayName: "Heavy Rain",
        icon: rain
    },
    {
        id: 66,
        displayName: "Freezing Light Rain",
        icon: freezingRain
    },
    {
        id: 67,
        displayName: "Freezing Heavy Rain",
        icon: freezingRain
    },
    {
        id: 71,
        displayName: "Slight Snowing",
        icon: snow
    },
    {
        id: 73,
        displayName: "Moderate Snowing",
        icon: snow
    },
    {
        id: 75,
        displayName: "Heavy Snowing",
        icon: snow
    },
    {
        id: 77,
        displayName: "Snow Grains",
        icon: snow
    },
    {
        id: 80,
        displayName: "Slight Shower",
        icon: rain2
    },
    {
        id: 81,
        displayName: "Moderate Shower",
        icon: rain2
    },
    {
        id: 82,
        displayName: "Violent Shower",
        icon: rain2
    },
    {
        id: 85,
        displayName: "Slight Snow Shower",
        icon: snow
    },
    {
        id: 86,
        displayName: "Heavy Snow Shower",
        icon: snow
    },
    {
        id: 95,
        displayName: "Thunderstorm",
        icon: thunderstorm
    },
    {
        id: 96,
        displayName: "Thunderstorm & Slight Hail",
        icon: thunderstorm
    },
    {
        id: 99,
        displayName: "Thunderstorm & Heavy Hail",
        icon: thunderstorm
    },
];

export default function getWeatherCode(code: number): WeatherCode {
    for (let i = 0; i < weatherCodes.length; i++) {
        let wc = weatherCodes[i];
        if (wc.id === code) {
            return wc;
        }
    }

    return undefined;
}