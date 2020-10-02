/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
    import c = nts.uk.ui.calendar;

    export interface ScheduleData extends c.DataInfo {
        wtype: string;
        wtime: string;
        value: {
            begin: number | null;
            finish: number | null;
        },
        event?: string;
    }

    const COMPONENT_NAME = 'scheduler';

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class SchedulerComponentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => c.DayData<ScheduleData>[], allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const schedules = valueAccessor();
            const width = allBindingsAccessor.get('width');
            const baseDate = allBindingsAccessor.get('baseDate');
            const clickCell = allBindingsAccessor.get('click-cell');
            const changeCell = allBindingsAccessor.get('change-cell');
            const tabIndex = element.getAttribute('tabindex') || allBindingsAccessor.get('tabindex') || '1';
            const params = { width, baseDate, schedules, clickCell, tabIndex };
            const component = { name, params };

            element.classList.add('scheduler');
            element.removeAttribute('tabindex');

            const binding = bindingContext
                .extend({
                    $change: changeCell,
                    $currenttab: ko.observable(null),
                    $tabindex: tabIndex
                });

            _.extend(window, { binding });

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
            <div data-bind=""></div>
            <div data-bind="popper: true"></div>
            <style type="text/css" rel="stylesheet">
                .scheduler .data-info {
                    min-height: 48px !important;
                }
                .scheduler .data-info .work-type .join,
                .scheduler .data-info .work-type .leave {
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
                    float: left;
                    width: 50%;
                    height: 24px;
                    font-size: 12px;
                    text-align: center;
                    box-sizing: border-box;                    
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
                    padding: 0 !important;
                    text-align: center !important;
                    font-size: 13px !important;
                    border-radius: 0 !important;
                    border: 0 !important;
                }
                .scheduler .ntsControl input:focus {
                    box-shadow: 0px 0px 0px 1px #0096f2 !important;
                    background-color: rgba(0, 150, 242, 0.1) !important;
                }
                .scheduler .ntsControl.error input {
                    box-shadow: 0px 0px 0px 1px #ff6666 !important;
                    background-color: rgba(255, 102, 102, 0.1) !important;
                }
                .scheduler .event-popper {
                    top: -999px;
                    left: -999px;
                    position: absolute;
                    visibility: hidden;
                    border: 2px solid #ddd;
                    background-color: #ccc;
                    border-radius: 5px;
                    padding: 5px;
                    min-width: 220px;
                    max-width: 400px;
                    min-height: 220px;
                    max-height: 400px;
                    overflow: hidden;
                }
                .scheduler .event-popper.show {
                    visibility: visible;
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
        const COMPONENT_NAME = 'scheduler-data-info';

        @handler({
            bindingName: 'popper'
        })
        export class SChedulerRefBindingHandler implements KnockoutBindingHandler {
            init($$popper: HTMLElement, _valueAccessor: () => c.DayData<ScheduleData>, _allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                $$popper.classList.add('event-popper');
                _.extend(bindingContext, { $$popper });
            }
        }

        @handler({
            bindingName: 'scheduler-event'
        })
        export class SChedulerEventBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => c.DayData<ScheduleData>, _allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, bindingContext: KnockoutBindingContext & { $$popper: HTMLElement }): void | { controlsDescendantBindings: boolean; } {
                const dayData = valueAccessor();
                const { $$popper } = bindingContext;
                const boundind = element.getBoundingClientRect();

                const { data } = dayData;

                if (data && data.event) {
                    $(element)
                        .on('mouseover', () => {
                            const { width, x, y } = boundind;

                            $$popper.innerHTML = _.escape(data.event).replace(/\n/g, '<br />').replace(/\s/g, '&nbsp;');

                            const pbound = $$popper.getBoundingClientRect();

                            const top1 = y - 150;
                            const top2 = window.innerHeight - pbound.height - 150 - 30;

                            const left1 = x + width + 2;
                            const left2 = window.innerWidth - pbound.width - 30;

                            $$popper.style.top = `${Math.min(top1, top2)}px`;
                            $$popper.style.left = `${Math.min(left1, left2)}px`;

                            $$popper.classList.add('show');
                        })
                        .on('mouseleave', () => {
                            $$popper.innerHTML = '';

                            $$popper.style.top = '-999px';
                            $$popper.style.left = '-999px';
                            $$popper.classList.remove('show');
                        });
                }

                return { controlsDescendantBindings: true };
            }
        }

        @handler({
            bindingName: COMPONENT_NAME,
            validatable: true,
            virtual: false
        })
        export class DataInfoComponentBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => c.DayData<ScheduleData>, _allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = COMPONENT_NAME;
                const dayData = valueAccessor();
                const params = { dayData, context: bindingContext };
                const component = { name, params };

                ko.applyBindingsToNode(element, { component }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: COMPONENT_NAME,
            template: `
            <div class="work-type cf">
                <div class="join" data-bind="text: text.wtype"></div>
                <div class="leave" data-bind="text: text.wtime"></div>
            </div>
            <div class="work-time cf">
                <div class="join">
                    <input class="begin" data-bind="
                        event: {
                            blur: function() { $component.hideInput.apply($component, ['begin']) },
                            click: function() { $component.showInput.apply($component, ['begin']) },
                            focus: function(evt) { $component.registerTab.apply($component, ['begin'], evt) }
                        },
                        attr: {
                            tabindex: $component.data.context.$tabindex
                        },
                        ntsTimeEditor: {
                            name: 'Duration',
                            constraint: 'SampleTimeDuration',
                            mode: 'time',
                            inputFormat: 'time',
                            value: $component.model.begin,
                            readonly: ko.unwrap($component.readonly).begin
                        }" />
                </div>
                <div class="leave">
                    <input class="finish" data-bind="
                        event: {
                            blur: function() { $component.hideInput.apply($component, ['finish']) },
                            click: function() { $component.showInput.apply($component, ['finish']) },
                            focus: function(evt) { $component.registerTab.apply($component, ['finish'], evt) }
                        },
                        attr: {
                            tabindex: $component.data.context.$tabindex
                        },
                        ntsTimeEditor: {
                            name: 'Duration',
                            constraint: 'SampleTimeDuration',
                            mode: 'time',
                            inputFormat: 'time',
                            value: $component.model.finish,
                            readonly: ko.unwrap($component.readonly).finish
                        }" />
                </div>
            </div>
            `
        })
        export class DataInfoComponent extends ko.ViewModel {
            model: WorkTimeRange = {
                begin: ko.observable(null),
                finish: ko.observable(null)
            };

            click: WorkTimeRange<number> = {
                begin: ko.observable(0),
                finish: ko.observable(0)
            };

            readonly!: KnockoutComputed<{ begin: boolean; finish: boolean; }>;

            text: {
                wtype: string;
                wtime: string;
            } = {
                    wtime: '',
                    wtype: ''
                };

            constructor(private data: { dayData: c.DayData<ScheduleData>; context: BindingContext }) {
                super();
            }

            created() {
                const vm = this;
                const { data, model } = vm;
                const { context, dayData } = data;

                let b: number | null = null;
                let f: number | null = null;

                if (dayData.data) {
                    const { data } = dayData;

                    vm.text.wtype = data.wtype;
                    vm.text.wtime = data.wtime;

                    if (data.value) {
                        const { value } = data;

                        model.begin(value.begin);
                        model.finish(value.finish);
                    }
                }

                model.begin
                    .subscribe((c: number) => {
                        if (_.isNumber(c) && b !== c) {
                            b = c;
                            const clone = _.cloneDeep(dayData);

                            clone.data.value.begin = c;
                            setTimeout(() => {
                                context.$change.apply(context.$vm, [clone]);
                            }, 100);
                        }
                    });

                model.finish
                    .subscribe(c => {
                        if (_.isNumber(c) && f !== c) {
                            f = c;
                            const clone = _.cloneDeep(dayData);

                            clone.data.value.finish = c;
                            setTimeout(() => {
                                context.$change.apply(context.$vm, [clone]);
                            }, 100);
                        }
                    });

                vm.readonly = ko.computed({
                    read: () => {
                        const begin = ko.unwrap(vm.click.begin);
                        const finish = ko.unwrap(vm.click.finish);

                        return {
                            begin: false && begin < 2,
                            finish: false && finish < 2
                        }
                    },
                    owner: vm
                });
            }

            mounted() {
                const vm = this;
                const { data } = vm;
                const { dayData, context } = data;
                const $current = ko.unwrap(context.$currenttab);

                if ($current) {
                    if (moment($current.date).isSame(dayData.date)) {
                        context.$currenttab(null);

                        $(vm.$el).find(`.${$current.input}`).focus();
                    }
                }
            }

            hideInput(input: 'begin' | 'finish') {
                const vm = this;

                if (input === 'begin') {
                    vm.click.begin(0);
                } else if (input === 'finish') {
                    vm.click.finish(0);
                }

                vm.data.context.$currenttab(null);
            }

            showInput(input: 'begin' | 'finish') {
                const vm = this;

                if (input === 'begin') {
                    const i = vm.click.begin();

                    vm.click.begin(i + 1);
                } else if (input === 'finish') {
                    const i = vm.click.finish();

                    vm.click.finish(i + 1);
                }
            }

            registerTab(input: 'begin' | 'finish', evt: KeyboardEvent) {
                const vm = this;
                const { data } = vm;

                vm.data.context
                    .$currenttab({
                        date: data.dayData.date,
                        input
                    });
            }
        }

        interface WorkTimeRange<T = number | null> {
            begin: KnockoutObservable<T>;
            finish: KnockoutObservable<T>;
        }

        interface BindingContext extends KnockoutBindingContext {
            $vm: any,
            $change: Function,
            $tabindex: string | number;
            $currenttab: KnockoutObservable<null | {
                date: Date;
                input: 'begin' | 'finish';
            }>;
        }
    }
}