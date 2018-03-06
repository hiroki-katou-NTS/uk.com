module nts.layout {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import nou = nts.uk.util.isNullOrUndefined;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let rmError = nts.uk.ui.errors["removeByCode"],
        getError = nts.uk.ui.errors["getErrorByElement"]

    export const validate = {
        removeDoubleLine: (items: Array<any>) => {
            let maps = _(items)
                .map((x, i) => (x.layoutItemType == IT_CLA_TYPE.SPER) ? i : -1)
                .filter(x => x != -1).value();

            _.each(maps, (t, i) => {
                if (maps[i + 1] == t + 1) {
                    _.remove(items, (m: any) => {
                        let item: any = ko.unwrap(items)[maps[i + 1]];
                        return item && item.layoutItemType == IT_CLA_TYPE.SPER && item.layoutID == m.layoutID;
                    });
                }
            });
        },
        initCheckError: (items: Array<any>) => {
            // validate button, radio button
            _(items)
                .filter(x => _.has(x, "items") && _.isFunction(x.items))
                .map(x => x.items())
                .flatten()
                .flatten()
                .filter((x: IItemData) => x.required && x.type != ITEM_TYPE.SET)
                .each((x: IItemData) => {
                    let v: any = ko.toJS(x),
                        id = v.itemDefId.replace(/[-_]/g, ''),
                        element = document.getElementById(id),
                        $element = $(element);

                    if (element && (element.tagName.toUpperCase() == "BUTTON" || $element.hasClass('radio-wrapper'))) {
                        x.value.subscribe(d => {
                            !nou(d) && rmError($element, "FND_E_REQ_SELECT");
                        });
                    }
                });
        },
        checkError: (items: Array<any>) => {
            _(items)
                .filter(x => _.has(x, "items") && _.isFunction(x.items))
                .map(x => x.items())
                .flatten()
                .flatten()
                .filter((x: any) => x.required && x.type != ITEM_TYPE.SET)
                .map(x => ko.toJS(x))
                .each(x => {
                    let id = x.itemDefId.replace(/[-_]/g, ''),
                        element = document.getElementById(id),
                        $element = $(element);

                    if (element) {
                        if (element.tagName.toUpperCase() == "INPUT") {
                            $element
                                .trigger('blur')
                                .trigger('change');
                        } else if (element.tagName.toUpperCase() == "BUTTON" || $element.hasClass('radio-wrapper')) {
                            if (nou(x.value) && x.required) {
                                if (!getError($element).length) {
                                    $element.ntsError('set', { messageId: "FND_E_REQ_SELECT", messageParams: [x.itemName] });
                                }
                            }
                        }
                        else {
                            $element
                                .find('.nts-input')
                                .trigger('blur')
                                .trigger('change');
                        }
                    }
                });
        }
    }

    class constraint {
        lstCls: Array<any> = [];

        constructor(lstCls: Array<any>) {
            let self = this;

            self.lstCls = lstCls;
        }

        find = (categoryCode: string, subscribeCode: string): IFindData => {
            let self = this,
                controls: Array<any> = _(self.lstCls).filter(x => _.has(x, "items") && _.isFunction(x.items)).map(x => x.items()).flatten().flatten().value(),
                subscribe: any = _.find(controls, (x: any) => x.categoryCode.indexOf(categoryCode) > -1 && x.itemCode == subscribeCode);

            if (subscribe) {
                return <IFindData>{
                    data: subscribe,
                    ctrl: $('#' + subscribe.itemDefId.replace(/[-_]/g, ''))
                };
            }

            return null;
        };

        finds = (categoryCode: string, subscribesCode: Array<string>): Array<IFindData> => {
            if (!_.isArray(subscribesCode)) {
                throw "[subscribesCode] isn't an array!";
            }

            let self = this,
                controls: Array<any> = _(self.lstCls).filter(x => _.has(x, "items") && _.isFunction(x.items)).map(x => x.items()).flatten().flatten().value(),
                subscribes: Array<any> = _.filter(controls, (x: any) => x.categoryCode.indexOf(categoryCode) > -1 && (subscribesCode || []).indexOf(x.itemCode) > -1);

            return subscribes.map(x => {
                return <IFindData>{
                    data: x,
                    ctrl: $('#' + x.itemDefId.replace(/[-_]/g, ''))
                };
            });
        };

        findChilds = (categoryCode: string, parentCode: string): Array<IFindData> => {
            let self = this,
                controls: Array<any> = _(self.lstCls).filter(x => _.has(x, "items") && _.isFunction(x.items)).map(x => x.items()).flatten().flatten().value(),
                subscribes: Array<any> = _.filter(controls, (x: any) => x.categoryCode.indexOf(categoryCode) > -1 && x.itemParentCode == parentCode);

            return subscribes.map(x => {
                return <IFindData>{
                    data: x,
                    ctrl: $('#' + x.itemDefId.replace(/[-_]/g, ''))
                };
            });
        };
    }

    const fetch = {
        check_start_end: (param: ICheckParam) => ajax(`ctx/pereg/person/common/checkStartEnd`, param),
        check_multi_time: (param: ICheckParam) => ajax(`ctx/pereg/person/common/checkMultiTime`, param)
    }

    export class validation {
        finder: IFinder = undefined;
        constructor(lstCls: Array<any>) {
            let self = this;
            self.finder = new constraint(lstCls);

            self.textBox();
            self.radio();
            self.button();
            self.combobox();
            validate.initCheckError(lstCls);
        }
        
        textBox = () => {
            let self = this,
            finder = self.finder,
            CS00002_IS00003: IFindData = finder.find('CS00002', 'IS00003'),
            CS00002_IS00004: IFindData = finder.find('CS00002', 'IS00004'),
            CS00002_IS00009: IFindData = finder.find('CS00002', 'IS00009'),
            CS00002_IS00010: IFindData = finder.find('CS00002', 'IS00010');
            if (CS00002_IS00003) {
                self.validateName(CS00002_IS00003);
                CS00002_IS00003.data.value.subscribe(x=> {
                   self.validateName(CS00002_IS00003);
                });
            }
            
            if (CS00002_IS00004) {
                self.validateName(CS00002_IS00004);
                CS00002_IS00004.data.value.subscribe(x=> {
                   self.validateName(CS00002_IS00004);
                });
            }
            if (CS00002_IS00009) {
                self.validateName(CS00002_IS00009);
                CS00002_IS00009.data.value.subscribe(x=> {
                   self.validateName(CS00002_IS00009);
                });
            }
            
            if (CS00002_IS00010) {
                self.validateName(CS00002_IS00010);
                CS00002_IS00010.data.value.subscribe(x=> {
                   self.validateName(CS00002_IS00010);
                });
            }
        };
        
        validateName(item: IFindData){
            if (!item.data.value().startsWith('　') && !item.data.value().endsWith('　') && item.data.value().includes('　')){
                rmError(item, "Msg_924");
            }  else if (item.data.value()) {
                if (!item.ctrl.parent().hasClass('error')) {
                    !item.ctrl.is(':disabled') && item.ctrl.ntsError('set', { messageId: "Msg_924" });
                }
            }
        }
        
        radio = () => {
            let self = this,
                finder = self.finder,
                CS00020_IS00248: IFindData = finder.find('CS00020', 'IS00248'),
                CS00020_IS00121: IFindData = finder.find('CS00020', 'IS00121'),
                CS00020_IS00123: IFindData = finder.find("CS00020", "IS00123");

            if (CS00020_IS00248) {
                CS00020_IS00248.data.value.subscribe(x => {
                    let ctrls: Array<IFindData> = finder.findChilds(CS00020_IS00248.data.categoryCode, CS00020_IS00248.data.itemParentCode);

                    _.each(ctrls, c => {
                        if (c.data.itemCode != CS00020_IS00248.data.itemCode) {
                            c.data.editable(x == 1);
                        }
                    });
                });
                setTimeout(() => {
                    CS00020_IS00248.data.value.valueHasMutated();
                }, 0);
            }

            if (CS00020_IS00121) {
                CS00020_IS00121.data.value.subscribe(x => {
                    let ctrls: Array<IFindData> = finder.findChilds(CS00020_IS00121.data.categoryCode, CS00020_IS00121.data.itemParentCode);

                    _.each(ctrls, c => {
                        if (c.data.itemCode != CS00020_IS00121.data.itemCode) {
                            c.data.editable(x == 1);
                            if (x == 1 && CS00020_IS00123) {
                                CS00020_IS00123.data.value.valueHasMutated();
                            }
                        }
                    });
                });
                setTimeout(() => {
                    CS00020_IS00121.data.value.valueHasMutated();
                }, 0);
            }
        };

        setItemData(Item, value) {
            Item && Item.data.value(value);
        }

        setItemName(Item, value) {
            Item && Item.data.textValue(value || '');
        }

        setEditAble(Item, value) {
            Item && Item.data.editable(value);
        }

        regClickEvent(btnEvent: IButtonEvent) {
            _.each(btnEvent.btnCodes, (code) => {
                let self = this,
                    finder = self.finder,
                    currentCtg = 'CS00020',
                    btnItem: IFindData = finder.find(currentCtg, code),
                    wkTypeItem: IFindData = finder.find(currentCtg, btnEvent.wkTypeCode),
                    wkTimeItem: IFindData = finder.find(currentCtg, btnEvent.timeCode),
                    startItem1: IFindData = finder.find(currentCtg, btnEvent.startTime1),
                    startItem2: IFindData = finder.find(currentCtg, btnEvent.startTime2),
                    endItem1: IFindData = finder.find(currentCtg, btnEvent.endTime1),
                    endItem2: IFindData = finder.find(currentCtg, btnEvent.endTime2),
                    validateEditable = (wtc: any) => {
                        let command: ICheckParam = {
                            workTimeCode: ko.toJS(wtc)
                        };

                        if (command.workTimeCode) {
                            fetch.check_start_end(command).done(first => {
                                self.setEditAble(startItem1, !!first);
                                self.setEditAble(endItem1, !!first);

                                fetch.check_multi_time(command).done(second => {
                                    self.setEditAble(startItem2, !!first && !!second);
                                    self.setEditAble(endItem2, !!first && !!second);
                                });
                            });
                        } else {
                            self.setEditAble(startItem1, false);
                            self.setEditAble(endItem1, false);
                            self.setEditAble(startItem2, false);
                            self.setEditAble(endItem2, false);
                        }
                    };

                // for first run
                validateEditable(wkTimeItem != null ? wkTimeItem.data.value : '');

                if (btnItem) {
                    btnItem.ctrl.on('click', () => {
                        let typeCode: string = wkTypeItem ? wkTypeItem.data.value() || "" : "",
                            typeCodes: Array<any> = btnEvent.wkTypeCodes.constructor === Array ? btnEvent.wkTypeCodes : !finder.find(currentCtg, btnEvent.wkTypeCodes) ? [] : finder.find(currentCtg, btnEvent.wkTypeCodes).data.lstComboBoxValue,
                            timeCode: string = wkTimeItem ? wkTimeItem.data.value() || "" : "",
                            timeCodes: Array<any> = btnEvent.wkTimeCodes.constructor === Array ? btnEvent.wkTimeCodes : !finder.find(currentCtg, btnEvent.wkTimeCodes) ? [] : finder.find(currentCtg, btnEvent.wkTimeCodes).data.lstComboBoxValue

                        setShared('parentCodes', {
                            workTypeCodes: _.map(typeCodes, x => x.optionValue),
                            selectedWorkTypeCode: typeCode,
                            workTimeCodes: _.map(timeCodes, x => x.optionValue),
                            selectedWorkTimeCode: timeCode
                        }, true);
                        if (btnItem.data.itemCode != "IS00128") {
                            modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                                let childData: IChildData = getShared('childData');

                                if (childData) {
                                    self.setItemData(wkTypeItem, childData.selectedWorkTypeCode);
                                    self.setItemName(wkTypeItem, childData.selectedWorkTypeName);
                                    self.setItemData(wkTimeItem, childData.selectedWorkTimeCode);
                                    self.setItemName(wkTimeItem, childData.selectedWorkTimeName);
                                    self.setItemData(startItem1, childData.first && childData.first.start);
                                    self.setItemData(endItem1, childData.first && childData.first.end);
                                    self.setItemData(startItem2, childData.second && childData.second.start);
                                    self.setItemData(endItem2, childData.second && childData.second.end);

                                    validateEditable(wkTimeItem != null ? wkTimeItem.data.value : '');
                                }
                            });
                        } else {
                            setShared("KDL002_Multiple", false, true);
                            setShared("KDL002_SelectedItemId", btnItem.data.value(), true);
                            setShared("KDL002_AllItemObj", _.map(btnItem.data.lstComboBoxValue, x => x.optionValue), true);
                            modal('at', '/view/kdl/002/a/index.xhtml').onClosed(() => {
                                let childData: IChildData = getShared('KDL002_SelectedNewItem');

                                if (childData[0]) {
                                    self.setItemData(wkTypeItem, childData[0].code);
                                    self.setItemName(wkTypeItem, childData[0].name);
                                }
                            });
                        }
                    });
                }
            });
        }

        button = () => {
            let self = this,
                finder = self.finder,
                cmm009Items = [],

                btnEvents: Array<IButtonEvent> = [
                    //128
                    {
                        btnCodes: ['IS00128'],
                        wkTypeCode: 'IS00128',
                        wkTypeCodes: 'IS00128',
                        timeCode: '',
                        wkTimeCodes: cmm009Items,
                        startTime1: '',
                        endTime1: '',
                        startTime2: '',
                        endTime2: ''
                    },
                    //130 131
                    {
                        btnCodes: ['IS00130', 'IS00131'],
                        wkTypeCode: 'IS00130',
                        wkTypeCodes: 'IS00130',
                        timeCode: 'IS00131',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00133',
                        endTime1: 'IS00134',
                        startTime2: 'IS00136',
                        endTime2: 'IS00137'
                    },
                    //139 140
                    {
                        btnCodes: ['IS00139', 'IS00140'],
                        wkTypeCode: 'IS00139',
                        wkTypeCodes: 'IS00139',
                        timeCode: 'IS00140',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00142',
                        endTime1: 'IS00143',
                        startTime2: 'IS00145',
                        endTime2: 'IS00146'
                    },
                    //157 158
                    {
                        btnCodes: ['IS00157', 'IS00158'],
                        wkTypeCode: 'IS00157',
                        wkTypeCodes: 'IS00157',
                        timeCode: 'IS00158',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00160',
                        endTime1: 'IS00161',
                        startTime2: 'IS00163',
                        endTime2: 'IS00164'
                    },

                    //166 167
                    {
                        btnCodes: ['IS00166', 'IS00167'],
                        wkTypeCode: 'IS00166',
                        wkTypeCodes: 'IS00166',
                        timeCode: 'IS00167',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00169',
                        endTime1: 'IS00170',
                        startTime2: 'IS00172',
                        endTime2: 'IS00173'
                    },

                    //175 176
                    {
                        btnCodes: ['IS00175', 'IS00176'],
                        wkTypeCode: 'IS00175',
                        wkTypeCodes: 'IS00175',
                        timeCode: 'IS00176',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00178',
                        endTime1: 'IS00179',
                        startTime2: 'IS00181',
                        endTime2: 'IS00182'
                    },
                    //148 149
                    {
                        btnCodes: ['IS00148', 'IS00149'],
                        wkTypeCode: 'IS00148',
                        wkTypeCodes: 'IS00148',
                        timeCode: 'IS00149',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00151',
                        endTime1: 'IS00152',
                        startTime2: 'IS00154',
                        endTime2: 'IS00155'
                    },
                    //193 194
                    {
                        btnCodes: ['IS00193', 'IS00194'],
                        wkTypeCode: 'IS00193',
                        wkTypeCodes: 'IS00193',
                        timeCode: 'IS00194',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00196',
                        endTime1: 'IS00197',
                        startTime2: 'IS00199',
                        endTime2: 'IS00200'
                    },
                    //202 203
                    {
                        btnCodes: ['IS00202', 'IS00203'],
                        wkTypeCode: 'IS00202',
                        wkTypeCodes: 'IS00202',
                        timeCode: 'IS00203',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00205',
                        endTime1: 'IS00206',
                        startTime2: 'IS00208',
                        endTime2: 'IS00209'
                    },
                    //211 212
                    {
                        btnCodes: ['IS00211', 'IS00212'],
                        wkTypeCode: 'IS00211',
                        wkTypeCodes: 'IS00211',
                        timeCode: 'IS00212',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00214',
                        endTime1: 'IS00215',
                        startTime2: 'IS00217',
                        endTime2: 'IS00218'
                    },

                    //220 221
                    {
                        btnCodes: ['IS00220', 'IS00221'],
                        wkTypeCode: 'IS00220',
                        wkTypeCodes: 'IS00220',
                        timeCode: 'IS00221',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00223',
                        endTime1: 'IS00224',
                        startTime2: 'IS00226',
                        endTime2: 'IS00227'
                    },
                    //220 221
                    {
                        btnCodes: ['IS00229', 'IS00230'],
                        wkTypeCode: 'IS00229',
                        wkTypeCodes: 'IS00229',
                        timeCode: 'IS00230',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00232',
                        endTime1: 'IS00233',
                        startTime2: 'IS00235',
                        endTime2: 'IS00236'
                    },

                    //238 239
                    {
                        btnCodes: ['IS00238', 'IS00239'],
                        wkTypeCode: 'IS00238',
                        wkTypeCodes: 'IS00238',
                        timeCode: 'IS00239',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00241',
                        endTime1: 'IS00242',
                        startTime2: 'IS00244',
                        endTime2: 'IS00245'
                    },
                    //184 185
                    {
                        btnCodes: ['IS00184', 'IS00185'],
                        wkTypeCode: 'IS00184',
                        wkTypeCodes: 'IS00184',
                        timeCode: 'IS00185',
                        wkTimeCodes: cmm009Items,
                        startTime1: 'IS00187',
                        endTime1: 'IS00188',
                        startTime2: 'IS00190',
                        endTime2: 'IS00191'
                    }
                ];

            //register Event
            _.each(btnEvents, (event) => {
                self.regClickEvent(event);
            });
        };

        combobox = () => {
            let self = this,
                finder: IFinder = self.finder,
                CS00020_IS00123: IFindData = finder.find("CS00020", "IS00123"),
                CS00020_IS00124: IFindData = finder.find("CS00020", "IS00124"),
                CS00020_IS00125: IFindData = finder.find("CS00020", "IS00125"),
                CS00020_IS00126: IFindData = finder.find("CS00020", "IS00126"),
                CS00020_IS00127: IFindData = finder.find("CS00020", "IS00127");

            if (CS00020_IS00123) {
                CS00020_IS00123.data.value.subscribe(v => {
                    switch (v) {
                        case "0":
                            CS00020_IS00124 && CS00020_IS00124.data.editable(true);
                            CS00020_IS00125 && CS00020_IS00125.data.editable(true);
                            CS00020_IS00126 && CS00020_IS00126.data.editable(true);
                            CS00020_IS00127 && CS00020_IS00127.data.editable(false);
                            break;
                        case "1":
                            CS00020_IS00124 && CS00020_IS00124.data.editable(false);
                            CS00020_IS00125 && CS00020_IS00125.data.editable(false);
                            CS00020_IS00126 && CS00020_IS00126.data.editable(true);
                            CS00020_IS00127 && CS00020_IS00127.data.editable(true);
                            break;
                        case "2":
                            CS00020_IS00124 && CS00020_IS00124.data.editable(false);
                            CS00020_IS00125 && CS00020_IS00125.data.editable(false);
                            CS00020_IS00126 && CS00020_IS00126.data.editable(false);
                            CS00020_IS00127 && CS00020_IS00127.data.editable(false);
                            break;
                    }
                });
                CS00020_IS00123.data.value.valueHasMutated();
            }
        };
    }

    // define ITEM_CLASSIFICATION_TYPE
    enum IT_CLA_TYPE {
        ITEM = <any>"ITEM", // single item
        LIST = <any>"LIST", // list item
        SPER = <any>"SeparatorLine" // line item
    }

    enum ITEM_TYPE {
        SET = 1,
        SINGLE = 2
    }

    interface IValidation {
        radio: () => void;
        button: () => void;
        combobox: () => void;
    }

    interface IFinder {
        find: (categoryCode: string, subscribeCode: string) => IFindData;
        finds: (categoryCode: string, subscribesCode: Array<string>) => Array<IFindData>;
        findChilds: (categoryCode: string, parentCode: string) => Array<IFindData>;
    }

    interface IFindData {
        ctrl: JQuery;
        data: IItemData
    }

    interface IItemData {
        'type': ITEM_TYPE;
        required: boolean;
        value: KnockoutObservable<any>;
        items: KnockoutObservableArray<any>;
        editable: KnockoutObservable<boolean>;
        readonly: KnockoutObservable<boolean>;
        categoryCode: string;
        itemCode: string;
        lstComboBoxValue: Array<any>;
        itemParentCode?: string;
    }

    interface IComboboxItem {
        optionText: string;
        optionValue: string;
    }

    interface IParentCodes {
        workTypeCodes: string;
        selectedWorkTypeCode: string;
        workTimeCodes: string;
        selectedWorkTimeCode: string
    }

    interface IChildData {
        selectedWorkTypeCode: string;
        selectedWorkTypeName: string;
        selectedWorkTimeCode: string;
        selectedWorkTimeName: string;
        first: IDateRange;
        second: IDateRange;
    }

    interface IDateRange {
        start: number;
        end: number;
    }

    interface ICheckParam {
        workTimeCode?: string;
    }

    interface IButtonEvent {
        btnCodes: any;
        wkTypeCode: string;
        wkTypeCodes: any;
        timeCode: string;
        wkTimeCodes: any;
        startTime1: string;
        endTime1: string;
        startTime2: string;
        endTime2: string;
    }
}
