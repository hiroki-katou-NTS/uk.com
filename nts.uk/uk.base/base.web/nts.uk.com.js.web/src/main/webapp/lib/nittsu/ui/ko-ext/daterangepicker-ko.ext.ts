module nts.uk.ui.koExtentions {
    export module date.range {
        const YM_FORMAT = 'YYYY/MM';
        const DATE_FORMAT = 'YYYY/MM/DD';
        const COMPONENT_NAME = 'nts-date-range-picker';

        type DateRange = {
            startDate: string;
            endDate: string;
        };

        type JumpUnit = 'month' | 'year';
        type DRType = 'date' | 'fullDate' | 'yearmonth';
        type MaxRange = 'none' | 'oneMonth' | 'oneYear';

        interface DateRangeOptions {
            value: KnockoutObservable<DateRange | null>;
            type: DRType | KnockoutObservable<DRType>;
            enable: boolean | KnockoutObservable<boolean>;
            maxRange: MaxRange | KnockoutObservable<MaxRange>;
            jumpUnit: JumpUnit | KnockoutObservable<JumpUnit>;
            name: string | KnockoutObservable<string>;
            startName: string | KnockoutObservable<string>;
            endName: string | KnockoutObservable<string>;
            required: boolean | KnockoutObservable<boolean>;
            pickOnly: boolean | KnockoutObservable<boolean>;
            showNextPrevious: boolean | KnockoutObservable<boolean>;
            tabindex?: string | number;
        }

        @handler({
            bindingName: 'ntsDateRangePicker',
            validatable: true,
            virtual: false
        })
        export class NtsDateRangePickerBindingHandler implements KnockoutBindingHandler {
            init(element: HTMLElement, valueAccessor: () => DateRangeOptions, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): { controlsDescendantBindings: boolean } {
                const accessor = valueAccessor();

                const name = COMPONENT_NAME;
                const tabindex = Math.max(0, element.tabIndex);
                const params = { tabindex, ...accessor };

                // show or hide next & preview buttons
                ko.computed({
                    read: () => {
                        const hideBtn = ko.unwrap<boolean>(accessor.showNextPrevious) === false;

                        if (!hideBtn) {
                            element.classList.add('has-btn');
                        } else {
                            element.classList.remove('has-btn');
                        }
                    },
                    disposeWhenNodeIsRemoved: element
                });

                ko.applyBindingsToNode(element, { component: { name, params } }, bindingContext);

                // add inline display as control
                element
                    .classList
                    .add('nts-daterange-picker');

                // disable container focusable
                element.removeAttribute('tabindex');

                // notify binding
                return { controlsDescendantBindings: true };
            }
        }

        @component({
            name: 'nts-date-range-picker',
            template: `
                <button data-bind="
                    attr: {
                        tabindex: $component.tabindex
                    },
                    i18n: '◀',
                    enable: $component.enable,
                    click: $component.reduce,
                    timeClick: -1"></button>
                <div class="start" data-bind="
                    attr: { 
                        tabindex: $component.tabindex
                    },
                    ntsDatePicker: {
                        enable: $component.enable,
                        name: $component.name.start,
                        value: $component.model.start,
                        dateType: $component.dateType,
                        dateFormat: $component.dateType,
                        required: $component.required.start
                    }"></div>
                <div data-bind="i18n: '～'"></div>
                <div class="end" data-bind="
                    attr: { 
                        tabindex: $component.tabindex
                    },
                    ntsDatePicker: {
                        enable: $component.enable,
                        name: $component.name.end,
                        value: $component.model.end,
                        dateType: $component.dateType,
                        dateFormat: $component.dateType,
                        required: $component.required.end
                    }"></div>
                <button data-bind="
                    attr: {
                        tabindex: $component.tabindex
                    },
                    i18n: '▶',
                    enable: $component.enable,
                    click: $component.increase,
                    timeClick: -1"></button>
            `
        })
        export class NtsDateRangePickerViewModel extends ko.ViewModel {
            enable!: KnockoutComputed<boolean>;
            tabindex!: KnockoutComputed<number | string | null>;
            dateType!: KnockoutComputed<'date' | 'yearmonth'>;

            model: ModelValue = {
                start: ko.observable(null).extend({ deferred: true }),
                end: ko.observable(null).extend({ deferred: true })
            };

            name!: ModelName & { range: KnockoutComputed<string> };
            required!: ModelRequired;

            constructor(private params: DateRangeOptions) {
                super();

                this.dateType = ko.computed({
                    read: () => {
                        return ko.unwrap<DRType>(params.type) === 'yearmonth' ? 'yearmonth' : 'date';
                    }
                });

                this.enable = ko.computed({
                    read: () => {
                        return ko.unwrap<boolean>(params.enable) !== false;
                    }
                });

                this.name = {
                    start: ko.computed({
                        read: () => {
                            const name = ko.unwrap<string>(params.name);
                            const startName = ko.unwrap<string>(params.startName);

                            return startName || `${name || '期間入力フォーム'}開始`;
                        }
                    }),
                    end: ko.computed({
                        read: () => {
                            const name = ko.unwrap<string>(params.name);
                            const endName = ko.unwrap<string>(params.endName);

                            return endName || `${name || '期間入力フォーム'}終了`;
                        }
                    }),
                    range: ko.computed({
                        read: () => {
                            const name = ko.unwrap<string>(params.name);

                            return name || '期間入力フォーム';
                        }
                    })
                };

                this.required = {
                    start: ko.computed({
                        read: () => {
                            return ko.unwrap<boolean>(params.required) === true;
                        }
                    }),
                    end: ko.computed({
                        read: () => {
                            return ko.unwrap<boolean>(params.required) === true;
                        }
                    })
                };

                this.tabindex = ko.computed({
                    read: () => {
                        const enabled = ko.unwrap<boolean>(params.enable) !== false;
                        const tabindex = ko.unwrap<number | string>(params.tabindex);

                        return enabled ? tabindex : null;
                    }
                });

                this.model.start
                    .subscribe((start: string) => {
                        const end = ko.unwrap<string>(this.model.end);
                        const format = ko.unwrap(this.dateType) === 'yearmonth' ? YM_FORMAT : DATE_FORMAT;

                        const value = {
                            startDate: start ? moment(start, DATE_FORMAT).format(format) : '',
                            endDate: end ? moment(end, DATE_FORMAT).format(format) : ''
                        };

                        if (!_.isEqual(value, ko.unwrap(params.value))) {
                            params.value(value);
                        }
                    });

                this.model.end
                    .subscribe((end: string) => {
                        const start = ko.unwrap<string>(this.model.start);
                        const format = ko.unwrap(this.dateType) === 'yearmonth' ? YM_FORMAT : DATE_FORMAT;

                        const value = {
                            startDate: start ? moment(start, DATE_FORMAT).format(format) : '',
                            endDate: end ? moment(end, DATE_FORMAT).format(format) : ''
                        };

                        if (!_.isEqual(value, ko.unwrap(params.value))) {
                            params.value(value);
                        }
                    });
            }

            created() {

            }

            mounted() {
                const vm = this;
                const { model, params, dateType } = vm;
                const modelUpdate = (v: DateRange | null) => {
                    if (v) {
                        const { startDate, endDate } = v;
                        const format = ko.unwrap(dateType) === 'yearmonth' ? YM_FORMAT : DATE_FORMAT;

                        if (!startDate) {
                            model.start('');
                        } else {
                            const value = moment(startDate, DATE_FORMAT).format(format);

                            if (value !== ko.unwrap(model.start)) {
                                model.start(value);
                            }
                        }

                        if (!endDate) {
                            model.end('');
                        } else {
                            const value = moment(endDate, DATE_FORMAT).format(format);

                            if (value !== ko.unwrap(model.end)) {
                                model.end(value);
                            }
                        }
                    }
                };

                if (ko.isObservable(params.value)) {
                    params.value.subscribe(modelUpdate);
                }

                modelUpdate(ko.toJS(params.value));

                $(vm.$el)
                    .removeAttr('data-bind')
                    .find('[data-bind]')
                    .removeAttr('data-bind');

                vm.$nextTick(() => vm.validate());
            }

            destroyed() {
                const vm = this;

                // for release memory purpose
                // dispose all computed at here
                vm.enable.dispose();

                vm.tabindex.dispose();

                vm.dateType.dispose();

                vm.required.start.dispose();
                vm.required.end.dispose();
            }

            reduce() {
                const vm = this;
                const { model, params } = vm;
                const start = ko.unwrap<string | null>(model.start);
                const end = ko.unwrap<string | null>(model.end);
                const ju = ko.unwrap<JumpUnit>(params.jumpUnit) || 'month';

                if (start) {
                    const reduceStart = moment(start, DATE_FORMAT).subtract(1, ju).startOf('day').format(DATE_FORMAT);

                    model.start(reduceStart);
                }

                if (end) {
                    const reduceEnd = moment(end, DATE_FORMAT).subtract(1, ju).startOf('day').format(DATE_FORMAT);

                    model.end(reduceEnd);
                }
            }

            increase() {
                const vm = this;
                const { model, params } = vm;
                const start = ko.unwrap<string | null>(model.start);
                const end = ko.unwrap<string | null>(model.end);
                const ju = ko.unwrap<JumpUnit>(params.jumpUnit) || 'month';

                if (start) {
                    const increaseStart = moment(start, DATE_FORMAT).add(1, ju).startOf('day').format(DATE_FORMAT);

                    model.start(increaseStart);
                }

                if (end) {
                    const increaseEnd = moment(end, DATE_FORMAT).add(1, ju).startOf('day').format(DATE_FORMAT);

                    model.end(increaseEnd);
                }
            }


            validate() {
                const vm = this;
                const { $el, name, model, params } = vm;
                const { start, end } = model;
                const { maxRange, type } = params;
                const $start = $($el).find('input').first();
                const $end = $($el).find('input').last();

                const validate = (start: string, end: string) => {
                    const ms = moment(start, DATE_FORMAT);
                    const me = moment(end, DATE_FORMAT);

                    $start
                        .ntsError('clearByCode', 'MsgB_21')
                        .ntsError('clearByCode', 'MsgB_22')
                        .ntsError('clearByCode', 'MsgB_23');

                    $end
                        .ntsError('clearByCode', 'MsgB_21')
                        .ntsError('clearByCode', 'MsgB_22')
                        .ntsError('clearByCode', 'MsgB_23');

                    if (!!start && !!end) {
                        if (me.isBefore(ms, 'date')) {
                            if (!$start.ntsError('hasError')) {
                                $start.ntsError('set', vm.$i18n.message('MsgB_21', [ko.unwrap(name.range)]), 'MsgB_21');
                            }

                            if (!$end.ntsError('hasError')) {
                                $end.ntsError('set', vm.$i18n.message('MsgB_21', [ko.unwrap(name.range)]), 'MsgB_21');
                            }
                        } else if (ko.unwrap(type) !== 'yearmonth' && ko.unwrap(maxRange) === 'oneMonth') {
                            if (!ms.isSame(me, 'month')) {
                                if (!$start.ntsError('hasError')) {
                                    $start.ntsError('set', vm.$i18n.message('MsgB_22', [ko.unwrap(name.range)]), 'MsgB_22');
                                }

                                if (!$end.ntsError('hasError')) {
                                    $end.ntsError('set', vm.$i18n.message('MsgB_22', [ko.unwrap(name.range)]), 'MsgB_22');
                                }
                            }
                        } else if (ko.unwrap(maxRange) === 'oneYear') {
                            if (!ms.isSame(me, 'year')) {
                                if (!$start.ntsError('hasError')) {
                                    $start.ntsError('set', vm.$i18n.message('MsgB_23', [ko.unwrap(name.range)]), 'MsgB_23');
                                }

                                if (!$end.ntsError('hasError')) {
                                    $end.ntsError('set', vm.$i18n.message('MsgB_23', [ko.unwrap(name.range)]), 'MsgB_23');
                                }
                            }
                        }
                    }
                };

                start
                    .subscribe((s: string) => validate(s, ko.unwrap(end)));

                end
                    .subscribe((e: string) => validate(ko.unwrap(start), e));
            }
        }
    }

    interface ModelValue<T = KnockoutObservable<string | null>> {
        start: T;
        end: T;
    }

    interface ModelName extends ModelValue<KnockoutComputed<string>> { }

    interface ModelRequired extends ModelValue<KnockoutComputed<boolean>> { }
}