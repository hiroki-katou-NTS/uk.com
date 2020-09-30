/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
    import c = nts.uk.ui.calendar;

    export interface ScheduleData {
        wtype: string;
        wtime: string;
        value: {
            begin: number | null;
            finish: number | null;
        }
    }

    const COMPONENT_NAME = 'scheduler';

    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class SchedulerComponentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => c.DayData<ScheduleData>[], allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const schedules = valueAccessor();
            const width = allBindingsAccessor.get('width');
            const baseDate = allBindingsAccessor.get('baseDate');
            const clickCell = allBindingsAccessor.get('click-cell');
            const tabIndex = element.getAttribute('tabindex') || allBindingsAccessor.get('tabindex') || '1';
            const params = { width, baseDate, schedules, clickCell, tabIndex };
            const component = { name, params };

            element.classList.add('scheduler');
            element.removeAttribute('tabindex');

            ko.applyBindingsToNode(element, { component }, bindingContext);

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
            <style type="text/css" rel="stylesheet">
                .scheduler .data-info {
                    min-height: 48px !important;
                }
                .scheduler .data-info .work-type .join,
                .scheduler .data-info .work-type .leave {
                    border-bottom: 1px dashed #808080;
                }
                .scheduler .data-info .work-type .join,
                .scheduler .data-info .work-time .join {
                    border-right: 1px dashed #808080;
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
                    border-color: transparent !important;
                }
                .scheduler .ntsControl.error input {
                    box-shadow: 0 0 1px 1px #ff6666 !important;
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
                                dataInfo: 'scheduler-data-info'
                            };
                        });
                });
        }
    }

    export module controls {
        const COMPONENT_NAME = 'scheduler-data-info';
        const COMPONENT_WT_NAME = 'scheduler-work-time';

        @handler({
            bindingName: COMPONENT_NAME,
            validatable: true,
            virtual: false
        })
        export class DataInfoComponentBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => c.DayData<ScheduleData>, allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = COMPONENT_NAME;
                const dayData = valueAccessor();
                const tabIndex = element.getAttribute('tabindex') || '1';
                const params = { dayData, tabIndex };
                const component = { name, params };

                element.removeAttribute('tabindex');

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
                <div class="join" data-bind="${COMPONENT_WT_NAME}: $component.model.begin"></div>
                <div class="leave" data-bind="${COMPONENT_WT_NAME}: $component.model.finish"></div>
            </div>
            `
        })
        export class DataInfoComponent extends ko.ViewModel {
            model: WorkTimeRange = {
                begin: ko.observable(null),
                finish: ko.observable(null)
            };

            text: {
                wtype: string;
                wtime: string;
            } = {
                    wtime: '',
                    wtype: ''
                };

            constructor(private data: { dayData: c.DayData<ScheduleData>; tabIndex: string; vm: any }) {
                super();
            }

            created() {
                const vm = this;
                const { data, model } = vm;
                const { dayData } = data;

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
                    .subscribe(c => {
                        console.log(c);
                    });

                model.finish
                    .subscribe(c => {
                        console.log(c);
                    });
            }
        }

        @handler({
            bindingName: COMPONENT_WT_NAME,
            validatable: true,
            virtual: false
        })
        export class WorkTimeComponentBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => KnockoutObservable<string>, _allBindingsAccessor: KnockoutAllBindingsAccessor, _viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
                const name = COMPONENT_WT_NAME;
                const value = valueAccessor();
                const params = { value };
                const component = { name, params };

                ko.applyBindingsToNode(element, { component }, bindingContext);

                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: COMPONENT_WT_NAME,
            template: `
            <input data-bind="
                event: {
                    blur: hideInput,
                    click: showInput
                },
                ntsTimeEditor: {
                    name: 'Duration',
                    constraint: 'SampleTimeDuration',
                    value: model,
                    inputFormat: 'time',
                    mode: 'time',
                    enable: true,
                    readonly: ko.unwrap(click) < 2,
                    required: false
                }" />
            `
        })
        export class WorkTimeComponent extends ko.ViewModel {
            click: KnockoutObservable<number> = ko.observable(0);

            model!: KnockoutObservable<number | null>;

            constructor(private data: { value: KnockoutObservable<number | null> }) {
                super();
            }

            created() {
                const vm = this;
                const { data } = vm;

                vm.model = data.value;
            }

            mounted() {
                const vm = this;

                $(vm.$el).find('[data-bind]').removeAttr('data-bind');
            }

            hideInput() {
                const vm = this;
                const { click } = vm;

                click(0);
            }

            showInput() {
                const vm = this;
                const { click } = vm;

                click(click() + 1);
            }
        }

        interface WorkTimeRange {
            begin: KnockoutObservable<number | null>;
            finish: KnockoutObservable<number | null>;
        }
    }
}