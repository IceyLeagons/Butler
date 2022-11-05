type Place = {
    address: string;
    type: string;
}

export default async function getPlaces(token: string): Promise<Place[]> {
    return new Promise(async (resolve) => {
        navigator.geolocation.getCurrentPosition(async (pos) => {
            let lat = pos.coords.latitude;
            let long = pos.coords.longitude;

            let resp = await fetch("/api/me/recommendations/places?lat=" + lat + "&lng=" + long, {
                method: "GET",
                credentials: 'include',
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            })
            let rawD = await resp.json();

            let result: Place[] = [];
            for (let i = 0; i < rawD.length; i++) {
                let data = rawD[i];

                result.push({
                    address: data["address"],
                    type: data["type"]
                })
            }

            resolve(result);
        });
    });
}