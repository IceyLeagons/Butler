type CalendarEvent = {
    id: number;
    title: string;
    start: Date;
    end: Date;
}

export default async function getCalendarData(token: string): Promise<CalendarEvent[]> {
    return new Promise(async (resolve) => {
        let resp = await fetch("/api/me/calendar.events", {
            method: "GET",
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + token
            }
        })
        let rawD = await resp.json();

        let result: CalendarEvent[] = [];
        for (let i = 0; i < rawD.length; i++) {
            let data = rawD[i];

            result.push({
                id: i,
                title: data["subject"],
                start: new Date(data.start["epochSeconds"] * 1000),
                end: new Date(data.end["epochSeconds"] * 1000)
            })
        }

        console.log(result);
        resolve(result);
    });
}