module cps003.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import close = nts.uk.ui.windows.close;
    import parseTime = nts.uk.time.parseTime;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const __viewContext: any = window['__viewContext'] || {},
        writeConstraint = window['nts']['uk']['ui']['validation']['writeConstraint'],
        parseTimeWidthDay = window['nts']['uk']['time']['minutesBased']['clock']['dayattr']['create']

    export class ViewModel {
        currentItem: ICurrentItem = {
            id: ko.observable(''),
            name: ko.observable(''),
            target: ko.observable(''),
            applyFor: ko.observable('all'),
            filter: ko.observable(''),
            value: ko.observable(),
            replacer: ko.observable(''),
            itemData: ko.observable({ itemCode: '', dataType: 0, amount: 0 }),
            textView: ko.observable('')
        };

        dataSources: KnockoutObservableArray<any> = ko.observableArray([]);

        constructor() {
            let self = this,
                data: IModelDto = getShared('CPS003F_PARAM') || { id: '', baseDate: '', itemsDefIds: [] };

            // sample data
            if (data.id) {
                service.fetch.setting(data.id).done(resp => {
                    let excs = resp.perInfoData.map(m => m.itemParentCD),
                        items = _(resp.perInfoData)
                            .filter(f => excs.indexOf(f.itemCD) == -1)
                            .filter(f => data.itemsDefIds.indexOf(f.perInfoItemDefID) > -1)
                            .orderBy([])
                            .map(m => ({ id: m.perInfoItemDefID, name: m.itemName })).value();

                    self.dataSources(items);

                    // select first item
                    if (items[0]) {
                        self.currentItem.id(items[0].id);
                    }
                });
            }

            // get info of current item
            self.currentItem.id.subscribe(id => {
                service.fetch.getItemsById(id).done(item => {
                    if (item && item.itemTypeState.dataTypeState) {
                        let dts = item.itemTypeState.dataTypeState,
                            itemData: IItemData = {
                                constraint: '',
                                itemName: item.itemName,
                                itemCode: item.itemCode,
                                dataType: dts.dataTypeValue,
                                amount: !!dts.numericItemAmount,
                                decimalLength: dts.decimalPart,
                                selectionItems: []
                            }, command = {
                                itemId: item.id,
                                baseDate: moment.utc().toISOString()
                            }, constraint: any = {
                                itemName: item.itemName,
                                itemCode: item.itemCode,
                                required: item.isRequired// !!x.isRequired
                            };

                        // set name for display on F2_004
                        self.currentItem.name(item.itemName);

                        // generate primitive value
                        if (dts) {
                            switch (dts.dataTypeValue) {
                                default:
                                case ITEM_SINGLE_TYPE.STRING:
                                    constraint.valueType = "String";
                                    constraint.maxLength = dts.stringItemLength || dts.maxLength;
                                    constraint.stringExpression = /(?:)/;

                                    switch (dts.stringItemType) {
                                        default:
                                        case ITEM_STRING_TYPE.ANY:
                                            constraint.charType = 'Any';
                                            break;
                                        case ITEM_STRING_TYPE.CARDNO:
                                            constraint.itemCode = 'StampNumber';
                                            constraint.charType = 'AnyHalfWidth';
                                            constraint.stringExpression = /^[a-zA-Z0-9\s"#$%&(~|{}\[\]@:`*+?;\\/_\-><)]{1,20}$/;
                                            break;
                                        case ITEM_STRING_TYPE.EMPLOYEE_CODE:
                                            constraint.itemCode = 'EmployeeCode';
                                            constraint.charType = 'AnyHalfWidth';
                                            break;
                                        case ITEM_STRING_TYPE.ANYHALFWIDTH:
                                            constraint.charType = 'AnyHalfWidth';
                                            break;
                                        case ITEM_STRING_TYPE.ALPHANUMERIC:
                                            constraint.charType = 'AlphaNumeric';
                                            break;
                                        case ITEM_STRING_TYPE.NUMERIC:
                                            constraint.charType = 'Numeric';
                                            break;
                                        case ITEM_STRING_TYPE.KANA:
                                            constraint.charType = 'Kana';
                                            break;
                                    }
                                    break;
                                case ITEM_SINGLE_TYPE.NUMERIC:
                                case ITEM_SINGLE_TYPE.NUMBERIC_BUTTON:
                                    constraint.charType = 'Numeric';
                                    if (dts.decimalPart == 0) {
                                        constraint.valueType = "Integer";
                                    } else {
                                        constraint.valueType = "Decimal";
                                        constraint.mantissaMaxLength = dts.decimalPart;
                                    }

                                    let max = (Math.pow(10, dts.integerPart) - Math.pow(10, -(dts.decimalPart || 0)));
                                    constraint.min = dts.numericItemMin || 0;
                                    constraint.max = dts.numericItemMax || max;
                                    break;
                                case ITEM_SINGLE_TYPE.DATE:
                                    constraint.valueType = "Date";
                                    constraint.max = parseTime(dts.max, true).format() || '';
                                    constraint.min = parseTime(dts.min, true).format() || '';
                                    break;
                                case ITEM_SINGLE_TYPE.TIME:
                                    constraint.valueType = "Time";
                                    constraint.max = parseTime(dts.max, true).format();
                                    constraint.min = parseTime(dts.min, true).format();
                                    break;
                                case ITEM_SINGLE_TYPE.TIMEPOINT:
                                    constraint.valueType = "Clock";
                                    constraint.max = parseTimeWidthDay(dts.timePointItemMax).shortText;
                                    constraint.min = parseTimeWidthDay(dts.timePointItemMin).shortText;
                                    break;
                                case ITEM_SINGLE_TYPE.SELECTION:
                                    constraint.valueType = "Selection";
                                    break;
                                case ITEM_SINGLE_TYPE.SEL_RADIO:
                                    constraint.valueType = "Radio";
                                    break;
                                case ITEM_SINGLE_TYPE.SEL_BUTTON:
                                    constraint.valueType = "Button";
                                    break;
                                case ITEM_SINGLE_TYPE.READONLY:
                                    constraint.valueType = "READONLY";
                                    break;
                                case ITEM_SINGLE_TYPE.RELATE_CATEGORY:
                                    constraint.valueType = "RELATE_CATEGORY";
                                    break;
                                case ITEM_SINGLE_TYPE.READONLY_BUTTON:
                                    constraint.valueType = "READONLY_BUTTON";
                                    break;
                            }
                        }

                        // update constraint for filter, value control
                        itemData.constraint = constraint.itemCode;

                        if (constraint.itemCode == 'EmployeeCode') {
                            _.extend(constraint, {
                                formatOption: __viewContext.primitiveValueConstraints.EmployeeCode.formatOption
                            });
                        }

                        writeConstraint(constraint.itemCode, constraint);

                        // if dataType isn't selection item
                        if ([ITEM_SINGLE_TYPE.SELECTION, ITEM_SINGLE_TYPE.SEL_RADIO, ITEM_SINGLE_TYPE.SEL_BUTTON]
                            .indexOf(item.itemTypeState.dataTypeState.dataTypeValue) == -1) {
                            self.currentItem.itemData(itemData);
                            //self.currentItem.filter.valueHasMutated();
                        } else {
                            // get selection options
                            service.fetch.getCbxOptions(command).done(items => {
                                itemData.selectionItems = items;
                                self.currentItem.itemData(itemData);
                                //self.currentItem.filter.valueHasMutated();
                            });
                        }
                    } else {
                        self.currentItem.itemData({ itemCode: '', dataType: 0, amount: 0, selectionItems: [] });
                        self.currentItem.filter.valueHasMutated();
                    }
                });
            });

            // 対象者
            ko.computed({
                read: () => {
                    let target: string = ko.toJS(self.currentItem.applyFor),
                        filter: string = ko.toJS(self.currentItem.filter),
                        itemName: string = ko.toJS(self.currentItem.name),
                        itemData = ko.toJS(self.currentItem.itemData);

                    if (target == 'all') {
                        self.currentItem.target(text('CPS003_119'));
                    } else {
                        if (!!filter) {
                            let value = '';
                            switch (itemData.dataType) {
                                case ITEM_SINGLE_TYPE.DATE:
                                    value = moment.utc(filter).format('YYYY/MM/DD');
                                    break;
                                case ITEM_SINGLE_TYPE.STRING:
                                    value = filter;
                                    break;
                                case ITEM_SINGLE_TYPE.NUMERIC:
                                    if (filter && !isNaN(Number(filter))) {
                                        if (!itemData.amount) {
                                            value = filter;
                                        } else {
                                            value = Number(filter).toLocaleString('ja-JP', { useGrouping: true });
                                        }
                                    }
                                    break;
                                case ITEM_SINGLE_TYPE.TIME:
                                    if (filter && !isNaN(Number(filter))) {
                                        value = parseTime(Number(filter), true).format();
                                    }
                                    break;
                                case ITEM_SINGLE_TYPE.TIMEPOINT:
                                    if (filter && !isNaN(Number(filter))) {
                                        value = parseTimeWidthDay(Number(filter)).fullText;
                                    }
                                    break;
                                case ITEM_SINGLE_TYPE.SELECTION:
                                case ITEM_SINGLE_TYPE.SEL_RADIO:
                                case ITEM_SINGLE_TYPE.SEL_BUTTON:
                                    value = (_.find(itemData.selectionItems, m => m.optionValue == filter) || { optionText: '' }).optionText;
                                    break;
                            }

                            self.currentItem.target(text('CPS003_120', [itemName, value]));
                        } else {
                            self.currentItem.target(text('CPS003_121', [itemName]));
                        }
                    }
                }
            });

            ko.computed({
                read: () => {
                    let itemData = self.currentItem.itemData(),
                        rep: any = self.currentItem.value();

                    if (itemData.amount && rep.mode == '2') {
                        self.currentItem.textView(text('CPS003_95'));
                    } else {
                        self.currentItem.textView(text('CPS003_94'));
                    }
                }
            });

            //self.currentItem.value.subscribe(v => console.log(v));
        }

        pushData() {
            let self = this;

            setShared('CPS003F_VALUE', {});
            self.close();
        }

        close() {
            close();
        }
    }

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

    enum ITEM_STRING_TYPE {
        ANY = 1,
        // 2:全ての半角文字(AnyHalfWidth)
        ANYHALFWIDTH = 2,
        // 3:半角英数字(AlphaNumeric)
        ALPHANUMERIC = 3,
        // 4:半角数字(Numeric)
        NUMERIC = 4,
        // 5:全角カタカナ(Kana)
        KANA = 5,
        // 6: カードNO
        CARDNO = 6,
        // 7: 社員コード
        EMPLOYEE_CODE = 7
    }

    interface IItemData {
        constraint?: string;
        itemName?: string;
        itemCode: string;
        dataType: number;
        amount?: number;
        decimalLength?: number;
        selectionItems?: Array<any>;
    }

    interface ICurrentItem {
        id: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        target: KnockoutObservable<string>;
        filter: KnockoutObservable<string>;
        value: KnockoutObservable<string>;
        replacer: KnockoutObservable<string>;
        applyFor: KnockoutObservable<string>;
        itemData: KnockoutObservable<IItemData>;
        // and more prop
        textView: KnockoutComputed<string>;
    }

    interface IModelDto {
        id: string;
        baseDate: string;
        itemsDefIds: Array<string>;
    }
}