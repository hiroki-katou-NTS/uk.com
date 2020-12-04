/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

declare module nts {
    export module uk {
        export module util {
            export module browser {
                export const version: string;
            }
        }
    }
}

module nts.uk.ui.at.ksu002.a {
    import c = nts.uk.ui.calendar;
    import b = nts.uk.util.browser;

    interface WData<T = string> {
        code: T;
        name: T;
    }

    export interface ScheduleData extends c.DataInfo {
        wtype: WData;
        wtime: WData;
        value: {
            begin: string | number | null;
            finish: string | number | null;
        };
        state: StateEdit<EDIT_STATE>;
        comfirmed: boolean;
        classification: WORK_STYLE | null;
        achievement: boolean | null;
        need2Work: boolean;
    }

    export interface ObserverScheduleData<R = any> extends c.DataInfo {
        $raw: R;
        wtype: WData<KnockoutObservable<string | null>>;
        wtime: WData<KnockoutObservable<string | null>>;
        value: {
            begin: KnockoutObservable<string | number | null>;
            finish: KnockoutObservable<string | number | null>;
            validate: KnockoutObservable<boolean>;
            required: KnockoutObservable<WORKTYPE_SETTING>;
        };
        state: StateEdit;
        confirmed: KnockoutObservable<boolean>;
        classification: KnockoutObservable<WORK_STYLE | null>;
        achievement: KnockoutObservable<boolean | null>;
        need2Work: KnockoutObservable<boolean>;
    }

    export interface StateEdit<T = KnockoutObservable<EDIT_STATE>> {
        wtype: T;
        wtime: T;
        value: {
            begin: T;
            finish: T;
        }
    }

    export enum EDIT_STATE {
        // 手修正（本人）
        HAND_CORRECTION_MYSELF = 0,
        // 手修正（他人）
        HAND_CORRECTION_OTHER = 1,
        // 申請反映
        REFLECT_APPLICATION = 2,
        // 打刻反映
        IMPRINT = 3
    }

    export enum WORK_STYLE {
        // １日出勤系
        FULL_TIME = 3,
        // 午前出勤系
        MORNING = 1,
        // 午後出勤系
        AFTERNOON = 2,
        // １日休日系
        HOLIDAY = 0
    }

    const COMPONENT_NAME = 'scheduler';

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class SchedulerComponentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => c.DayData<ObserverScheduleData>[], allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const schedules = valueAccessor();
            const mode = allBindingsAccessor.get('mode');
            const width = allBindingsAccessor.get('width');
            const baseDate = allBindingsAccessor.get('baseDate');
            const clickCell = allBindingsAccessor.get('click-cell');
            const changeCell = allBindingsAccessor.get('change-cell');
            const tabIndex = element.getAttribute('tabindex') || allBindingsAccessor.get('tabindex') || '1';
            const params = { width, baseDate, schedules, clickCell, tabIndex };
            const component = { name, params };

            element.classList.add('cf');
            element.classList.add('scheduler');
            element.removeAttribute('tabindex');

            const binding = bindingContext
                .extend({
                    $change: changeCell,
                    $tabindex: tabIndex,
                    $editable: ko.computed({
                        read: () => {
                            return ko.unwrap(mode) === 'edit';
                        }
                    })
                });

            ko.applyBindingsToNode(element, { component }, binding);

