module nts.uk.pr.view.qmm041.a.viewmodel {

    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    import format = nts.uk.text.format;
    import getShared = nts.uk.ui.windows.getShared;
    import model = nts.uk.pr.view.qmm041.share.model;
    import errors = nts.uk.ui.errors;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        // VM
        dataSource: KnockoutObservableArray<model.SalPerUnitPriceName[]> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable(null);
        isAddableHis: KnockoutObservable<boolean> = ko.observable(false);
        isEditableHis: KnockoutObservable<boolean> = ko.observable(false);
        isRegistrable: KnockoutObservable<boolean> = ko.observable(false);
        historyList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedHistoryCode: KnockoutObservable<string> = ko.observable(null);
        columns: any;
        currencyValue: KnockoutObservable<number> = ko.observable(null);
        currencyEnable: KnockoutObservable<boolean> = ko.observable(false);
        mode: KnockoutObservable<number> = ko.observable(model.MODE.NORMAL);
        startYearMonth: KnockoutObservable<string> = ko.observable(null);
        endYearMonth: KnockoutObservable<string> = ko.observable(null);
        personalUnitPriceShortName: KnockoutObservable<string> = ko.observable(null);
        personalUnitPriceCode: KnockoutObservable<string> = ko.observable(null);
        A5_6: KnockoutObservable<string> = ko.observable(null);

        // CCG001
        ccgComponent: GroupOption;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

        //KCP009
        employeeInputList: KnockoutObservableArray<IEmployeeModel>;
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;

        constructor() {
            let self = this;

            //VM
            self.columns = [
                {key: 'historyId', length: 0, hidden: true},
                {key: 'period', length: 8, template: "<div>${period}</div>"},
                {key: 'amountOfMoney', length: 3, template: "<div style='text-align: right'>${amountOfMoney}</div>"}
            ];

            //CCG001
            self.selectedEmployee = ko.observableArray([]);
            self.ccgComponent = {
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
            };

            //KCP009
            self.employeeInputList = ko.observableArray([]);
            self.systemReference = ko.observable(SystemType.SALARY);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = getText("KCP009_3");
            self.selectedItem = ko.observable(null);
            self.tabindex = 2;

            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };


            //subscribe
            self.selectedCode.subscribe((code) => {
                let dto = {
                    personalUnitPriceCode: code,
                    employeeId: self.selectedItem()
                };
                self.getIndEmpSalUnitPriceHistories(dto);
            });

            self.selectedItem.subscribe((item) => {
                let dto = {
                    personalUnitPriceCode: self.selectedCode(),
                    employeeId: item
                };
                self.getIndEmpSalUnitPriceHistories(dto);
            });

            self.selectedHistoryCode.subscribe((historyCode) => {
                errors.clearAll();
                if (self.mode() === model.MODE.ADD_HISTORY) {
                    let dto = {
                        personalUnitPriceCode: self.selectedCode(),
                        employeeId: self.selectedItem()
                    };
                    self.getIndEmpSalUnitPriceHistories(dto).done(() => {
                        self.handleHistorySelection(historyCode);
                    });
                } else {
                    self.handleHistorySelection(historyCode);
                }
            });

