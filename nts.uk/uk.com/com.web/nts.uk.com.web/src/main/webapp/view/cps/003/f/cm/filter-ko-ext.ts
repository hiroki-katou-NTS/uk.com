module nts.custombinding {
    // define ITEM_SINGLE_TYPE
    // type of item if it's single item
    enum ITEM_SINGLE_TYPE {
        STRING = 1,
        NUMERIC = 2,
        DATE = 3,
        TIME = 4,
        TIMEPOINT = 5,
        SELECTION = 6,
        SEL_RADIO = 7,
        SEL_BUTTON = 8,
        READONLY = 9,
        RELATE_CATEGORY = 10,
        NUMBERIC_BUTTON = 11,
        READONLY_BUTTON = 12
    }

    interface IItemData {
        constraint: string;
        itemCode: string;
        dataType: ITEM_SINGLE_TYPE;
        amount: number;
        decimalLength?: number;
        selectionItems: Array<any>;
    }

    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class FilterBoxControl implements KnockoutBindingHandler {
        init = (element: HTMLElement, valueAccessor: () => { itemData: KnockoutObservable<IItemData>; value: KnockoutObservable<any>; enable: KnockoutObservable<boolean>; }, allBindingsAccessor: () => { settingChange: Function }, viewModel: any, bindingContext: KnockoutBindingContext) => {
            let accessor = valueAccessor(),
                itemData: IItemData = ko.toJS(accessor.itemData),
                template = {
                    str: `<input data-bind="ntsTextEditor: { name: i18n('CPS003_78'), value: value, constraint: constraint,enable: enable, required: false }" />`,
                    numb: `<input data-bind="ntsNumberEditor: { name: i18n('CPS003_78'), value: value, constraint: constraint, enable: enable, required: false, option: options }" />`,
                    date: `<div data-bind="ntsDatePicker: { name: i18n('CPS003_78'), constraint: constraint,value: value, enable: enable, required: false, dateFormat: 'YYYY/MM/DD' }"></div>`,
                    time: `<input data-bind="ntsTimeEditor: { name: i18n('CPS003_78'), value: value, constraint: constraint,inputFormat: 'time', mode: 'time', enable: enable, required: false }" />`,
                    timep: `<input data-bind="ntsTimeWithDayEditor: { name: i18n('CPS003_78'), value: value, constraint: constraint, enable: enable, required: false }" />`,
                    radio: `<div id="combo-box" data-bind="ntsComboBox: {
                                name: i18n('CPS003_78'), 
                                options: itemOptions,
                                optionsValue: 'optionValue',
                                visibleItemsCount: 5,
                                value: value,
                                optionsText: 'optionText',
                                editable: false,
                                required: false,
                                selectFirstIfNull: true,
                                enable: enable,
                                columns: [
                                    { prop: 'optionText', length: 10 }
                                ]}"></div>`,
                    button: `<button data-bind="text: i18n('CPS001_106'), enable: enable, click: openDialog"></button>
                             <label class="value-text readonly" data-bind="html: textValue"></label>`
                }, vm = {
                    i18n: nts.uk.resource.getText,
                    value: ko.observable(),
                    constraint: '',
                    options: {
                        grouplength: 0,
                        decimallength: 0
                    },
                    enable: ko.observable(false),
                    textValue: ko.observable(''),
                    itemOptions: ko.observableArray([]),
                    openDialog: () => {
                        let itemData = ko.toJS(accessor.itemData);

                        if (['IS00131', 'IS00140',
                            'IS00158', 'IS00167',
                            'IS00176', 'IS00149',
                            'IS00194', 'IS00203',
                            'IS00212', 'IS00221',
                            'IS00230', 'IS00239',
                            'IS00185'].indexOf(itemData.itemCode) > -1) {
                            setShared('parentCodes', {
                                workTypeCodes: [],
                                selectedWorkTypeCode: '',
                                workTimeCodes: [],
                                selectedWorkTimeCode: ''
                            }, true);

                            modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                                let childData: any = getShared('childData');
                                vm.value(childData.selectedWorkTimeCode);

                                vm.textValue(`${childData.selectedWorkTimeCode} ${childData.selectedWorkTimeName}`);
                            });
                        } else {
                            setShared("KDL002_isShowNoSelectRow", true);
                            setShared("KDL002_Multiple", false, true);
                            setShared('kdl002isSelection', false, true);
                            setShared("KDL002_SelectedItemId", [vm.value()], true);
                            setShared("KDL002_AllItemObj", itemData.selectionItems.map(x => x.optionValue), true);

                            modal('at', '/view/kdl/002/a/index.xhtml').onClosed(() => {
                                let childData: Array<any> = getShared('KDL002_SelectedNewItem');

                                if (childData[0]) {
                                    vm.value(childData[0].code);

                                    vm.textValue(`${childData[0].code} ${childData[0].name}`);
                                }
                            });
                        }
                    }
                };

            // bind data out
            vm.value.subscribe(v => accessor.value(v));

            // update value of binding
            ko.computed({
                read: () => {
                    let value = ko.toJS(accessor.value),
                        enable = !!ko.toJS(accessor.enable);

                    // update obsr
                    vm.enable(enable);
                    vm.textValue('');

                    if (enable) {
                        vm.value(value);

                        let ctrl = element.querySelector('.ntsControl[tabindex]');

                        if (ctrl) {
                            ctrl.focus();
                        } else {
                            let input = element.querySelector('input');

                            if (input) {
                                input.focus();
                            }
                        }
                    } else {
                        vm.value('');

                        switch (itemData.dataType) {
                            default:
                                break;
                            case ITEM_SINGLE_TYPE.DATE:
                                break;
                            case ITEM_SINGLE_TYPE.TIME:
                            case ITEM_SINGLE_TYPE.STRING:
                            case ITEM_SINGLE_TYPE.TIMEPOINT:
                            case ITEM_SINGLE_TYPE.NUMERIC:
                                $(element).find('input').ntsError('clear');
                                break;
                            case ITEM_SINGLE_TYPE.SELECTION:
                            case ITEM_SINGLE_TYPE.SEL_RADIO:
                                break;
                            case ITEM_SINGLE_TYPE.SEL_BUTTON:
                                break;
                        }
                    }
                }
            });

            ko.computed({
                read: () => {
                    itemData = ko.toJS(accessor.itemData);

                    // clear old value
                    vm.value('');

                    vm.constraint = itemData.constraint;

                    // bind items to dropdownList (if avaiable)
                    vm.itemOptions(itemData.selectionItems || []);

                    // clean binding
                    ko.cleanNode(element);
                    nts.uk.ui.errors.clearAll();

                    switch (itemData.dataType) {
                        default:
                            ko.utils.setHtml(element, '');
                            break;
                        case ITEM_SINGLE_TYPE.DATE:
                            ko.utils.setHtml(element, template.date);
                            break;
                        case ITEM_SINGLE_TYPE.STRING:
                            ko.utils.setHtml(element, template.str);
                            break;
                        case ITEM_SINGLE_TYPE.TIME:
                            ko.utils.setHtml(element, template.time);
                            break;
                        case ITEM_SINGLE_TYPE.TIMEPOINT:
                            ko.utils.setHtml(element, template.timep);
                            break;
                        case ITEM_SINGLE_TYPE.NUMERIC:
                            ko.utils.setHtml(element, template.numb);

                            if (itemData.amount) {
                                vm.options.grouplength = 3;
                            } else {
                                vm.options.grouplength = 0;
                            }

                            vm.options.decimallength = itemData.decimalLength;
                            break;
                        case ITEM_SINGLE_TYPE.SELECTION:
                        case ITEM_SINGLE_TYPE.SEL_RADIO:
                            ko.utils.setHtml(element, template.radio);
                            break;
                        case ITEM_SINGLE_TYPE.SEL_BUTTON:
                            ko.utils.setHtml(element, template.button);
                            break;
                    }

                    // re binding viewModel to view
                    ko.applyBindingsToDescendants(vm, element);
                }
            });

            return { controlsDescendantBindings: true };
        }
    }
}

ko.bindingHandlers["cps003fFilter"] = new nts.custombinding.FilterBoxControl();