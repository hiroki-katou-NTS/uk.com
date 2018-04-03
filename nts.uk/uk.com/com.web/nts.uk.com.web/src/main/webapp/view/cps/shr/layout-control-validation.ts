module nts.layout {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import nou = nts.uk.util.isNullOrUndefined;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    let rmError = nts.uk.ui.errors["removeByCode"],
        getError = nts.uk.ui.errors["getErrorByElement"],
        getErrorList = nts.uk.ui.errors["getErrorList"],
        clearError = window['nts']['uk']['ui']['errors']['clearAll'];

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
                    id: `#${subscribe.itemDefId.replace(/[-_]/g, '')}`,
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
                    id: `#${x.itemDefId.replace(/[-_]/g, '')}`,
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
                    id: `#${x.itemDefId.replace(/[-_]/g, '')}`,
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
            self.relate_radio();
            self.relate_button();

            validate.initCheckError(lstCls);
        }

        textBox = () => {
            let self = this,
                finder = self.finder,
                CS00002_IS00003: IFindData = finder.find('CS00002', 'IS00003'),
                CS00002_IS00004: IFindData = finder.find('CS00002', 'IS00004'),
                CS00002_IS00015: IFindData = finder.find('CS00002', 'IS00015'),
                CS00002_IS00016: IFindData = finder.find('CS00002', 'IS00016'),
                validateName = (item: IFindData) => {
                    $(document).on('change', item.id, () => {
                        let value: string = ko.toJS(item.data.value),
                            index: number = value.indexOf('　'),
                            lindex: number = value.lastIndexOf('　'),
                            dom = $(item.id);

                        if (index > 0 && lindex < value.length - 1) {
                            rmError(dom, "Msg_924");
                        } else if (!dom.parent().hasClass('error')) {
                            !dom.is(':disabled') && dom.ntsError('set', { messageId: "Msg_924" });
                        }
                    });
                };

            CS00002_IS00003 && validateName(CS00002_IS00003);

            CS00002_IS00004 && validateName(CS00002_IS00004);

            CS00002_IS00015 && validateName(CS00002_IS00015);

            CS00002_IS00016 && validateName(CS00002_IS00016);
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
        }

        relate_radio = () => {
            let self = this,
                finder = self.finder,
                radios: Array<IRelateRadio> = [
                    {
                        ctgCode: 'CS00024',
                        radioCode: 'IS00387',
                        setParentCode: 'IS00388'
                    }
                ],
                validation = (radio: IRelateRadio) => {
                    let rd: IFindData = finder.find(radio.ctgCode, radio.radioCode),
                        ctrls: Array<IFindData> = finder.findChilds(radio.ctgCode, radio.setParentCode);

                    //debugger;
                    if (rd) {
                        rd.data.value.subscribe(x => {
                            _.each(ctrls, c => {
                                c.data.editable(x == 1);
                            });
                        });

                        setTimeout(() => {
                            rd.data.value.valueHasMutated();
                        }, 0);
                    }
                };

            _(radios).each(radio => validation(radio));
        }

        button = () => {
            let self = this,
                finder = self.finder,
                groups: Array<IGroupControl> = [
                    {
                        ctgCode: 'CS00019',
                        firstTimes: {
                            start: 'IS00106',
                            end: 'IS00107'
                        },
                        secondTimes: {
                            start: 'IS00109',
                            end: 'IS00110'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00128'
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00130',
                        workTime: 'IS00131',
                        firstTimes: {
                            start: 'IS00133',
                            end: 'IS00134'
                        },
                        secondTimes: {
                            start: 'IS00136',
                            end: 'IS00137'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00139',
                        workTime: 'IS00140',
                        firstTimes: {
                            start: 'IS00142',
                            end: 'IS00143'
                        },
                        secondTimes: {
                            start: 'IS00145',
                            end: 'IS00146'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00148',
                        workTime: 'IS00149',
                        firstTimes: {
                            start: 'IS00151',
                            end: 'IS00152'
                        },
                        secondTimes: {
                            start: 'IS00154',
                            end: 'IS00155'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00157',
                        workTime: 'IS00158',
                        firstTimes: {
                            start: 'IS00160',
                            end: 'IS00161'
                        },
                        secondTimes: {
                            start: 'IS00163',
                            end: 'IS00164'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00166',
                        workTime: 'IS00167',
                        firstTimes: {
                            start: 'IS00169',
                            end: 'IS00170'
                        },
                        secondTimes: {
                            start: 'IS00172',
                            end: 'IS00173'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00175',
                        workTime: 'IS00176',
                        firstTimes: {
                            start: 'IS00178',
                            end: 'IS00179'
                        },
                        secondTimes: {
                            start: 'IS00181',
                            end: 'IS00182'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00193',
                        workTime: 'IS00194',
                        firstTimes: {
                            start: 'IS00196',
                            end: 'IS00197'
                        },
                        secondTimes: {
                            start: 'IS00199',
                            end: 'IS00200'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00202',
                        workTime: 'IS00203',
                        firstTimes: {
                            start: 'IS00205',
                            end: 'IS00206'
                        },
                        secondTimes: {
                            start: 'IS00208',
                            end: 'IS00209'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00211',
                        workTime: 'IS00212',
                        firstTimes: {
                            start: 'IS00214',
                            end: 'IS00215'
                        },
                        secondTimes: {
                            start: 'IS00217',
                            end: 'IS00218'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00220',
                        workTime: 'IS00221',
                        firstTimes: {
                            start: 'IS00223',
                            end: 'IS00224'
                        },
                        secondTimes: {
                            start: 'IS00226',
                            end: 'IS00227'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00229',
                        workTime: 'IS00230',
                        firstTimes: {
                            start: 'IS00232',
                            end: 'IS00233'
                        },
                        secondTimes: {
                            start: 'IS00235',
                            end: 'IS00236'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00238',
                        workTime: 'IS00239',
                        firstTimes: {
                            start: 'IS00241',
                            end: 'IS00242'
                        },
                        secondTimes: {
                            start: 'IS00244',
                            end: 'IS00245'
                        }
                    },
                    {
                        ctgCode: 'CS00020',
                        workType: 'IS00184',
                        workTime: 'IS00185',
                        firstTimes: {
                            start: 'IS00187',
                            end: 'IS00188'
                        },
                        secondTimes: {
                            start: 'IS00190',
                            end: 'IS00191'
                        }
                    }
                ],
                setData = (ctrl: IFindData, value?: any) => {
                    ctrl && ctrl.data.value(value || undefined);
                },
                setDataText = (ctrl: IFindData, value?: any) => {
                    ctrl && ctrl.data.textValue(value || undefined);
                },
                setEditAble = (ctrl: IFindData, editable?: boolean) => {
                    ctrl && ctrl.data.editable(editable || false);
                },
                validateGroup = (group: IGroupControl) => {
                    let firstTimes: ITimeFindData = group.firstTimes && {
                        start: finder.find(group.ctgCode, group.firstTimes.start),
                        end: finder.find(group.ctgCode, group.firstTimes.end)
                    },
                        secondTimes: ITimeFindData = group.secondTimes && {
                            start: finder.find(group.ctgCode, group.secondTimes.start),
                            end: finder.find(group.ctgCode, group.secondTimes.end)
                        };

                    if (firstTimes && secondTimes) {
                        if (firstTimes.end && secondTimes.start) {
                            $(document).on('change', `${firstTimes.end.id}, ${secondTimes.start.id}`, () => {
                                setTimeout(() => {
                                    let dom1 = $(firstTimes.end.id),
                                        dom2 = $(secondTimes.start.id),
                                        pv = ko.toJS(firstTimes.end.data.value),
                                        nv = ko.toJS(secondTimes.start.data.value),
                                        tpt = typeof pv == 'number',
                                        tnt = typeof nv == 'number';

                                    if (tpt && tnt && pv > nv) {
                                        if (!dom1.parent().hasClass('error')) {
                                            dom1.ntsError('set', { messageId: "Msg_859" });
                                        }

                                        if (!dom2.parent().hasClass('error')) {
                                            dom2.parent().addClass('error');
                                        }
                                    }

                                    if ((tpt && tnt && !(pv > nv)) || (!tpt || !tnt)) {
                                        rmError(dom1, "Msg_859");

                                        if (!getError(dom2).length) {
                                            dom2.parent().removeClass('error');
                                        }

                                        if (!getErrorList().length) {
                                            clearError();
                                        }
                                    }
                                }, 100);
                            });
                        }
                    }
                },
                validateEditable = (group: IGroupControl, wtc?: any) => {
                    let command: ICheckParam = {
                        workTimeCode: ko.toJS(wtc || undefined)
                    },
                        firstTimes: ITimeFindData = group.firstTimes && {
                            start: finder.find(group.ctgCode, group.firstTimes.start),
                            end: finder.find(group.ctgCode, group.firstTimes.end)
                        },
                        secondTimes: ITimeFindData = group.secondTimes && {
                            start: finder.find(group.ctgCode, group.secondTimes.start),
                            end: finder.find(group.ctgCode, group.secondTimes.end)
                        };


                    if (command.workTimeCode) {
                        fetch.check_start_end(command).done(first => {
                            firstTimes && setEditAble(firstTimes.start, !!first);
                            firstTimes && setEditAble(firstTimes.end, !!first);

                            fetch.check_multi_time(command).done(second => {
                                secondTimes && setEditAble(secondTimes.start, !!first && !!second);
                                secondTimes && setEditAble(secondTimes.end, !!first && !!second);
                            });
                        });
                    } else {
                        firstTimes && setEditAble(firstTimes.start, false);
                        firstTimes && setEditAble(firstTimes.end, false);

                        secondTimes && setEditAble(secondTimes.start, false);
                        secondTimes && setEditAble(secondTimes.end, false);
                    }
                };

            _.each(groups, (group: IGroupControl) => {
                let workType: IFindData = group.workType && finder.find(group.ctgCode, group.workType),
                    workTime: IFindData = group.workTime && finder.find(group.ctgCode, group.workTime),
                    firstTimes: ITimeFindData = group.firstTimes && {
                        start: finder.find(group.ctgCode, group.firstTimes.start),
                        end: finder.find(group.ctgCode, group.firstTimes.end)
                    },
                    secondTimes: ITimeFindData = group.secondTimes && {
                        start: finder.find(group.ctgCode, group.secondTimes.start),
                        end: finder.find(group.ctgCode, group.secondTimes.end)
                    };

                if (firstTimes && secondTimes) {
                    validateGroup(group);
                }

                if (!workType) {
                    return;
                }

                if (!workTime) {
                    workType.ctrl.on('click', () => {
                        setShared("KDL002_Multiple", false, true);
                        setShared("KDL002_SelectedItemId", workType.data.value(), true);
                        setShared("KDL002_AllItemObj", _.map(workType.data.lstComboBoxValue, x => x.optionValue), true);

                        modal('at', '/view/kdl/002/a/index.xhtml').onClosed(() => {
                            let childData: Array<any> = getShared('KDL002_SelectedNewItem');

                            if (childData[0]) {
                                setData(workType, childData[0].code);
                                setDataText(workType, childData[0].name);
                            }
                        });
                    });
                } else {
                    validateEditable(group, workTime.data.value);

                    workType.ctrl.on('click', () => {
                        setShared('parentCodes', {
                            workTypeCodes: workType && _.map(workType.data.lstComboBoxValue, x => x.optionValue),
                            selectedWorkTypeCode: workType && ko.toJS(workType.data.value),
                            workTimeCodes: workTime && _.map(workTime.data.lstComboBoxValue, x => x.optionValue),
                            selectedWorkTimeCode: workTime && ko.toJS(workTime.data.value)
                        }, true);

                        modal('at', '/view/kdl/003/a/index.xhtml').onClosed(() => {
                            let childData: IChildData = getShared('childData');

                            if (childData) {
                                setData(workType, childData.selectedWorkTypeCode);
                                setDataText(workType, childData.selectedWorkTypeName);

                                setData(workTime, childData.selectedWorkTimeCode);
                                setDataText(workTime, childData.selectedWorkTimeName);

                                firstTimes && setData(firstTimes.start, childData.first && childData.first.start);
                                firstTimes && setData(firstTimes.end, childData.first && childData.first.end);

                                secondTimes && setData(secondTimes.start, childData.second && childData.second.start);
                                secondTimes && setData(secondTimes.end, childData.second && childData.second.end);

                                validateEditable(group, workTime.data.value);
                            }
                        });
                    });

                    // handle click event of workType
                    workTime.ctrl.on('click', () => workType.ctrl.trigger('click'));
                }
            });
        }

        relate_button = () => {
            let self = this,
                finder: IFinder = self.finder,
                buttons: Array<IRelateButton> = [{
                    ctgCode: 'CS00024',
                    btnCode: 'IS00276'
                }],
                validation = (btn: IRelateButton) => {
                    let button: IFindData = finder.find(btn.ctgCode, btn.btnCode);
                    //debugger;
                };

            _(buttons).each(btn => validation(btn));
        }

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
        }

        dateTime = () => {
            let self = this,
                finder: IFinder = self.finder;
        }
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
        id: string;
        ctrl: JQuery;
        data: IItemData;
    }

    interface IItemData {
        'type': ITEM_TYPE;
        required: boolean;
        value: KnockoutObservable<any>;
        textValue: KnockoutObservable<any>;
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

    interface IGroupControl {
        ctgCode: string;
        workType?: string;
        workTime?: string;
        firstTimes?: ITimeRange;
        secondTimes?: ITimeRange;
    }

    interface ITimeRange {
        start: string;
        end: string;
    }

    interface ITimeFindData {
        start: IFindData;
        end: IFindData;
    }

    interface IRelateRadio {
        ctgCode: string;
        radioCode: string;
        setParentCode: string;
    }

    interface IRelateButton {
        ctgCode: string;
        btnCode: string;
    }
}
