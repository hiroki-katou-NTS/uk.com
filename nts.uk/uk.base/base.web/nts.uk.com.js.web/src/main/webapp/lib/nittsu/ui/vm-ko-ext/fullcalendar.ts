/// <reference path="../../generic.d.ts/fullcalendar/index.d.ts" />

module nts.uk.ui.components.fullcalendar {
    const { randomId } = nts.uk.util;
    const { version } = nts.uk.util.browser;

    type Calendar = FullCalendar.Calendar;
    type EventApi = Partial<FullCalendar.EventApi>;
    type EventContentArg = FullCalendar.EventContentArg;
    type CustomButtonInput = FullCalendar.CustomButtonInput;

    type EventClickArg = FullCalendar.EventClickArg;
    type EventDropArg = FullCalendar.EventDropArg;
    type EventDragStopArg = FullCalendar.EventDragStopArg;
    type EventDragStartArg = FullCalendar.EventDragStartArg;

    type EventResizeStartArg = FullCalendar.EventResizeStartArg;
    type EventResizeDoneArg = FullCalendar.EventResizeDoneArg;

    type DateSelectArg = FullCalendar.DateSelectArg;
    type EventRemoveArg = FullCalendar.EventRemoveArg;
    type EventReceiveLeaveArg = FullCalendar.EventReceiveLeaveArg;
    type DayHeaderContentArg = FullCalendar.DayHeaderContentArg;
    type SlotLabelContentArg = FullCalendar.SlotLabelContentArg;
    type DateRangeInput = FullCalendar.DateRangeInput;

    type JQueryEvent = JQueryEventObject & { originalEvent: WheelEvent & { wheelDelta: number; } };

    type EventSlim = {
        start: Date;
        end: Date;
    };

    type EventStatus = 'new' | 'delete' | 'normal';

    type EventRaw = EventSlim & {
        title: string;
        backgroundColor: string;
        textColor: string;
        extendedProps: Record<string, any> & {
            id: string;
            status: EventStatus;
            relateId: string;
            description: string;
        };
    };

    const CM2KBC = /([a-z0-9]|(?=[A-Z]))([A-Z])/g;
    const toKebabCase = (s: string) => s.replace(CM2KBC, '$1-$2').toLowerCase();

    const GROUP_ID = 'groupId';
    const BORDER_COLOR = 'borderColor';
    const BLACK = '#000';
    const TRANSPARENT = 'transparent';
    const SELECTED = 'selected';
    const BACKGROUND = 'background';
    const DURATION_EDITABLE = 'durationEditable';

