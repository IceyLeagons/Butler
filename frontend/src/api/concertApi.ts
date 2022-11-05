type Concert = {
    name: string;
    performer: string;
    location: string;
    time: Date;
    avgPrice: number;
    lowestPrice: number;
}

export default async function getConcerts(token: string): Promise<Concert[]> {
    return new Promise(async (resolve) => {
        let resp = await fetch("/api/me/recommendations/concerts", {
            method: "GET",
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
        let rawD = await resp.json();

        let result: Concert[] = [];
        for (let i = 0; i < rawD.length; i++) {
            let data = rawD[i];

            result.push({
                name: data["name"],
                performer: data["performer"],
                location: data["location"],
                time: new Date(data.time["epochSeconds"] * 1000),
                avgPrice: data["averagePrice"],
                lowestPrice: data["lowestPrice"]
            })
        }

        resolve(result);
    });
}