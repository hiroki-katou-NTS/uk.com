/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

/**
 * Use
 * Reference path of javascript file
 *  <com:scriptfile path="{path_to_kcp}/kcp/013/component/kcp013.vm.js" />
 * 
 * Call component by binding:
    <div data-bind="
        kcp013: ko.observable(''),
        workplaceId: ko.observable('')
        dataSources: ko.observableArray([]),
        filter: ko.observable(false),
        disabled: ko.observable(false),
        show-mode: 1,
        tabindex: 0,
        width: 570,
    "></div>
 *
 * Explain:
 *  kcp013: the selected value from combobox (single select) or listbox (multi select)
 *      single select:  KnockoutObservable<string> | string
 *      multi select:   KnockoutObservableArray<string> | string[]
    workplaceId: workplaceid of employee
        KnockoutObservable<string> | string
    dataSources: datasource bind to combobox or listbox after component mount or data changed
        KnockoutObservableArray<WorkTimeModel> (please see at bottom file for unknow model structure)
    filter: show or hide checkbox filter
        KnockoutObservable<boolean> | boolean
    disabled: enable or disable combobox/listbox and checkbox filter
        KnockoutObservable<boolean> | boolean
    show-mode:
        KnockoutObservable<SHOW_MODE> | 0 | 1 | 2 | 3
            0: not show none & deffered option
            1: show none option
            2: show deffered option
            3: show bottle none & deffered options
    tabindex: tabindex of combobox/listbox and checkbox (if show)
        number
    width: width of combobox/listbox (checkbox default is 95px)
        KnockoutObservable<number> | number
 */

module nts.uk.ui.at.kcp013.a {
    enum SHOW_MODE {
        // not has any option
        NOT_SET = 0,
        // show none option
        NONE = 1,
        // show deffered option
        DEFFERED = 2,
        // show none & deffered option
        BOTTLE = 3
    }

    export interface Parameters {
        width: KnockoutObservable<number>;
        tabIndex: KnockoutObservable<number | string>;
        filter: KnockoutObservable<boolean>;
        disabled: KnockoutObservable<boolean>;
        workplaceId: KnockoutObservable<string>;
        selected: KnockoutObservable<string> | KnockoutObservableArray<string>;
        dataSources: KnockoutObservableArray<WorkTimeModel>;
        showMode: KnockoutObservable<SHOW_MODE>;
    }

    const COMPONENT_NAME = 'kcp013';

    const GET_WORK_HOURS_URL = 'at/record/stamp/workhours/workhours';
    const GET_ALL_WORK_HOURS_URL = 'at/record/stamp/workhours/getallworkhours';

    type TF = (format: string, value: number) => string;

    const SCF = 'Clock_Short_HM';
    const format: TF = _.get(nts.uk.time, 'format.byId');

    @handler({
        bindingName: COMPONENT_NAME
    })
    export class KCP013ComponentBindingHandler implements KnockoutBindingHandler {
        init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;

            const selected = valueAccessor();
            const filter = allBindingsAccessor.get('filter');
            const disabled = allBindingsAccessor.get('disabled');
            const workplaceId = allBindingsAccessor.get('workplace-id');
            const showMode = allBindingsAccessor.get('show-mode');
            const tabIndex = allBindingsAccessor.get('tabindex') || element.getAttribute('tabindex') || '0';
            const dataSources = allBindingsAccessor.get('dataSources');
            const width = allBindingsAccessor.get('width');

            const params = { width, tabIndex, selected, filter, disabled, workplaceId, showMode, dataSources };
            const component = { name, params };

            element.classList.add('cf');
            element.classList.add('kcp-013');
            element.classList.add('ntsControl');
            element.removeAttribute('tabindex');

            ko.applyBindingsToNode(element, { component }, bindingContext);