            return { controlsDescendantBindings: true };
        }
    }

    @component({
        name: COMPONENT_NAME,
        template: `<div data-bind="
                calendar: $component.data.schedules,
                baseDate: $component.data.baseDate,
                width: $component.data.width,
                tabindex: $component.data.tabIndex,
                click-cell: $component.data.clickCell
            "></div>
            <div class="calendar cf">
                <div class="filter cf">&nbsp;</div>
                <div class="calendar-container">
                    <div class="month title">
                        <div class="week cf">
                            <div class="day">
                                <div class="status" data-bind="i18n: 'KSU002_24'"></div>
                            </div>
                        </div>
                    </div>
                    <div class="month">
                        <div class="week cf">
                            <div class="day">
                                <div class="status wk-hours">
                                    <span data-bind="i18n: 'KSU002_25'"></span>
                                    <span data-bind="i18n: 'KSU002_35'"></span>
                                </div>
                                <div class="data-info">&nbsp;</div>
                            </div>
                            <div class="day">
                                <div class="status wk-hours full-height">
                                    <span data-bind="i18n: 'KSU002_26'"></span>
                                </div>
                                <div class="data-info">&nbsp;</div>
                            </div>
                        </div>
                        <!-- ko foreach: [1, 2, 3, 4, 5] -->
                        <div class="week cf">
                            <div class="day">
                                <div class="status">
                                    <span>&nbsp;</span>
                                </div>
                                <div class="data-info">&nbsp;</div>
                            </div>
                            <div class="day">
                                <div class="status">
                                    <span>&nbsp;</span>
                                </div>
                                <div class="data-info">&nbsp;</div>
                            </div>
                        </div>
                        <!-- /ko -->
                    </div>
                </div>
            </div>
            <style type="text/css" rel="stylesheet">
                .scheduler .data-info {
                    min-height: 48px !important;
                }
                .scheduler .data-info .work-type .join,
                .scheduler .data-info .work-type .leave {
                    overflow: hidden;
                    border-bottom: 1px dashed #b9b9b9;
                }
                .scheduler .data-info .work-type .join,
                .scheduler .data-info .work-time .join {
                    border-right: 1px dashed #b9b9b9;
                }
                .scheduler .data-info .work-type .join,
                .scheduler .data-info .work-type .leave,
                .scheduler .data-info .work-time .join,
                .scheduler .data-info .work-time .leave {
                    color: #000;
                    float: left;
                    width: 50%;
                    height: 24px;
                    line-height: 23px;
                    font-size: 12px;
                    text-align: center;
                    box-sizing: border-box;
                    white-space: nowrap;
                }
                .scheduler .data-info .join *,
                .scheduler .data-info .leave *{
                    display: block;
                    width: 100%;
                    height: 100%;
                    box-sizing: border-box;                    
                }
                .scheduler .join input,
                .scheduler .leave input {
                    color: #000;
                    padding: 0 !important;
                    text-align: center !important;
                    font-size: 13px !important;
                    border-radius: 0 !important;
                    border: 0 !important;
                    cursor: pointer;
                    background-color: transparent;
                    position: relative;
                    z-index: 9;
                }
                .scheduler .ntsControl input:focus {
                    color: #fff;
                    box-shadow: 0px 0px 0px 2px #000 !important;
                    background-color: #007fff !important;
                }
                .scheduler .ntsControl input.state-2:focus {
                    color: #000;
                    box-shadow: 0px 0px 0px 2px #000 !important;
                    background-color: #fff !important;
                }
                .scheduler .ntsControl.error input.state-0,
                .scheduler .ntsControl.error input.state-1,
                .scheduler .ntsControl.error input.state-2 {
                    color: #000;
                    box-shadow: 0px 0px 0px 2px #ff6666 !important;
                    background-color: transparent !important;
                }
                .scheduler .ntsControl.error input.state-0:focus,
                .scheduler .ntsControl.error input.state-1:focus,
                .scheduler .ntsControl.error input.state-2:focus {
                    color: #000;
                    box-shadow: 0px 0px 0px 2px #ff6666 !important;
                    background-color: rgba(255, 102, 102, 0.1) !important;                    
                }
                .scheduler .calendar {
                    float: left;
                    display: block;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.reflected-wtype .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.reflected-wtime .data-info .work-type .leave,
                .scheduler .calendar .calendar-container .month .week .day.same-month.reflected-wtime-begin .data-info .work-time .join input,
                .scheduler .calendar .calendar-container .month .week .day.same-month.reflected-wtime-finish .data-info .work-time .leave input {
                    background-color: #bfea60;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.other-alter-wtype .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.other-alter-wtime .data-info .work-type .leave,
                .scheduler .calendar .calendar-container .month .week .day.same-month.other-alter-wtime-begin .data-info .work-time .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.other-alter-wtime-finish .data-info .work-time .leave {
                    background-color: #cee6ff;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.self-alter-wtype .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.self-alter-wtime .data-info .work-type .leave,
                .scheduler .calendar .calendar-container .month .week .day.same-month.self-alter-wtime-begin .data-info .work-time .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.self-alter-wtime-finish .data-info .work-time .leave {
                    background-color: #94b7fe;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.confirmed .data-info {
                    background-color: #eccefb;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.need-2work .data-info {
                    background-color: #ddddd2;                    
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.achievement .data-info {
                    background-color: #d9d9d9;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-holiday .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-holiday .data-info .work-type .leave {
                    color: #f00;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-morning .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-morning .data-info .work-type .leave,
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-afternoon .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-afternoon .data-info .work-type .leave {
                    color: #FF7F27;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-fulltime .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.classification-fulltime .data-info .work-type .leave {
                    color: #0000ff;
                }
                .scheduler .calendar .calendar-container .month .week .day.same-month.achievement .data-info .work-type .join,
                .scheduler .calendar .calendar-container .month .week .day.same-month.achievement .data-info .work-type .leave,
                .scheduler .calendar .calendar-container .month .week .day.same-month.achievement .data-info .work-time input {
                    color: #008000;
                }
                .scheduler .calendar+.calendar {
                    width: 201px;
                }
                .scheduler .calendar+.calendar .calendar-container {
                    border-left: 0;
                }
                .scheduler .calendar+.calendar .calendar-container .month .week:not(:first-child) .day {
                    border-right: 0;
                }
                .scheduler .calendar+.calendar .calendar-container .month .week:not(:first-child) .day:not(:last-child) .data-info {
                    border-right: 1px solid #808080;
                }
                .scheduler .calendar+.calendar .filter {
                    line-height: 35px;
                }
                .scheduler .calendar+.calendar .month.title .day {
                    width: 100% !important;
                }
                .scheduler .calendar+.calendar .month+.month .day {
                    height: 86px !important;
                }
                .scheduler .calendar+.calendar .month+.month .day .status {
                    display: block;
                    height: 38px;
                    background-color: #d9d9d9;
                    box-sizing: border-box;
                    border-bottom: 1px solid #808080;
                    padding: 0 25px;
                }
                .scheduler .calendar+.calendar .month+.month .day .status.wk-hours {
                    overflow: hidden;
                    background-color: #FFC91D;
                }
                .scheduler .calendar+.calendar .month+.month .day .status.full-height>span {
                    font-size: 12px;
                    line-height: 37px;
                }
            </style>`
    })
    export class ShedulerComponent extends ko.ViewModel {

        constructor(private data: c.Parameter) {
            super();
        }

        created() {
            const vm = this;
            const { data } = vm;

            data.schedules
                .subscribe((days) => {
                    days
                        .forEach((c) => {
                            const b: any = c.binding;

                            c.binding = {
                                ...(b || {}),
                                daisy: 'scheduler-event',
                                dataInfo: 'scheduler-data-info'
                            };
                        });
                });
        }
    }

    export module controls {
        const RO = 'readonly';
        const CLBC = 'clearByCode';
        const MSG_1811 = 'Msg_1811';
        const VALIDATE = 'validate';
        const COMPONENT_NAME = 'scheduler-data-info';

        @handler({
            bindingName: COMPONENT_NAME,
            validatable: true,
            virtual: false
        })
        export class DataInfoComponentBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => c.DayData<ObserverScheduleData>, _allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = COMPONENT_NAME;
                const dayData = valueAccessor();
                const params = { dayData, context: bindingContext };
                const component = { name, params };

                const { data, className } = dayData;

                if (data) {
                    const { state } = data;

                    ko.computed({
                        read: () => {
                            const comfirmed = ko.unwrap(data.confirmed);

                            if (comfirmed) {
                                className.push(c.COLOR_CLASS.CONFIRMED);
                            } else {
                                className.remove(c.COLOR_CLASS.CONFIRMED);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });

                    ko.computed({
                        read: () => {
                            const achievement = ko.unwrap(data.achievement);

                            if (achievement) {
                                className.push(c.COLOR_CLASS.ACHIEVEMENT);
                            } else {
                                className.remove(c.COLOR_CLASS.ACHIEVEMENT);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });

                    ko.computed({
                        read: () => {
                            const need2Work = ko.unwrap(data.need2Work);

                            if (!need2Work) {
                                className.push(c.COLOR_CLASS.NEED2WORK);
                            } else {
                                className.remove(c.COLOR_CLASS.NEED2WORK);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });

                    ko.computed({
                        read: () => {
                            const classification = ko.unwrap(data.classification);

                            if (classification === WORK_STYLE.AFTERNOON) {
                                className.push(c.COLOR_CLASS.CLASSIFICATION_AFTERNOON);
                            } else {
                                className.remove(c.COLOR_CLASS.CLASSIFICATION_AFTERNOON);
                            }

                            if (classification === WORK_STYLE.FULL_TIME) {
                                className.push(c.COLOR_CLASS.CLASSIFICATION_FULLTIME);
                            } else {
                                className.remove(c.COLOR_CLASS.CLASSIFICATION_FULLTIME);
                            }

                            if (classification === WORK_STYLE.HOLIDAY) {
                                className.push(c.COLOR_CLASS.CLASSIFICATION_HOLIDAY);
                            } else {
                                className.remove(c.COLOR_CLASS.CLASSIFICATION_HOLIDAY);
                            }

                            if (classification === WORK_STYLE.MORNING) {
                                className.push(c.COLOR_CLASS.CLASSIFICATION_MORNING);
                            } else {
                                className.remove(c.COLOR_CLASS.CLASSIFICATION_MORNING);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });

                    ko.computed({
                        read: () => {
                            const wtype = ko.unwrap(state.wtype);

                            if (wtype === EDIT_STATE.IMPRINT) {
                                className.push(c.COLOR_CLASS.IMPRINT_WTYPE);
                            } else {
                                className.remove(c.COLOR_CLASS.IMPRINT_WTYPE);
                            }

                            if (wtype === EDIT_STATE.REFLECT_APPLICATION) {
                                className.push(c.COLOR_CLASS.REFLECTED_WTYPE);
                            } else {
                                className.remove(c.COLOR_CLASS.REFLECTED_WTYPE);
                            }

                            if (wtype === EDIT_STATE.HAND_CORRECTION_MYSELF) {
                                className.push(c.COLOR_CLASS.SELF_ALTER_WTYPE);
                            } else {
                                className.remove(c.COLOR_CLASS.SELF_ALTER_WTYPE);
                            }

                            if (wtype === EDIT_STATE.HAND_CORRECTION_OTHER) {
                                className.push(c.COLOR_CLASS.OTHER_ALTER_WTYPE);
                            } else {
                                className.remove(c.COLOR_CLASS.OTHER_ALTER_WTYPE);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });

                    ko.computed({
                        read: () => {
                            const wtime = ko.unwrap(state.wtime);

                            if (wtime === EDIT_STATE.IMPRINT) {
                                className.push(c.COLOR_CLASS.IMPRINT_WTIME);
                            } else {
                                className.remove(c.COLOR_CLASS.IMPRINT_WTIME);
                            }

                            if (wtime === EDIT_STATE.REFLECT_APPLICATION) {
                                className.push(c.COLOR_CLASS.REFLECTED_WTIME);
                            } else {
                                className.remove(c.COLOR_CLASS.REFLECTED_WTIME);
                            }

                            if (wtime === EDIT_STATE.HAND_CORRECTION_MYSELF) {
                                className.push(c.COLOR_CLASS.SELF_ALTER_WTIME);
                            } else {
                                className.remove(c.COLOR_CLASS.SELF_ALTER_WTIME);
                            }

                            if (wtime === EDIT_STATE.HAND_CORRECTION_OTHER) {
                                className.push(c.COLOR_CLASS.OTHER_ALTER_WTIME);
                            } else {
                                className.remove(c.COLOR_CLASS.OTHER_ALTER_WTIME);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });

                    ko.computed({
                        read: () => {
                            const begin = ko.unwrap(state.value.begin);

                            if (begin === EDIT_STATE.IMPRINT) {
                                className.push(c.COLOR_CLASS.IMPRINT_WTIME_BEGIN);
                            } else {
                                className.remove(c.COLOR_CLASS.IMPRINT_WTIME_BEGIN);
                            }

                            if (begin === EDIT_STATE.REFLECT_APPLICATION) {
                                className.push(c.COLOR_CLASS.REFLECTED_WTIME_BEGIN);
                            } else {
                                className.remove(c.COLOR_CLASS.REFLECTED_WTIME_BEGIN);
                            }

                            if (begin === EDIT_STATE.HAND_CORRECTION_MYSELF) {
                                className.push(c.COLOR_CLASS.SELF_ALTER_WTIME_BEGIN);
                            } else {
                                className.remove(c.COLOR_CLASS.SELF_ALTER_WTIME_BEGIN);
                            }

                            if (begin === EDIT_STATE.HAND_CORRECTION_OTHER) {
                                className.push(c.COLOR_CLASS.OTHER_ALTER_WTIME_BEGIN);
                            } else {
                                className.remove(c.COLOR_CLASS.OTHER_ALTER_WTIME_BEGIN);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });

                    ko.computed({
                        read: () => {
                            const finish = ko.unwrap(state.value.finish);

                            if (finish === EDIT_STATE.IMPRINT) {
                                className.push(c.COLOR_CLASS.IMPRINT_WTIME_FINISH);
                            } else {
                                className.remove(c.COLOR_CLASS.IMPRINT_WTIME_FINISH);
                            }

                            if (finish === EDIT_STATE.REFLECT_APPLICATION) {
                                className.push(c.COLOR_CLASS.REFLECTED_WTIME_FINISH);
                            } else {
                                className.remove(c.COLOR_CLASS.REFLECTED_WTIME_FINISH);
                            }

                            if (finish === EDIT_STATE.HAND_CORRECTION_MYSELF) {
                                className.push(c.COLOR_CLASS.SELF_ALTER_WTIME_FINISH);
                            } else {
                                className.remove(c.COLOR_CLASS.SELF_ALTER_WTIME_FINISH);
                            }

                            if (finish === EDIT_STATE.HAND_CORRECTION_OTHER) {
                                className.push(c.COLOR_CLASS.OTHER_ALTER_WTIME_FINISH);
                            } else {
                                className.remove(c.COLOR_CLASS.OTHER_ALTER_WTIME_FINISH);
                            }
                        },
                        owner: dayData,
                        disposeWhenNodeIsRemoved: element
                    });
                }

                ko.applyBindingsToNode(element, { component }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: COMPONENT_NAME,
            template: `
            <div class="work-type cf">
                <div class="join" data-bind="i18n: text.wtype"></div>
                <div class="leave" data-bind="i18n: text.wtime"></div>
            </div>
            <div class="work-time cf">
                <div class="join">
                    <input class="begin" data-bind="
                        css: {
                            'state-0': [1, 2].indexOf(ko.unwrap($component.click.begin)) === -1,
                            'state-1': ko.unwrap($component.click.begin) === 1,
                            'state-2': ko.unwrap($component.click.begin) === 2,
                        },
                        attr: {
                            tabindex: $tabindex
                        },
                        ntsTimeWithDayEditor: {
                            name: $component.$i18n('KSU002_28'),
                            constraint: 'TimeWithDayAttr',
                            mode: 'time',
                            inputFormat: 'time',
                            value: $component.model.begin,
                            readonly: false,
                            enable: $component.enable,
                            required: $component.model.required,
                            option: {
                                timeWithDay: false
                            }
                        },
                        event: {
                            blur: function() { $component.hideInput.apply($component, ['begin']) },
                            click: function() { $component.showInput.apply($component, ['begin']) }
                        }" />
                </div>
                <div class="leave">
                    <input class="finish" data-bind="
                        css: {
                            'state-0': [1, 2].indexOf(ko.unwrap($component.click.finish)) === -1,
                            'state-1': ko.unwrap($component.click.finish) === 1,
                            'state-2': ko.unwrap($component.click.finish) === 2,
                        },
                        attr: {
                            tabindex: $tabindex
                        },
                        ntsTimeWithDayEditor: {
                            name: $component.$i18n('KSU002_29'),
                            constraint: 'TimeWithDayAttr',
                            mode: 'time',
                            inputFormat: 'time',
                            value: $component.model.finish,
                            readonly: false,
                            enable: $component.enable,
                            required: $component.model.required,
                            option: {
                                timeWithDay: false
                            }
                        },
                        event: {
                            blur: function() { $component.hideInput.apply($component, ['finish']) },
                            click: function() { $component.showInput.apply($component, ['finish']) }
                        }" />
                </div>
            </div>
            `
        })
        export class DataInfoComponent extends ko.ViewModel {
            model: WorkTimeRange & {
                required: KnockoutObservable<boolean>;
            } = {
                    begin: ko.observable(null),
                    finish: ko.observable(null),
                    required: ko.observable(false)
                };

            click: WorkTimeRange<number> = {
                begin: ko.observable(0),
                finish: ko.observable(0)
            };

            text: {
                wtype: KnockoutObservable<string>;
                wtime: KnockoutObservable<string>;
            } = {
                    wtime: ko.observable(''),
                    wtype: ko.observable('')
                };

            enable!: KnockoutComputed<boolean>;

            constructor(private data: { dayData: c.DayData<ObserverScheduleData>; context: BindingContext }) {
                super();
            }

            created() {
                const vm = this;
                const pt = nts.uk.time.parseTime;
                const { data, model, text } = vm;
                const { context, dayData } = data;
                const cache: { begin: string | number | null; finish: string | number | null; } = { begin: null, finish: null };

                if (dayData.data) {
                    const { data } = dayData;
                    const { wtype, wtime, value } = data;

                    text.wtype = wtype.name;
                    text.wtime = wtime.name;

                    vm.enable = ko.computed({
                        read: () => {
                            return context.$editable()
                                && !(data.confirmed() || data.achievement())
                                && !!data.wtime.code()
                                && data.value.required() === WORKTYPE_SETTING.REQUIRED;
                        },
                        owner: vm
                    })

                    ko.computed({
                        read: () => {
                            model.required(ko.unwrap(value.required) === WORKTYPE_SETTING.REQUIRED || !!ko.unwrap(wtime.code));
                        },
                        owner: vm
                    });

                    value.begin
                        .subscribe((b) => {
                            const $begin = $(vm.$el).find('input.begin');
                            const $finish = $(vm.$el).find('input.finish');

                            $.Deferred()
                                .resolve(true)
                                .then(() => $begin.ntsError('clear'))
                                .then(() => model.begin(b))
                                .then(() => $begin.trigger(VALIDATE))
                                .then(() => {
                                    if ($finish.ntsError('hasError')) {
                                        $finish.trigger(VALIDATE);
                                    }
                                })
                                .then(() => value.validate(!$begin.ntsError('hasError') && !$finish.ntsError('hasError')));
                        });

                    value.begin.valueHasMutated();

                    value.finish
                        .subscribe((f) => {
                            const $begin = $(vm.$el).find('input.begin');
                            const $finish = $(vm.$el).find('input.finish');

                            $.Deferred()
                                .resolve(true)
                                .then(() => $finish.ntsError('clear'))
                                .then(() => model.finish(f))
                                .then(() => $finish.trigger(VALIDATE))
                                .then(() => {
                                    if ($begin.ntsError('hasError')) {
                                        $begin.trigger(VALIDATE);
                                    }
                                })
                                .then(() => value.validate(!$begin.ntsError('hasError') && !$finish.ntsError('hasError')));
                        });

                    value.finish.valueHasMutated();

                    model.begin
                        .subscribe((b: number | string | null) => {
                            if (cache.begin !== b && ko.unwrap(value.begin) !== b) {
                                cache.begin = b;

                                const clone: c.DayData<ScheduleData> = ko.toJS(dayData);

                                clone.data.value.begin = b;

                                context.$change.apply(context.$vm, [clone]);
                            }
                        });

                    model.finish
                        .subscribe((f: number | string | null) => {
                            if (cache.finish !== f && ko.unwrap(value.finish) !== f) {
                                cache.finish = f;
                                value.validate(true);

                                const clone: c.DayData<ScheduleData> = ko.toJS(dayData);

                                clone.data.value.finish = f;

                                context.$change.apply(context.$vm, [clone]);
                            }
                        });
                }
            }

            mounted() {
                const vm = this;
                const { click, model, enable } = vm;
                const $begin = $(vm.$el).find('input.begin');
                const $finish = $(vm.$el).find('input.finish');
                const validate = () => {
                    const b = ko.unwrap(model.begin);
                    const f = ko.unwrap(model.finish);

                    if (ko.unwrap(enable)) {
                        if (_.isNumber(b) && _.isNumber(f) && b >= f) {
                            if (!$begin.ntsError('hasError')) {
                                $begin.ntsError('set', { messageId: MSG_1811 });
                            }

                            if (!$finish.ntsError('hasError')) {
                                $finish.ntsError('set', { messageId: MSG_1811 });
                            }
                        } else {
                            $begin.ntsError(CLBC, MSG_1811);
                            $finish.ntsError(CLBC, MSG_1811);
                        }
                    } else {
                        $begin.ntsError('clear');
                        $finish.ntsError('clear');
                    }
                };

                $begin.on(VALIDATE, validate);

                $finish.on(VALIDATE, validate);

                ko.computed({
                    read: () => {
                        const readonly = vm.data.context.$editable() ? ko.unwrap(click.begin) < 2 : true;

                        if ($begin.length) {
                            if (readonly) {
                                $begin.attr(RO, RO);
                            } else {
                                $begin.removeAttr(RO).select();
                            }
                        }
                    },
                    owner: vm,
                    disposeWhenNodeIsRemoved: vm.$el
                });

                ko.computed({
                    read: () => {
                        const readonly = vm.data.context.$editable() ? ko.unwrap(click.finish) < 2 : true;

                        if ($finish.length) {
                            if (readonly) {
                                $finish.attr(RO, RO);
                            } else {
                                $finish.removeAttr(RO).select();
                            }
                        }
                    },
                    owner: vm,
                    disposeWhenNodeIsRemoved: vm.$el
                });

                /*$begin
                    .on('keyup', (evt: JQueryEventObject) => {
                        if ([13, 32].indexOf(evt.keyCode) > -1) {
                            vm.showInput('begin');
                        }

                        return true;
                    });

                $finish
                    .on('keyup', (evt: JQueryEventObject) => {
                        if ([13, 32].indexOf(evt.keyCode) > -1) {
                            vm.showInput('finish');
                        }

                        return true;
                    });*/

                $(vm.$el).find('[data-bind]').removeAttr('data-bind');
            }

            hideInput(input: INPUT_TYPE) {
                const vm = this;

                if (input === 'begin') {
                    vm.click.begin(0);
                } else if (input === 'finish') {
                    vm.click.finish(0);
                }
            }

            showInput(input: INPUT_TYPE) {
                const vm = this;
                const { dayData } = vm.data;
                const { data } = dayData;

                if (!data || ko.unwrap(data.confirmed) || ko.unwrap(data.achievement) || !ko.unwrap(data.need2Work)) {
                    return;
                }

                if (input === 'begin') {
                    const i = vm.click.begin();

                    if (i <= 1) {
                        vm.click.begin(i + 1);
                    }
                } else if (input === 'finish') {
                    const i = vm.click.finish();

                    if (i <= 1) {
                        vm.click.finish(i + 1);
                    }
                }
            }
        }

        type INPUT_TYPE = 'begin' | 'finish';

        interface WorkTimeRange<T = string | number | null> {
            begin: KnockoutObservable<T>;
            finish: KnockoutObservable<T>;
        }

        interface BindingContext extends KnockoutBindingContext {
            $vm: any,
            $change: Function,
            $tabindex: string | number;
            $editable: KnockoutReadonlyComputed<boolean>;
        }
    }
}