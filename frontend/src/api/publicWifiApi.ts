type PublicWifi = {
    ssid: string;
    lat: number;
    lng: number;
}

export default async function getPublicWifis(token: string): Promise<PublicWifi[]> {
    return new Promise(async (resolve) => {
        navigator.geolocation.getCurrentPosition(async (pos) => {
            let lat = pos.coords.latitude;
            let long = pos.coords.longitude;

            let resp = await fetch("/api/me/wifi.near/public?lat=" + lat + "&lng=" + long, {
                method: "GET",
                credentials: 'include',
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            })
            let rawD = await resp.json();

            let result: PublicWifi[] = [];
            for (let i = 0; i < rawD.length; i++) {
                let data = rawD[i];

                result.push({
                    ssid: data["ssid"],
                    lat: data["lat"],
                    lng: data["lng"]
                });
            }

            resolve(result);
        });
    });
}