/// <reference path="../../generic.d.ts/fullcalendar/index.d.ts" />

module nts.uk.ui.koExtentions {
    const COMPONENT_NAME = 'nts-fullcalendar';
    const DEFAULT_STYLES = `
        .fc .fc-toolbar.fc-header-toolbar {
            margin-bottom: 10px;
        }
        .fc .fc-timegrid thead>tr>td td:first-child {
            font-size: 11px;
            vertical-align: middle;
        }
        .fc .fc-timegrid thead>tr>td td:first-child,
        .fc .fc-timegrid thead>tr>td tr:last-child td {
            text-align: center;
        }
        .fc .fc-timegrid thead>tr>td {
            border-bottom: 3px solid #ddd;
        }
        .fc .fc-timegrid thead td .fc-scroller {
            overflow: hidden !important;
        }
        .fc .fc-scrollgrid table {
            border-right-style: solid;
            border-bottom-style: hidden;
        }
        .fc .fc-button-group button:not(:last-child) {
            border-top-right-radius: 0px;
            border-bottom-right-radius: 0px;
        }
        .fc .fc-button-group button:not(:first-child) {
            margin-left: -1px;
            border-top-left-radius: 0px;
            border-bottom-left-radius: 0px;
        }
        .fc .fc-day-today {
            background-color: #fffadf;
        }
        .fc .fc-event-note.no-data {
            background-color: #f3f3f3;
        }
        .fc .fc-event-note>div {
            padding: 2px;
            min-height: 60px;
            overflow: hidden;
        }`;

    @component({
        name: 'fc-events',
        template: `<td data-bind="i18n: '勤怠時間'"></td>
        <!-- ko foreach: [0,1,2,3,4,5,6] -->
        <td class="fc-event-note">
            <div>
                <div>勤怠　8:00 ~ 17:00</div>
                <div>休憩　1:00</div>
                <div>総労働時間　8:00</div>
            </div>
        </td>
        <!-- /ko -->`
    })
    export class FullCalendarEventHeaderComponent extends ko.ViewModel {

    }

    type FullCalendarTime = {
        date: string;
        value: number;
    }

    @component({
        name: 'fc-times',
        template: `<td data-bind="i18n: '作業時間'"></td>
        <!-- ko foreach: [0,1,2,3,4,5,6] -->
        <td>00:00</td>
        <!-- /ko -->`
    })
    export class FullCalendarTimesHeaderComponent extends ko.ViewModel {
        constructor(private data: KnockoutObservableArray<FullCalendarTime>) {
            super();

            if (!this.data) {
                this.data = ko.observableArray([]);
            }
        }

