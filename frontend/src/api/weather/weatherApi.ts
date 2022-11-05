import WeatherCode from "./weatherCodes";
import getWeatherCode from "./weatherCodes";

type Weather = {
    temperature: number;
    windspeed: number;
    winddir: number;
    weatherCode: any;
}

export default async function getWeather(lat: number, long: number): Promise<Weather> {
    return new Promise(async (resolve) => {
        let resp = await fetch("https://api.open-meteo.com/v1/forecast?latitude=46.25&longitude=20.15&current_weather=true&timeformat=unixtime");
        let json = await resp.json();
        let data = json["current_weather"];

        let wcode = getWeatherCode(data["weathercode"]);
        let response: Weather = {
            temperature: data["temperature"],
            windspeed: data["windspeed"],
            winddir: data["winddirection"],
            weatherCode: wcode
        }

        resolve(response);
    });
}