<script lang="ts">
    import FullCalendar from "svelte-fullcalendar";
    import type CalendarEvent from "../api/calendarApi"
    import daygridPlugin from "@fullcalendar/daygrid";
    import timegridPlugin from "@fullcalendar/timegrid";
    import interactionPlugin from "@fullcalendar/interaction";

    export let events: CalendarEvent[];

    let options = {
        droppable: false,
        editable: false,
        events: events,
        initialView: 'dayGridMonth',
        plugins: [daygridPlugin, timegridPlugin, interactionPlugin],
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay',
        },
        height: '100%',
        weekends: true,
    };
</script>
<FullCalendar {options} />