            return { controlsDescendantBindings: true };
        }
    }

    @component({
        name: COMPONENT_NAME,
        template: `
        <div data-bind="
            attr: {
                tabindex: $component.data.tabIndex
            },
            ntsComboBox: {
                optionsText: 'name',
                optionsValue: 'id',
                width: $component.data.width,
                enable: !ko.unwrap($component.data.disabled),
                editable: false,
                visibleItemsCount: 10,
                value: $component.data.selected,
                options: $component.data.dataSources,
                columns: [
                    { prop: 'code', length: 5 },
                    { prop: 'name', length: 1 },
                    { prop: 'tzStartToEnd1', length: 1 },
                    { prop: 'tzStartToEnd2', length: 1 },
                    { prop: 'workStyleClassfication', length: 1 },
                    { prop: 'remark', length: 1 }
                ]
            }">
        </div>
        <div data-bind="
            attr: {
                tabindex: $component.data.tabIndex
            },
            visible: $component.data.filter,
            ntsCheckBox: {
                checked: $component.filter,
                enable: !ko.unwrap($component.data.disabled),                
                text: $component.$i18n('KCP013_3')
            }"></div>
        <style rel="stylesheet" data-bind="html: $component.style"></style>
        `
    })
    export class ViewModel extends ko.ViewModel {
        style!: KnockoutComputed<string>;
        filter: KnockoutObservable<boolean> = ko.observable(false);

        /**
         * Init default data by constructor
         * @param data 
         */
        constructor(private data: Parameters) {
            super();

            const vm = this;

            if (!data) {
                vm.data = {
                    disabled: ko.observable(false),
                    filter: ko.observable(true),
                    selected: ko.observable(''),
                    showMode: ko.observable(SHOW_MODE.NOT_SET),
                    tabIndex: ko.observable(0),
                    width: ko.observable(450),
                    workplaceId: ko.observable(''),
                    dataSources: ko.observableArray([])
                };
            }

            const {
                workplaceId,
                width,
                tabIndex,
                showMode,
                selected,
                filter,
                disabled,
                dataSources
            } = vm.data;

            if (workplaceId === undefined) {
                vm.data.workplaceId = ko.observable('');
            }

            if (width === undefined) {
                vm.data.width = ko.observable(450);
            }

            if (tabIndex === undefined) {
                vm.data.tabIndex = ko.observable(0);
            }

            if (showMode === undefined) {
                vm.data.showMode = ko.observable(SHOW_MODE.NOT_SET);
            }

            if (selected === undefined) {
                vm.data.selected = ko.observable('');
            }

            if (filter === undefined) {
                vm.data.filter = ko.observable(false);
            }

            if (disabled === undefined) {
                vm.data.disabled = ko.observable(false);
            }

            if (dataSources === undefined) {
                vm.data.dataSources = ko.observableArray([]);
            }
        }

        created() {
            const vm = this;
            const { data } = vm;
            const subscribe = (workPlaceId: string) => {
                const fillter = ko.unwrap(vm.filter);
                const showMode = ko.unwrap(data.showMode);
                const selected = ko.toJS(data.selected);

                const filterable = fillter && workPlaceId;

                const cmd = filterable ? { fillter, workPlaceId } : undefined;
                const url = filterable ? GET_WORK_HOURS_URL : GET_ALL_WORK_HOURS_URL;

                vm.$ajax('at', url, cmd)
                    .then((data: WorkTimeModel[]) => {
                        const items: WorkTimeModel[] = [];

                        if ([SHOW_MODE.NONE, SHOW_MODE.BOTTLE].indexOf(showMode) > -1) {
                            items.push({
                                id: 'none',
                                code: '',
                                name: vm.$i18n('KCP013_5'),
                                remark: '',
                                tzEnd1: 0,
                                tzEnd2: 0,
                                tzStart1: 0,
                                tzStart2: 0,
                                useDistintion: 0,
                                workStyleClassfication: '',
                                tzStartToEnd1: '',
                                tzStartToEnd2: ''
                            });
                        }

                        if ([SHOW_MODE.DEFFERED, SHOW_MODE.BOTTLE].indexOf(showMode) > -1) {
                            items.push({
                                id: 'deferred',
                                code: '',
                                name: vm.$i18n('KCP013_6'),
                                remark: '',
                                tzEnd1: 0,
                                tzEnd2: 0,
                                tzStart1: 0,
                                tzStart2: 0,
                                useDistintion: 0,
                                workStyleClassfication: '',
                                tzStartToEnd1: '',
                                tzStartToEnd2: ''
                            });
                        }

                        items.push(...data.map((m) => ({
                            ...m,
                            id: m.code,
                            tzStartToEnd1: `${format(SCF, m.tzStart1)}${vm.$i18n('KCP013_4')}${format(SCF, m.tzEnd1)}`,
                            tzStartToEnd2: m.useDistintion === 1 && m.tzStart2 && m.tzEnd2 ? `${format(SCF, m.tzStart2)}${vm.$i18n('KCP013_4')}${format(SCF, m.tzEnd2)}` : ''
                        })));

                        $.Deferred()
                            .resolve()
                            .then(() => {
                                if (ko.isObservable(vm.data.dataSources)) {
                                    vm.data.dataSources(items);
                                }
                            })
                            .then(() => {
                                if (items.map(m => m.code).indexOf(selected) > -1) {
                                    if (ko.isObservable(vm.data.selected)) {
                                        vm.data.selected(selected);
                                    }
                                }
                            });
                    });
            };

            /**
             * Calculate width of componet by width params & filter mode
             */
            vm.style = ko.computed({
                read: () => {
                    const filter = ko.unwrap(data.filter);
                    const width = ko.unwrap(data.width);

                    return `
                        .kcp-013 {
                            width: ${width + (filter ? 95 : 0)}px;
                        }
                        .kcp-013 .checkbox-wrapper {
                            vertical-align: top;
                            margin-top: 3px;
                        }
                    `;
                },
                owner: vm
            });

            vm.filter
                .subscribe(() => {
                    if (ko.isObservable(data.showMode)) {
                        data.showMode.valueHasMutated()
                    }
                });

            if (ko.isObservable(data.showMode)) {
                data.showMode
                    .subscribe(() => {
                        if (ko.isObservable(data.workplaceId)) {
                            data.workplaceId.valueHasMutated();
                        }
                    });
            }

            if (ko.isObservable(data.workplaceId)) {
                data.workplaceId.subscribe(subscribe);
            }

            // call first subscribe
            subscribe(data.workplaceId());
        }
    }

    interface WorkTimeModel {
        id: string;
        code: string;
        name: string;
        tzStart1: number;
        tzEnd1: number;
        tzStart2: number;
        tzEnd2: number;
        workStyleClassfication: string;
        remark: string;
        useDistintion: number;
        tzStartToEnd1: string;
        tzStartToEnd2: string;
    }
}