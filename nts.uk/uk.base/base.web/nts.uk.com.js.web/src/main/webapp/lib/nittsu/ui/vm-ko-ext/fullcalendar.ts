/// <reference path="../../generic.d.ts/fullcalendar/index.d.ts" />

module nts.uk.ui.koExtentions {
    const { version } = nts.uk.util.browser;

    const C_COMP_NAME = 'fc-copy';
    const E_COMP_NAME = 'fc-editor';
    const COMPONENT_NAME = 'fullcalendar';
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
        .fc-container .fc-button-group button:focus {
            z-index: 2;
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
        .fc-container .fc-timegrid-slot-label-bold {
            font-weight: bold;
        }
        .fc-container .fc-timegrid-slot-lane-even,
        .fc-container .fc-timegrid-slot-label-even {
            background-color: #d9e3f4;
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
        }
        .fc-container .fc-one-day-button.active,
        .fc-container .fc-five-day-button.active,
        .fc-container .fc-full-week-button.active,
        .fc-container .fc-full-month-button.active,
        .fc-container .fc-list-week-button.active {
            color: #fff;
            background-color: #00B050;
        }`;

    @component({
        name: 'fc-events',
        template: `<td data-bind="i18n: '勤怠時間'"></td>
        <!-- ko foreach: { data: $component.data, as: 'day' } -->
        <td class="fc-event-note" data-bind="css: { 'no-data': !day.length }">
            <div data-bind="foreach: { data: day, as: 'note' }">
                <div data-bind="text: note"></div>
            </div>
        </td>
        <!-- /ko -->`
    })
    export class FullCalendarEventHeaderComponent extends ko.ViewModel {
        constructor(private data: KnockoutComputed<string[][]>) {
            super();

            if (!this.data) {
                this.data = ko.computed(() => []);
            }
        }

        created() {

        }

        mounted() {
            const vm = this;
            const { $el, data } = vm;

            ko.computed({
                read: () => {
                    const ds = ko.unwrap(data);

                    if (ds.length) {
                        $el.style.display = null;
                    } else {
                        $el.style.display = 'none';
                    }

                    $($el).find('[data-bind]').removeAttr('data-bind');
                },
                disposeWhenNodeIsRemoved: $el
            });
        }
    }

    @component({
        name: 'fc-times',
        template: `<td data-bind="i18n: '作業時間'"></td>
        <!-- ko foreach: { data: $component.data, as: 'time' } -->
        <td data-bind="text: $component.formatTime(time)"></td>
        <!-- /ko -->`
    })
    export class FullCalendarTimesHeaderComponent extends ko.ViewModel {
        constructor(private data: KnockoutComputed<number[]>) {
            super();

            if (!this.data) {
                this.data = ko.computed(() => []);
            }
        }

        mounted() {
            const vm = this;
            const { $el, data } = vm;

            ko.computed({
                read: () => {
                    const ds = ko.unwrap(data);

                    if (ds.length) {
                        $el.style.display = null;
                    } else {
                        $el.style.display = 'none';
                    }

                    $($el).find('[data-bind]').removeAttr('data-bind');
                },
                disposeWhenNodeIsRemoved: $el
            });
        }

        formatTime(time: number) {
            const hour = Math.floor(time / 60);
            const minute = Math.floor(time % 60);

            return `${hour}:${_.padStart(`${minute}`, 2, '0')}`;
        }
    }

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: false,
        virtual: false
    })
    export class FullCalendarBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => KnockoutObservableArray<fc.EventApi>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;

            const events = valueAccessor();

            const event = allBindingsAccessor.get('event');
            const locale = allBindingsAccessor.get('locale');
            const weekends = allBindingsAccessor.get('weekends');
            const editable = allBindingsAccessor.get('editable');
            const firstDay = allBindingsAccessor.get('firstDay');
            const scrollTime = allBindingsAccessor.get('scrollTime');
            const initialDate = allBindingsAccessor.get('initialDate');
            const initialView = allBindingsAccessor.get('initialView');
            const slotDuration = allBindingsAccessor.get('slotDuration');
            const businessHours = allBindingsAccessor.get('businessHours');
            const attendanceTimes = allBindingsAccessor.get('attendanceTimes');

            const params = { events, event, locale, initialDate, initialView, scrollTime, weekends, editable, firstDay, slotDuration, attendanceTimes, businessHours, viewModel };
            const component = { name, params };

            ko.applyBindingsToNode(element, { component }, bindingContext);

            element.classList.add('fc-container');
            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }

    type D_EVENT = {
        alt: KnockoutObservable<boolean>;
        ctrl: KnockoutObservable<boolean>;
        shift: KnockoutObservable<boolean>;
        popup: KnockoutObservable<boolean>;
        mouse: KnockoutObservable<boolean>;
        event: KnockoutObservable<null | fc.EventApi>;
        [key: string]: KnockoutObservable<any>;
    };
    type J_EVENT = (evt: JQueryEventObject) => void;
    type G_EVENT = { [key: string]: J_EVENT[]; };

    type LOCALE = 'en' | 'ja' | 'vi';
    type SLOT_DURATION = 5 | 10 | 15 | 30;
    const durations: SLOT_DURATION[] = [5, 10, 15, 30];

    enum DAY_OF_WEEK {
        SUN = 0,
        MON = 1,
        TUE = 2,
        WED = 3,
        THU = 4,
        FRI = 5,
        SAT = 6
    }

    type BUSINESSHOUR = {
        daysOfWeek: DAY_OF_WEEK[];
        startTime: number;
        endTime: number;
    };

    type ATTENDANCE_TIME = {
        date: Date;
        events: string[];
    };

    type INITIAL_VIEW = 'oneDay' | 'fiveDay' | 'listWeek' | 'fullWeek' | 'fullMonth';

    type PARAMS = {
        viewModel: any;
        events: fc.EventApi[] | KnockoutObservableArray<fc.EventApi>;
        locale: LOCALE | KnockoutObservable<LOCALE>;
        initialView: INITIAL_VIEW | KnockoutObservable<INITIAL_VIEW>;
        initialDate: Date | KnockoutObservable<Date>;
        scrollTime: number | KnockoutObservable<number>;
        slotDuration: SLOT_DURATION | KnockoutObservable<SLOT_DURATION>;
        weekends: boolean | KnockoutObservable<boolean>;
        editable: boolean | KnockoutObservable<boolean>;
        firstDay: DAY_OF_WEEK | KnockoutObservable<DAY_OF_WEEK>;
        attendanceTimes: ATTENDANCE_TIME[] | KnockoutObservableArray<ATTENDANCE_TIME>;
        businessHours: BUSINESSHOUR[] | KnockoutObservableArray<BUSINESSHOUR>;
        event: {
            coppyDay: (from: Date, to: Date) => void;
            datesSet: (start: Date, end: Date) => void;
        };
    };

    type DATES_SET = {
        start: Date;
        end: Date;
    };

    const formatDate = (date: Date) => moment(date).format('YYYY-MM-DDTHH:mm:00');
    const formatTime = (time: number) => {
        const f = Math.floor;
        const times = [f(time / 60), f(time % 60), 0]

        return times
            .map((m) => m.toString())
            .map((m) => _.padStart(m, 2, '0'))
            .join(':')
    };
    const activeClass = (t: EventTarget) => {
        $(t)
            .closest('.fc-button-group')
            .find('button')
            .removeClass('active');

        $(t).addClass('active');
    };

    const defaultDEvent = (): D_EVENT => ({
        alt: ko.observable(false),
        ctrl: ko.observable(false),
        shift: ko.observable(false),
        popup: ko.observable(false),
        mouse: ko.observable(false),
        event: ko.observable(null)
    });

    @component({
        name: COMPONENT_NAME,
        template: `<div class="fc"></div>
        <div data-bind="fc-copy"></div>
        <div data-bind="fc-editor: $component.dataEvent.event"></div>
        <style>${DEFAULT_STYLES}</style>
        <style></style>`
    })
    export class FullCalendarComponent extends ko.ViewModel {
        // stored all global events
        public events: G_EVENT = {};
        // stored glodal date events
        public dataEvent: D_EVENT = defaultDEvent();

        constructor(private params: PARAMS) {
            super();

            if (!params) {
                this.params = {
                    viewModel: this,
                    scrollTime: ko.observable(360),
                    locale: ko.observable('ja'),
                    firstDay: ko.observable(1),
                    slotDuration: ko.observable(30),
                    weekends: ko.observable(true),
                    editable: ko.observable(false),
                    initialView: ko.observable('fullWeek'),
                    initialDate: ko.observable(new Date()),
                    events: ko.observableArray([]),
                    attendanceTimes: ko.observableArray([]),
                    businessHours: ko.observableArray([]),
                    event: {
                        coppyDay: (__: Date, ___: Date) => { },
                        datesSet: (__: Date, ___: Date) => { }
                    }
                };
            }

            const { locale, event, events, scrollTime, initialDate, initialView, weekends, editable, firstDay, slotDuration, attendanceTimes, businessHours } = this.params;

            if (locale === undefined) {
                this.params.locale = ko.observable('ja');
            }

            if (scrollTime === undefined) {
                this.params.scrollTime = ko.observable(360);
            }

            if (initialDate === undefined) {
                this.params.initialDate = ko.observable(new Date());
            }

            if (initialView === undefined) {
                this.params.initialView = ko.observable('fullWeek');
            }

            if (weekends === undefined) {
                this.params.weekends = ko.observable(true);
            }

            if (editable === undefined) {
                this.params.editable = ko.observable(false);
            }

            if (firstDay === undefined) {
                this.params.firstDay = ko.observable(1);
            }

            if (slotDuration === undefined) {
                this.params.slotDuration = ko.observable(30);
            }

            if (events === undefined) {
                this.params.events = ko.observableArray([]);
            }

            if (attendanceTimes === undefined) {
                this.params.attendanceTimes = ko.observableArray([]);
            }

            if (businessHours === undefined) {
                this.params.businessHours = ko.observableArray([]);
            }

            if (event === undefined) {
                this.params.event = {
                    coppyDay: (__: Date, ___: Date) => { },
                    datesSet: (__: Date, ___: Date) => { }
                };
            }

            const { coppyDay, datesSet } = event;

            if (coppyDay === undefined) {
                this.params.event.coppyDay = (__: Date, ___: Date) => { };
            }

            if (datesSet === undefined) {
                this.params.event.datesSet = (__: Date, ___: Date) => { };
            }
        }

        public mounted() {
            const vm = this;
            const { params, dataEvent } = vm;
            const { locale, event, events, scrollTime, firstDay, weekends, editable, initialDate, initialView, viewModel, attendanceTimes } = params;

            const $el = $(vm.$el);
            const $fc = $el.find('div.fc').get(0);
            const FC: FullCalendar | null = _.get(window, 'FullCalendar') || null;
            const mappedEvents = (events: fc.EventApi[] | KnockoutObservableArray<fc.EventApi>) => {
                const wraped = ko.unwrap(events) as fc.EventApi[];

                return wraped.map(m => ({
                    ...m,
                    start: formatDate(m.start),
                    end: formatDate(m.end)
                }));
            };
            const updateActive = () => {
                $el
                    .find('.fc-header-toolbar button')
                    .each((__, e) => {
                        e.classList.remove('active');
                        e.classList.remove('fc-button');
                        e.classList.remove('fc-button-primary');

                        const view = ko.unwrap(initialView);

                        switch (view) {
                            case 'oneDay':
                                if (e.className.match(/one-day/)) {
                                    e.classList.add('active');
                                }
                                break;
                            case 'fiveDay':
                                if (e.className.match(/five-day/)) {
                                    e.classList.add('active');
                                }
                                break;
                            default:
                            case 'fullWeek':
                                if (e.className.match(/full-week/)) {
                                    e.classList.add('active');
                                }
                                break;
                            case 'fullMonth':
                                if (e.className.match(/full-month/)) {
                                    e.classList.add('active');
                                }
                                break;
                            case 'listWeek':
                                if (e.className.match(/list-week/)) {
                                    e.classList.add('active');
                                }
                                break;
                        }
                    });
            };

            const datesSet: KnockoutObservable<DATES_SET | null> = ko.observable(null);

            const timesSet: KnockoutComputed<number[]> = ko.computed({
                read() {
                    const ds = ko.unwrap(datesSet);
                    const evts: fc.EventApi[] = ko.unwrap(events) as any;

                    if (ds) {
                        const { start, end } = ds;

                        const first = moment(start);
                        const diff: number = moment(end).diff(start, 'day');

                        return _.range(0, diff, 1).map(m => {
                            const date = first.clone().add(m, 'day');
                            const exists = _.filter(evts, (d: fc.EventApi) => {
                                return !d.allDay &&
                                    d.display !== 'background' &&
                                    date.isSame(d.start, 'date');
                            });

                            return exists.reduce((p, c) => p += moment(c.end).diff(c.start, 'minute'), 0);
                        });
                    }

                    return [] as number[];
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            const attendancesSet: KnockoutComputed<string[][]> = ko.computed({
                read: () => {
                    const ds = ko.unwrap(datesSet);
                    const ads: ATTENDANCE_TIME[] = ko.unwrap(attendanceTimes) as any;

                    if (ds) {
                        const { start, end } = ds;

                        const first = moment(start);
                        const diff: number = moment(end).diff(start, 'day');

                        return _.range(0, diff, 1)
                            .map(m => {
                                const date = first.clone().add(m, 'day');
                                const exist = _.find(ads, (d: ATTENDANCE_TIME) => date.isSame(d.date, 'date'));

                                if (exist) {
                                    return exist.events;
                                }

                                return [];
                            });
                    }

                    return [] as string[][];
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            const initialViewComputed = ko.computed({
                read: () => {
                    const iv = ko.unwrap(initialView);

                    updateActive();

                    switch (iv) {
                        case 'oneDay':
                            return 'timeGridDay';
                        default:
                        case 'fiveDay':
                            if (ko.isObservable(weekends)) {
                                weekends(false);
                            } else if (calendar) {
                                calendar.setOption('weekends', false);
                            }
                            return 'timeGridWeek';
                        case 'fullWeek':
                            if (ko.isObservable(weekends)) {
                                weekends(true);
                            } else if (calendar) {
                                calendar.setOption('weekends', true);
                            }
                            return 'timeGridWeek';
                        case 'fullMonth':
                            return 'dayGridMonth';
                        case 'listWeek':
                            return 'listWeek';
                    }
                },
                disposeWhenNodeIsRemoved: vm.$el
            })

            if (version.match(/IE/)) {
                $el.addClass('ie');
            }

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

            dataEvent.alt
                .subscribe((c) => {
                    $el.attr('alt', c + '');
                });

            dataEvent.ctrl
                .subscribe((c) => {
                    $el.attr('ctrl', c + '');
                });

            dataEvent.shift
                .subscribe((c) => {
                    $el.attr('shift', c + '');
                });

            dataEvent.alt.valueHasMutated();
            dataEvent.ctrl.valueHasMutated();
            dataEvent.shift.valueHasMutated();

            const calendar = new FC.Calendar($fc, {
                customButtons: {
                    'copy-day': {
                        text: '1日分コピー',
                        click: (evt) => {
                            const btn = evt.target as HTMLElement;

                            if (ko.unwrap(editable) === true) {
                                event.coppyDay.apply(viewModel, [(new Date(), new Date())]);
                            }
                        }
                    },
                    'one-day': {
                        text: '日',
                        click: (evt) => {
                            if (calendar.view.type !== 'timeGridDay') {
                                activeClass(evt.target);

                                if (ko.isObservable(weekends)) {
                                    weekends(true);
                                } else {
                                    calendar.setOption('weekends', true);
                                }

                                if (ko.isObservable(initialView)) {
                                    initialView('oneDay');
                                } else {
                                    calendar.changeView('timeGridDay');
                                }
                            }
                        }
                    },
                    'five-day': {
                        text: '稼働日',
                        click: (evt) => {
                            if (calendar.view.type !== 'timeGridWeek' || ko.unwrap(weekends) !== false) {
                                activeClass(evt.target);

                                if (ko.isObservable(weekends)) {
                                    weekends(false);
                                } else {
                                    calendar.setOption('weekends', false);
                                }

                                if (ko.isObservable(initialView)) {
                                    initialView('fiveDay');
                                } else {
                                    calendar.changeView('timeGridWeek');
                                }
                            }
                        }
                    },
                    'full-week': {
                        text: '週',
                        click: (evt) => {
                            if (calendar.view.type !== 'timeGridWeek' || ko.unwrap(weekends) !== true) {
                                activeClass(evt.target);

                                if (ko.isObservable(weekends)) {
                                    weekends(true);
                                } else {
                                    calendar.setOption('weekends', true);
                                }

                                if (ko.isObservable(initialView)) {
                                    initialView('fullWeek');
                                } else {
                                    calendar.changeView('timeGridWeek');
                                }
                            }
                        }
                    },
                    'full-month': {
                        text: '月',
                        click: (evt) => {
                            if (calendar.view.type !== 'dayGridMonth') {
                                activeClass(evt.target);

                                if (ko.isObservable(weekends)) {
                                    weekends(true);
                                } else {
                                    calendar.setOption('weekends', true);
                                }

                                if (ko.isObservable(initialView)) {
                                    initialView('fullMonth');
                                } else {
                                    calendar.changeView('dayGridMonth');
                                }
                            }
                        }
                    },
                    'list-week': {
                        text: '一覧表',
                        click: (evt) => {
                            if (calendar.view.type !== 'listWeek') {
                                activeClass(evt.target);

                                if (ko.isObservable(weekends)) {
                                    weekends(true);
                                } else {
                                    calendar.setOption('weekends', true);
                                }

                                if (ko.isObservable(initialView)) {
                                    initialView('listWeek');
                                } else {
                                    calendar.changeView('listWeek');
                                }
                            }
                        }
                    }
                },
                height: '500px',
                headerToolbar: {
                    left: 'today prev,next one-day,five-day,full-week,full-month,list-week',
                    center: 'title',
                    right: 'copy-day'
                },
                themeSystem: 'default',
                initialView: ko.unwrap(initialViewComputed),
                events: mappedEvents(events),
                locale: ko.unwrap(locale),
                firstDay: ko.unwrap(firstDay),
                weekends: ko.unwrap(weekends),
                scrollTime: formatTime(ko.unwrap(scrollTime)),
                initialDate: formatDate(ko.unwrap(initialDate)),
                editable: ko.unwrap(editable),
                selectable: ko.unwrap(editable),
                selectMirror: true,
                selectMinDistance: 4,
                nowIndicator: true,
                dayHeaders: true,
                allDaySlot: false,
                slotEventOverlap: false,
                // hiddenDays: [5, 6],
                dayHeaderContent: (opts: any) => moment(opts.date).format('DD(ddd)'),
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
                    if (evt.el) {
                        if (dataEvent.ctrl) {
                            return;
                        }

                        dataEvent.popup(true);
                        dataEvent.event(evt.event);
                    }
                },
                eventDragStart: (evt) => {
                },
                eventDrop: (evt) => {
                    if (evt.event.allDay) {
                        evt.revert();
                    }

                    const evts: any[] = calendar
                        .getEvents()
                        .map(({
                            id,
                            start,
                            end,
                            title,
                            display,
                            groupId,
                            backgroundColor
                        }) => ({
                            id,
                            start,
                            end,
                            title,
                            display,
                            groupId,
                            backgroundColor
                        }));

                    // update data sources
                    if (ko.isObservable(events)) {
                        events(evts);
                    }
                },
                eventResize: () => {
                    const evts: any[] = calendar
                        .getEvents()
                        .map(({
                            id,
                            start,
                            end,
                            title,
                            display,
                            groupId,
                            backgroundColor
                        }) => ({
                            id,
                            start,
                            end,
                            title,
                            display,
                            groupId,
                            backgroundColor
                        }));

                    // update data sources
                    if (ko.isObservable(events)) {
                        events(evts);
                    }
                },
                eventContent: (args: any) => {
                    const { type } = args.view;
                    const { start, end, title } = args.event;
                    const hour = (value: Date) => moment(value).format('H');
                    const format = (value: Date) => moment(value).format('HH:mm');

                    if (['timeGridDay', 'timeGridWeek'].indexOf(type) !== -1) {
                        return {
                            html: `<div class="fc-event-time">${format(start)} - ${format(end)}</div>
                            <div class="fc-event-title-container">
                                <div class="fc-event-title fc-sticky">${title}</div>
                            </div>`
                        };
                    }

                    if (type === 'dayGridMonthc') {
                        const hours = hour(start);
                        const minutes = format(start);

                        return {
                            html: `<div class="fc-daygrid-event-dot"></div>
                            <div class="fc-event-time">${minutes.match(/\:00$/) ? `${hours}時` : minutes}</div>
                            <div class="fc-event-title">${title}</div>`
                        };
                    }

                    if (type === 'listWeek') {
                        return {
                            html: `<h4>${title}</h4>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>
                            <div>Content 1</div>`
                        };
                    }

                    return undefined;
                },
                eventOverlap: false, // (stillEvent) => stillEvent.allDay,
                select: (arg) => {
                    calendar.unselect();
                    calendar.addEvent(arg);

                    const evts: any[] = calendar
                        .getEvents()
                        .map(({
                            id,
                            start,
                            end,
                            title,
                            display,
                            groupId,
                            backgroundColor
                        }) => ({
                            id,
                            start,
                            end,
                            title,
                            display,
                            groupId,
                            backgroundColor
                        }));

                    // update data sources
                    if (ko.isObservable(events)) {
                        events(evts);
                    }
                },
                selectOverlap: false, // (evt) => evt.allDay,
                selectAllow: (evt) => evt.start.getDate() === evt.end.getDate(),
                slotLabelContent: (opts: any) => {
                    const { milliseconds } = opts.time;
                    const min = milliseconds / 1000 / 60;
                    const hour = Math.floor(min / 60);
                    const minite = Math.floor(min % 60);

                    return !minite ? `${hour}:00` : `${minite}`;
                },
                slotLabelClassNames: (opts) => {
                    const { milliseconds } = opts.time;
                    const min = milliseconds / 1000 / 60;
                    const hour = Math.floor(min / 60);
                    const minite = Math.floor(min % 60);

                    return `${!minite ? 'fc-timegrid-slot-label-bold' : ''} fc-timegrid-slot-label-${hour}`;
                },
                slotLaneClassNames: (opts) => {
                    const { milliseconds } = opts.time;
                    const min = milliseconds / 1000 / 60;
                    const hour = Math.floor(min / 60);

                    return `fc-timegrid-slot-lane-${hour}`;
                },
                datesSet: (dateInfo) => {
                    const { start, end } = dateInfo;

                    datesSet({ start, end });
                    event.datesSet.apply(viewModel, [start, end]);
                },
                viewDidMount: (opts) => {
                    updateActive();

                    if (['timeGridDay', 'timeGridWeek'].indexOf(opts.view.type) === -1) {
                        return false;
                    }

                    const header = $(opts.el).find('thead tbody');

                    if (header.length) {
                        const _events = document.createElement('tr');
                        const __times = document.createElement('tr');

                        header.append(_events);
                        header.append(__times);

                        ko.applyBindingsToNode(__times, { component: { name: 'fc-times', params: timesSet } });
                        ko.applyBindingsToNode(_events, { component: { name: 'fc-events', params: attendancesSet } });
                    }
                }
            });

            calendar.render();

            // update height
            const fce = calendar.el.getBoundingClientRect();

            if (fce) {
                const { top } = fce;
                const { innerHeight } = window;

                calendar.setOption('height', `${innerHeight - top - 10}px`);
            }

            // change view
            initialViewComputed.subscribe((view) => calendar.changeView(view));

            if (ko.isObservable(events)) {
                events.subscribe((evts) => {
                    calendar.removeAllEvents();

                    evts.forEach(e => calendar.addEvent({ ...e, start: formatDate(e.start), end: formatDate(e.end) }));
                });
            }

            // set locale
            if (ko.isObservable(locale)) {
                locale.subscribe(l => calendar.setOption('locale', l));
            }

            // set weekends
            if (ko.isObservable(weekends)) {
                weekends.subscribe(w => calendar.setOption('weekends', w));
            }

            // set editable

            if (ko.isObservable(editable)) {
                editable.subscribe(e => {
                    calendar.setOption('editable', e);
                    calendar.setOption('selectable', e);

                    if (e !== false) {
                        $el.find('.fc-copy-day-button').removeAttr('disabled');
                    } else {
                        $el.find('.fc-copy-day-button').attr('disabled', 'disabled');
                    }
                });
            }

            // set firstDay
            if (ko.isObservable(firstDay)) {
                firstDay.subscribe(f => calendar.setOption('firstDay', f));
            }

            // set scrollTime
            if (ko.isObservable(scrollTime)) {
                scrollTime.subscribe(c => calendar.scrollToTime(formatTime(c)));
            }

            // set initialDate
            if (ko.isObservable(initialDate)) {
                initialDate.subscribe(c => calendar.gotoDate(formatDate(c)));
            }

            // set slotDuration
            ko.computed({
                read: () => {
                    const slotdr = ko.unwrap(params.slotDuration);
                    const time = !slotdr ? '00:15:00' : formatTime(slotdr);

                    calendar.setOption('slotDuration', time);
                    calendar.setOption('slotLabelInterval', time);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set businessHours
            ko.computed({
                read: () => {
                    const businessHours: BUSINESSHOUR[] = ko.unwrap(params.businessHours) as any;

                    calendar.setOption('businessHours', businessHours.map((m) => ({
                        ...m,
                        startTime: formatTime(m.startTime),
                        endTime: formatTime(m.endTime)
                    })));
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // register all global event
            vm.initalEvents();

            // test item
            _.extend(window, { calendar, params });
        }

        public destroyed() {
            const vm = this;
            const { events } = vm;

            _.each(events, (evts: J_EVENT[], k: string) => {
                _.each(evts, (h: J_EVENT) => $(window).off(k, h))
            });
        }

        private initalEvents() {
            const vm = this;
            const { $el, params, dataEvent } = vm;

            $($el).on('mousewheel', (evt) => {
                if (dataEvent.ctrl() === true || dataEvent.popup() === true) {
                    evt.preventDefault();

                    if (dataEvent.ctrl() === true) {
                        const { deltaY } = evt.originalEvent as WheelEvent;
                        const slotDuration = ko.unwrap(params.slotDuration);

                        if (ko.isObservable(params.slotDuration)) {
                            const index = durations.indexOf(slotDuration);

                            if (deltaY < 0) {
                                params.slotDuration(durations[Math.max(index - 1, 0)]);
                            } else {
                                params.slotDuration(durations[Math.min(index + 1, durations.length - 1)]);
                            }
                        }
                    }
                }
            });

            vm.registerEvent('mouseup', () => dataEvent.mouse(false));
            vm.registerEvent('mousedown', () => dataEvent.mouse(true));

            vm.registerEvent('keydown', (evt: JQueryEventObject) => {
                if (evt.keyCode === 16 || evt.shiftKey) {
                    dataEvent.shift(true);
                }

                if (evt.keyCode === 17 || evt.ctrlKey) {
                    dataEvent.ctrl(true);
                }

                if (evt.keyCode === 18 || evt.altKey) {
                    dataEvent.alt(true);
                }
            });

            vm.registerEvent('keyup', (evt: JQueryEventObject) => {
                if (evt.keyCode === 16 || evt.shiftKey) {
                    dataEvent.shift(false);
                }

                if (evt.keyCode === 17 || evt.ctrlKey) {
                    dataEvent.ctrl(false);
                }

                if (evt.keyCode === 18 || evt.altKey) {
                    dataEvent.alt(false);
                }
            });
        }

        private registerEvent(name: string, cb: (evt: JQueryEventObject) => void) {
            const vm = this;
            let hook = vm.events[name];

            if (!hook) {
                hook = vm.events[name] = [];
            }

            hook.push(cb);

            $(window).on(name, cb);
        }
    }

    export module components {
        @handler({
            bindingName: E_COMP_NAME,
            validatable: true,
            virtual: false
        })
        export class FullCalendarEditorBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement): { controlsDescendantBindings: boolean; } {
                element.removeAttribute('data-bind');

                return { controlsDescendantBindings: true };
            }
            update(element: HTMLElement, valueAccessor: () => KnockoutObservable<null | fc.EventApi>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = E_COMP_NAME;
                const params = valueAccessor();
                const event = ko.unwrap(params);
                const component = { name, params };

                ko.cleanNode(element);
                element.innerHTML = '';

                if (event) {
                    ko.applyBindingsToNode(element, { component }, bindingContext);
                } else {
                    $.Deferred()
                        .resolve(true)
                        .then(() => {
                            element
                                .classList
                                .remove('show');
                        })
                        .then(() => {
                            element.style.top = null;
                            element.style.left = null;
                        });
                }
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
            constructor(private data: KnockoutObservable<null | fc.EventApi>) {
                super();
            }

            mounted() {
                const vm = this;
                const data = ko.unwrap(vm.data);

                $(vm.$el).find('[data-bind]').removeAttr('data-bind');
            }

            edit() {
                const vm = this;

                vm.data(null);
            }

            remove() {
                const vm = this;
                const data = ko.unwrap(vm.data);

                if (data) {
                    data.remove();
                }

                vm.data(null);
            }

            close() {
                const vm = this;
                const data = ko.unwrap(vm.data);

                if (data) {
                    const { id, groupId, title } = data;

                    if (!id && !groupId && !title) {
                        data.remove();
                    }
                }

                vm.data(null);
            }
        }


        @handler({
            bindingName: C_COMP_NAME,
            validatable: false,
            virtual: false
        })
        export class FullCalendarCopyBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => fc.EventApi, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = C_COMP_NAME;
                const component = { name, params: valueAccessor() };

                ko.applyBindingsToNode(element, { component }, bindingContext);

                element.classList.add('fc-popup-editor');

                element.removeAttribute('data-bind');

                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: C_COMP_NAME,
            template: `COPY`
        })
        export class FullCalendarCopyComponent extends ko.ViewModel {
        }
    }
}
