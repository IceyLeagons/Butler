const placeholder = [
    {
        subject: "Title title",
        start: 1667644427290,
        end: 1667644449290,
        description: "Asdiuneiurv",
        location: "asd" // or null
    }
];

type CalendarEvent = {
    title: string;
    start: Date;
    end: Date;
}

export default async function getCalendarData(): Promise<CalendarEvent[]> {
    return new Promise(async (resolve) => {
        let result: CalendarEvent[] = [];
        for (let i = 0; i < placeholder.length; i++) {
            let data = placeholder[i];

            result.push({
                title: data["subject"],
                start: new Date(data.start),
                end: new Date(data.end)
            })
        }
        resolve(result);
    });
}