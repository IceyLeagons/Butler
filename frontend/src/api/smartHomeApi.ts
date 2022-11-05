export type SmartAppliance = {
    controlType: string;
    controlStatus: number;
    imageUrl: string;
    name: string;
    maker: string;
}

export default async function getAppliances(token: string): Promise<SmartAppliance[]> {
    return new Promise(async (resolve) => {
        navigator.geolocation.getCurrentPosition(async (pos) => {
            let lat = pos.coords.latitude;
            let long = pos.coords.longitude;

            let resp = await fetch("/api/me/appliances.list", {
                method: "GET",
                credentials: 'include',
                headers: {
                    'Authorization': 'Bearer ' + token
                }
            })
            let rawD = await resp.json();

            let result: SmartAppliance[] = [];
            for (let i = 0; i < rawD.length; i++) {
                let data = rawD[i];

                result.push({
                    controlType: data["controlType"],
                    controlStatus: data["controlStatus"],
                    imageUrl: data["imageUrl"],
                    name: data["name"],
                    maker: data["maker"]
                })
            }

            resolve(result);
        });
    });
}