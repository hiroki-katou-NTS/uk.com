/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
    const D_FORMAT = 'YYYYMM';
    const COMPONENT_NAME = 'scheduler';
    const COMPONENT_TEMP = `
        <div class="filter cf">
            <label class="filter-title" data-bind="i18n: 'KSU002_30'"></label>
            <div data-bind="ntsComboBox: {
                width: '100px',
                name: $component.$i18n('KSU002_30'),
                value: $component.startDate,
                options: $component.dateOptions,
                optionsValue: 'id',
                optionsText: 'title',
                editable: false,
                selectFirstIfNull: true,
                columns: [
                    { prop: 'title', length: 10 },
                ]}"></div>
        </div>
        <div class="calendar-container">
            <div data-bind="if: !!ko.unwrap($component.showBaseDate), css: { 'title': !!ko.unwrap($component.showBaseDate) }">
                <div data-bind="ntsDatePicker: { value: baseDate, dateFormat: 'yearmonth', valueFormat: 'YYYYMM', showJumpButtons: true }"></div>
            </div>
            <div class="calendar title">
                <div class="week cf" data-bind="foreach: { data: titleDays, as: 'day' }">
                    <div class="day" data-bind="scheduler-class: day">
                        <div class="status cf">
                            <span data-bind="date: day, format: 'ddd'"></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="calendar" data-bind="foreach: { data: schedules, as: 'days' }">
                <div class="week cf" data-bind="foreach: { data: days, as: 'day' }">
                    <div class="day" data-bind="scheduler-class: day">
                        <div class="status cf">
                            <span data-bind="scheduler-date: day"></span>
                            <span data-bind="scheduler-note: day"></span>
                        </div>
                        <div class="status cf" data-bind="scheduler-holiday: day"></div>
                        <div class="data-info" data-bind="scheduler-data-info: day"></div>
                    </div>
                </div>
            </div>
        </div>
        <div data-bind="if: !!ko.unwrap(showExtends), css: { 'calendar-container': !!ko.unwrap(showExtends) }">
        </div>
        <style type="text/css" rel="stylesheet">
            .scheduler {
                display: inline-block;
            }
            .scheduler .filter {
                padding-bottom: 5px;
            }
            .scheduler .filter-title {
                float: left;
                margin-right: 10px;
                line-height: 32px;
            }
            .scheduler .calendar-container {
                float: left;
                width: 600px;
                box-sizing: border-box;
                border: 1px solid #808080;
            }
            .scheduler .calendar-container .title {
                padding: 5px 0;
                background-color: #C7F391;
                border-bottom: 1px solid #808080;
            }
            .scheduler .calendar-container .title .nts-datepicker-wrapper {
                margin: 0 calc(50% - 76px);
            }
            .scheduler .calendar-container .calendar.title {
                padding: 0;
            }
            .scheduler .calendar-container .calendar.title .week .day .status {
                background-color: #C7F391 !important;
            }
            .scheduler .calendar-container .calendar.title .week .day .status span {
                color: #000;
                font-size: 11px;
                font-weight: 300;
            }
            .scheduler .calendar-container .calendar.title .week .day.sunday .status span {
                color: #f00;
            }
            .scheduler .calendar-container .calendar.title .week .day.saturday .status span {
                color: #0000ff;
            }
            .scheduler .calendar-container .calendar .week {
                box-sizing: border-box;
            }
            .scheduler .calendar-container .calendar .week:not(:last-child)  {
                border-bottom: 1px solid #808080;
            }
            .scheduler .calendar-container .calendar .week .day {
                float: left;
                width: 14.2857%;
                box-sizing: border-box;
            }
            .scheduler .calendar-container .calendar .week .day:not(:last-child) {
                border-right: 1px solid #808080;
            }
            .scheduler .calendar-container .calendar .week .day .status {
                text-align: center;
                background-color: #EDFAC2;
            }
            .scheduler .calendar-container .calendar .week .day .status span {
                color: gray;
                font-size: 9px;
                font-weight: 600;
            }
            .scheduler .calendar-container .calendar .week .day .status span+span {
                float: right;
            }
            .scheduler .calendar-container .calendar .week .day .status+.status {
                border-top: 1px solid #808080;
                border-bottom: 1px solid #808080;
            }
            .scheduler .calendar-container .calendar .week .day.sunday .status {
                background-color: #FABF8F;
            }
            .scheduler .calendar-container .calendar .week .day.saturday .status {
                background-color: #9BC2E6;
            }
            .scheduler .calendar-container .calendar .week .day .data-info{
                width: 100%;
                height: 40px;
                box-sizing: border-box;
            }
            .scheduler .calendar-container .calendar .week .day.diff-month .data-info {
                background-color: #d9d9d9;
            }
            .scheduler .calendar-container .calendar .week .day.same-month .data-info {
                background-color: #ffffff;
            }
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

    @handler({
        bindingName: 'scheduler-class'
    })
    export class ClassDayComponentBindingHandler implements KnockoutBindingHandler {
        update(element: any, valueAccessor: () => DayData, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void {
            const day = ko.unwrap(valueAccessor());

            if (_.isDate(day)) {
                element.classList.add(moment(day).locale('en').format('dddd').toLowerCase());
            } else {
                if (!day.inRange) {
                    element.classList.add('diff-month');
                } else {
                    element.classList.add('same-month');
                }

                element.classList.add(moment(day.date).locale('en').format('dddd').toLowerCase());
            }
        }
    }

    @handler({
        bindingName: 'scheduler-holiday'
    })
    export class SchedulerHolidayBindingHandler implements KnockoutBindingHandler {
        update(element: any, valueAccessor: () => DayData, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void {
            const data = ko.unwrap(valueAccessor());

            if (data.inRange) {
                element.innerHTML = '&nbsp;';
            }
            else {
                element.innerHTML = '&nbsp;'; //moment(data.date).format('D');
            }
        }
    }

    @handler({
        bindingName: 'scheduler-date'
    })
    export class SchedulerDateBindingHandler implements KnockoutBindingHandler {
        update(element: any, valueAccessor: () => DayData, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void {
            const data = ko.unwrap(valueAccessor());

            if (!data.inRange) {
                element.innerHTML = '&nbsp;';
            }
            else {
                if (!data.startDate) {
                    element.innerHTML = moment(data.date).format('D');
                } else {
                    element.innerHTML = moment(data.date).format('M/D');
                }
            }
        }
    }

    @handler({
        bindingName: 'scheduler-note'
    })
    export class SchedulerNoteBindingHandler implements KnockoutBindingHandler {
        update(element: any, valueAccessor: () => DayData, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void {
            const data = ko.unwrap(valueAccessor());

            if (data.inRange) {
                element.innerHTML = '&nbsp;';
            }
            else {
                element.innerHTML = '&nbsp;'; //moment(data.date).format('D');
            }
        }
    }

    @handler({
        bindingName: 'scheduler-info'
    })
    export class SchedulerInfoBindingHandler implements KnockoutBindingHandler {
        update(element: any, valueAccessor: () => DayData, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void {
            const data = ko.unwrap(valueAccessor());

            if (data.inRange) {
                element.innerHTML = '&nbsp;';
            }
            else {
                element.innerHTML = '&nbsp;'; //moment(data.date).format('D');
            }
        }
    }

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class CalendarComponentBindingHandler implements KnockoutBindingHandler {
        init(element: any, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const schedules = valueAccessor();
            const width = allBindingsAccessor.get('width');
            const baseDate = allBindingsAccessor.get('baseDate');

            element.classList.add('cf');
            element.classList.add('scheduler');

            ko.applyBindingsToNode(element, { component: { name: name, params: { width: width, baseDate: baseDate, schedules: schedules } } }, bindingContext);

            return { controlsDescendantBindings: true };
        }
    }

    interface Parameter {
        width: KnockoutObservable<number>;
        baseDate: KnockoutObservable<Date | { begin: Date, finish: Date }>;
        schedules: KnockoutObservableArray<DayData>;
        extendsColumns: KnockoutObservable<number>;
    }

    @component({
        name: COMPONENT_NAME,
        template: COMPONENT_TEMP
            .replace(/\n{1,}/g, ' ')
            .replace(/\t{1,}/g, ' ')
            .replace(/\s{1,}/g, ' ')
            .replace(/\s{\s/g, '{')
            .replace(/\s}\s/g, '}')
            .replace(/;}/g, '}')
            .replace(/;}/g, '}')
            .replace(/:\s/g, ':')
            .replace(/;\s/g, ';')
    })
    export class CalendarComponent extends ko.ViewModel {
        style: KnockoutObservable<string> = ko.observable('');
        baseDate: KnockoutObservable<string | null> = ko.observable(null);
        startDate: KnockoutObservable<number | null> = ko.observable(null);
        dateOptions: KnockoutObservableArray<DateOption> = ko.observableArray([]);

        schedules!: KnockoutComputed<DayData[][]>;
        titleDays!: KnockoutComputed<Date[]>;

        showExtends!: KnockoutComputed<boolean>;
        showBaseDate!: KnockoutComputed<boolean>;

        constructor(private data: Parameter) {
            super();

            const vm = this;
            const date = new Date();

            if (!vm.data) {
                vm.data = {
                    width: ko.observable(630),
                    baseDate: ko.observable(date),
                    schedules: ko.observableArray([]),
                    extendsColumns: ko.observable(0)
                };
            }

            const { baseDate, schedules } = vm.data;

            if (!ko.unwrap(baseDate)) {
                if (ko.isObservable(baseDate)) {
                    vm.data.baseDate(date);
                } else {
                    vm.data.baseDate = ko.observable(date)
                }
            }

            if (!ko.unwrap(schedules)) {
                if (ko.isObservable(schedules)) {
                    vm.data.schedules([]);
                } else {
                    vm.data.schedules = ko.observableArray([]);
                }
            }
        }

        created() {
            const vm = this;
            const { data } = vm;

            const startDate = moment().startOf('week');
            const listDates = _.range(0, 7)
                .map(m => startDate.clone().add(m, 'day'))
                .map(d => ({ id: d.get('day'), title: d.format('dddd') }));

            vm.dateOptions(listDates);

            ko.computed({
                read: () => {
                    const date = ko.unwrap(data.baseDate);

                    if (_.isDate(date)) {
                        vm.baseDate(moment(date).format(D_FORMAT));
                    }
                }
            });

            vm.baseDate
                .subscribe((date: string) => {
                    if (date && date.match(/^\d{6}$/)) {
                        data.baseDate(moment(date, D_FORMAT).toDate());
                    }
                });

            vm.style = ko.computed({
                read: () => {
                    const width = ko.unwrap(data.width);
                    const extC = ko.unwrap(data.extendsColumns);

                    return (`
                        .scheduler .calendar-container { width: ${width || 630}px !important; }
                        ${extC ? `.scheduler .calendar-container+.calendar-container { width: ${(width || 630) / 7 * extC}px !important; }` : ''}
                        `)
                        .replace(/\n{1,}/g, ' ')
                        .replace(/\t{1,}/g, ' ')
                        .replace(/\s{1,}/g, ' ')
                        .replace(/(px){2,}/g, 'px')
                        .replace(/%px/g, '%');
                },
                owner: vm
            });

            vm.schedules = ko.computed({
                read: () => {
                    const scheds = ko.unwrap(data.schedules);

                    return _.chunk(scheds, 7);
                },
                owner: vm
            });

            vm.titleDays = ko.computed({
                read: () => {
                    const scheds = ko.unwrap(vm.schedules);
                    const dates = (scheds[0] || []).map((m) => m.date);

                    return dates;
                },
                owner: vm
            });

            vm.showExtends = ko.computed({
                read: () => {
                    return ko.unwrap(data.extendsColumns) > 0;
                },
                owner: vm
            });

            vm.showBaseDate = ko.computed({
                read: () => {
                    const bDate = ko.unwrap(data.baseDate);

                    return _.isDate(bDate);
                },
                owner: vm
            });

            data.baseDate
                .subscribe((baseDate) => {
                    moment.updateLocale('ja', {
                        week: {
                            dow: ko.unwrap(vm.startDate),
                            doy: 0
                        }
                    });

                    const start = moment(_.isDate(baseDate) ? baseDate : baseDate.begin)
                        .clone()
                        .startOf(_.isDate(baseDate) ? 'month' : 'week')
                        .startOf('week')
                        .startOf('day');
                    const isInRange = (d: moment.Moment) => {
                        const s = d.clone().startOf('day');
                        const e = d.clone().endOf('day');

                        if (_.isDate(baseDate)) {
                            return d.isSame(baseDate, 'month');
                        } else {
                            const b1 = moment(baseDate.begin).startOf('day');
                            const b2 = moment(baseDate.finish).endOf('day');

                            return s.isSameOrAfter(b1) && e.isSameOrBefore(b2);
                        }
                    };
                    const isStartDate = (d: moment.Moment) => {
                        if (d.get('date') === 1) {
                            return true;
                        }

                        if (!_.isDate(baseDate)) {
                            return moment(baseDate.begin).isSame(d.startOf('day'));
                        }

                        return false;
                    };

                    const daysOfMonth = _.range(0, 42, 1)
                        .map((d) => start.clone().add(d, 'day'))
                        .map((d) => ({
                            date: d.toDate(),
                            inRange: isInRange(d),
                            startDate: isStartDate(d),
                            data: ko.observable({})
                        }));

                    data.schedules(daysOfMonth);

                    moment.updateLocale('ja', {
                        week: {
                            dow: 0,
                            doy: 0
                        }
                    });

                    vm.$nextTick(() => $(vm.$el).find('[data-bind]').removeAttr('data-bind'));
                });

            vm.startDate
                .subscribe(() => {
                    data.baseDate.valueHasMutated();
                });
        }

        mounted() {
            const vm = this;
        }
    }

    interface DateOption {
        id: number;
        title: string;
    }

    interface DayData {
        date: Date;
        data: KnockoutObservable<any>;
        inRange: boolean;
        startDate: boolean;
    }
}