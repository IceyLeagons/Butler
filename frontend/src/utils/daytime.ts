import afternoon from "../assets/afternoon.svg";
import evening from "../assets/evening.svg";
import morning from "../assets/morning.svg";
import night from "../assets/night.svg";

export class DayTime {

    static readonly MORNING = new DayTime(5, 12, "Good Morning", morning);
    static readonly AFTERNOON = new DayTime(12, 18, "Good Afternoon", afternoon);
    static readonly EVENING = new DayTime(18, 22, "Good Evening", evening);
    static readonly NIGHT = new DayTime(22, 5, "Good Night", night);

    static readonly VALUES: DayTime[] = [this.MORNING, this.AFTERNOON, this.EVENING, this.NIGHT];

    private constructor(public readonly min: number,
                        public readonly max: number, public readonly greeting: string, public readonly icon: any) {}
}

function getCurrentDaytime(): DayTime {
    let now = new Date();
    let hours = now.getHours();

    for (let i = 0; i < DayTime.VALUES.length; i++) {
        let dt = DayTime.VALUES[i];

        console.log(hours);
        if (hours >= dt.min && hours < dt.max) {
            return dt;
        }
    }

    return null;
}

export { getCurrentDaytime }