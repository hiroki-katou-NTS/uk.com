/// <reference path="../../generic.d.ts/fullcalendar/index.d.ts" />

module nts.uk.ui.components.fullcalendar {
    const { randomId } = nts.uk.util;
    const { version } = nts.uk.util.browser;

    const CM2KBC = /([a-z0-9]|(?=[A-Z]))([A-Z])/g;
    const toKebabCase = (s: string) => s.replace(CM2KBC, '$1-$2').toLowerCase();

    const C_COMP_NAME = 'fc-copy';
    const E_COMP_NAME = 'fc-editor';
    const COMPONENT_NAME = 'fullcalendar';
    const DEFAULT_STYLES = `
        body.fc-unselectable li.fc-event-dragging{
            list-style: none;
            display: none;
        }
        .fc-container {
            position: relative;
            overflow: hidden;
        }
        .fc-container .fc-sidebar {
            float: left;
            width: 210px;
            min-height: 1px;
            overflow: hidden;
            margin-right: 10px;
            box-sizing: border-box;
        }
        .fc-container .fc-sidebar .fc-events,
        .fc-container .fc-sidebar .fc-employees,
        .fc-container .fc-sidebar .fc-component {
            margin-top: 5px;
            border-top: 1px solid #ddd;
        }
        .fc-container .fc-sidebar .fc-events>ul,
        .fc-container .fc-sidebar .fc-employees>ul {
            padding-left: 10px;
            overflow: hidden auto;
        }
        .fc-container .fc-sidebar .fc-events>ul {
            max-height: 112px;
        }
        .fc-container .fc-sidebar .fc-employees>ul {
            max-height: 154px;
        }
        .fc-container .fc-sidebar .fc-events>ul>li,
        .fc-container .fc-sidebar .fc-employees>ul>li {
            padding: 3px;
            cursor: pointer;
        }
        .fc-container .fc-sidebar .fc-employees>ul>li.selected {
            background-color: #ccc;
        }
        .fc-container .fc-sidebar .fc-events>ul>li>div,
        .fc-container .fc-sidebar .fc-employees>ul>li>div {
            overflow: hidden;
        }
        .fc-container .fc-sidebar .fc-events>ul>li>div {
            line-height: 22px;
        }
        .fc-container .fc-sidebar .fc-employees>ul>li>div {
            line-height: 16px;
        }
        .fc-container .fc-sidebar .fc-events>ul>li>div:first-child {
            float: left;
            width: 22px;
            height: 22px;
            margin-right: 3px;
            border-radius: 50%;
        }
        .fc-container .fc-sidebar .fc-employees>ul>li>div:first-child {
            float: left;
            width: 70px;
        }
        .fc-container .fc-sidebar .fc-events>ul>li>div:not(:first-child),
        .fc-container .fc-sidebar .fc-employees>ul>li>div:not(:first-child) {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .fc-container .fc-sidebar .datepicker-panel>ul[data-view='days']>li {
            height: 20px;
            line-height: 18px;
        }
        .fc-container .fc-sidebar .datepicker-panel>ul[data-view='days']>li.picked {
            background-color: #ccc;
        }
        .fc-container .fc-sidebar .datepicker-panel>ul[data-view='days']>li:hover,
        .fc-container .fc-sidebar .datepicker-panel>ul[data-view='days']>li.picked,
        .fc-container .fc-sidebar .datepicker-panel>ul[data-view='days']>li.highlighted {
            border-radius: 50%;
        }
        .fc-container .fc-toolbar.fc-header-toolbar {
            min-height: 33px;
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
        .fc-container .fc-v-event {
            overflow: hidden;
            border-width: 2px;
        }
        .fc-container .fc-scrollgrid table {
            border-right-style: solid;
            border-bottom-style: hidden;
        }        
        .fc-container .fc-button-group button {
            min-width: 32px;
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
        }
        .fc-container .fc-events.tree-list {
            cursor: pointer;
            height: 240px;
            margin-top: 10px;
            padding: 5px 0 5px 5px;
            overflow: hidden auto;
            box-sizing: border-box;
            border: 1px solid #ccc;
        }`;

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: false,
        virtual: false
    })
    export class FullCalendarBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => KnockoutObservableArray<fc.EventApi>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;

            const events = valueAccessor();

            const employees = allBindingsAccessor.get('employees');
            const dragItems = allBindingsAccessor.get('dragItems');

            const event = allBindingsAccessor.get('event');
            const locale = allBindingsAccessor.get('locale');
            const editable = allBindingsAccessor.get('editable');
            const firstDay = allBindingsAccessor.get('firstDay');
            const scrollTime = allBindingsAccessor.get('scrollTime');
            const initialDate = allBindingsAccessor.get('initialDate');
            const initialView = allBindingsAccessor.get('initialView');
            const availableView = allBindingsAccessor.get('availableView');
            const slotDuration = allBindingsAccessor.get('slotDuration');
            const breakTime = allBindingsAccessor.get('breakTime');
            const businessHours = allBindingsAccessor.get('businessHours');
            const attendanceTimes = allBindingsAccessor.get('attendanceTimes');

            const params = { events, employees, dragItems, event, locale, initialDate, initialView, availableView, scrollTime, editable, firstDay, slotDuration, attendanceTimes, breakTime, businessHours, viewModel };
            const component = { name, params };

            ko.applyBindingsToNode(element, { component }, bindingContext);

            element.classList.add('fc-container');
            element.classList.add('cf');
            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }

    type EMPLOYEE = {
        code: string;
        name: string;
        selected: boolean;
    };

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

    type BUSSINESTIME = {
        startTime: number;
        endTime: number;
    }

    type BUSINESSHOUR = BUSSINESTIME & {
        daysOfWeek: DAY_OF_WEEK[];
    };

    type ATTENDANCE_TIME = {
        date: Date;
        events: string[];
    };

    type INITIAL_VIEW = 'oneDay' | 'fiveDay' | 'listWeek' | 'fullWeek' | 'fullMonth';
    type BUTTON_VIEWS = 'one-day' | 'five-day' | 'list-week' | 'full-week' | 'full-month';

    type PARAMS = {
        viewModel: any;
        events: fc.EventApi[] | KnockoutObservableArray<fc.EventApi>;
        employees: EMPLOYEE[] | KnockoutObservableArray<EMPLOYEE>;
        dragItems: fc.EventApi[] | KnockoutObservableArray<fc.EventApi>;
        locale: LOCALE | KnockoutObservable<LOCALE>;
        initialView: INITIAL_VIEW | KnockoutObservable<INITIAL_VIEW>;
        availableView: INITIAL_VIEW[] | KnockoutObservableArray<INITIAL_VIEW>;
        initialDate: Date | KnockoutObservable<Date>;
        scrollTime: number | KnockoutObservable<number>;
        slotDuration: SLOT_DURATION | KnockoutObservable<SLOT_DURATION>;
        editable: boolean | KnockoutObservable<boolean>;
        firstDay: DAY_OF_WEEK | KnockoutObservable<DAY_OF_WEEK>;
        attendanceTimes: ATTENDANCE_TIME[] | KnockoutObservableArray<ATTENDANCE_TIME>;
        breakTime: BUSSINESTIME | KnockoutObservable<undefined | BUSSINESTIME>;
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
        template: `<div class="fc-sidebar">
            <div class="fc-datepicker" data-bind="component: {
                    name: 'fc-datepicker',
                    params: $component.params
                }"></div>
            <div class="fc-employees">
                <h3 data-bind="i18n: '対象社員'"></h3>
                <ul data-bind="foreach: { data: $component.params.employees, as: 'item' }">
                    <li class="item" data-bind="
                        click: function() { $component.selectEmployee(item) },
                        timeClick: -1,
                        css: {
                            'selected': !!ko.unwrap($component.params.employees) && item.selected
                        }">
                        <div data-bind="text: item.code"></div>
                        <div data-bind="text: item.name"></div>
                    </li>
                </ul>
            </div>
            <div class="fc-events">
                <h3 data-bind="i18n: 'よく使う作業から作業項目'"></h3>
                <ul data-bind="foreach: { data: $component.params.dragItems, as: 'item' }">
                    <li class="title" data-bind="attr: {
                        'data-id': item.id,
                        'data-color': item.backgroundColor
                    }">
                        <div data-bind="style: {
                            'background-color': item.backgroundColor
                        }"></div>
                        <div data-bind="text: item.title"></div>
                    </li>
                </ul>
            </div>
            <div class="fc-component"></div>
        </div>
        <div class="fc-calendar"></div>
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
                    editable: ko.observable(false),
                    initialView: ko.observable('fullWeek'),
                    availableView: ko.observableArray([]),
                    initialDate: ko.observable(new Date()),
                    events: ko.observableArray([]),
                    employees: ko.observableArray([]),
                    dragItems: ko.observableArray([]),
                    attendanceTimes: ko.observableArray([]),
                    breakTime: ko.observable(null),
                    businessHours: ko.observableArray([]),
                    event: {
                        coppyDay: (__: Date, ___: Date) => { },
                        datesSet: (__: Date, ___: Date) => { }
                    }
                };
            }

            const { locale, event, events, employees, dragItems, scrollTime, initialDate, initialView, availableView, editable, firstDay, slotDuration, attendanceTimes, breakTime, businessHours } = this.params;

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

            if (availableView === undefined) {
                this.params.availableView = ko.observableArray([]);
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

            if (employees === undefined) {
                this.params.employees = ko.observableArray([]);
            }

            if (dragItems === undefined) {
                this.params.dragItems = ko.observableArray([]);
            }

            if (attendanceTimes === undefined) {
                this.params.attendanceTimes = ko.observableArray([]);
            }

            if (breakTime === undefined) {
                this.params.breakTime = ko.observable(undefined);
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
            const { locale, event, events, scrollTime, firstDay, editable, initialDate, initialView, availableView, viewModel, attendanceTimes } = params;

            const $el = $(vm.$el);
            const $dg = $el.find('div.fc-events').get(0);
            const $fc = $el.find('div.fc-calendar').get(0);
            const FC: FullCalendar | null = _.get(window, 'FullCalendar') || null;
            const updateActive = () => {
                $el
                    .find('.fc-header-toolbar button')
                    .each((__, e) => {
                        e.classList.remove('active');
                        e.classList.remove('fc-button');
                        e.classList.remove('fc-button-primary');

                        const view = ko.unwrap(initialView);

                        if (e.className.match(toKebabCase(view))) {
                            e.classList.add('active');
                        }
                    });
            };

            const weekends: KnockoutObservable<boolean> = ko.observable(true);
            const datesSet: KnockoutObservable<DATES_SET | null> = ko.observable(null);
            const selectedEvents: KnockoutObservableArray<fc.EventApi> = ko.observableArray([]);

            const timesSet: KnockoutComputed<number[]> = ko.computed({
                read() {
                    const ds = ko.unwrap(datesSet);
                    const evts: fc.EventApi[] = ko.unwrap(events) as any;

                    if (ds) {
                        const { start, end } = ds;

                        const first = moment(start);
                        const diff: number = moment(end).diff(start, 'day');
                        const mkend = first.clone().add(1, 'hour').toDate();

                        return _.range(0, diff, 1).map(m => {
                            const date = first.clone().add(m, 'day');
                            const exists = _.filter(evts, (d: fc.EventApi) => {
                                return !d.allDay &&
                                    d.display !== 'background' &&
                                    date.isSame(d.start, 'date');
                            });

                            return exists.reduce((p, c) => p += moment(c.end || mkend).diff(c.start, 'minute'), 0);
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

            const computedView = ko.computed({
                read: () => {
                    const iv = ko.unwrap(initialView);

                    updateActive();

                    switch (iv) {
                        case 'oneDay':
                            weekends(true);
                            return 'timeGridDay';
                        default:
                        case 'fiveDay':
                            weekends(false);
                            return 'timeGridWeek';
                        case 'fullWeek':
                            weekends(true);
                            return 'timeGridWeek';
                        case 'fullMonth':
                            weekends(true);
                            return 'dayGridMonth';
                        case 'listWeek':
                            weekends(true);
                            return 'listWeek';
                    }
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            const computedEvents = ko.computed({
                read: () => {
                    const rawEvents = ko.unwrap<fc.EventApi[]>(events);
                    const sltedEvents = ko.unwrap<fc.EventApi[]>(selectedEvents);
                    const isSelected = (m: fc.EventApi) => {
                        return !!_.find(sltedEvents, (e: any) => {
                            return formatDate(e.start) === formatDate(m.start)
                                && formatDate(e.end) === formatDate(m.end);
                        });
                    };

                    return [...rawEvents.map(m => ({
                        ...m,
                        allDay: false,
                        start: formatDate(m.start),
                        end: formatDate(m.end),
                        // groupId: sltedEvents.length && isSelected(m) ? 'selected' : Date.now(),
                        borderColor: isSelected(m) ? '#000' : 'transparent'
                    })), /*selectedEvents.length ? {
                        groupId: 'selected',
                        display: 'background',
                        backgroundColor: 'transparent'
                    } : null*/]
                        .filter(f => !!f);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            dataEvent.alt
                .subscribe((c) => $el.attr('alt', +c));

            dataEvent.ctrl
                .subscribe((c) => $el.attr('ctrl', +c));

            dataEvent.shift
                .subscribe((c) => $el.attr('shift', +c));

            dataEvent.alt.valueHasMutated();
            dataEvent.ctrl.valueHasMutated();
            dataEvent.shift.valueHasMutated();

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

            const viewButtons: {
                [name: string]: fc.CustomButtonInput;
            } = {
                'one-day': {
                    text: '日',
                    click: (evt) => {
                        if (calendar.view.type !== 'timeGridDay') {
                            activeClass(evt.target);

                            weekends(true);
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

                            weekends(false);
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

                            weekends(true);
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

                            weekends(true);
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

                            weekends(true);
                            if (ko.isObservable(initialView)) {
                                initialView('listWeek');
                            } else {
                                calendar.changeView('listWeek');
                            }
                        }
                    }
                }
            };
            const customButtons: {
                [name: string]: fc.CustomButtonInput;
            } = {
                'current-day': {
                    text: '今日',
                    click: () => {
                        if (ko.isObservable(initialDate)) {
                            initialDate(new Date());
                        } else {
                            calendar.gotoDate(formatDate(new Date()));
                        }
                    }
                },
                'next-day': {
                    text: '›',
                    click: () => {
                        if (ko.isObservable(initialDate)) {
                            const date = ko.unwrap(initialDate);
                            const view = ko.unwrap(initialView);

                            switch (view) {
                                case 'oneDay':
                                    initialDate(moment(date).add(1, 'day').toDate());
                                    break;
                                default:
                                case 'fiveDay':
                                case 'fullWeek':
                                case 'listWeek':
                                    initialDate(moment(date).add(1, 'week').toDate());
                                    break;
                                case 'fullMonth':
                                    initialDate(moment(date).add(1, 'month').toDate());
                                    break;
                            }
                        }
                    }
                },
                'preview-day': {
                    text: '‹',
                    click: () => {
                        if (ko.isObservable(initialDate)) {
                            const date = ko.unwrap(initialDate);
                            const view = ko.unwrap(initialView);

                            switch (view) {
                                case 'oneDay':
                                    initialDate(moment(date).subtract(1, 'day').toDate());
                                    break;
                                default:
                                case 'fiveDay':
                                case 'fullWeek':
                                case 'listWeek':
                                    initialDate(moment(date).subtract(1, 'week').toDate());
                                    break;
                                case 'fullMonth':
                                    initialDate(moment(date).subtract(1, 'month').toDate());
                                    break;
                            }
                        }
                    }
                },
                'copy-day': {
                    text: '1日分コピー',
                    click: (evt) => {
                        if (ko.unwrap(editable) === true) {
                            event.coppyDay.apply(viewModel, [(new Date(), new Date())]);
                        }
                    }
                }
            };

            const headerToolbar: fc.ToolbarInput = {
                left: 'current-day preview-day,next-day',
                center: 'title',
                right: 'copy-day'
            };

            const getEvents = (): fc.EventApi[] => calendar.getEvents()
                .map(({
                    id,
                    start,
                    end,
                    title,
                    display,
                    backgroundColor,
                    textColor,
                    extendedProps
                }) => ({
                    id,
                    start,
                    end,
                    title,
                    display,
                    backgroundColor,
                    textColor,
                    extendedProps
                })) as any;
            const mutatedEvents = () => {
                if (ko.isObservable(events)) {
                    events(getEvents().filter(f => f.display !== 'background'));
                }
            };

            const dragger = new FC.Draggable($dg, {
                itemSelector: '.title',
                eventData: (el) => ({
                    id: el.getAttribute('data-id'),
                    title: el.innerText,
                    borderColor: 'transparent',
                    backgroundColor: el.getAttribute('data-color')
                })
            });

            const calendar = new FC.Calendar($fc, {
                height: '500px',
                themeSystem: 'default',
                initialView: ko.unwrap(computedView),
                events: ko.unwrap(computedEvents),
                locale: ko.unwrap(locale),
                firstDay: ko.unwrap(firstDay),
                weekends: ko.unwrap(weekends),
                scrollTime: formatTime(ko.unwrap(scrollTime)),
                initialDate: formatDate(ko.unwrap(initialDate)),
                editable: ko.unwrap(editable),
                droppable: ko.unwrap(editable),
                selectable: ko.unwrap(editable),
                dropAccept: () => !!ko.unwrap(editable),
                selectMirror: true,
                selectMinDistance: 4,
                nowIndicator: true,
                dayHeaders: true,
                allDaySlot: false,
                slotEventOverlap: false,
                dayHeaderContent: (opts: any) => moment(opts.date).format('DD(ddd)'),
                dateClick: () => selectedEvents([]),
                eventLongPressDelay: 100,
                eventClick: (args) => {
                    const cloned = _.cloneDeep(args.event);

                    if (!ko.unwrap(dataEvent.ctrl)) {
                        selectedEvents([cloned]);
                    } else {
                        const unwraped = ko.unwrap<fc.EventApi[]>(selectedEvents);

                        if (unwraped.length === 0) {
                            selectedEvents([cloned]);
                        } else {
                            const [first] = unwraped;
                            const { id, start } = cloned;

                            if (unwraped.map(m => m.id).indexOf(id) === -1) {
                                if (moment(start).isSame(first.start, 'date')) {
                                    selectedEvents.push(cloned);
                                }
                            } else {
                                selectedEvents.remove(c => c.id === id);
                            }
                        }
                    }
                },
                eventMouseEnter: (args) => {
                    // console.log('enter', args);
                },
                eventMouseLeave: (args) => {
                    // console.log('leave', args);
                },
                eventDragStart: (evt) => {
                    if (ko.unwrap(dataEvent.shift) && ko.unwrap<fc.EventApi[]>(selectedEvents).length) {
                        selectedEvents([]);
                    }
                },
                eventDrop: (evt) => {
                    if (evt.event.allDay) {
                        evt.revert();
                    }

                    // update data sources
                    mutatedEvents();

                    selectedEvents([]);
                },
                eventResize: () => {
                    // update data sources
                    mutatedEvents();

                    selectedEvents([]);
                },
                eventContent: (args: any) => {
                    const { type } = args.view;
                    const { start, end, title } = args.event;
                    const hour = (value: Date) => moment(value).format('H');
                    const format = (value: Date) => moment(value).format('HH:mm');

                    if (['timeGridDay', 'timeGridWeek'].indexOf(type) !== -1) {
                        return {
                            html: `<div class="fc-event-time">${format(start)} - ${format(end || moment(start).add(1, 'hour').toDate())}</div>
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
                            <div>Content 2</div>
                            <div>Content 3</div>`
                        };
                    }

                    return undefined;
                },
                eventOverlap: false, // (stillEvent) => stillEvent.allDay,
                select: (arg) => {
                    const { start, end } = arg;

                    $.Deferred()
                        .resolve(true)
                        .then(() => {
                            calendar.unselect();
                            calendar.addEvent(arg);
                        })
                        .then(() => {
                            // update data sources
                            mutatedEvents();
                            selectedEvents([{ start, end } as any]);
                        });
                },
                eventReceive: (info) => {
                    const { id, title, start, backgroundColor } = info.event;
                    const event: any = { id, title, start, end: moment(start).add(1, 'hour').toDate(), backgroundColor };

                    events.push(event);
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
                    const current = moment().startOf('day');
                    const { start, end } = dateInfo;
                    const $btn = $el.find('.fc-current-day-button');

                    selectedEvents([]);
                    datesSet({ start, end });

                    if (current.isBetween(start, end, 'date', '[)')) {
                        $btn.attr('disabled', 'disabled');
                    } else {
                        $btn.removeAttr('disabled');
                    }

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

            // change weekends 
            weekends.subscribe(w => calendar.setOption('weekends', w));

            // change view
            computedView.subscribe((view) => calendar.changeView(view));

            computedEvents.subscribe((evts) => {
                calendar.removeAllEvents();

                evts.forEach(e => calendar.addEvent(e));
            });

            // set locale
            if (ko.isObservable(locale)) {
                locale.subscribe(l => calendar.setOption('locale', l));
            }

            // set editable
            if (ko.isObservable(editable)) {
                editable.subscribe(e => {
                    calendar.setOption('editable', e);
                    calendar.setOption('droppable', e);
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

            ko.computed({
                read: () => {
                    const { left, right, center, start, end } = headerToolbar;
                    const avs: BUTTON_VIEWS[] = (ko.unwrap(availableView) as any).map(toKebabCase);
                    const buttons = avs.map((m: BUTTON_VIEWS) => ({ [m]: viewButtons[m] }))

                    calendar.setOption('customButtons', {
                        ...customButtons,
                        ...buttons.reduce((p, c) => ({ ...p, ...c }), {})
                    });

                    calendar.setOption('headerToolbar', {
                        left: avs.length ? `${left} ${avs.join(',')}` : left,
                        center, right, start, end
                    });

                    updateActive();
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

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
                    const breakTime: BUSSINESTIME = ko.unwrap(params.breakTime) as any;
                    const businessHours: BUSINESSHOUR[] = ko.unwrap(params.businessHours) as any;

                    if (!breakTime) {
                        calendar.setOption('businessHours', businessHours.map((m) => ({
                            ...m,
                            startTime: formatTime(m.startTime),
                            endTime: formatTime(m.endTime)
                        })));
                    } else {
                        const { startTime, endTime } = breakTime;

                        if (businessHours.length) {
                            const starts = businessHours.map((m) => ({
                                ...m,
                                startTime: formatTime(m.startTime),
                                endTime: m.startTime !== 0 && m.endTime !== 0 ? formatTime(startTime) : formatTime(0)
                            }));
                            const ends = businessHours.map((m) => ({
                                ...m,
                                startTime: m.startTime !== 0 && m.endTime !== 0 ? formatTime(endTime) : formatTime(0),
                                endTime: formatTime(m.endTime)
                            }));

                            calendar.setOption('businessHours', [...starts, ...ends]);
                        } else {
                            calendar.setOption('businessHours', [{
                                daysOfWeek: [0, 1, 2, 3, 4, 5, 6],
                                startTime: formatTime(0),
                                endTime: formatTime(startTime)
                            }, {
                                daysOfWeek: [0, 1, 2, 3, 4, 5, 6],
                                startTime: formatTime(endTime),
                                endTime: formatTime(24 * 60)
                            }]);
                        }
                    }
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // register all global event
            vm.initalEvents();

            $el
                .removeAttr('data-bind')
                .find('[data-bind]')
                .removeAttr('data-bind');

            // test item
            _.extend(window, { dragger, calendar, params, computedEvents, selectedEvents });
        }

        public destroyed() {
            const vm = this;
            const { events } = vm;

            _.each(events, (evts: J_EVENT[], k: string) => {
                _.each(evts, (h: J_EVENT) => $(window).off(k, h))
            });
        }

        public isSelected(item: EMPLOYEE) {
            return item.selected;
        }

        public selectEmployee(item: EMPLOYEE) {
            const vm = this;
            const { params } = vm;
            const { editable, employees } = params;

            if (!!ko.unwrap(editable)) {
                const unwraped = ko.unwrap(employees) as EMPLOYEE[];

                _.each(unwraped, (emp: EMPLOYEE) => {
                    if (emp.code === item.code) {
                        emp.selected = true;
                    } else {
                        emp.selected = false;
                    }
                });

                if (ko.isObservable(employees)) {
                    employees(unwraped);
                }
            }
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


        const DATE_PICKER_TEMP = `<div class="datepicker-container">
            <div class="datepicker-panel" data-view="years picker">
            <ul>
                <li data-view="month prev">&lsaquo;</li>
                <li data-view="month current"></li>
                <li data-view="month next">&rsaquo;</li>
            </ul>
            <ul data-view="week"></ul>
            <ul data-view="days"></ul>
            </div>
            <div class="datepicker-panel" data-view="months picker">
            <ul>
                <li data-view="month prev">&lsaquo;</li>
                <li data-view="month current"></li>
                <li data-view="month next">&rsaquo;</li>
            </ul>
            <ul data-view="week"></ul>
            <ul data-view="days"></ul>
            </div>
            <div class="datepicker-panel" data-view="days picker">
            <ul>
                <li data-view="month prev">&lsaquo;</li>
                <li data-view="month current"></li>
                <li data-view="month next">&rsaquo;</li>
            </ul>
            <ul data-view="week"></ul>
            <ul data-view="days"></ul>
            </div>
        </div>`;

        @component({
            name: 'fc-datepicker',
            template: 'DATE_PICKER'
        })
        export class FullCalendarDatepicker extends ko.ViewModel {

            constructor(private params: PARAMS) {
                super();
            }

            mounted() {
                const vm = this;
                const ds = 'destroy';
                const { $el, params } = vm;

                $el.innerHTML = '';

                const $dp = $($el)
                    .on('pick.datepicker', (evt: any) => {
                        if (ko.isObservable(params.initialDate)) {
                            params.initialDate(evt.date);
                        }
                    });

                ko.computed({
                    read: () => {
                        const options: any = {
                            inline: true,
                            language: 'ja-JP',
                            date: ko.unwrap(params.initialDate),
                            weekStart: ko.unwrap(params.firstDay),
                            template: DATE_PICKER_TEMP
                        };

                        $dp
                            .datepicker(ds)
                            .datepicker(options);
                    },
                    disposeWhenNodeIsRemoved: $el
                })

                ko.computed({
                    read: () => {
                        $dp.datepicker('setDate', ko.unwrap(params.initialDate));
                    },
                    disposeWhenNodeIsRemoved: $el
                });

                $el.removeAttribute('data-bind');
            }
        }
    }
}
