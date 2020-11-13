/// <reference path="../../generic.d.ts/fullcalendar/index.d.ts" />

module nts.uk.ui.koExtentions {
    const E_COMP_NAME = 'fc-editor';
    const COMPONENT_NAME = 'nts-fullcalendar';
    const DEFAULT_STYLES = `
        .fc-container {
            position: relative;
            overflow: hidden;
        }
        .fc-container .fc-toolbar.fc-header-toolbar {
            margin-bottom: 10px;
        }
        .fc-container .fc-timegrid thead>tr>td td:first-child {
            font-size: 11px;
            vertical-align: middle;
        }
        .fc-container .fc-timegrid thead>tr>td td:first-child,
        .fc-container .fc-timegrid thead>tr>td tr:last-child td {
            text-align: center;
        }
        .fc-container .fc-timegrid thead>tr>td {
            border-bottom: 3px solid #ddd;
        }
        .fc-container .fc-timegrid-body {
            -webkit-touch-callout: none; /* iOS Safari */
            -webkit-user-select: none; /* Safari */
            -khtml-user-select: none; /* Konqueror HTML */
            -moz-user-select: none; /* Old versions of Firefox */
            -ms-user-select: none; /* Internet Explorer/Edge */
            user-select: none; /* Non-prefixed version, currently
                                        supported by Chrome, Edge, Opera and Firefox */
        }
        .fc-container:not(.ie) .fc-timegrid thead td .fc-scroller {
            overflow: hidden !important;
        }
        .fc-container .fc-scrollgrid table {
            border-right-style: solid;
            border-bottom-style: hidden;
        }
        .fc-container .fc-button-group button:not(:last-child) {
            border-top-right-radius: 0px;
            border-bottom-right-radius: 0px;
        }
        .fc-container .fc-button-group button:not(:first-child) {
            margin-left: -1px;
            border-top-left-radius: 0px;
            border-bottom-left-radius: 0px;
        }
        .fc-container .fc-day-today {
            background-color: #fffadf;
        }
        .fc-container .fc-event-note.no-data {
            background-color: #f3f3f3;
        }
        .fc-container .fc-event-note>div {
            padding: 2px;
            min-height: 60px;
            overflow: hidden;
        }
        .fc-container .fc-popup-editor {
            position: fixed;
            border-radius: 3px;
            -webkit-box-shadow: 1px 1px 5px 2px #888;
            box-shadow: 1px 1px 5px 2px #888;
            width: 0px;
            height: 0px;
            background-color: #fff;
            z-index: 99;
            visibility: hidden;
            -webkit-transition: opacity 200ms ease-in-out;
            transition: opacity 200ms ease-in-out;
            opacity: 0;
            box-sizing: border-box;
        }
        .fc-container .fc-popup-editor.show {
            overflow: hidden;
            visibility: visible;
            width: 250px;
            height: 270px;
            padding: 10px;
            opacity: 1;
        }
        .fc-container .fc-popup-editor .toolbar {
            text-align: right;
        }
        .fc-container .fc-popup-editor .toolbar svg {
            fill: #5f6368;
        }
        .fc-container .fc-popup-editor .toolbar svg:last-child {
            margin-left: 10px;
        }
        .fc-container .fc-popup-editor .toolbar svg:not(:last-child) {
            margin-right: 10px;
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

            element.classList.add('fc-container');
            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }

    type D_EVENT = {
        alt: boolean;
        ctrl: boolean;
        shift: boolean;
        popup: boolean;
        [key: string]: any;
    };
    type J_EVENT = (evt: JQueryEventObject) => void;
    type G_EVENT = { [key: string]: J_EVENT[]; };

    type PARAMS = {
        shiftSelect: boolean | KnockoutObservable<boolean>;
    }

    @component({
        name: COMPONENT_NAME,
        template: `<div class="fc"></div>
        <div class="fc-popup-editor"></div>
        <style>${DEFAULT_STYLES}</style>
        <style></style>`
    })
    export class FullCalendarComponent extends ko.ViewModel {
        events: G_EVENT = {};
        dataEvent: D_EVENT = {
            alt: false,
            ctrl: false,
            shift: false,
            popup: false
        };

        constructor(private params: PARAMS) {
            super();

            if (!params) {
                this.params = {
                    shiftSelect: ko.observable(false)
                };
            }

            const { shiftSelect } = this.params;

            if (shiftSelect === undefined) {
                this.params.shiftSelect = ko.observable(false)
            }
        }

        created() {
            const vm = this;
        }

        mounted() {
            const vm = this;
            const { params, dataEvent } = vm;

            const $el = $(vm.$el);
            const $fc = $el.find('div.fc').get(0);
            const $ed = $el.find('div.fc-popup-editor').get(0);
            const FC: FullCalendar = _.get(window, 'FullCalendar') || null;

            const hideEditor = (target?: Element) => {
                const $target = $(target);

                if (!$target.is($ed) && !$(target).closest('.fc-popup-editor.show').length) {
                    $ed.style.top = null;
                    $ed.style.left = null;

                    $ed.classList.remove('show');
                }

                dataEvent.popup = false;
            };

            if (!FC || !FC.Calendar) {
                const pre = document.createElement('pre');

                ko.applyBindingsToNode(pre, {
                    prettify: 'xml',
                    code: `<com:stylefile set="FULLCALENDAR" />\n<com:scriptfile set="FULLCALENDAR" />`
                });

                $el.append('Please add 2 tag at below to htmlHead ui defined:');
                $el.append(pre);

                return;
            }

            const { version } = nts.uk.util.browser;

            if (version.match(/IE/)) {
                $el.addClass('ie');
            }

            const calendar = new FC.Calendar($fc, {
                customButtons: {
                    copyDay: {
                        text: '1日分コピー',
                        click: function () {
                            alert('clicked the custom button!');
                        }
                    }
                },
                height: '700px',
                locale: 'ja',
                headerToolbar: {
                    left: 'today prev,next',
                    center: 'title',
                    right: 'copyDay'
                },
                themeSystem: 'default',
                initialView: 'timeGridWeek',
                // navLinks: true, // can click day/week names to navigate views
                editable: true,
                selectable: false,
                selectMirror: true,
                selectMinDistance: 15,
                nowIndicator: true,
                dayMaxEvents: true, // allow "more" link when too many events
                scrollTime: '07:00:00',
                dayHeaders: true,
                allDaySlot: false,
                // weekends: false,
                // hiddenDays: [5, 6],
                dayHeaderContent: (opts: any) => {
                    return moment(opts.date).format('DD(ddd)');
                },
                slotDuration: '00:30:00',
                slotLabelInterval: '01:00',
                eventDidMount: (arg) => {
                    const { id, title, groupId } = arg.event;

                    // draw new event
                    if (!id && !groupId && !title) {
                        calendar.trigger('eventClick', {
                            el: arg.el,
                            event: arg.event,
                            jsEvent: null,
                            view: calendar.view
                        });
                    }
                },
                eventClick: (evt) => {
                    if ($ed && evt.el) {
                        if (dataEvent.ctrl) {
                            return;
                        }

                        const bounds = evt.el.getBoundingClientRect();

                        if (bounds) {
                            const { innerHeight } = window;
                            const { top, left, right } = bounds;

                            $ed.classList.add('show');

                            if (left - 263 <= 0) {
                                $ed.style.left = `${right + 3}px`;
                            } else {
                                $ed.style.left = `${left - 253}px`;
                            }

                            if (innerHeight - top - 280 > 0) {
                                $ed.style.top = `${top}px`;
                            } else {
                                $ed.style.top = `${innerHeight - 280}px`;
                            }

                            ko.applyBindingsToNode($ed, { [E_COMP_NAME]: evt.event });

                            dataEvent.popup = true;
                        }
                    }
                },
                eventDragStart: (evt) => {
                    console.log(evt);
                },
                eventDrop: (evt) => {
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
                },
                events: [],
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
                    if (ko.unwrap(params.shiftSelect) ? dataEvent.shift : true) {
                        calendar.unselect();
                        calendar.addEvent(arg);
                    }
                },
                selectOverlap: (evt) => evt.allDay,
                selectAllow: (evt) => {
                    return evt.start.getDate() === evt.end.getDate();
                }
            });

            calendar.render();

            ko.computed({
                read: () => {
                    calendar.setOption('selectable', !ko.unwrap(params.shiftSelect));
                },
                disposeWhenNodeIsRemoved: vm.$el
            });
            // calendar.setOption('slotDuration', '00:30:00');

            const fce = calendar.el.getBoundingClientRect();

            if (fce) {
                const { top } = fce;
                const { innerHeight } = window;

                calendar.setOption('height', `${innerHeight - top - 10}px`);
            }

            vm.registerEvent('resize', () => {
                calendar.updateSize();
            });

            vm.registerEvent('mousedown', (evt: JQueryEventObject) => hideEditor(evt.target));

            vm.registerEvent('keydown', (evt: JQueryEventObject) => {
                if (evt.keyCode === 16) {
                    vm.dataEvent.shift = true;

                    if (ko.unwrap(params.shiftSelect)) {
                        calendar.setOption('selectable', true);
                    }

                    document.body.setAttribute('shift', 'true');
                } else if (evt.keyCode === 17) {
                    vm.dataEvent.ctrl = true;

                    document.body.setAttribute('ctrl', 'true');
                } else if (evt.keyCode === 18) {
                    vm.dataEvent.alt = true;

                    document.body.setAttribute('alt', 'true');
                }
            });

            vm.registerEvent('keyup', (evt: JQueryEventObject) => {
                if (evt.keyCode === 16) {
                    vm.dataEvent.shift = false;

                    if (ko.unwrap(params.shiftSelect)) {
                        calendar.setOption('selectable', false);
                    }

                    document.body.setAttribute('shift', 'false');
                } else if (evt.keyCode === 17) {
                    vm.dataEvent.ctrl = false;

                    document.body.setAttribute('ctrl', 'false');
                } else if (evt.keyCode === 18) {
                    vm.dataEvent.alt = false;

                    document.body.setAttribute('alt', 'false');
                }
            });

            $el.on('mousewheel', (evt) => {
                if (vm.dataEvent.ctrl === true || vm.dataEvent.popup === true) {
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


    export module components {
        @handler({
            bindingName: E_COMP_NAME,
            validatable: true,
            virtual: false
        })
        export class FullCalendarEditorBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => fc.EventApi, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = E_COMP_NAME;
                const component = { name, params: valueAccessor() };

                ko.applyBindingsToNode(element, { component }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: E_COMP_NAME,
            template: `<div class="toolbar">
                <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Email">
                    <path d="M20 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm-.8 2L12 10.8 4.8 6h14.4zM4 18V7.87l8 5.33 8-5.33V18H4z"></path>
                </svg>
                <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Edit" data-bind="click: edit, timeClick: -1">
                    <path d="M20.41 4.94l-1.35-1.35c-.78-.78-2.05-.78-2.83 0L3 16.82V21h4.18L20.41 7.77c.79-.78.79-2.05 0-2.83zm-14 14.12L5 19v-1.36l9.82-9.82 1.41 1.41-9.82 9.83z"></path>
                </svg>
                <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Remove" data-bind="click: remove, timeClick: -1">
                    <path d="M15 4V3H9v1H4v2h1v13c0 1.1.9 2 2 2h10c1.1 0 2-.9 2-2V6h1V4h-5zm2 15H7V6h10v13z"></path>
                    <path d="M9 8h2v9H9zm4 0h2v9h-2z"></path>
                </svg>
                <svg width="20" height="20" viewBox="0 0 24 24" focusable="false" title="Close" data-bind="click: $component.close, timeClick: -1">
                    <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z"></path>
                </svg>
            </div>
            <div>
            </div>`
        })
        export class FullCalendarEditorComponent extends ko.ViewModel {
            constructor(private data: fc.EventApi) {
                super();
            }

            mounted() {
                const vm = this;

                $(vm.$el).find('[data-bind]').removeAttr('data-bind');
            }

            edit() {
                const vm = this;
                const { data } = vm;

                console.log(data);

                vm.unbind();
            }

            remove() {
                const vm = this;
                const { data } = vm;

                if (data) {
                    data.remove();
                }

                vm.unbind();
            }

            close() {
                const vm = this;
                const { data } = vm;

                if (data) {
                    const { id, groupId, title } = data;

                    if (!id && !groupId && !title) {
                        data.remove();
                    }
                }

                vm.unbind();
            }

            unbind() {
                const vm = this;
                const { $el } = vm;

                if ($el) {
                    ko.cleanNode($el);

                    $el.classList.remove('show');

                    $el.style.top = null;
                    $el.style.left = null;

                    $el.innerHTML = '';
                }
            }
        }
    }
}


/*{
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