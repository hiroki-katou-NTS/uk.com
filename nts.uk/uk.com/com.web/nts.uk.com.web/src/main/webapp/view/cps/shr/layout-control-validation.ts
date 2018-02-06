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
        combobox: (baseDate: string, itemCode: string) => ajax(`/pereg/get-combo-value/${itemCode}/${baseDate}`)
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
                CS00020_IS00121: IFindData = finder.find('CS00020', 'IS00121');

            if (CS00020_IS00248) {
                CS00020_IS00248.data.value.subscribe(x => {
                    let ctrls: Array<IFindData> = finder.findChilds(CS00020_IS00248.data.categoryCode, CS00020_IS00248.data.itemParentCode);

                    _.each(ctrls, c => {
                        if (c.data.itemCode != CS00020_IS00248.data.itemCode) {
                            c.data.editable(x == 1);
                            c.data.readonly(x != 1);
                        }
                    });
                });
            }

            if (CS00020_IS00121) {
                CS00020_IS00121.data.value.subscribe(x => {
                    let ctrls: Array<IFindData> = finder.findChilds(CS00020_IS00248.data.categoryCode, CS00020_IS00248.data.itemParentCode);

                    _.each(ctrls, c => {
                        if (c.data.itemCode != CS00020_IS00248.data.itemCode) {
                            c.data.editable(x == 1);
                        }
                    });
                });
            }
        };

        button = () => {
            let self = this,
                finder = self.finder,
                CS00020_IS00130: IFindData = finder.find('CS00020', 'IS00130'),
                CS00020_IS00238: IFindData = finder.find('CS00020', 'IS00238'),
                CS00020_IS00239: IFindData = finder.find('CS00020', 'IS00239'),
                CS00020_IS00184: IFindData = finder.find('CS00020', 'IS00184'),
                CS00020_IS00185: IFindData = finder.find('CS00020', 'IS00185');

            if (CS00020_IS00130) {
                CS00020_IS00130.ctrl.on('click', () => {
                    let _finder = finder,
                        lstComboBoxValue = CS00020_IS00130.data.lstComboBoxValue,
                        selectedWorkTypeCode = CS00020_IS00130.data.value() || "";

                    setShared('parentCodes', {
                        workTypeCodes: _.map(lstComboBoxValue, x => x.optionValue),
                        selectedWorkTypeCode: selectedWorkTypeCode,
                        workTimeCodes: "",
                        selectedWorkTimeCode: ""
                    }, true);

                    modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                        var childData: IChildData = getShared('childData');
                        if (!childData) {
                            CS00020_IS00130.data.value(undefined);
                        } else {
                            CS00020_IS00130.data.value(childData.selectedWorkTypeCode);
                        }
                    });
                });
            }

            // Button IS00238
            if (CS00020_IS00238) {
                CS00020_IS00238.ctrl.on('click', () => {
                    let _finder = finder,
                        lstComboBoxValue = CS00020_IS00238.data.lstComboBoxValue,
                        selectedWorkTypeCode = CS00020_IS00238.data.value() || "";

                    setShared('CS00020_IS00238', {
                        workTypeCodes: _.map(lstComboBoxValue, x => x.optionValue),
                        selectedWorkTypeCode: selectedWorkTypeCode
                    }, true);

                    modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                        var childData: IChildData = getShared('CS00020_IS00238');
                        if (!childData) {
                            CS00020_IS00238.data.value(undefined);
                        } else {
                            CS00020_IS00238.data.value(childData.selectedWorkTypeCode);
                        }
                    });
                });
            }

            // Button IS00239
            if (CS00020_IS00239) {
                CS00020_IS00239.ctrl.on('click', () => {
                    let _finder = finder,
                        selectedWorkTimeCode = CS00020_IS00239.data.value() || "";

                    setShared('CS00020_IS00239', {
                        workTimeCodes: "",
                        selectedWorkTimeCode: selectedWorkTimeCode
                    }, true);

                    modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                        var childData: IChildData = getShared('CS00020_IS00239');
                        if (!childData) {
                            CS00020_IS00239.data.value(undefined);
                        } else {
                            CS00020_IS00239.data.value(childData.selectedWorkTypeCode);
                        }
                    });
                });
            }

            // Button IS00184
            if (CS00020_IS00184) {
                CS00020_IS00184.ctrl.on('click', () => {
                    let _finder = finder,
                        lstComboBoxValue = CS00020_IS00184.data.lstComboBoxValue,
                        selectedWorkTypeCode = CS00020_IS00184.data.value() || "";

                    setShared('CS00020_IS00184', {
                        workTypeCodes: _.map(lstComboBoxValue, x => x.optionValue),
                        selectedWorkTypeCode: selectedWorkTypeCode
                    }, true);

                    modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                        var childData: IChildData = getShared('CS00020_IS00184');
                        if (!childData) {
                            CS00020_IS00184.data.value(undefined);
                        } else {
                            CS00020_IS00184.data.value(childData.selectedWorkTypeCode);
                        }
                    });
                });
            }


            // Button IS00185
            if (CS00020_IS00185) {
                CS00020_IS00185.ctrl.on('click', () => {
                    let _finder = finder,
                        selectedWorkTimeCode = CS00020_IS00185.data.value() || "";

                    setShared('CS00020_IS00185', {
                        workTimeCodes: "",
                        selectedWorkTimeCode: selectedWorkTimeCode
                    }, true);

                    modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                        var childData: IChildData = getShared('CS00020_IS00185');
                        if (!childData) {
                            CS00020_IS00185.data.value(undefined);
                        } else {
                            CS00020_IS00185.data.value(childData.selectedWorkTypeCode);
                        }
                    });
                });
            }


        };

        combobox = () => {
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
        firstTime: string;
        secondTime: string;
    }
} 