        created() {

        }
    }

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: false,
        virtual: false
    })
    export class FullCalendarBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const params = {};
            const component = { name, params };

            ko.applyBindingsToNode(element, { component }, bindingContext);

            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }

    type J_EVENT = (evt: JQueryEventObject) => void;
    type G_EVENT = { [key: string]: J_EVENT[]; };

    @component({
        name: COMPONENT_NAME,
        template: `<div></div><style>${DEFAULT_STYLES}</style><style></style>`
    })
    export class FullCalendarComponent extends ko.ViewModel {
        events: G_EVENT = {};

        created() {

        }

        mounted() {
            const vm = this;
            const $el = $(vm.$el);
            const FC: FullCalendar = _.get(window, 'FullCalendar') || null;

            if (!FC || !FC.Calendar) {
                $el.html(`<pre>${_.escape('Please add 2 tag at below to htmlHead ui defined:\n<com:stylefile set="FULLCALENDAR" />\n<com:scriptfile set="FULLCALENDAR" />')}</pre>`);

                return;
            }

            const calendar = new FC.Calendar($el.find('div').get(0), {
                customButtons: {
                    copyDay: {
                        text: '1日分コピー',
                        click: function () {
                            alert('clicked the custom button!');
                        }
                    }
                },
                height: 'auto',
                locale: 'ja',
                expandRows: true,
                // slotMinTime: '08:00',
                // slotMaxTime: '20:00',
                headerToolbar: {
                    left: 'today prev,next',
                    center: 'title',
                    right: 'copyDay'
                },
                themeSystem: 'default',
                initialView: 'timeGridWeek',
                initialDate: '2020-09-12',
                navLinks: true, // can click day/week names to navigate views
                editable: true,
                selectable: true,
                nowIndicator: true,
                dayMaxEvents: true, // allow "more" link when too many events
                scrollTime: '07:00:00',
                allDaySlot: false,
                allDayContent: 'abc',
                dayHeaders: true,
                // weekends: false,
                // hiddenDays: [5, 6],
                dayHeaderFormat: {
                    weekday: 'short',
                    day: 'numeric',
                    omitCommas: true
                },
                dayHeaderContent: (opts: any) => {
                    return moment(opts.date).format('DD(ddd)');
                },
                slotDuration: '00:30:00',
                slotLabelInterval: '01:00',
                slotLabelFormat: {
                    hour: 'numeric',
                    meridiem: false
                },
                eventClick: (evt: any) => {
                    evt.event.remove();
                },
                eventDragStart: (evt) => {
                    console.log(evt);
                },
                eventDrop: (evt: any) => {
                    if (evt.event.allDay) {
                        evt.revert();
                    }
                },
                eventOverlap: (stillEvent) => stillEvent.allDay,
                slotEventOverlap: false,
                // eventBackgroundColor: '#ccc',
                // eventBorderColor: '#ddd',
                slotLabelContent: (opts: any) => {
                    const min = opts.time.milliseconds / 1000 / 60;
                    const hour = Math.floor(min / 60);
                    const minite = Math.floor(min % 60);

                    return `${hour}:${_.padStart(`${minite}`, 2, '0')}`;
                },
                dayCellDidMount: (evt: any) => {
                    // console.log(evt);
                },
                allDayDidMount: (evt: any) => {
                    // console.log(evt.el);
                },
                datesSet: (dateInfo) => {
                    console.log(dateInfo);
                },
                viewDidMount: (opts) => {
                    $('.fc-header-toolbar button').removeAttr('class');

                    if (opts.view.type !== 'timeGridWeek') {
                        return false;
                    }

                    const header = $(opts.el).find('thead tbody');

                    if (header.length) {
                        const evts = document.createElement('tr');
                        const times = document.createElement('tr');

                        header.append(evts);
                        header.append(times);

                        ko.applyBindingsToNode(evts, { component: { name: 'fc-events', params: {} } });
                        ko.applyBindingsToNode(times, { component: { name: 'fc-times', params: {} } });
                    }

                    //$el.find('style').first().html(DEFAULT_STYLE);
                },
                slotLaneDidMount: (evt: any) => {
                    // console.log(evt);
                },
                handleWindowResize: true,
                windowResize: (opts) => {
                    console.log(opts.view);
                },
                firstDay: 1,
                events: [
                    {
                        title: 'All Day Event',
                        start: '2020-09-07T16:00:00',
                        allDay: true,
                        color: 'transparent',
                        textColor: '#000',
                        editable: false
                    },
                    {
                        title: 'Long Event',
                        start: '2020-09-07',
                        color: 'transparent',
                        textColor: '#000',
                        editable: false
                    },
                    {
                        groupId: '999',
                        title: 'Repeating Event',
                        start: '2020-09-09T16:00:00'
                    },
                    {
                        groupId: '999',
                        title: 'Repeating Event',
                        start: '2020-09-16T16:00:00'
                    },
                    {
                        title: 'Conference',
                        start: '2020-09-11',
                        end: '2020-09-13',
                        editable: false
                    },
                    {
                        groupId: 'abc',
                        title: 'Meeting',
                        start: '2020-09-12T10:30:00',
                        end: '2020-09-12T12:00:00'
                    },
                    {
                        groupId: 'abc',
                        title: 'Lunch',
                        start: '2020-09-12T13:00:00',
                        end: '2020-09-12T14:30:00'
                    }, {
                        groupId: 'abc',
                        start: '2020-09-12T10:30:00',
                        end: '2020-09-12T14:30:00',
                        display: 'background',
                        backgroundColor: 'transparent'
                    },
                    {
                        title: 'Meeting',
                        start: '2020-09-12T14:30:00',
                    },
                    {
                        title: 'Happy Hour',
                        start: '2020-09-12T17:30:00'
                    },
                    {
                        title: 'Dinner',
                        start: '2020-09-12T20:00:00'
                    },
                    {
                        title: 'Birthday Party',
                        start: '2020-09-13T07:00:00'
                    },
                    {
                        title: 'Click for Google',
                        url: 'http://google.com/',
                        start: '2020-09-28',
                    },
                    // areas where "Meeting" must be dropped
                    /*{
                        groupId: 'availableForMeeting',
                        start: '2020-09-07T10:00:00',
                        end: '2020-09-07T16:00:00',
                        display: 'background'
                    },
                    {
                        groupId: 'availableForMeeting',
                        start: '2020-09-09T10:00:00',
                        end: '2020-09-09T16:00:00',
                        display: 'background',
                        backgroundColor: '#ddd'
                    },*/
                ],
                /*validRange: {
                    start: '2020-11-06T05:00',
                    end: '2020-11-06T06:00'
                },*/
                businessHours: [{
                    // days of week. an array of zero-based day of week integers (0=Sunday)
                    daysOfWeek: [1, 2, 3, 4, 5], // Monday - Thursday
                    startTime: '08:30', // a start time (10am in this example)
                    endTime: '12:00', // an end time (6pm in this example)
                }, {
                    // days of week. an array of zero-based day of week integers (0=Sunday)
                    daysOfWeek: [1, 2, 3, 4, 5], // Monday - Thursday
                    startTime: '13:00', // a start time (10am in this example)
                    endTime: '17:30', // an end time (6pm in this example)
                }, {
                    daysOfWeek: [0, 6],
                    startTime: '00:00',
                    endTime: '00:00'
                }],
                select: (arg) => {
                    calendar.addEvent(arg);
                },
                selectOverlap: (evt) => evt.allDay,
                selectAllow: (evt) => {
                    // const time = new Date().getTime();
                    return evt.start.getDate() === evt.end.getDate();/* &&
                                ((evt.start.getTime() < time && evt.end.getTime() < time) ||
                                    (evt.start.getTime() > time && evt.end.getTime() > time));*/
                }
            });

            calendar.render();

            // calendar.addEvent({ title: 'Vuong Dep trai', start: '2020-09-07', allDay: false })

            // Object.assign(window, { calendar });

            // calendar.setOption('slotDuration', '00:30:00');

            calendar.setOption('height', '700px');

            vm.registerEvent('keydown', (evt: JQueryEventObject) => {
                if (evt.keyCode === 17) {
                    document.body.setAttribute('ctrl', 'true');
                }
            });

            vm.registerEvent('keyup', (evt: JQueryEventObject) => {
                if (evt.keyCode === 17) {
                    document.body.setAttribute('ctrl', 'false');
                }
            });

            $el.on('mousewheel', (evt) => {
                if (document.body.getAttribute('ctrl') === 'true') {
                    evt.preventDefault();
                }
            });
        }

        registerEvent(name: string, cb: (evt: JQueryEventObject) => void) {
            const vm = this;
            let hook = vm.events[name];

            if (!hook) {
                hook = vm.events[name] = [];
            }

            hook.push(cb);

            $(window).on(name, cb);
        }

        destroyed() {
            const vm = this;
            const { events } = vm;

            _.each(events, (evts: J_EVENT[], k: string) => {
                _.each(evts, (h: J_EVENT) => $(window).off(k, h))
            });
        }
    }
}