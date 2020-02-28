module nts.uk.pr.view.qmm039.a.viewmodel {

    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    import format = nts.uk.text.format;

    import ITEM_CLASS = nts.uk.pr.view.qmm039.share.model.ITEM_CLASS;
    import PERVALUECATECLS = nts.uk.pr.view.qmm039.share.model.PERVALUECATECLS;
    import SALBONUSCATE = nts.uk.pr.view.qmm039.share.model.SALBONUSCATE;
    import getShared = nts.uk.ui.windows.getShared;
    import MODE = nts.uk.pr.view.qmm039.share.model.MODE;
    import MOFIDY_METHOD = nts.uk.pr.view.qmm039.share.model.MOFIDY_METHOD;
    import hasError = nts.uk.ui.errors.hasError;

    export class ScreenModel {
        mode: KnockoutObservable<number> = ko.observable(MODE.NORMAL);
        classificationCategory: KnockoutObservable<number> = ko.observable(PERVALUECATECLS.SUPPLY);
        salaryBonusCategory: KnockoutObservable<number> = ko.observable(SALBONUSCATE.SALARY);
        itemList: KnockoutObservableArray<ItemModel>;
        individualPriceCode: KnockoutObservable<any> = ko.observable('');
        individualPriceName: KnockoutObservable<any> = ko.observable('');
        individualPriceCodeLabel: KnockoutObservable<string> = ko.observable('');
        individualPriceNameLabel: KnockoutObservable<string> = ko.observable('');
        periodStartYM: KnockoutObservable<string> = ko.observable('');
        periodEndYM: KnockoutObservable<string> = ko.observable('');
        individualPriceName: KnockoutObservable<any> = ko.observable('');
        onTab: KnockoutObservable<number> = ko.observable(ITEM_CLASS.SALARY_SUPLY);
        itemClas: KnockoutObservable<number> = ko.observable(0);
        titleTab: KnockoutObservable<string> = ko.observable('');
        isRegistrationable: KnockoutObservable<boolean> = ko.observable(false);
        isAddableHis: KnockoutObservable<boolean> = ko.observable(false);
        isEditableHis: KnockoutObservable<boolean> = ko.observable(false);
        focusStartPage: boolean;
        itemClassLabel: KnockoutObservable<string> = ko.observable('');
        selectedTab: KnockoutObservable<string>;
        dataSource: any = ko.observableArray([]);
        selectedHis: KnockoutObservable<ItemModel>;
        selectedHisCode: any;
        singleSelectedCode: any;
        listComponentOption: ComponentOption;
        tabindex: number;
        enable: KnockoutObservable<boolean>;
        A7_8: KnockoutObservable<string> = ko.observable(null);

        //_____CCG001________
        ccgcomponent: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        targetBtnText: string = getText("KCP009_3");

        //___________KCP009______________
        employeeInputList: KnockoutObservableArray<EmployeeKcp009> = ko.observableArray([]);
        systemReference: KnockoutObservable<number> = ko.observable(SystemType.SALARY);
        isDisplayOrganizationName: KnockoutObservable<boolean> = ko.observable(false);
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        selectedEmployeeCode: KnockoutObservableArray<string>;
        employeeList: KnockoutObservableArray<TargetEmployee>;
        date: KnockoutObservable<string>;
        value: KnockoutObservable<number>;
        currencyeditor: any;
        columns: any;

        constructor() {
            let self = this;
            self.selectedTab = ko.observable('tab-1');
            self.columns = [
                {key: 'index', length: 0, hidden: true},
                {key: 'period', length: 7, template: "<div>${period}</div>"},
                {key: 'amount', length: 3, template: "<div style='text-align: right'>${amount}</div>"}
            ];
            // initial ccg options
            self.itemList = ko.observableArray([]);
            self.itemList.subscribe(function (newValue) {
                if (newValue.length > 0) {
                    self.isEditableHis(true);
                    switch (self.itemClas()) {
                        case ITEM_CLASS.SALARY_SUPLY:
                            self.itemClassLabel(format(getText('QMM039_21'), '給与支給'));
                            break;
                        case ITEM_CLASS.SALARY_DEDUCTION:
                            self.itemClassLabel(format(getText('QMM039_21'), '給与控除'));
                            break;
                        case ITEM_CLASS.BONUS_SUPLY:
                            self.itemClassLabel(format(getText('QMM039_21'), '賞与支給'));
                            break;
                        case ITEM_CLASS.BONUS_DEDUCTION:
                            self.itemClassLabel(format(getText('QMM039_21'), '賞与控除'));
                            break;
                        default:
                            self.itemClassLabel(format(getText('QMM039_21'), '給与支給'));
                            break;
                    }
                    self.individualPriceCodeLabel(self.individualPriceCode());
                    self.individualPriceNameLabel(self.individualPriceName());
                } else {
                    self.isEditableHis(false);
                    self.itemClassLabel('');
                    self.individualPriceCodeLabel('');
                    self.individualPriceNameLabel('');
                    self.periodStartYM('');
                    self.periodEndYM('');
                    self.currencyeditor.value(null);
                }
            });
            self.focusStartPage = true;
            self.selectedHis = ko.observable(null);
            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedCode.subscribe(function (newValue) {
                let index = _.findIndex(self.dataSource(), function (o) {
                    return o.code == newValue;
                });
                if (index != -1) {
                    self.individualPriceCode(self.dataSource()[index].code);
                    self.individualPriceName(self.dataSource()[index].name);
                    self.historyProcess(self.dataSource()[index].code, 0);
                }
                self.selectedHisCode(0);
            });
            self.selectedItem.subscribe((newValue) => {
                self.historyProcess(self.individualPriceCode(), 0);
                if (self.itemList().length > 0) {
                    self.selectedHisCode(0);
                }
            });
            self.selectedHisCode = ko.observable(0);
            self.selectedHisCode.subscribe(function (newValue) {
                if (newValue == "") {
                    return;
                }
                self.changeHistory(self.itemList()[newValue]);
                if (self.mode() == MODE.ADD_HISTORY) {
                    self.singleSelectedCode.valueHasMutated();
                    self.mode(MODE.NORMAL);
                }
            });
            self.currencyeditor = {
                value: ko.observable(0),
                constraint: '',
                option: new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 0,
                    currencyformat: 'JPY'
                }),
                required: ko.observable(true),
                enable: ko.observable(true),
                readonly: ko.observable(false)
            };

            // _____CCG001________
            self.selectedEmployee = ko.observableArray([]);
            self.ccgcomponent = {
                /** Common properties */
                systemType: SystemType.SALARY,
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment().toISOString(),
                periodStartDate: moment().toISOString(),
                periodEndDate: moment().toISOString(),
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: true,
                showSameWorkplace: false,
                showSameWorkplaceAndChild: false,

                /** Advanced search properties */
                showEmployment: true,
                showDepartment: true,
                showWorkplace: false,
                showClassification: false,
                showJobTitle: false,
                showWorktype: false,
                isMutipleCheck: true,
                showOnStart: true,
                tabindex: 4,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    self.selectedEmployee(data.listEmployee);
                    self.convertEmployeeCcg01ToKcp009(data.listEmployee);
                }
            }
        }

        onSelectTab(param) {
            let self = this;
            switch (param) {
                case ITEM_CLASS.SALARY_SUPLY:
                    self.classificationCategory(PERVALUECATECLS.SUPPLY);
                    self.salaryBonusCategory(SALBONUSCATE.SALARY);
                    self.onTab(ITEM_CLASS.SALARY_SUPLY);
                    self.titleTab(getText('QMM039_6'));
                    $("#sidebar").ntsSideBar("active", param);
                    self.changeItemClass(PERVALUECATECLS.SUPPLY);
                    self.itemClas(ITEM_CLASS.SALARY_SUPLY);
                    $('#emp-component').focus();
                    break;
                case ITEM_CLASS.SALARY_DEDUCTION:
                    self.classificationCategory(PERVALUECATECLS.DEDUCTION);
                    self.salaryBonusCategory(SALBONUSCATE.SALARY);
                    self.onTab(ITEM_CLASS.SALARY_DEDUCTION);
                    self.titleTab(getText('QMM039_7'));
                    $("#sidebar").ntsSideBar("active", param);
                    self.changeItemClass(PERVALUECATECLS.DEDUCTION);
                    self.itemClas(ITEM_CLASS.SALARY_DEDUCTION);
                    $('#emp-component').focus();
                    break;
                case ITEM_CLASS.BONUS_SUPLY:
                    self.classificationCategory(PERVALUECATECLS.SUPPLY);
                    self.salaryBonusCategory(SALBONUSCATE.BONUSES);
                    self.onTab(ITEM_CLASS.BONUS_SUPLY);
                    self.titleTab(getText('QMM039_8'));
                    $("#sidebar").ntsSideBar("active", param);
                    self.changeItemClass(PERVALUECATECLS.SUPPLY);
                    self.itemClas(ITEM_CLASS.BONUS_SUPLY);
                    $('#emp-component').focus();
                    break;
                case ITEM_CLASS.BONUS_DEDUCTION:
                    self.classificationCategory(PERVALUECATECLS.DEDUCTION);
                    self.salaryBonusCategory(SALBONUSCATE.BONUSES);
                    self.onTab(ITEM_CLASS.BONUS_DEDUCTION);
                    self.titleTab(getText('QMM039_9'));
                    $("#sidebar").ntsSideBar("active", param);
                    self.changeItemClass(PERVALUECATECLS.DEDUCTION);
                    self.itemClas(ITEM_CLASS.BONUS_DEDUCTION);
                    $('#emp-component').focus();
                    break;
                default:
                    self.classificationCategory(PERVALUECATECLS.SUPPLY);
                    self.salaryBonusCategory(SALBONUSCATE.SALARY);
                    self.onTab(ITEM_CLASS.SALARY_SUPLY);
                    self.titleTab(getText('QMM039_9'));
                    $("#sidebar").ntsSideBar("active", 1);
                    self.changeItemClass(PERVALUECATECLS.SUPPLY);
                    self.itemClas(ITEM_CLASS.SALARY_SUPLY);
                    $('#emp-component').focus();
                    break;
            }
        }

        //TODO SELECT  ITEM CLASSFICATION
        changeItemClass(item_class: number): void {
            let self = this;
            nts.uk.ui.errors.clearAll();
            service.getPersonalMoneyName(item_class).done(function (data) {
                if (data.length > 0) {
                    let array = [];
                    let index = 0;
                    _.forEach(data, function (salIndAmountName) {
                        array.push(new Node(index, salIndAmountName.individualPriceCode, salIndAmountName.individualPriceName));
                        index++;
                    });
                    self.dataSource(array);
                    self.singleSelectedCode(data[0].individualPriceCode);
                    self.individualPriceName(data[0].individualPriceName);
                    self.individualPriceCode(data[0].individualPriceCode);
                    if (self.itemList.length > 0) {
                        self.individualPriceNameLabel(data[0].individualPriceName);
                        self.individualPriceCodeLabel(data[0].individualPriceCode);
                        self.A7_8('～');
                    }
                    self.historyProcess(data[0].individualPriceCode, 0);
                } else {
                    nts.uk.ui.dialog.alertError({messageId: "MsgQ_169"});
                    self.A7_8(null);
                    self.itemList([]);
                    self.dataSource([]);
                    self.singleSelectedCode(null);
                    self.individualPriceCode('');
                    self.individualPriceName('');
                    self.individualPriceCodeLabel('');
                    self.individualPriceNameLabel('');
                    self.periodStartYM('');
                    self.periodEndYM('');
                    self.currencyeditor.value(null);
                    self.mode(MODE.HISTORY_UNREGISTERED);
                    self.isAddableHis(false);
                }
            });
        }

        //TODO CHANGE ITEM
        historyProcess(perValCode, selectedIndex): void {
            let self = this;
            self.isAddableHis(true);
            nts.uk.ui.errors.clearAll();
            let dto = {
                perValCode: perValCode,
                empId: self.selectedItem(),
                cateIndicator: self.classificationCategory(),
                salBonusCate: self.salaryBonusCategory()
            }
            service.getSalIndAmountHis(dto).done(function (data) {
                if (data != null) {
                    self.A7_8('～');
                    self.isRegistrationable(true);
                    self.currencyeditor.enable(true);
                    self.mode(MODE.NORMAL);
                    let array = [];
                    for (let i = 0; i < data.period.length; i++) {
                        array.push(
                            new ItemModel(
                                i,
                                data.period[i].historyID,
                                data.period[i].periodStartYm,
                                data.period[i].periodEndYm,
                                format(getText("QMM039_18"), self.formatYM(data.period[i].periodStartYm), self.formatYM(data.period[i].periodEndYm)), data.salIndAmountList[i].amountOfMoney))
                    }

                    self.itemList(array);
                    self.isRegistrationable(true);
                    self.changeHistory(array[selectedIndex]);
                    if (self.focusStartPage) {
                        $('#emp-component').focus();
                        console.log("focus");
                        self.focusStartPage = false;
                    }
                } else {
                    self.A7_8(null);
                    self.itemList([]);
                    self.periodStartYM(null);
                    self.periodEndYM(null);
                    self.isRegistrationable(false);
                    self.currencyeditor.value(null);
                    self.currencyeditor.enable(false);
                    self.mode(MODE.HISTORY_UNREGISTERED);
                }
            });
        }

        //TODO CHANGE HISTORY
        changeHistory(selectedHis) {
            let self = this;
            self.selectedHis(selectedHis);
            nts.uk.ui.errors.clearAll();
            self.periodStartYM(self.formatYM(self.selectedHis().periodStartYm));
            self.periodEndYM(self.formatYM(self.selectedHis().periodEndYm));
            self.currencyeditor.value(self.selectedHis().amount);
        }

        formatYM(intYM) {
            return intYM.toString().substr(0, 4) + '/' + intYM.toString().substr(4, intYM.length);
        }

        formatYMToInt(stringYM: string) {
            let arr = stringYM.split('/');
            return parseInt(arr[0]) * 100 + parseInt(arr[1]);
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getInfoEmLogin().done(function (emp) {
                service.getWpName().done(function (wp) {
                    service.getBaseDate().done((baseDate) => {
                        if (wp == null || wp.workplaceId == null || wp.workplaceId == "") {
                        } else {

                            self.employeeInputList.push(new EmployeeKcp009(emp.sid,
                                emp.employeeCode, emp.employeeName, wp.name, wp.name));
                            self.initKCP009();
                            if (baseDate) {
                                self.ccgcomponent.baseDate = baseDate;
                            }
                            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
                            dfd.resolve();

                        }
                        self.onSelectTab(ITEM_CLASS.SALARY_SUPLY);
                    }).fail((res) => {
                        dfd.reject();
                    });
                }).fail(function (result) {
                    dfd.reject();
                });
                $('#emp-component').focus();
            }).fail(function (result) {
                dfd.reject();
            });
            return dfd.promise();
        }

        initKCP009() {
            let self = this;
            //_______KCP009_______
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: -1
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }

        convertEmployeeCcg01ToKcp009(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeInputList([]);
            _.each(dataList, function (item) {
                self.employeeInputList.push(new EmployeeKcp009(item.employeeId, item.employeeCode, item.employeeName, item.workplaceName, ""));
            });
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
            if (dataList.length == 0) {
                self.selectedItem('');
            } else {
                let item = self.findIdSelected(dataList, self.selectedItem());
                let sortByEmployeeCode = _.orderBy(dataList, ["employeeCode"], ["asc"]);
                if (item == undefined) self.selectedItem(sortByEmployeeCode[0].employeeId);
            }
        }

        findIdSelected(dataList: Array<any>, selectedItem: string): any {
            return _.find(dataList, function (obj) {
                return obj.employeeId == selectedItem;
            })
        }

        public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let employeeSearchs: TargetEmployee[] = [];
            self.selectedEmployeeCode([]);
            for (let i = 0; i < dataList.length; i++) {
                let employeeSearch = dataList[i];
                let employee = {
                    code: employeeSearch.employeeCode,
                    name: employeeSearch.employeeName,
                    workplaceName: employeeSearch.workplaceName,
                    sid: employeeSearch.employeeId,
                    scd: employeeSearch.employeeCode,
                    businessname: employeeSearch.employeeName
                };
                employeeSearchs.push(<TargetEmployee>employee);
                self.selectedEmployeeCode.push(employee.code);

                if (i == (dataList.length - 1)) {
                    dfd.resolve();
                }
            }
            self.employeeList(employeeSearchs);
            return dfd.promise();
        }

        //TODO TO SCREEN B
        public toScreenB(): void {
            let self = this;
            let params = {};
            if (self.mode() == MODE.NORMAL) {
                params = {
                    historyID: self.itemList()[0].historyID,
                    period: {
                        baseYm: self.itemList()[0].periodStartYm,
                        periodStartYm: self.itemList()[0].periodStartYm,
                        periodEndYm: self.itemList()[0].periodEndYm
                    }
                };
                $('#A5_3').focus();
                self.openModalB(params);
            } else {
                service.getEmploymentCode(self.selectedItem()).done((dto) => {
                    if (dto) {
                        service.processYearFromEmp(dto.employmentCode).done((data) => {
                            params = {
                                period: {
                                    baseYm: data,
                                    periodStartYm: null,
                                    periodEndYm: 999912
                                }
                            };
                            self.openModalB(params);
                        }).fail((err) => {
                            nts.uk.ui.dialog.alertError(err.message);
                        });
                    } else {
                        params = {
                            period: {
                                baseYm: self.formatYMToInt(moment().format("YYYY/MM")),
                                periodStartYm: null,
                                periodEndYm: 999912
                            }
                        };
                        self.openModalB(params);
                    }
                }).fail((err) => {
                    nts.uk.ui.dialog.alertError(err.message);
                });
            }

        }

        openModalB(params) {
            let self = this;
            setShared("QMM039_A_PARAMS", params);
            modal('/view/qmm/039/b/index.xhtml').onClosed(function () {
                let params = getShared("QMM039_B_RES_PARAMS");
                if (params) {
                    self.selectedHisCode(0);
                    self.periodStartYM(nts.uk.time.parseYearMonth(params.periodStartYm).format());
                    self.A7_8('～');
                    self.periodEndYM(nts.uk.time.parseYearMonth(params.periodEndYm).format());

                    if (params.takeoverMethod == 1) {
                        self.currencyeditor.value(null);
                    } else {
                        self.currencyeditor.value(self.itemList()[0].amount);
                    }
                    let array = self.itemList();
                    array.unshift(new ItemModel(
                        0,
                        null,
                        params.periodStartYm,
                        params.periodEndYm,
                        format(getText("QMM039_18"), nts.uk.time.parseYearMonth(params.periodStartYm).format(), nts.uk.time.parseYearMonth(params.periodEndYm).format()),
                        self.currencyeditor.value()
                    ));
                    if (array.length > 1) {

                        array[1].periodEndYm = (params.periodStartYm - 1) % 100 == 0 ? params.periodStartYm - 101 + 12 : params.periodStartYm - 1;
                        array[1].period = format(getText("QMM039_18"), nts.uk.time.parseYearMonth(array[1].periodStartYm).format(), nts.uk.time.parseYearMonth(array[1].periodEndYm).format());

                    }

                    for (let i = 0; i < array.length; i++) {
                        array[i].index = i;
                    }
                    self.itemList(array);
                    self.isEditableHis(false);
                    self.isAddableHis(false);
                    self.isRegistrationable(true);
                    self.currencyeditor.enable(true);
                    self.mode(MODE.ADD_HISTORY);
                }
            });
        }

        //TODO TO SCREEN C
        public toScreenC(): void {
            let self = this;
            if (self.mode() == MODE.NORMAL) $('#list-box').focus();
            let params = {
                employeeInfo: {
                    empId: self.selectedItem(),
                    personalValcode: self.individualPriceCode(),
                    itemClass: self.itemClas()
                },
                period: {
                    historyId: self.selectedHis().historyID,
                    periodStartYm: self.selectedHis().periodStartYm,
                    periodEndYm: self.selectedHis().periodEndYm
                },

                lastHistoryId: self.itemList().length == (self.itemList()[self.selectedHisCode()].index + 1) ? null : self.itemList()[parseInt(self.selectedHisCode()) + 1].historyID,
                lastPeriodEndYm: self.itemList().length == (self.itemList()[self.selectedHisCode()].index + 1) ? null : self.itemList()[parseInt(self.selectedHisCode()) + 1].periodEndYm,
                lastPeriodStartYm: self.itemList().length == (self.itemList()[self.selectedHisCode()].index + 1) ? null : self.itemList()[parseInt(self.selectedHisCode()) + 1].periodStartYm

            }
            setShared("QMM039_C_PARAMS", params);
            modal('/view/qmm/039/c/index.xhtml', {title: '',}).onClosed(function (): any {
                let params = getShared('QMM039_C_RES_PARAMS');
                if (params) {
                    if (params.modifyMethod == MOFIDY_METHOD.DELETE) {
                        self.historyProcess(self.individualPriceCode(), 0);
                    } else {
                        self.historyProcess(self.individualPriceCode(), self.selectedHis().index);
                    }
                }

                self.mode(MODE.NORMAL);
            });
        }

        //TODO TO SCREEN D
        public toScreenD(): void {
            let self = this;
            let empName;
            let empId;
            _.forEach(self.employeeInputList(), function (data) {
                _.forEach(data, function (value, key) {
                    if (key == 'id' && value == self.selectedItem()) {
                        empName = data.businessName;
                        empId = data.code;
                    }
                });
            });
            let params = {
                empCode: empId,
                empName: empName,
                empId: self.selectedItem(),
                itemClassification: self.itemClas(),
                personalValCode: self.individualPriceCode(),
                cateIndicator: self.classificationCategory(),
                salBonusCate: self.salaryBonusCategory()
            }
            setShared("QMM039_D_PARAMS", params);
            modal('/view/qmm/039/d/index.xhtml').onClosed(() => {

            });
        }

        registration(): void {
            //TODO REGISTRATION
            let self = this;
            $('.nts-input').trigger('validate');
            if (hasError()) return;
            if (self.mode() == MODE.NORMAL) {
                let command = {
                    historyId: self.selectedHis().historyID,
                    amountOfMoney: self.currencyeditor.value()
                };
                service.updateHistory(command).done(function (data) {
                    nts.uk.ui.dialog.info({messageId: "Msg_15"});
                    let tempSelected = self.selectedHisCode();
                    self.historyProcess(self.individualPriceCode(), tempSelected);

                    self.selectedHisCode(tempSelected);

                });
            } else if (self.mode() == MODE.ADD_HISTORY) {
                let historyId = nts.uk.util.randomId();
                let command = {
                    salIndAmountHisCommand: {
                        perValCode: self.individualPriceCode(),
                        empId: self.selectedItem(),
                        cateIndicator: self.classificationCategory(),
                        yearMonthHistoryItem: [{
                            historyId: historyId,
                            startMonth: self.formatYMToInt(self.periodStartYM()),
                            endMonth: self.formatYMToInt(self.periodEndYM()),
                        }],
                        salBonusCate: self.salaryBonusCategory()
                    },
                    salIndAmountCommand: {
                        historyId: historyId,
                        amountOfMoney: self.currencyeditor.value()
                    },
                    oldHistoryId: null,
                    newEndMonthOfOldHistory: null
                };
                if (self.itemList().length > 1) {
                    command.oldHistoryId = self.itemList()[1].historyID;
                    command.newEndMonthOfOldHistory = self.itemList()[1].periodEndYm;
                }


                service.addHistory(command).done(function (data) {
                    nts.uk.ui.dialog.info({messageId: "Msg_15"});
                    self.historyProcess(self.individualPriceCode(), 0);
                    self.isEditableHis(true);
                    self.mode(MODE.NORMAL);
                });
            }
        }
    }

    export interface GroupOption {
        /** Common properties */
        showEmployeeSelection?: boolean; // 検索タイプ
        systemType: number; // システム区分
        showQuickSearchTab?: boolean; // クイック検索
        showAdvancedSearchTab?: boolean; // 詳細検索
        showBaseDate?: boolean; // 基準日利用
        showClosure?: boolean; // 就業締め日利用
        showAllClosure?: boolean; // 全締め表示
        showPeriod?: boolean; // 対象期間利用
        periodFormatYM?: boolean; // 対象期間精度
        isInDialog?: boolean;

        /** Required parameter */
        baseDate?: string; // 基準日
        periodStartDate?: string; // 対象期間開始日
        periodEndDate?: string; // 対象期間終了日
        inService: boolean; // 在職区分
        leaveOfAbsence: boolean; // 休職区分
        closed: boolean; // 休業区分
        retirement: boolean; // 退職区分

        /** Quick search tab options */
        showAllReferableEmployee?: boolean; // 参照可能な社員すべて
        showOnlyMe?: boolean; // 自分だけ
        showSameWorkplace?: boolean; // 同じ職場の社員
        showSameWorkplaceAndChild?: boolean; // 同じ職場とその配下の社員

        /** Advanced search properties */
        showEmployment?: boolean; // 雇用条件
        showWorkplace?: boolean; // 職場条件
        showClassification?: boolean; // 分類条件
        showJobTitle?: boolean; // 職位条件
        showWorktype?: boolean; // 勤種条件
        isMutipleCheck?: boolean; // 選択モード
        isTab2Lazy?: boolean;

        /** Data returned */
        returnDataFromCcg001: (data: Ccg001ReturnedData) => void;
    }

    export interface EmployeeSearchDto {
        employeeId: string;
        employeeCode: string;
        employeeName: string;
        workplaceId: string;
        workplaceName: string;
    }

    export interface Ccg001ReturnedData {
        baseDate: string; // 基準日
        closureId?: number; // 締めID
        periodStart: string; // 対象期間（開始)
        periodEnd: string; // 対象期間（終了）
        listEmployee: Array<EmployeeSearchDto>; // 検索結果
    }

    export interface TargetEmployee {
        code: string;
        name?: string;
        workplaceName?: string;
        isAlreadySetting?: boolean;
        sid: string;
        scd: string;
        businessname: string;
    }


    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<EmployeeKcp009>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
    }

    export class SystemType {
        static PERSONAL_INFORMATION = 1;
        static EMPLOYMENT = 2;
        static SALARY = 3;
        static HUMAN_RESOURCES = 4;
        static ADMINISTRATOR = 5;
    }

    class Node {
        index: number;
        code: string;
        name: string;

        constructor(index: number, code: string, name: string) {
            var self = this;
            self.index = index;
            self.code = code;
            self.name = name;
        }
    }

    class ItemModel {
        index: number;
        historyID: string;
        periodStartYm: number;
        periodEndYm: number;
        period: string;
        amount: number;

        constructor(index: number, historyID: string, periodStartYm: number, periodEndYm: number, period: string, amount: number) {
            this.index = index;
            this.historyID = historyID;
            this.periodStartYm = periodStartYm;
            this.periodEndYm = periodEndYm;
            this.period = period;
            this.amount = amount;
        }
    }

    export class EmployeeKcp009 {
        id: string;
        code: string;
        businessName: string;
        workplaceName: string;
        depName: string;

        constructor(id: string, code: string, businessName: string, workplaceName: string, depName: string) {
            this.id = id;
            this.code = code;
            this.businessName = businessName;
            this.workplaceName = workplaceName;
            this.depName = depName;
        }
    }
}