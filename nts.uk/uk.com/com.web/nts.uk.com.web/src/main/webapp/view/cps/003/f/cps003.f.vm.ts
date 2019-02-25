module cps003.f.vm {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import parseTime = nts.uk.time.parseTime;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    const __viewContext: any = window['__viewContext'] || {},
        writeConstraint = window['nts']['uk']['ui']['validation']['writeConstraint'],
        parseTimeWidthDay = window['nts']['uk']['time']['minutesBased']['clock']['dayattr']['create'];

    export class ViewModel {
        currentItem: ICurrentItem = {
            allOrMatch: ko.observableArray([
                { id: 'all', name: text('CPS003_76') },
                { id: 'match', name: text('CPS003_77', ['対象項目名']) }
            ]),
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
                            .filter(f => [
                                'IS00106', 'IS00107', 'IS00109', 'IS00110', // 
                                'IS00133', 'IS00134', 'IS00136', 'IS00137', // IS00131
                                'IS00142', 'IS00143', 'IS00145', 'IS00146', // IS00140
                                'IS00151', 'IS00152', 'IS00154', 'IS00155', // IS00149
                                'IS00160', 'IS00161', 'IS00163', 'IS00164', // IS00158
                                'IS00169', 'IS00170', 'IS00172', 'IS00173', // IS00167
                                'IS00178', 'IS00179', 'IS00181', 'IS00182', // IS00176
                                'IS00196', 'IS00197', 'IS00199', 'IS00200', // IS00194
                                'IS00205', 'IS00206', 'IS00208', 'IS00209', // IS00203
                                'IS00214', 'IS00215', 'IS00217', 'IS00218', // IS00212
                                'IS00223', 'IS00224', 'IS00226', 'IS00227', // IS00221
                                'IS00232', 'IS00233', 'IS00235', 'IS00236', // IS00230
                                'IS00241', 'IS00242', 'IS00244', 'IS00245', // IS00239
                                'IS00187', 'IS00188', 'IS00190', 'IS00191'  // IS00185
                            ].indexOf(f.itemCD) == -1)
                            //.orderBy([])
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
                if (!id) {
                    return;
                }

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

                    if (itemData.amount && rep.mode == 2) {
                        self.currentItem.textView(text('CPS003_95'));
                    } else {
                        self.currentItem.textView(text('CPS003_94'));
                    }
                }
            });
        }

        pushData() {
            let self = this,
                item: any = ko.toJS(self.currentItem),
                mode: any = item.value.mode != undefined ? Number(item.value.mode) : null,
                value: IValueDto = {
                    mode: undefined,
                    replaceAll: item.applyFor == 'all',
                    targetItem: item.itemData.itemCode,
                    matchValue: item.filter || undefined,
                    replaceValue: undefined,
                    replaceFormat: undefined
                };

            $('input').trigger('validate');

            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            switch (item.itemData.dataType) {
                default:
                    break;
                case ITEM_SINGLE_TYPE.DATE:
                    if (value.matchValue) {
                        value.matchValue = moment.utc(value.matchValue).format('YYYY/MM/DD');
                    }

                    if ([
                        'IS00279', 'IS00295',
                        'IS00302', 'IS00309',
                        'IS00316', 'IS00323',
                        'IS00330', 'IS00337',
                        'IS00344', 'IS00351',
                        'IS00358', 'IS00559',
                        'IS00566', 'IS00573',
                        'IS00580', 'IS00587',
                        'IS00594', 'IS00601',
                        'IS00608', 'IS00615',
                        'IS00622'].indexOf(item.itemData.itemCode) > -1) {
                        value.mode = APPLY_MODE.GRANDDATE;
                        switch (mode) {
                            case 0:
                                value.replaceFormat = REPLACE_FORMAT.HIRE_DATE;
                                break;
                            case 1:
                                value.replaceFormat = REPLACE_FORMAT.GRAND_DATE;
                                break;
                            case 2:
                                value.replaceValue = [Number(item.value.value0), item.value.value1];
                                value.replaceFormat = REPLACE_FORMAT.DESI_YEAR_OE;
                                break;
                            case 3:
                                if (item.value.value2) {
                                    value.replaceValue = moment.utc(item.value.value2).format('YYYY/MM/DD');
                                }
                                value.replaceFormat = REPLACE_FORMAT.VALUE;
                                break;
                        }
                    } else {
                        value.mode = APPLY_MODE.DATE;
                        if (item.value.value0) {
                            value.replaceValue = moment.utc(item.value.value0).format('YYYY/MM/DD');
                        }
                        value.replaceFormat = REPLACE_FORMAT.VALUE;
                    }
                    break;
                case ITEM_SINGLE_TYPE.STRING:
                    value.mode = APPLY_MODE.STRING;
                    value.replaceValue = item.value.value0;
                    value.replaceFormat = REPLACE_FORMAT.VALUE;
                    break;
                case ITEM_SINGLE_TYPE.TIME:
                    if (item.itemData.itemCode == 'IS00287') {
                        value.mode = APPLY_MODE.TIMEYEAR;
                        if (mode == 0) {
                            value.replaceValue = Number(item.value.value0);
                            value.replaceFormat = REPLACE_FORMAT.CONTRACT_TIME;
                        } else {
                            value.replaceValue = item.value.value1;
                            value.replaceFormat = REPLACE_FORMAT.VALUE;
                        }
                    } else {
                        value.mode = APPLY_MODE.TIME;
                        value.replaceValue = item.value.value0;
                        value.replaceFormat = REPLACE_FORMAT.VALUE;
                    }
                    break;
                case ITEM_SINGLE_TYPE.TIMEPOINT:
                    value.mode = APPLY_MODE.CLOCK;
                    value.replaceValue = item.value.value0;
                    value.replaceFormat = REPLACE_FORMAT.VALUE;
                    break;
                case ITEM_SINGLE_TYPE.NUMERIC:
                    if (!item.itemData.amount) {
                        value.mode = APPLY_MODE.NUMBER;
                        if (item.value.value0) {
                            value.replaceValue = Number(item.value.value0);
                        }
                        value.replaceFormat = REPLACE_FORMAT.VALUE;
                    } else {
                        value.mode = APPLY_MODE.AMOUNT;
                        if (mode == 0) {
                            if (item.value.value0) {
                                value.replaceValue = Number(item.value.value0);
                            }
                            value.replaceFormat = REPLACE_FORMAT.VALUE;
                        } else {
                            if (item.value.value2) {
                                value.replaceValue = Number(item.value.value1 + item.value.value2);
                            }
                            value.replaceFormat = REPLACE_FORMAT.ADD_OR_SUB;
                        }
                    }
                    break;
                case ITEM_SINGLE_TYPE.SELECTION:
                case ITEM_SINGLE_TYPE.SEL_RADIO:
                case ITEM_SINGLE_TYPE.SEL_BUTTON:
                    if ([
                        ITEM_SINGLE_TYPE.SELECTION,
                        ITEM_SINGLE_TYPE.SEL_RADIO].indexOf(item.itemData.dataType) > -1) {
                        value.mode = APPLY_MODE.SELECTION;
                        value.replaceValue = item.value.value0;
                        value.replaceFormat = REPLACE_FORMAT.VALUE;
                    } else {
                        if (['IS00131', 'IS00140',
                            'IS00158', 'IS00167',
                            'IS00176', 'IS00149',
                            'IS00194', 'IS00203',
                            'IS00212', 'IS00221',
                            'IS00230', 'IS00239',
                            'IS00185'].indexOf(item.itemData.itemCode) > -1) {
                            let target = value.targetItem,
                                values = item.value.value1;

                            value.mode = APPLY_MODE.WORKTIME;
                            switch (target) {
                                case "IS00131":
                                    value.targetItem = [target, 'IS00133', 'IS00134', 'IS00136', 'IS00137'];
                                    break;
                                case "IS00140":
                                    value.targetItem = [target, 'IS00142', 'IS00143', 'IS00145', 'IS00146'];
                                    break;
                                case "IS00158":
                                    value.targetItem = [target, 'IS00160', 'IS00161', 'IS00163', 'IS00164'];
                                    break;
                                case "IS00167":
                                    value.targetItem = [target, 'IS00169', 'IS00170', 'IS00172', 'IS00173'];
                                    break;
                                case "IS00176":
                                    value.targetItem = [target, 'IS00178', 'IS00179', 'IS00181', 'IS00182'];
                                    break;
                                case "IS00149":
                                    value.targetItem = [target, 'IS00151', 'IS00152', 'IS00154', 'IS00155'];
                                    break;
                                case "IS00194":
                                    value.targetItem = [target, 'IS00196', 'IS00197', 'IS00199', 'IS00200'];
                                    break;
                                case "IS00203":
                                    value.targetItem = [target, 'IS00205', 'IS00206', 'IS00208', 'IS00209'];
                                    break;
                                case "IS00212":
                                    value.targetItem = [target, 'IS00214', 'IS00215', 'IS00217', 'IS00218'];
                                    break;
                                case "IS00221":
                                    value.targetItem = [target, 'IS00223', 'IS00224', 'IS00226', 'IS00227'];
                                    break;
                                case "IS00230":
                                    value.targetItem = [target, 'IS00232', 'IS00233', 'IS00235', 'IS00236'];
                                    break;
                                case "IS00239":
                                    value.targetItem = [target, 'IS00241', 'IS00242', 'IS00244', 'IS00245'];
                                    break;
                                case "IS00185":
                                    value.targetItem = [target, 'IS00187', 'IS00188', 'IS00190', 'IS00191'];
                                    break;
                            }
                            value.replaceValue = [values.selectedWorkTimeCode, values.first.start, values.first.end, values.second.start, values.second.end];
                            value.replaceFormat = REPLACE_FORMAT.VALUE;
                        } else {
                            value.mode = APPLY_MODE.SELECTION;
                            value.replaceValue = item.value.value0;
                            value.replaceFormat = REPLACE_FORMAT.VALUE;
                        }
                    }
                    break;
            }

            // 画面項目「対象者選択（F1_007）」の状態をチェックする
            if (value.replaceAll) { // 全員（F1_008）が選択されている場合
                if (mode == null) {
                    if (value.replaceValue) {
                        confirm({ messageId: 'Msg_633', messageParams: [item.name, item.replacer] }).ifYes(() => {
                            setShared('CPS003F_VALUE', value);
                            close();
                        });
                    } else {
                        confirm({ messageId: 'Msg_634', messageParams: [item.name] }).ifYes(() => {
                            setShared('CPS003F_VALUE', value);
                            close();
                        });
                    }
                } else {
                    if (item.itemData.amount) { // 画面モード＝金額モードの場合
                        if (mode == 0) { // 通常置換（F1_026）が選択されている場合
                            if (value.replaceValue) {
                                confirm({ messageId: 'Msg_633', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                    setShared('CPS003F_VALUE', value);
                                    close();
                                });
                            } else {
                                confirm({ messageId: 'Msg_634', messageParams: [item.name] }).ifYes(() => {
                                    setShared('CPS003F_VALUE', value);
                                    close();
                                });
                            }
                        } else { // 加減算（F1_027）が選択されている場合
                            if (value.replaceValue) {
                                confirm({ messageId: 'Msg_679', messageParams: [item.name, text(value.replaceValue1 > 0 ? 'CPS003_123' : 'CPS003_124') + Math.abs(value.replaceValue1)] }).ifYes(() => {
                                    setShared('CPS003F_VALUE', value);
                                    close();
                                });
                            }
                        }
                    } else {
                        // 画面モード＝時間年休上限モードの場合
                        if (item.itemData.dataType == ITEM_SINGLE_TYPE.TIME && item.itemData.itemCode == 'IS00287') {
                            if (mode == 0) {
                                confirm({ messageId: 'Msg_633', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                    setShared('CPS003F_VALUE', value);
                                    close();
                                });
                            } else {
                                if (value.replaceValue) {
                                    confirm({ messageId: 'Msg_633', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                } else {
                                    confirm({ messageId: 'Msg_634', messageParams: [item.name] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                }
                            }
                        } else {
                            if ([0, 1, 2].indexOf(mode) > -1) {
                                confirm({ messageId: 'Msg_633', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                    setShared('CPS003F_VALUE', value);
                                    close();
                                });
                            } else {
                                if (value.replaceValue) {
                                    confirm({ messageId: 'Msg_633', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                } else {
                                    confirm({ messageId: 'Msg_634', messageParams: [item.name] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                }
                            }
                        }
                    }
                }
            } else { // 一致する社員のみ（F1_009）が選択されている場合
                if (value.matchValue) {
                    if (mode == null) {
                        if (value.replaceValue) {
                            confirm({ messageId: 'Msg_635', messageParams: [item.name, value.matchValue, item.replacer] }).ifYes(() => {
                                setShared('CPS003F_VALUE', value);
                                close();
                            });
                        } else {
                            confirm({ messageId: 'Msg_636', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                setShared('CPS003F_VALUE', value);
                                close();
                            });
                        }
                    } else {
                        if (item.itemData.amount) {
                            if (mode == 0) { // 通常置換（F1_026）が選択されている場合
                                if (value.replaceValue) {
                                    confirm({ messageId: 'Msg_635', messageParams: [item.name, value.matchValue, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                } else {
                                    confirm({ messageId: 'Msg_636', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                }
                            } else { // 加減算（F1_027）が選択されている場合
                                if (value.replaceValue) {
                                    confirm({ messageId: 'Msg_714', messageParams: [item.name, text(value.replaceValue1 > 0 ? 'CPS003_123' : 'CPS003_124') + Math.abs(value.replaceValue1)] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                }
                            }
                        } else {
                            if ([0, 1, 2].indexOf(mode) > -1) {
                                confirm({ messageId: 'Msg_635', messageParams: [item.name, value.matchValue, item.replacer] }).ifYes(() => {
                                    setShared('CPS003F_VALUE', value);
                                    close();
                                });
                            } else {
                                if (value.replaceValue) {
                                    confirm({ messageId: 'Msg_635', messageParams: [item.name, value.matchValue, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                } else {
                                    confirm({ messageId: 'Msg_636', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                }
                            }
                        }
                    }
                } else {
                    if (mode == null) {
                        if (value.replaceValue) {
                            confirm({ messageId: 'Msg_637', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                setShared('CPS003F_VALUE', value);
                                close();
                            });
                        } else {
                            alert({ messageId: 'Msg_638', messageParams: [item.name, item.replacer] }).then(() => {
                                setShared('CPS003F_VALUE', null);
                                //close();
                            });
                        }
                    } else {
                        if (item.itemData.amount) {
                            if (value.matchValue) {
                                if (mode == 0) {
                                    confirm({ messageId: 'Msg_637', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                } else {
                                    alert({ messageId: 'Msg_638', messageParams: [item.name, item.replacer] }).then(() => {
                                        setShared('CPS003F_VALUE', null);
                                        //close();
                                    });
                                }
                            } else {
                                alert({ messageId: 'Msg_1069', messageParams: [] }).then(() => {
                                    setShared('CPS003F_VALUE', null);
                                    //close();
                                });
                            }
                        } else {
                            // 画面モード＝時間年休上限モードの場合
                            if (item.itemData.dataType == ITEM_SINGLE_TYPE.TIME && item.itemData.itemCode == 'IS00287') {
                                if (value.matchValue) {
                                    if (mode == 0) {
                                        confirm({ messageId: 'Msg_635', messageParams: [item.name, value.matchValue, item.replacer] }).ifYes(() => {
                                            setShared('CPS003F_VALUE', value);
                                            close();
                                        });
                                    } else {
                                        if (value.replaceValue) {
                                            confirm({ messageId: 'Msg_635', messageParams: [item.name, value.matchValue, item.replacer] }).ifYes(() => {
                                                setShared('CPS003F_VALUE', value);
                                                close();
                                            });
                                        } else {
                                            confirm({ messageId: 'Msg_636', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                                setShared('CPS003F_VALUE', value);
                                                close();
                                            });
                                        }
                                    }
                                } else {
                                    if (mode == 0) {
                                        confirm({ messageId: 'Msg_637', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                            setShared('CPS003F_VALUE', value);
                                            close();
                                        });
                                    } else {
                                        if (value.replaceValue) {
                                            confirm({ messageId: 'Msg_637', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                                setShared('CPS003F_VALUE', value);
                                                close();
                                            });
                                        } else {
                                            alert({ messageId: 'Msg_638', messageParams: [item.name, item.replacer] }).then(() => {
                                                setShared('CPS003F_VALUE', null);
                                                //close();
                                            });
                                        }
                                    }
                                }
                            } else {
                                if ([0, 1, 2].indexOf(mode) > -1) {
                                    confirm({ messageId: 'Msg_637', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                        setShared('CPS003F_VALUE', value);
                                        close();
                                    });
                                } else {
                                    if (value.replaceValue) {
                                        confirm({ messageId: 'Msg_637', messageParams: [item.name, item.replacer] }).ifYes(() => {
                                            setShared('CPS003F_VALUE', value);
                                            close();
                                        });
                                    } else {
                                        alert({ messageId: 'Msg_638', messageParams: [item.name, item.replacer] }).then(() => {
                                            setShared('CPS003F_VALUE', null);
                                            //close();
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
        allOrMatch: KnockoutObservableArray<any>;
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

    // return value
    interface IValueDto {
        mode: APPLY_MODE;
        // 全て置換する
        replaceAll: boolean;
        // 対象項目
        targetItem: string | string[];
        // 一致する値
        matchValue: any;
        // 値
        replaceValue: any | any[]; // 入社年指定 or 対象項目 (string[]), replaceValue is any[]
        // 置換形式 
        replaceFormat: REPLACE_FORMAT;
    }
    
    enum APPLY_MODE {
        DATE = 1,
        STRING = 2,
        TIME = 3,
        CLOCK = 4,
        NUMBER = 5,
        AMOUNT = 6,
        SELECTION = 7,
        WORKTIME = 8,
        GRANDDATE = 9,
        TIMEYEAR = 10
    }

    enum REPLACE_FORMAT {
        VALUE = 0,          //値指定
        ADD_OR_SUB = 1,     //加減算
        HIRE_DATE = 2,      //入社日
        GRAND_DATE = 3,     //年休付与基準日
        DESI_YEAR_OE = 4,   //入社年指定
        CONTRACT_TIME = 5   //契約時間
    }

    enum YEAR_OF_JOIN {
        NEXT = 0, //翌年
        SAME = 1, //同年
        PREV = 2  //前年
    }
}