            self.mode.subscribe((mode) => {
                if (mode === model.MODE.NORMAL) {
                    self.isRegistrable(true);
                    self.isEditableHis(true);
                    self.isAddableHis(true);
                    self.currencyEnable(true);
                }
                if (mode === model.MODE.HISTORY_UNREGISTERED) {
                    self.isRegistrable(false);
                    self.isEditableHis(false);
                    self.isAddableHis(true);
                    self.currencyEnable(false);
                    self.selectedHistoryCode(null);
                }
                if (mode === model.MODE.ADD_HISTORY) {
                    self.isRegistrable(true);
                    self.isEditableHis(false);
                    self.isAddableHis(false);
                    self.currencyEnable(true);
                }
            });
        }

        handleHistorySelection(historyCode) {
            let self = this;
            if (historyCode) {
                let index = _.findIndex(self.dataSource(), (obj) => {
                    return obj.code === self.selectedCode();
                });
                let historyIndex = self.findHistoryIndex(historyCode);
                self.personalUnitPriceCode(self.dataSource()[index].code);
                self.personalUnitPriceShortName(self.dataSource()[index].shortName);
                self.A5_6(' ～ ');
                self.endYearMonth(self.formatYM(self.historyList()[historyIndex].endYearMonth));
                self.startYearMonth(self.formatYM(self.historyList()[historyIndex].startYearMonth));
                self.currencyValue(self.historyList()[historyIndex].amountOfMoney);
                self.currencyEnable(true);
                self.isEditableHis(true);
                self.isRegistrable(true);
            } else {
                self.personalUnitPriceCode(null);
                self.personalUnitPriceShortName(null);
                self.A5_6(null);
                self.endYearMonth(null);
                self.startYearMonth(null);
                self.currencyValue(null);
                self.currencyEnable(false);
                self.isEditableHis(false);
                self.isRegistrable(false);
            }
        }

        formatYM(intYM) {
            return nts.uk.time.parseYearMonth(intYM).format();
        }

        formatYMToInt(stringYM: string) {
            let arr = stringYM.split('/');
            return parseInt(arr[0]) * 100 + parseInt(arr[1]);
        }

        findHistoryIndex(historyId: string): any {
            let self = this;
            return _.findIndex(self.historyList(), x => historyId === x.historyId);
        }

        toQmm042() {
            nts.uk.request.jump("pr", "/view/qmm/042/a/index.xhtml");
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            $.when(service.getInfoEmpLogin(), service.getWpName(), service.getBaseDate(), service.getSalPerUnitPriceName())
                .done((employee, workplace, baseDate, data) => {
                    if (data.length > 0) {
                        self.dataSource(data);
                        self.selectedCode(data[0].code);
                        self.employeeInputList.push(new EmployeeModel(employee.sid,
                            employee.employeeCode, employee.employeeName, workplace.name, ""));
                        if (baseDate) {
                            self.ccgComponent.baseDate = baseDate;
                        }
                        let dto = {
                            personalUnitPriceCode: self.selectedCode(),
                            employeeId: employee.sid
                        };
                        self.getIndEmpSalUnitPriceHistories(dto).done(() => {
                            block.clear();
                            dfd.resolve();
                        });
                    } else {
                        self.employeeInputList.push(new EmployeeModel(employee.sid,
                            employee.employeeCode, employee.employeeName, workplace.name, ""));
                        dialog.alertError({messageId: "MsgQ_170"}).then(() => {
                            self.isAddableHis(false);
                        });
                    }
                }).always(() => {
                block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }

        getIndEmpSalUnitPriceHistories(dto): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getIndEmpSalUnitPriceHistories(dto).done((data) => {
                self.historyList([]);
                if (data.length > 0) {
                    let array = [];
                    for (let i = 0; i < data.length; i++) {
                        array.push(new HistoryModel({
                            personalUnitPriceCode: data[i].personalUnitPriceCode,
                            employeeId: data[i].employeeId,
                            historyId: data[i].historyId,
                            startYearMonth: data[i].startYearMonth,
                            endYearMonth: data[i].endYearMonth,
                            period: format(getText("QMM041_13"), self.formatYM(data[i].startYearMonth), self.formatYM(data[i].endYearMonth)),
                            amountOfMoney: data[i].amountOfMoney
                        }));
                    }
                    self.historyList(array);
                    self.selectedHistoryCode(self.historyList()[0].historyId);
                    self.mode(model.MODE.NORMAL);
                } else {
                    self.mode(model.MODE.HISTORY_UNREGISTERED);
                }
                dfd.resolve();
            }).fail((res) => {
                dialog.alertError(res.message);
                dfd.reject();
            });
            return dfd.promise();
        }

        convertEmployeeCcg01ToKcp009(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeInputList([]);
            _.each(dataList, (item) => {
                self.employeeInputList.push(new EmployeeModel(item.employeeId, item.employeeCode, item.employeeName, item.workplaceName, ""));
            });
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
            if (dataList.length === 0) {
                self.selectedItem('');
            } else {
                let item = self.findIdSelected(dataList, self.selectedItem());
                let sortByEmployeeCode = _.orderBy(dataList, ["employeeCode"], ["asc"]);
                if (item === undefined) self.selectedItem(sortByEmployeeCode[0].employeeId);
            }
        }

        findIdSelected(dataList: Array<any>, selectedItem: string): any {
            return _.find(dataList, function (obj) {
                return obj.employeeId === selectedItem;
            });
        }

        public displayScreenB(): void {
            let self = this;
            let params = {};
            if (self.mode() === model.MODE.NORMAL) {
                params = {
                    startYearMonth: self.historyList()[0].startYearMonth,
                    historyStatus: model.HISTORY_STATUS.WITH_HISTORY
                };
                self.openModalB(params);
            } else {
                service.getEmploymentCode(self.selectedItem()).done((dto) => {
                    if (dto) {
                        service.processYearFromEmp(dto.employmentCode).done((data) => {
                            params = {
                                startYearMonth: data,
                                historyStatus: model.HISTORY_STATUS.NO_HISTORY
                            };
                            self.openModalB(params);
                        }).fail((err) => {
                            dialog.alertError(err.message);
                        });
                    } else {
                        params = {
                            startYearMonth: self.formatYMToInt(moment().format("YYYY/MM")),
                            historyStatus: model.HISTORY_STATUS.NO_HISTORY
                        };
                        self.openModalB(params);
                    }
                }).fail((err) => {
                    dialog.alertError(err.message);
                });
            }
        }

        openModalB(params) {
            let self = this;
            setShared("QMM041_A_PARAMS", params);
            modal('/view/qmm/041/b/index.xhtml').onClosed(() => {
                let params = getShared("QMM041_B_RES_PARAMS");
                if (params) {
                    self.startYearMonth(self.formatYM(params.startYearMonth));
                    self.endYearMonth("9999/12");

                    if (params.takeOverMethod === model.INHERITANCE.NO) {
                        self.currencyValue(null);
                    } else {
                        self.currencyValue(self.historyList()[0].amountOfMoney);
                    }

                    let history: any = new HistoryModel({
                        personalUnitPriceCode: self.selectedCode(),
                        employeeId: self.selectedItem(),
                        historyId: params.historyId,
                        startYearMonth: params.startYearMonth,
                        endYearMonth: 999912,
                        period: format(getText("QMM041_13"), self.formatYM(params.startYearMonth), "9999/12"),
                        amountOfMoney: self.currencyValue()
                    });
                    let array = self.historyList();
                    array.unshift(history);
                    if (array.length > 1) {
                        array[1].endYearMonth = (params.startYearMonth - 1) % 100 == 0 ? params.startYearMonth - 89 : params.startYearMonth - 1;
                        array[1].period = format(getText("QMM041_13"), self.formatYM(array[1].startYearMonth), self.formatYM(array[1].endYearMonth));
                    }
                    self.historyList(array);
                    self.selectedHistoryCode(history.historyId);
                    self.mode(model.MODE.ADD_HISTORY);
                }
            });
        }

        public displayScreenC(): void {
            let self = this;
            let index = self.findHistoryIndex(self.selectedHistoryCode());
            let params = {
                personalUnitPriceCode: self.selectedCode(),
                employeeId: self.selectedItem(),
                historyId: self.historyList()[index].historyId,
                startYearMonth: self.historyList()[index].startYearMonth,
                endYearMonth: self.historyList()[index].endYearMonth,
                lastHistoryId: index == self.historyList().length - 1 ? null : self.historyList()[index + 1].historyId,
                lastStartYearMonth: index == self.historyList().length - 1 ? null : self.historyList()[index + 1].startYearMonth,
                lastEndYearMonth: index == self.historyList().length - 1 ? null : self.historyList()[index + 1].endYearMonth
            };
            setShared("QMM041_C_PARAMS", params);
            modal('/view/qmm/041/c/index.xhtml').onClosed(() => {
                let params = getShared('QMM041_C_RES_PARAMS');
                if (params) {
                    if (params.modifyMethod == model.MODIFY_METHOD.UPDATE) {
                        let array = self.historyList();
                        array[index].startYearMonth = params.startYearMonth;
                        array[index].period = format(getText("QMM041_13"), self.formatYM(array[index].startYearMonth), self.formatYM(array[index].endYearMonth));
                        if (index < self.historyList().length - 1) {
                            array[index + 1].endYearMonth = params.lastEndYearMonth;
                            array[index + 1].period = format(getText("QMM041_13"), self.formatYM(array[index + 1].startYearMonth), self.formatYM(array[index + 1].endYearMonth));
                        }
                        self.historyList([]);
                        self.historyList(array);
                        self.selectedHistoryCode.valueHasMutated();
                    } else {
                        self.selectedCode.valueHasMutated();
                    }
                }
            });
        }

        public displayScreenD(): void {
            let self = this;
            let employeeCode = null;
            let employeeName = null;
            let params = {};
            _.forEach(self.employeeInputList(), (data) => {
                _.forEach(data, (value, key) => {
                    if (key == 'id' && value == self.selectedItem()) {
                        employeeName = data.businessName;
                        employeeCode = data.code;
                    }
                });
            });
            service.getEmploymentCode(self.selectedItem()).done((code) => {
                params = {
                    employeeId: self.selectedItem(),
                    employeeCode: employeeCode,
                    employeeName: employeeName,
                    employmentCode: code.employmentCode
                };
                setShared("QMM041_D_PARAMS", params);
            });
            modal('/view/qmm/041/d/index.xhtml').onClosed(() => {

            });
        }

        register(): void {
            let self = this;
            $('.nts-input').trigger("validate");
            if (errors.hasError()) return;
            block.invisible();
            if (self.mode() == model.MODE.NORMAL) {
                let command = {
                    historyId: self.selectedHistoryCode(),
                    amountOfMoney: self.currencyValue()
                };
                service.updateAmount(command).done(() => {
                    let index = self.findHistoryIndex(self.selectedHistoryCode());
                    let array = self.historyList();
                    array[index].amountOfMoney = Number(self.currencyValue());
                    self.historyList([]);
                    self.historyList(array);
                    dialog.info({messageId: "Msg_15"});
                }).always(() => {
                    block.clear();
                });
            } else if (self.mode() == model.MODE.ADD_HISTORY) {
                let command = {
                    personalUnitPriceCode: self.selectedCode(),
                    employeeId: self.selectedItem(),
                    historyId: self.historyList()[0].historyId,
                    startYearMonth: self.historyList()[0].startYearMonth,
                    endYearMonth: self.historyList()[0].endYearMonth,
                    amountOfMoney: self.currencyValue(),
                    oldHistoryId: null,
                    newEndYearMonth: null
                };
                if (self.historyList().length > 1) {
                    command.oldHistoryId = self.historyList()[1].historyId;
                    command.newEndYearMonth = self.historyList()[1].endYearMonth;
                }
                service.addHistory(command).done(() => {
                    self.selectedCode.valueHasMutated();
                    dialog.info({messageId: "Msg_15"});
                }).always(() => {
                    block.clear();
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
        showDepartment?: boolean; // 部門条件
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

    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList: KnockoutObservableArray<IEmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
    }

    export interface IEmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }

    export class EmployeeModel {
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

    export class SystemType {
        static PERSONNEL = 1;
        static EMPLOYMENT = 2;
        static SALARY = 3;
        static ACCOUNTING = 4;
        static ADMIN = 6;
    }

    interface IHistoryModel {
        personalUnitPriceCode: string;
        employeeId: string;
        historyId: string;
        startYearMonth: number;
        endYearMonth: number;
        period: string;
        amountOfMoney: number;
    }

    class HistoryModel {
        personalUnitPriceCode: string;
        employeeId: string;
        historyId: string;
        startYearMonth: number;
        endYearMonth: number;
        period: string;
        amountOfMoney: number;

        constructor(params: IHistoryModel) {
            this.personalUnitPriceCode = params.personalUnitPriceCode;
            this.employeeId = params.employeeId;
            this.historyId = params.historyId;
            this.startYearMonth = params.startYearMonth;
            this.endYearMonth = params.endYearMonth;
            this.period = params.period;
            this.amountOfMoney = params.amountOfMoney;
        }
    }

}