    const C_COMP_NAME = 'fc-copy';
    const E_COMP_NAME = 'fc-editor';
    const COMPONENT_NAME = 'fullcalendar';
    const POWNER_CLASS_CPY = 'popup-owner-copy';
    const POWNER_CLASS_EVT = 'popup-owner-event';
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
        .fc-container .fc-sidebar .fc-datepicker .datepicker-container.datepicker-inline {
            position: static !important;
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
        .fc-container .fc .fc-list-sticky .fc-list-day>th {
            z-index: 1;
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
        .fc-container .fc-event-title h4 {
            margin: 0;
            padding: 0;
        }
        .fc-container .fc-event-description {
            font-size: 11px;
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
        .fc-container .fc-popup-editor>div {
            display: inline-block;
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
        .fc-container .fc-popup-editor.fc-popup-copyday {

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
        init(element: HTMLElement, valueAccessor: () => KnockoutObservableArray<EventRaw>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;

            const events = valueAccessor();

            const params = { events, ...allBindingsAccessor(), viewModel };
            const component = { name, params };

            ko.applyBindingsToNode(element, { component }, bindingContext);

            element.classList.add('fc-container');
            element.classList.add('cf');
            element.removeAttribute('data-bind');

            return { controlsDescendantBindings: true };
        }
    }

    type Employee = {
        code: string;
        name: string;
        selected: boolean;
    };

    type PopupPosition = {
        event: KnockoutObservable<null | DOMRect>;
        copyDay: KnockoutObservable<null | DOMRect>;
    }

    type DataEventStore = {
        alt: KnockoutObservable<boolean>;
        ctrl: KnockoutObservable<boolean>;
        shift: KnockoutObservable<boolean>;
        mouse: KnockoutObservable<boolean>;
        delete: KnockoutObservable<boolean>;
        target: KnockoutObservable<TargetElement>;
    };

    type TargetElement = 'event' | 'date' | null;

    type J_EVENT = (evt: JQueryEventObject) => void;
    type GlobalEvent = { [key: string]: J_EVENT[]; };

    type CalendarLocale = 'en' | 'ja' | 'vi';

    type SlotDuration = 5 | 10 | 15 | 30;
    const durations: SlotDuration[] = [5, 10, 15, 30];

    enum DayOfWeek {
        SUN = 0,
        MON = 1,
        TUE = 2,
        WED = 3,
        THU = 4,
        FRI = 5,
        SAT = 6
    }

    type BussinessTime = {
        startTime: number;
        endTime: number;
    };

    type BreakTime = BussinessTime & {
        backgroundColor: string;
    };

    type BussinessHour = BussinessTime & {
        daysOfWeek: DayOfWeek[];
    };

    type AttendanceTime = {
        date: Date;
        events: string[];
    };

    type InitialView = 'oneDay' | 'fiveDay' | 'listWeek' | 'fullWeek' | 'fullMonth';
    type ButtonView = 'one-day' | 'five-day' | 'list-week' | 'full-week' | 'full-month';

    type ButtonSet = { [name: string]: CustomButtonInput; };

    type ComponentParameters = {
        viewModel: any;
        events: EventRaw[] | KnockoutObservableArray<EventRaw>;
        employees: Employee[] | KnockoutObservableArray<Employee>;
        dragItems: EventRaw[] | KnockoutObservableArray<EventRaw>;
        locale: CalendarLocale | KnockoutObservable<CalendarLocale>;
        initialView: InitialView | KnockoutObservable<InitialView>;
        availableView: InitialView[] | KnockoutObservableArray<InitialView>;
        initialDate: Date | KnockoutObservable<Date>;
        scrollTime: number | KnockoutObservable<number>;
        slotDuration: SlotDuration | KnockoutObservable<SlotDuration>;
        editable: boolean | KnockoutObservable<boolean>;
        firstDay: DayOfWeek | KnockoutObservable<DayOfWeek>;
        attendanceTimes: AttendanceTime[] | KnockoutObservableArray<AttendanceTime>;
        breakTime: BreakTime | KnockoutObservable<undefined | BreakTime>;
        businessHours: BussinessHour[] | KnockoutObservableArray<BussinessHour>;
        validRange: DateRangeInput | KnockoutObservable<DateRangeInput>;
        event: {
            copyDay: (from: Date, to: Date) => void;
            datesSet: (start: Date, end: Date) => void;
        };
        components: {
            event: string;
            copyDay: string;
        }
    };

    type PopupData = {
        event: KnockoutObservable<null | EventApi>;
        copyDay: KnockoutObservable<null | CopyDay>;
    };

    type CopyDay = {
        from: Date;
        to: Date;
    }

    type DatesSet = {
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

    const defaultDEvent = (): DataEventStore => ({
        alt: ko.observable(false),
        ctrl: ko.observable(false),
        shift: ko.observable(false),
        mouse: ko.observable(false),
        delete: ko.observable(false),
        target: ko.observable(null)
    });

    const defaultPopupData = (): PopupData => ({
        event: ko.observable(null),
        copyDay: ko.observable(null)
    });

    const defaultPPosition = (): PopupPosition => ({
        copyDay: ko.observable(null),
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
                        'data-id': _.get(item.extendedProps, 'relateId', ''),
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
        <div data-bind="
                fc-editor: $component.popupData.event,
                name: $component.params.components.event,
                position: $component.popupPosition.event
            "></div>
        <div data-bind="
                fc-copy: $component.popupData.copyDay,
                name: $component.params.components.copyDay,
                position: $component.popupPosition.copyDay
            "></div>
        <style>${DEFAULT_STYLES}</style>
        <style data-bind="html: $component.$style"></style>`
    })
    export class FullCalendarComponent extends ko.ViewModel {
        // Fullcalendar instance
        public calendar!: Calendar;

        // stored all global events
        public events: GlobalEvent = {};

        // stored glodal date events
        public dataEvent: DataEventStore = defaultDEvent();

        // store popup data
        public popupData: PopupData = defaultPopupData();

        // store popup state
        public popupPosition: PopupPosition = defaultPPosition();

        public selectedEvents: EventSlim[] = [];

        public $style: KnockoutObservable<string> = ko.observable('');

        constructor(private params: ComponentParameters) {
            super();

            /**
             * Prevent exception of params (merge params with default options)
             */
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
                    validRange: ko.observable({}),
                    event: {
                        copyDay: (__: Date, ___: Date) => { },
                        datesSet: (__: Date, ___: Date) => { }
                    },
                    components: {
                        event: '',
                        copyDay: ''
                    }
                };
            }

            const { locale, event, components, events, employees, dragItems, scrollTime, initialDate, initialView, availableView, editable, firstDay, slotDuration, attendanceTimes, breakTime, businessHours } = this.params;

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
                    copyDay: (__: Date, ___: Date) => { },
                    datesSet: (__: Date, ___: Date) => { }
                };
            }

            const { copyDay, datesSet } = this.params.event;

            if (copyDay === undefined) {
                this.params.event.copyDay = (__: Date, ___: Date) => { };
            }

            if (datesSet === undefined) {
                this.params.event.datesSet = (__: Date, ___: Date) => { };
            }

            if (components === undefined) {
                this.params.components = {
                    event: '',
                    copyDay: ''
                };
            }

            if (this.params.components.event === undefined) {
                this.params.components.event = '';
            }

            if (this.params.components.copyDay === undefined) {
                this.params.components.copyDay = '';
            }
        }

        public mounted() {
            const vm = this;
            const {
                params,
                dataEvent,
                popupData,
                popupPosition,
            } = vm;
            const {
                locale,
                event,
                events,
                dragItems,
                scrollTime,
                firstDay,
                editable,
                initialDate,
                initialView,
                availableView,
                viewModel,
                attendanceTimes
            } = params;
            const $caches: {
                new: KnockoutObservable<EventApi | null>;
                drag: KnockoutObservable<EventApi | null>;
            } = {
                new: ko.observable(null),
                drag: ko.observable(null)
            };

            const $el = $(vm.$el);
            const $dg = $el.find('div.fc-events').get(0);
            const $fc = $el.find('div.fc-calendar').get(0);
            const FC: FullCalendar.FullCalendar | null = _.get(window, 'FullCalendar', null);
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

                const current = moment().startOf('day');
                const { start, end } = ko.unwrap(datesSet) || { start: new Date(), end: new Date() };

                const $btn = $el.find('.fc-current-day-button');

                if (!current.isBetween(start, end, 'date', '[)')) {
                    $btn.removeAttr('disabled');
                } else {
                    $btn.attr('disabled', 'disabled');
                }
            };

            const weekends: KnockoutObservable<boolean> = ko.observable(true);
            const datesSet: KnockoutObservable<DatesSet | null> = ko.observable(null).extend({ deferred: true, rateLimit: 100 });

            // emit date range to viewmodel
            datesSet.subscribe((ds) => {
                const { start, end } = ds;

                event.datesSet.apply(viewModel, [start, end]);
            });

            // calculate time on header
            const timesSet: KnockoutComputed<number[]> = ko.computed({
                read: () => {
                    const ds = ko.unwrap(datesSet);
                    const evts = ko.unwrap<EventRaw[]>(events);
                    const cache = ko.unwrap<EventApi>($caches.new);
                    const { start, end } = cache || { start: null, end: null };
                    const nkend = moment(start).clone().add(1, 'hour').toDate();
                    const duration = moment(end || nkend).diff(start, 'minute');

                    if (ds) {
                        const { start, end } = ds;

                        const first = moment(start);
                        const diff: number = moment(end).diff(start, 'day');
                        const mkend = first.clone().add(1, 'hour').toDate();

                        return _.range(0, diff, 1)
                            .map(m => {
                                const date = first.clone().add(m, 'day');
                                const exists = _.filter([...evts], (d: EventApi) => {
                                    return !d.allDay &&
                                        d.display !== 'background' &&
                                        date.isSame(d.start, 'date');
                                });

                                return exists.reduce((p, c) => p += moment(c.end || mkend).diff(c.start, 'minute'), date.isSame(nkend, 'date') ? duration : 0);
                            });
                    }

                    return [] as number[];
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            const attendancesSet: KnockoutComputed<string[][]> = ko.computed({
                read: () => {
                    const ds = ko.unwrap<DatesSet>(datesSet);
                    const ads = ko.unwrap<AttendanceTime[]>(attendanceTimes);

                    if (!ds) {
                        return [];
                    }

                    const { start, end } = ds;

                    const first = moment(start);
                    const diff: number = moment(end).diff(start, 'day');

                    return _.range(0, diff, 1)
                        .map(m => {
                            const date = first.clone().add(m, 'day');
                            const exist = _.find(ads, (d: AttendanceTime) => date.isSame(d.date, 'date'));

                            if (exist) {
                                return exist.events;
                            }

                            return [];
                        });
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

            dataEvent.alt
                .subscribe((c) => $el.attr('alt', +c));

            dataEvent.ctrl
                .subscribe((c) => $el.attr('ctrl', +c));

            dataEvent.shift
                .subscribe((c) => $el.attr('shift', +c));

            dataEvent.alt.valueHasMutated();
            dataEvent.ctrl.valueHasMutated();
            dataEvent.shift.valueHasMutated();

            popupData.event
                .subscribe((evt) => { });

            popupData.copyDay
                .subscribe((data) => {
                    if (data && ko.unwrap(editable) === true) {
                        event.copyDay.apply(viewModel, [data.from, data.to]);
                    }
                });

            // hide popup event day
            popupPosition.event
                .subscribe(e => {
                    if (!e) {
                        $(`.${POWNER_CLASS_EVT}`).removeClass(POWNER_CLASS_EVT);
                    }
                });

            // hide popup copy day
            popupPosition.copyDay
                .subscribe(c => {
                    if (!c) {
                        $(`.${POWNER_CLASS_CPY}`).removeClass(POWNER_CLASS_CPY);
                    }
                });

            // fix ie display
            if (version.match(/IE/)) {
                $el.addClass('ie');
            }

            // check instance of fullcalendar
            if (!FC || !FC.Calendar) {
                const pre = document.createElement('pre');
                const prettify = 'xml';
                const code = '<com:stylefile set="FULLCALENDAR" />\n<com:scriptfile set="FULLCALENDAR" />';

                $el.append('Please add 2 tag at below to htmlHead ui defined:');

                // pretty message as html
                ko.applyBindingsToNode(pre, { prettify, code });

                // append message to root element
                $el.append(pre);

                return;
            }

            const clearSelection = () => {
                vm.selectedEvents = [];

                // clear selections
                _.each(vm.calendar.getEvents(), (e: EventApi) => {
                    if (e.borderColor === BLACK) {
                        e.setProp(GROUP_ID, '');

                        e.setProp(BORDER_COLOR, TRANSPARENT);
                        e.setProp(DURATION_EDITABLE, true);
                    }
                });
            };

            const viewButtons: ButtonSet = {
                'one-day': {
                    text: '日',
                    click: (evt) => {
                        clearSelection();

                        if (vm.calendar.view.type !== 'timeGridDay') {
                            activeClass(evt.target);

                            weekends(true);
                            if (ko.isObservable(initialView)) {
                                initialView('oneDay');
                            } else {
                                vm.calendar.changeView('timeGridDay');
                            }
                        }
                    }
                },
                'five-day': {
                    text: '稼働日',
                    click: (evt) => {
                        clearSelection();

                        if (vm.calendar.view.type !== 'timeGridWeek' || ko.unwrap(weekends) !== false) {
                            activeClass(evt.target);

                            weekends(false);
                            if (ko.isObservable(initialView)) {
                                initialView('fiveDay');
                            } else {
                                vm.calendar.changeView('timeGridWeek');
                            }
                        }
                    }
                },
                'full-week': {
                    text: '週',
                    click: (evt) => {
                        clearSelection();

                        if (vm.calendar.view.type !== 'timeGridWeek' || ko.unwrap(weekends) !== true) {
                            activeClass(evt.target);

                            weekends(true);
                            if (ko.isObservable(initialView)) {
                                initialView('fullWeek');
                            } else {
                                vm.calendar.changeView('timeGridWeek');
                            }
                        }
                    }
                },
                'full-month': {
                    text: '月',
                    click: (evt) => {
                        clearSelection();

                        if (vm.calendar.view.type !== 'dayGridMonth') {
                            activeClass(evt.target);

                            weekends(true);
                            if (ko.isObservable(initialView)) {
                                initialView('fullMonth');
                            } else {
                                vm.calendar.changeView('dayGridMonth');
                            }
                        }
                    }
                },
                'list-week': {
                    text: '一覧表',
                    click: (evt) => {
                        clearSelection();

                        if (vm.calendar.view.type !== 'listWeek') {
                            activeClass(evt.target);

                            weekends(true);
                            if (ko.isObservable(initialView)) {
                                initialView('listWeek');
                            } else {
                                vm.calendar.changeView('listWeek');
                            }
                        }
                    }
                }
            };
            const customButtons: ButtonSet = {
                'current-day': {
                    text: '今日',
                    click: () => {
                        clearSelection();

                        if (ko.isObservable(initialDate)) {
                            initialDate(new Date());
                        } else {
                            vm.calendar.gotoDate(formatDate(new Date()));
                        }
                    }
                },
                'next-day': {
                    text: '›',
                    click: () => {
                        clearSelection();

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
                        clearSelection();

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
                    text: vm.$i18n('1日分コピー'),
                    click: (evt) => {
                        const tg: HTMLElement = evt.target as any;

                        if (tg) {
                            tg.classList.add(POWNER_CLASS_CPY);

                            popupPosition.copyDay(tg.getBoundingClientRect());
                        }
                    }
                }
            };

            const headerToolbar: FullCalendar.ToolbarInput = {
                left: 'current-day preview-day,next-day',
                center: 'title',
                right: 'copy-day'
            };

            const getEvents = (): EventRaw[] => vm.calendar
                // get all event from calendar
                .getEvents()
                // except background event
                .filter(f => f.display !== 'background')
                // map EventApi to EventRaw
                .map(({
                    start,
                    end,
                    title,
                    backgroundColor,
                    textColor,
                    extendedProps
                }) => ({
                    start,
                    end,
                    title,
                    backgroundColor,
                    textColor,
                    extendedProps: extendedProps as any
                }));
            const mutatedEvents = () => {
                if (ko.isObservable(events)) {
                    // emit event except new event (no data)
                    events(getEvents().filter(({ extendedProps }) => !!extendedProps.id));
                }
            };
            const updateEvents = () => {
                const sltds = vm.selectedEvents;
                const isSelected = (m: EventSlim) => _.some(sltds, (e: EventSlim) => formatDate(e.start) === formatDate(m.start));

                const events = ko
                    .unwrap<EventRaw[]>(params.events)
                    .filter(({ extendedProps }) => extendedProps.status !== 'delete')
                    .map((m) => ({
                        ...m,
                        id: randomId(),
                        start: formatDate(m.start),
                        end: formatDate(m.end),
                        [GROUP_ID]: isSelected(m) ? SELECTED : '',
                        [BORDER_COLOR]: isSelected(m) ? BLACK : TRANSPARENT,
                        [DURATION_EDITABLE]: isSelected(m) ? sltds.length < 2 : true,
                        extendedProps: {
                            ...m.extendedProps,
                            status: m.extendedProps.status || 'normal'
                        }
                    }));

                // clear old events
                vm.calendar.removeAllEvents();
                vm.calendar.removeAllEventSources();

                // set new events
                vm.calendar.setOption('events', events);
                // _.each(events, (e: EventRaw) => vm.calendar.addEvent(e));

                vm.selectedEvents = [];
            };
            const removeNewEvent = (event: EventApi | null) => {
                _.each(vm.calendar.getEvents(), (e: EventApi) => {
                    // remove new event (no save)
                    if (!e.title && e.extendedProps.status === 'new' && (!event || e.id !== event.id)) {
                        e.remove();
                    }
                });
            }

            const dragger = new FC.Draggable($dg, {
                itemSelector: '.title',
                eventData: (el) => {
                    const id = el.getAttribute('data-id');
                    const unwraped = ko.unwrap<EventRaw[]>(dragItems);

                    _.each(vm.calendar.getEvents(), (e: EventApi) => {
                        if (e.extendedProps.status === 'new' && !e.extendedProps.id) {
                            e.remove();
                        } else if (e.groupId === SELECTED) {
                            e.setProp(GROUP_ID, '');

                            e.setProp(BORDER_COLOR, TRANSPARENT);
                            e.setProp(DURATION_EDITABLE, true);
                        }
                    });

                    if (id) {
                        const exist = _.find(unwraped, (e: EventRaw) => e.extendedProps.relateId === id);

                        if (exist) {
                            const { title, backgroundColor, extendedProps } = exist;

                            return {
                                title,
                                backgroundColor,
                                borderColor: 'transparent',
                                extendedProps: {
                                    ...extendedProps,
                                    id: randomId(),
                                    status: 'new'
                                }
                            };
                        }
                    }

                    vm.$dialog.error({ messageId: 'DATA_SOURCE_NOT_FOUND' });

                    return null;
                }
            });

            vm.calendar = new FC.Calendar($fc, {
                height: '100px',
                themeSystem: 'default',
                selectMirror: true,
                selectMinDistance: 4,
                nowIndicator: true,
                dayHeaders: true,
                allDaySlot: false,
                slotEventOverlap: false,
                eventOverlap: false,
                selectOverlap: false,
                dateClick: () => {
                    const events = vm.calendar.getEvents();

                    _.each(events, (e: EventApi) => {
                        // remove new event (empty data)
                        if (!e.extendedProps.id) {
                            e.remove();
                        } else if (e.groupId === SELECTED) {
                            e.setProp(GROUP_ID, '');
                            // unselect events
                            e.setProp(BORDER_COLOR, TRANSPARENT);
                            e.setProp(DURATION_EDITABLE, true);
                        }
                    });
                },
                dropAccept: () => !!ko.unwrap(editable),
                dayHeaderContent: (opts: DayHeaderContentArg) => moment(opts.date).format('DD(ddd)'),
                selectAllow: (evt) => evt.start.getDate() === evt.end.getDate(),
                slotLabelContent: (opts: SlotLabelContentArg) => {
                    const { milliseconds } = opts.time;

                    const min = milliseconds / 60000;
                    const hour = Math.floor(min / 60);
                    const minite = Math.floor(min % 60);

                    return !minite ? `${hour}:00` : `${minite}`;
                },
                slotLabelClassNames: (opts) => {
                    const { milliseconds } = opts.time;

                    const min = milliseconds / 60000;
                    const hour = Math.floor(min / 60);
                    const minite = Math.floor(min % 60);

                    return `${!minite ? 'fc-timegrid-slot-label-bold' : ''} fc-timegrid-slot-label-${hour}`;
                },
                slotLaneClassNames: (opts) => {
                    const { milliseconds } = opts.time;
                    const min = milliseconds / 60000;
                    const hour = Math.floor(min / 60);
                    const brkt = ko.unwrap(params.breakTime);
                    const className = [`fc-timegrid-slot-lane-${hour}`];

                    // add breaktime class
                    if (brkt) {
                        const { startTime, endTime } = ko.unwrap(params.breakTime);

                        if (startTime <= min && min < endTime) {
                            className.push('fc-timegrid-slot-lane-breaktime');
                        }
                    }

                    return className.join(' ');
                },
                eventContent: (args: EventContentArg) => {
                    const { type } = args.view;
                    const { title, extendedProps } = args.event;
                    const { descriptions } = extendedProps;

                    if (['timeGridDay', 'timeGridWeek'].indexOf(type) !== -1) {
                        return {
                            html: `<div class="fc-event-title-container">
                                <div class="fc-event-title fc-sticky"><h4>${title}</h4></div>
                                ${_.isString(descriptions) ? descriptions.split('\n').map((m: string) => `<div class="fc-event-description fc-sticky">${m}</div>`).join('') : ''}
                            </div>`
                        };
                    }

                    if (type === 'dayGridMonth') {
                        return {
                            html: `<div class="fc-daygrid-event-dot"></div>
                            <div class="fc-event-title"><h4>${title}</h4></div>`
                        };
                    }

                    if (type === 'listWeek') {
                        return {
                            html: `<h4>${title}</h4>
                            ${_.isString(descriptions) ? descriptions.split('\n').map((m: string) => `<div class="fc-event-description fc-sticky">${m}</div>`).join('') : ''}`
                        };
                    }

                    return undefined;
                },
                viewDidMount: (opts) => {
                    // render attendence time & total time by ko binding
                    if (['timeGridDay', 'timeGridWeek'].indexOf(opts.view.type) > -1) {
                        const header = $(opts.el).find('thead tbody');

                        if (header.length) {
                            const _events = document.createElement('tr');
                            const __times = document.createElement('tr');

                            header.append(_events);
                            header.append(__times);

                            $.Deferred()
                                .resolve(true)
                                .then(() => {
                                    ko.applyBindingsToNode(__times, { component: { name: 'fc-times', params: timesSet } });
                                    ko.applyBindingsToNode(_events, { component: { name: 'fc-events', params: attendancesSet } });
                                })
                                .then(() => vm.calendar.setOption('height', '100px'))
                                .then(() => {
                                    // update height
                                    const fce = vm.calendar.el.getBoundingClientRect();

                                    if (fce) {
                                        const { top } = fce;
                                        const { innerHeight } = window;

                                        vm.calendar.setOption('height', `${innerHeight - top - 10}px`);
                                    }
                                });
                        }
                    }
                },
                eventClick: (args: EventClickArg) => {
                    const { event } = args;
                    const shift = ko.unwrap<boolean>(dataEvent.shift);
                    /**
                     * Note: remove group id before change other prop
                     */

                    // remove new event (with no data) & background event
                    removeNewEvent(event);

                    // get all event with border is black
                    const seletions = () => _.filter(vm.calendar.getEvents(), (e: EventApi) => e.borderColor === BLACK);

                    // single select
                    if (!shift) {
                        _.each(seletions(), (e: EventApi) => {
                            e.setProp(GROUP_ID, '');
                            e.setProp(BORDER_COLOR, TRANSPARENT);
                        });

                        event.setProp(BORDER_COLOR, BLACK);
                    } else {
                        // multi select
                        const [first] = seletions();

                        // no selections
                        if (!first) {
                            event.setProp(BORDER_COLOR, BLACK);
                        } else {
                            // odd select
                            if (event.borderColor === BLACK) {
                                event.setProp(GROUP_ID, '');
                                event.setProp(BORDER_COLOR, TRANSPARENT);
                            } else
                                // add new select
                                if (moment(first.start).isSame(event.start, 'date')) {
                                    event.setProp(BORDER_COLOR, BLACK);
                                }
                        }
                    }

                    const selecteds = seletions();

                    if (!!selecteds.length) {
                        // group selected event & disable resizeable
                        _.each(selecteds, (e: EventApi) => {
                            e.setProp(DURATION_EDITABLE, selecteds.length === 1);
                            e.setProp(GROUP_ID, SELECTED);
                        });
                    }
                },
                eventDragStart: (arg: EventDragStartArg) => {
                    const { event } = arg;
                    const {
                        id,
                        start,
                        end,
                        borderColor,
                        groupId,
                        extendedProps
                    } = event;

                    // remove new event (with no data) & background event
                    removeNewEvent(arg.event);

                    // cache drag event
                    $caches.drag({ id, start, end, groupId, borderColor, extendedProps });

                    // copy event by drag
                    if (ko.unwrap<boolean>(dataEvent.shift)) {
                        updateEvents();
                    }

                    vm.selectedEvents = [];

                    // clear all old selection
                    _.each(vm.calendar.getEvents(), (e: EventApi) => {
                        e.setProp(GROUP_ID, '');

                        e.setProp(BORDER_COLOR, TRANSPARENT);
                    });

                    arg.event.setProp(BORDER_COLOR, BLACK);
                },
                eventDragStop: (arg: EventDragStopArg) => {
                    // clear drag cache
                    $caches.drag(null);
                },
                eventDrop: (arg: EventDropArg) => {
                    const { event, relatedEvents } = arg;
                    const { start, end, id, title, extendedProps, borderColor, groupId } = event;
                    const rels = relatedEvents.map(({ start, end }) => ({ start, end }));

                    vm.selectedEvents = [{ start, end }, ...rels];

                    // update data sources
                    mutatedEvents();

                    // add new event (no save) if new event is dragging
                    if (!title && extendedProps.status === 'new' && !rels.length) {
                        $caches.new(vm.calendar
                            .addEvent({
                                id,
                                start,
                                end,
                                borderColor,
                                groupId,
                                extendedProps
                            }));
                    }
                },
                eventResizeStart: (arg: EventResizeStartArg) => {
                    // remove new event (with no data) & background event
                    removeNewEvent(arg.event);

                    vm.selectedEvents = [];

                    // clear all oll selection
                    _.each(vm.calendar.getEvents(), (e: EventApi) => {
                        e.setProp(GROUP_ID, '');

                        e.setProp(BORDER_COLOR, TRANSPARENT);
                    });

                    arg.event.setProp(BORDER_COLOR, BLACK);
                    arg.event.setProp(GROUP_ID, SELECTED);

                    popupPosition.event(null);
                },
                eventResize: (arg: EventResizeDoneArg) => {
                    const { event } = arg;
                    const { start, end, title, extendedProps, id, borderColor, groupId } = event;

                    vm.selectedEvents = [{ start, end }];

                    // update data sources
                    mutatedEvents();

                    // add new event (no save) if new event is dragging
                    if (!title && extendedProps.status === 'new') {
                        $caches.new(vm.calendar
                            .addEvent({
                                id,
                                start,
                                end,
                                borderColor,
                                groupId,
                                extendedProps
                            }));
                    }
                },
                select: (arg: DateSelectArg) => {
                    const { start, end } = arg;

                    // clean selection
                    vm.calendar.unselect();

                    // rerender event (deep clean selection)
                    updateEvents();

                    // add new event from selected data
                    $caches.new(vm.calendar
                        .addEvent({
                            id: randomId(),
                            start: formatDate(start),
                            end: formatDate(end),
                            [BORDER_COLOR]: BLACK,
                            [GROUP_ID]: SELECTED,
                            extendedProps: {
                                status: 'new'
                            }
                        }));
                },
                eventRemove: (args: EventRemoveArg) => {
                    const { event } = args;

                    // remove event from event sources
                    _.forEach(vm.calendar.getEvents(), (e: EventApi) => {
                        if (e.id === event.id) {
                            e.remove();
                        }
                    });

                    // emit data out if event isn't new
                    if (event.title && event.extendedProps.status !== 'new') {
                        mutatedEvents();
                    } else if (!event.title && event.extendedProps.status === 'new') {
                        $caches.new(null);
                    }
                },
                eventReceive: (info: EventReceiveLeaveArg) => {
                    const { event } = info;
                    const {
                        title,
                        start,
                        backgroundColor,
                        textColor,
                        extendedProps
                    } = event;
                    const end = moment(start).add(1, 'hour').toDate();

                    // remove drop event
                    event.remove();

                    vm.selectedEvents = [{ start, end }];

                    // add cloned event to datasources
                    events.push({
                        title,
                        start,
                        end,
                        textColor,
                        backgroundColor,
                        extendedProps: {
                            ...extendedProps,
                            id: randomId(),
                            status: 'new'
                        } as any
                    });
                },
                datesSet: (dateInfo) => {
                    const current = moment().startOf('day');
                    const { start, end } = dateInfo;
                    const isValidRange = () => {
                        const validRange = ko.unwrap<DateRangeInput>(params.validRange);

                        if (validRange) {
                            const { start, end } = validRange;

                            if (start && end) {
                                return current.isBetween(start, end, 'date', '[)');
                            }

                            if (start) {
                                return current.isSameOrAfter(start, 'date');
                            }

                            if (end) {
                                return current.isSameOrBefore(end, 'date');
                            }
                        }

                        return true;
                    };

                    const $btn = $el.find('.fc-current-day-button');

                    datesSet({ start, end });

                    if (!current.isBetween(start, end, 'date', '[)') && isValidRange()) {
                        $btn.removeAttr('disabled');
                    } else {
                        $btn.attr('disabled', 'disabled');
                    }
                },
                eventDidMount: (args) => {
                    /*const { el, event } = args;
                    const nEvent = ko.unwrap(newEvent);
                    const selected = ko.unwrap<string[]>(selectedEvents);
 
                    if (selected.length === 1 && !ko.unwrap(dataEvent.shift)) {
                        const [id] = selected;
 
                        if (event.id === id) {
                            $.Deferred()
                                .resolve(true)
                                .then(() => el.classList.add(POWNER_CLASS_EVT))
                                .then(() => popupData.event(event))
                                .then(() => {
                                    const bound = el.getBoundingClientRect();
                                    const { top, left } = bound;
 
                                    if (!top || !left) {
                                        popupPosition.event(null);
                                    } else if (!_.isEmpty(event.extendedProps)) {
                                        popupPosition.event(bound);
                                    } else {
                                        popupPosition.event(null);
                                    }
                                });
                        }
                    } else if (nEvent) {
                        const { end, start } = nEvent;
 
                        if (moment(start).isSame(event.start) && moment(end).isSame(event.end)) {
                            $.Deferred()
                                .resolve(true)
                                .then(() => el.classList.add(POWNER_CLASS_EVT))
                                .then(() => popupData.event(event))
                                .then(() => {
                                    const bound = el.getBoundingClientRect();
                                    const { top, left } = bound;
 
                                    if (!top || !left) {
                                        popupPosition.event(null);
                                    } else {
                                        popupPosition.event(bound);
                                    }
                                });
                        }
                    }*/
                },
                windowResize: () => {
                    // update height
                    const fce = vm.calendar.el.getBoundingClientRect();

                    if (fce) {
                        const { top } = fce;
                        const { innerHeight } = window;

                        vm.calendar.setOption('height', `${innerHeight - top - 10}px`);
                    }
                },
                windowResizeDelay: 100,
                handleWindowResize: true
            });

            vm.calendar.render();

            // change weekends 
            ko.computed({
                read: () => {
                    const wk = ko.unwrap<boolean>(weekends);

                    vm.calendar.setOption('weekends', wk);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // change view
            ko.computed({
                read: () => {
                    const cv = ko.unwrap<string>(computedView);

                    vm.calendar.changeView(cv);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set locale
            ko.computed({
                read: () => {
                    const lc = ko.unwrap<string>(locale);

                    vm.calendar.setOption('locale', lc);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set editable
            ko.computed({
                read: () => {
                    const ed = ko.unwrap<boolean>(editable);

                    vm.calendar.setOption('editable', ed);
                    vm.calendar.setOption('droppable', ed);
                    vm.calendar.setOption('selectable', ed);

                    if (ed !== false) {
                        $el.find('.fc-copy-day-button').removeAttr('disabled');
                    } else {
                        $el.find('.fc-copy-day-button').attr('disabled', 'disabled');
                    }
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set firstDay
            ko.computed({
                read: () => {
                    const fd = ko.unwrap<number>(firstDay);

                    vm.calendar.setOption('firstDay', fd);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set scrollTime
            ko.computed({
                read: () => {
                    const sc = ko.unwrap<number>(scrollTime);

                    vm.calendar.scrollToTime(formatTime(sc));
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set initialDate
            ko.computed({
                read: () => {
                    const id = ko.unwrap<Date>(initialDate);

                    clearSelection();

                    vm.calendar.gotoDate(formatDate(id));
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set events to calendar
            ko.computed({
                read: updateEvents,
                disposeWhenNodeIsRemoved: vm.$el
            });

            // calculate button show on view
            ko.computed({
                read: () => {
                    const { left, right, center, start, end } = headerToolbar;
                    const avs: string[] = ko.unwrap<InitialView[]>(availableView).map(toKebabCase);
                    const buttons = avs.map((m: ButtonView) => ({ [m]: viewButtons[m] }))

                    vm.calendar.setOption('customButtons', {
                        ...customButtons,
                        ...buttons.reduce((p, c) => ({ ...p, ...c }), {})
                    });

                    vm.calendar.setOption('headerToolbar', {
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

                    vm.calendar.setOption('slotDuration', time);
                    vm.calendar.setOption('slotLabelInterval', time);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set businessHours
            ko.computed({
                read: () => {
                    const breakTime = ko.unwrap<BreakTime>(params.breakTime);
                    const businessHours = ko.unwrap<BussinessHour[]>(params.businessHours);

                    if (!breakTime) {
                        vm.calendar.setOption('businessHours', businessHours.map((m) => ({
                            ...m,
                            startTime: formatTime(m.startTime),
                            endTime: formatTime(m.endTime)
                        })));
                    } else {
                        const { startTime, endTime, backgroundColor } = breakTime;

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

                            vm.calendar.setOption('businessHours', [...starts, ...ends]);
                        } else {
                            vm.calendar.setOption('businessHours', [{
                                daysOfWeek: [0, 1, 2, 3, 4, 5, 6],
                                startTime: formatTime(0),
                                endTime: formatTime(startTime)
                            }, {
                                daysOfWeek: [0, 1, 2, 3, 4, 5, 6],
                                startTime: formatTime(endTime),
                                endTime: formatTime(24 * 60)
                            }]);
                        }

                        vm.$style(`.fc-timegrid-slot-lane-breaktime { background-color: ${backgroundColor || 'transparent'} }`);
                    }
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // set validRange
            ko.computed({
                read: () => {
                    const validRange = ko.unwrap<DateRangeInput>(params.validRange);

                    vm.calendar.setOption('validRange', validRange);
                },
                disposeWhenNodeIsRemoved: vm.$el
            });

            // register all global event
            vm.initalEvents()
                .registerEvent('mousemove', () => {
                    // clear new event when start select
                    if (ko.unwrap(dataEvent.target) === 'date') {
                        removeNewEvent(null);
                    }
                });

            $el
                .removeAttr('data-bind')
                .find('[data-bind]')
                .removeAttr('data-bind');

            // test item
            _.extend(window, { dragger, calendar: vm.calendar, params, popupPosition });
        }

        public destroyed() {
            const vm = this;
            const { events } = vm;

            _.each(events, (evts: J_EVENT[], k: string) => {
                _.each(evts, (h: J_EVENT) => $(window).off(k, h))
            });
        }

        public removeEvent(id: string) {

        }


        public selectEmployee(item: Employee) {
            const vm = this;
            const { params } = vm;
            const { employees } = params;
            const unwraped = ko.toJS(employees);

            _.each(unwraped, (emp: Employee) => {
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

        private initalEvents() {
            const vm = this;
            const { $el, params, calendar, dataEvent, popupPosition } = vm;

            $($el)
                .on('mousewheel', (evt: JQueryEvent) => {
                    if (ko.unwrap(dataEvent.shift) === true) {
                        evt.preventDefault();

                        const { deltaY, wheelDelta } = evt.originalEvent;
                        const slotDuration = ko.unwrap(params.slotDuration);

                        if (!version.match(/IE/) && ['timeGridDay', 'timeGridWeek'].indexOf(calendar.view.type) !== -1 && ko.isObservable(params.slotDuration)) {
                            const index = durations.indexOf(slotDuration);

                            if ((wheelDelta || deltaY) < 0) {
                                params.slotDuration(durations[Math.max(index - 1, 0)]);
                            } else {
                                params.slotDuration(durations[Math.min(index + 1, durations.length - 1)]);
                            }
                        }
                    }
                });

            vm
                .registerEvent('mouseup', () => {
                    dataEvent.mouse(false);
                    dataEvent.target(null);
                })
                .registerEvent('mousewheel', () => {
                    popupPosition.event(null);
                    popupPosition.copyDay(null);
                })
                .registerEvent('mousedown', (evt) => {
                    const $tg = $(evt.target);

                    const iown = $tg.hasClass('.popup-owner-copy');
                    const cown = $tg.closest('.popup-owner-copy').length > 0;
                    const ipov = $tg.hasClass('.fc-popup-editor');
                    const cpov = $tg.closest('.fc-popup-editor').length > 0;
                    const ipkr = $tg.hasClass('.datepicker-container') && $tg.not('.datepicker-inline');
                    const cpkr = $tg.closest('.datepicker-container').length > 0 && $tg.closest('.datepicker-inline').length === 0;

                    dataEvent.mouse(true);

                    const targ = $tg
                        .closest('.fc-timegrid-event.fc-v-event.fc-event').length ? 'event' :
                        ($tg.hasClass('fc-non-business') || $tg.hasClass('fc-timegrid-slot')) ? 'date' : null;

                    dataEvent.target(targ);

                    // close popup if target isn't owner & poper.
                    if (!iown && !cown && !ipov && !cpov && !ipkr && !cpkr) {
                        popupPosition.event(null);
                        popupPosition.copyDay(null);
                    }
                })
                .registerEvent('mousemove', () => {
                    if (ko.unwrap(dataEvent.mouse)) {
                        popupPosition.event(null);
                        popupPosition.copyDay(null);
                    }
                })
                // store data keyCode
                .registerEvent('keydown', (evt: JQueryEventObject) => {
                    if (evt.keyCode === 16 || evt.shiftKey || evt.which === 16) {
                        dataEvent.shift(true);
                    }

                    if (evt.keyCode === 17 || evt.ctrlKey || evt.which === 17) {
                        dataEvent.ctrl(true);
                    }

                    if (evt.keyCode === 18 || evt.altKey || evt.which === 18) {
                        dataEvent.alt(true);
                    }
                })
                // remove data keyCode
                .registerEvent('keyup', (evt: JQueryEventObject) => {
                    if (evt.keyCode === 16 || evt.shiftKey || evt.which === 16) {
                        dataEvent.shift(false);
                    }

                    if (evt.keyCode === 17 || evt.ctrlKey || evt.which === 17) {
                        dataEvent.ctrl(false);
                    }

                    if (evt.keyCode === 18 || evt.altKey || evt.which === 18) {
                        dataEvent.alt(false);
                    }

                    if (evt.keyCode === 46 && !ko.unwrap(dataEvent.delete)) {
                        if (vm.calendar) {
                            const selecteds = _.filter(vm.calendar.getEvents(), (e: EventApi) => e.groupId === SELECTED);

                            if (selecteds.length) {
                                dataEvent.delete(true);

                                vm.$dialog
                                    .confirm({ messageId: 'DELETE_CONFIRM' })
                                    .then((v: 'yes' | 'no') => {
                                        if (v === 'yes') {
                                            const starts = selecteds.map(({ start }) => formatDate(start));

                                            if (ko.isObservable(vm.params.events)) {
                                                vm.params.events.remove((e: EventRaw) => starts.indexOf(formatDate(e.start)) !== -1);
                                            }
                                        }

                                        dataEvent.delete(false);
                                    });
                            }
                        }
                    }
                })
                .registerEvent('resize', () => {
                    popupPosition.event(null);
                    popupPosition.copyDay(null);
                });

            return vm;
        }

        private registerEvent(name: string, cb: (evt: JQueryEventObject) => void) {
            const vm = this;
            let hook = vm.events[name];

            if (!hook) {
                hook = vm.events[name] = [];
            }

            hook.push(cb);

            $(window).on(name, cb);

            return vm;
        }
    }

    export module components {

        type EVENT_PARAMS = {
            name: string;
            data: KnockoutObservable<null | EventApi>;
            position: KnockoutObservable<null | DOMRect>;
        };

        @handler({
            bindingName: E_COMP_NAME,
            validatable: true,
            virtual: false
        })
        export class FullCalendarEditorBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => EventApi, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = E_COMP_NAME;
                const data = valueAccessor();
                const cName = allBindingsAccessor.get('name');
                const position = allBindingsAccessor.get('position');
                const component = { name, params: { data, position, name: cName } };

                element.removeAttribute('data-bind');
                element.classList.add('fc-popup-editor');
                element.classList.add('fc-popup-event');

                ko.applyBindingsToNode(element, { component }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: E_COMP_NAME,
            template: `DETAIL_EVENT`
        })
        export class FullCalendarEditorComponent extends ko.ViewModel {
            event!: (evt: JQueryEventObject) => void;

            constructor(private params: EVENT_PARAMS) {
                super();
            }

            mounted() {
                const vm = this;
                const { $el, params } = vm;
                const { name, data, position } = params;

                if (name) {
                    $el.innerHTML = '';
                    const close = () => vm.close();
                    const remove = () => vm.remove();

                    ko.applyBindingsToNode($el, { component: { name, params: { remove, close, data } } });
                }

                ko.computed({
                    read: () => {
                        const pst = ko.unwrap(position);

                        if (!pst) {
                            $el.removeAttribute('style');
                            $el.classList.remove('show');
                        } else {
                            const { top, left } = pst;

                            const first = $el.querySelector('div');

                            $el.classList.add('show');

                            $el.style.top = `${top || 0}px`;

                            if (!first) {
                                $el.style.left = `${left || 0}px`;
                            } else {
                                const { width, height } = first.getBoundingClientRect();

                                $el.style.left = `${left || 0}px`;

                                $el.style.width = `${width + 20}px`;
                                $el.style.height = `${height + 20}px`;
                            }
                        }
                    },
                    disposeWhenNodeIsRemoved: $el
                });

                vm.event = (evt: JQueryEventObject) => {
                    const tg = evt.target as HTMLElement;

                    if (tg && !!ko.unwrap(position)) {
                        if (!tg.classList.contains(POWNER_CLASS_EVT) && !$(tg).closest(`.${POWNER_CLASS_EVT}`).length && !$(tg).closest('.fc-popup-event').length) {
                            position(null);
                        }
                    }
                };

                $(document).on('click', vm.event);
            }

            destroyed() {
                const vm = this;

                $(document).off('click', vm.event);
            }

            edit() {
                const vm = this;
                const { params } = vm;
                const { data, position } = params;

                const event = ko.unwrap(data);
                const { id, start, end, backgroundColor, textColor, extendedProps } = event;

                data({
                    id,
                    start,
                    end,
                    backgroundColor,
                    textColor,
                    title: 'Edited',
                    extendedProps: {
                        edited: true,
                        id: extendedProps.id || 'xxx-xxxxx',
                        descriptions: 'ZDescriptions'
                    }
                } as any);

                position(null);
            }

            remove() {
                const vm = this;
                const { params } = vm;
                const { data, position } = params;

                $.Deferred()
                    .resolve(true)
                    .then(() => {
                        const event = ko.unwrap(data);

                        event.remove();
                    })
                    .then(() => data(null))
                    .then(() => position(null));
            }

            close() {
                const vm = this;
                const { params } = vm;
                const { data, position } = params;

                $.Deferred()
                    .resolve(true)
                    .then(() => {
                        const event = ko.unwrap(data);

                        if (_.isEmpty(event.extendedProps)) {
                            event.remove();
                        }
                    })
                    .then(() => data(null))
                    .then(() => position(null));
            }
        }

        @handler({
            bindingName: C_COMP_NAME,
            validatable: false,
            virtual: false
        })
        export class FullCalendarCopyBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => EventApi, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = C_COMP_NAME;
                const data = valueAccessor();
                const cName = allBindingsAccessor.get('name');
                const position = allBindingsAccessor.get('position');
                const component = { name, params: { data, position, name: cName } };

                element.removeAttribute('data-bind');
                element.classList.add('fc-popup-editor');
                element.classList.add('fc-popup-copyday');

                ko.applyBindingsToNode(element, { component }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        type COPY_PARAMS = {
            name: string;
            data: KnockoutObservable<null | { from: Date; to: Date; }>;
            position: KnockoutObservable<null | DOMRect>;
        };

        @component({
            name: C_COMP_NAME,
            template: `COPY_DAY`
        })
        export class FullCalendarCopyDayComponent extends ko.ViewModel {
            event!: (evt: JQueryEventObject) => void;

            constructor(private params: COPY_PARAMS) {
                super();
            }

            mounted() {
                const vm = this;
                const { $el, params } = vm;
                const { name, data, position } = params;

                if (name) {
                    $el.innerHTML = '';
                    const close = () => vm.close();

                    ko.applyBindingsToNode($el, { component: { name, params: { close, data } } });
                }

                ko.computed({
                    read: () => {
                        const pst = ko.unwrap(position);

                        if (!pst) {
                            $el.removeAttribute('style');
                            $el.classList.remove('show');
                        } else {
                            const { top, left } = pst;
                            const first = $el.querySelector('div');

                            $el.classList.add('show');

                            $el.style.top = `${top}px`;

                            if (first) {
                                const { width, height } = first.getBoundingClientRect();

                                $el.style.left = `${left - width - 23}px`;

                                $el.style.width = `${width + 20}px`;
                                $el.style.height = `${height + 20}px`;
                            } else {
                                $el.style.left = `${left}px`;
                            }
                        }
                    },
                    disposeWhenNodeIsRemoved: $el
                });

                vm.event = (evt: JQueryEventObject) => {
                    evt.preventDefault();

                    const tg = evt.target as HTMLElement;

                    if (tg && !!ko.unwrap(position)) {
                        if (!tg.classList.contains(POWNER_CLASS_CPY) && !$(tg).closest(`.${POWNER_CLASS_CPY}`).length && !$(tg).closest('.fc-popup-copyday').length) {
                            position(null);
                        }
                    }
                };

                $(document).on('click', vm.event);
            }

            close() {
                const vm = this;
                const { params } = vm;

                params.position(null);
            }

            destroyed() {
                const vm = this;

                $(document).off('click', vm.event);
            }
        }

        @component({
            name: 'fc-events',
            template:
                `<td data-bind="i18n: '勤怠時間'"></td>
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

                // fix display on ie
                vm.$el.removeAttribute('style');
            }
        }

        @component({
            name: 'fc-times',
            template:
                `<td data-bind="i18n: '作業時間'"></td>
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

                // fix display on ie
                vm.$el.removeAttribute('style');
            }

            formatTime(time: number) {
                const hour = Math.floor(time / 60);
                const minute = Math.floor(time % 60);

                return `${hour}:${_.padStart(`${minute}`, 2, '0')}`;
            }
        }

        @component({
            name: 'fc-datepicker',
            template: 'DATE_PICKER'
        })
        export class FullCalendarDatepicker extends ko.ViewModel {

            constructor(private params: ComponentParameters) {
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

                        $('body>.datepicker-container').addClass('datepicker-hide');
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

        const DATE_PICKER_TEMP =
            `<div class="datepicker-container">
                <div class="datepicker-panel" data-view="years picker">
                    <ul>
                        <li data-view="month current"></li>
                        <li data-view="month prev">&lsaquo;</li>
                        <li data-view="month next">&rsaquo;</li>
                    </ul>
                    <ul data-view="week"></ul>
                    <ul data-view="days"></ul>
                </div>
                <div class="datepicker-panel" data-view="months picker">
                    <ul>
                        <li data-view="month current"></li>
                        <li data-view="month prev">&lsaquo;</li>
                        <li data-view="month next">&rsaquo;</li>
                    </ul>
                    <ul data-view="week"></ul>
                    <ul data-view="days"></ul>
                </div>
                <div class="datepicker-panel" data-view="days picker">
                    <ul>
                        <li data-view="month current"></li>
                        <li data-view="month prev">&lsaquo;</li>
                        <li data-view="month next">&rsaquo;</li>
                    </ul>
                    <ul data-view="week"></ul>
                    <ul data-view="days"></ul>
                </div>
            </div>`;
    }
}
