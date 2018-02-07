module nts.layout {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

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

            self.radio();
            self.button();
            self.combobox();
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
            }
        };

        setItemData(Item, value) {
            Item && Item.data.value(value || '');
        }

        setItemName(Item, value) {
            Item && Item.data.textValue(value || '');
        }

        regClickEvent(btnEvent: ButtonEvent) {
            let self = this,
                finder = self.finder,
                currentCtg = 'CS00020',
                btnItem: IFindData = finder.find(currentCtg, btnEvent.btnCode),
                wkTypeItem: IFindData = finder.find(currentCtg, btnEvent.wkTypeCode),
                wkTimeItem: IFindData = finder.find(currentCtg, btnEvent.timeCode),
                startItem1: IFindData = finder.find(currentCtg, btnEvent.startTime1),
                startItem2: IFindData = finder.find(currentCtg, btnEvent.startTime2),
                endItem1: IFindData = finder.find(currentCtg, btnEvent.endTime1),
                endItem2: IFindData = finder.find(currentCtg, btnEvent.endTime2);

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

                    modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                        let childData: IChildData = getShared('childData');

                        if (childData) {
                            self.setItemData(wkTypeItem, childData.selectedWorkTypeCode);
                            self.setItemName(wkTypeItem, childData.selectedWorkTypeName);
                            self.setItemData(wkTimeItem, childData.selectedWorkTimeCode);
                            self.setItemName(wkTimeItem, childData.selectedWorkTimeName);
                            self.setItemData(startItem1, childData.first ? childData.first.start : undefined);
                            self.setItemData(endItem1, childData.first ? childData.first.end : undefined);
                            self.setItemData(startItem2, childData.second ? childData.second.start : undefined);
                            self.setItemData(endItem2, childData.second ? childData.second.end : undefined);

                            let command: ICheckParam = {
                                workTimeCode: ko.toJS(wkTimeItem.data.value)
                            };

                            fetch.check_start_end(command).done(first => {
                                startItem1.data.editable(!!first);
                                endItem1.data.editable(!!first);

                                fetch.check_multi_time(command).done(second => {
                                    startItem2.data.editable(!!first && !!second);
                                    endItem2.data.editable(!!first && !!second);
                                });
                            });
                        }
                    });
                });
            }
        };

        button = () => {
            let self = this,
                finder = self.finder,
                CS00020_IS00128: IFindData = finder.find('CS00020', 'IS00128'),
                CS00020_IS00130: IFindData = finder.find('CS00020', 'IS00130'),

                btnEvents: Array<ButtonEvent> = [
                    //128
                    new ButtonEvent('IS00128', 'IS00128', 'IS00128', '', [], '', '', '', ''),
                    //130
                    new ButtonEvent('IS00130', 'IS00130', 'IS00130', 'IS00131', [], 'IS00133', 'IS00134', 'IS00136', 'IS00137'),
                    //131
                    new ButtonEvent('IS00131', 'IS00130', 'IS00130', 'IS00131', [], 'IS00133', 'IS00134', 'IS00136', 'IS00137'),
                    //139
                    new ButtonEvent('IS00139', 'IS00139', 'IS00139', 'IS00140', [], 'IS00142', 'IS00143', 'IS00145', 'IS00146'),
                    //140
                    new ButtonEvent('IS00140', 'IS00139', 'IS00139', 'IS00140', [], 'IS00142', 'IS00143', 'IS00145', 'IS00146'),
                    //157
                    new ButtonEvent('IS00157', 'IS00157', 'IS00157', 'IS00158', [], 'IS00160', 'IS00160', 'IS00163', 'IS00164'),
                    //158
                    new ButtonEvent('IS00158', 'IS00157', 'IS00157', 'IS00158', [], 'IS00160', 'IS00160', 'IS00163', 'IS00164'),
                    //166
                    new ButtonEvent('IS00166', 'IS00166', 'IS00166', 'IS00167', [], 'IS00169', 'IS00170', 'IS00172', 'IS00173'),
                    //167
                    new ButtonEvent('IS00167', 'IS00166', 'IS00166', 'IS00167', [], 'IS00169', 'IS00170', 'IS00172', 'IS00173'),
                    //175
                    new ButtonEvent('IS00175', 'IS00175', 'IS00175', 'IS00176', [], 'IS00178', 'IS00179', 'IS00181', 'IS00182'),
                    //176
                    new ButtonEvent('IS00176', 'IS00175', 'IS00175', 'IS00176', [], 'IS00178', 'IS00179', 'IS00181', 'IS00182'),
                    //148
                    new ButtonEvent('IS00148', 'IS00148', 'IS00148', 'IS00149', [], 'IS00151', 'IS00152', 'IS00154', 'IS00155'),
                    //149
                    new ButtonEvent('IS00149', 'IS00148', 'IS00148', 'IS00149', [], 'IS00151', 'IS00152', 'IS00154', 'IS00155'),
                    //193
                    new ButtonEvent('IS00193', 'IS00193', 'IS00193', 'IS00194', [], 'IS00196', 'IS00197', 'IS00199', 'IS00200'),
                    //194
                    new ButtonEvent('IS00194', 'IS00193', 'IS00193', 'IS00194', [], 'IS00196', 'IS00197', 'IS00199', 'IS00200'),
                    //202
                    new ButtonEvent('IS00202', 'IS00202', 'IS00202', 'IS00203', [], 'IS00205', 'IS00206', 'IS00208', 'IS00209'),
                    //203
                    new ButtonEvent('IS00203', 'IS00202', 'IS00202', 'IS00203', [], 'IS00205', 'IS00206', 'IS00208', 'IS00209'),
                    //211
                    new ButtonEvent('IS00211', 'IS00211', 'IS00211', 'IS00212', [], 'IS00214', 'IS00215', 'IS00217', 'IS00218'),
                    //212
                    new ButtonEvent('IS00212', 'IS00211', 'IS00211', 'IS00212', [], 'IS00214', 'IS00215', 'IS00217', 'IS00218'),
                    //220
                    new ButtonEvent('IS00220', 'IS00220', 'IS00220', 'IS00221', [], 'IS00223', 'IS00224', 'IS00226', 'IS00227'),
                    //221
                    new ButtonEvent('IS00221', 'IS00220', 'IS00220', 'IS00221', [], 'IS00223', 'IS00224', 'IS00226', 'IS00227'),
                    //229
                    new ButtonEvent('IS00229', 'IS00229', 'IS00229', 'IS00230', [], 'IS00232', 'IS00233', 'IS00235', 'IS00236'),
                    //230
                    new ButtonEvent('IS00230', 'IS00229', 'IS00229', 'IS00230', [], 'IS00232', 'IS00233', 'IS00235', 'IS00236'),
                    //238
                    new ButtonEvent('IS00238', 'IS00238', 'IS00238', 'IS00239', [], 'IS00241', 'IS00242', 'IS00244', 'IS00245'),
                    //239
                    new ButtonEvent('IS00239', 'IS00238', 'IS00238', 'IS00239', [], 'IS00241', 'IS00242', 'IS00244', 'IS00245'),
                    //184
                    new ButtonEvent('IS00184', 'IS00184', 'IS00184', 'IS00185', [], 'IS00187', 'IS00188', 'IS00190', 'IS00191'),
                    //185
                    new ButtonEvent('IS00185', 'IS00184', 'IS00184', 'IS00185', [], 'IS00187', 'IS00188', 'IS00190', 'IS00191'),

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
                            CS00020_IS00124.data.editable(true);
                            CS00020_IS00125.data.editable(true);
                            CS00020_IS00126.data.editable(true);
                            CS00020_IS00127.data.editable(false);
                            break;
                        case "1":
                            CS00020_IS00124.data.editable(false);
                            CS00020_IS00125.data.editable(false);
                            CS00020_IS00126.data.editable(true);
                            CS00020_IS00127.data.editable(true);
                            break;
                        case "2":
                            CS00020_IS00124.data.editable(false);
                            CS00020_IS00125.data.editable(false);
                            CS00020_IS00126.data.editable(false);
                            CS00020_IS00127.data.editable(false);
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
        value: KnockoutObservable<any>;
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

    class ButtonEvent {
        btnCode: string;
        wkTypeCode: string;
        wkTypeCodes: any;
        timeCode: string;
        wkTimeCodes: any;
        startTime1: string;
        endTime1: string;
        startTime2: string;
        endTime2: string;

        constructor(btnCode, wkTypeCode, wkTypeCodes, timeCode, wkTimeCodes, startTime1, endTime1, startTime2, endTime2) {
            this.btnCode = btnCode;
            this.wkTypeCode = wkTypeCode;
            this.wkTypeCodes = wkTypeCodes;
            this.timeCode = timeCode;
            this.wkTimeCodes = wkTimeCodes;
            this.startTime1 = startTime1;
            this.endTime1 = endTime1;
            this.startTime2 = startTime2;
            this.endTime2 = endTime2;

        }
    }
} 