module nts.uk.pr.view.qmm041.a.viewmodel {

    import MODE = nts.uk.pr.view.qmm041.share.model.MODE;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    import format = nts.uk.text.format;
    import getShared = nts.uk.ui.windows.getShared;
    import MODIFY_METHOD = nts.uk.pr.view.qmm041.share.model.MODIFY_METHOD;
    import hasError = nts.uk.ui.errors.hasError;
    import model = nts.uk.pr.view.qmm041.share.model;

    export class ScreenModel {
        // VM
        dataSource: KnockoutObservableArray<model.SalPerUnitPriceName[]> = ko.observableArray([]);
        selectedCode: KnockoutObservable<string> = ko.observable(null);
        isAddableHis: KnockoutObservable<boolean> = ko.observable(false);
        isEditableHis: KnockoutObservable<boolean> = ko.observable(false);
        isRegistrable: KnockoutObservable<boolean> = ko.observable(false);
        historyList: KnockoutObservableArray<HistoryModel> = ko.observableArray([]);
        selectedHistoryCode: KnockoutObservable<number> = ko.observable(null);
        columns: any;
        currencyValue: KnockoutObservable<number> = ko.observable(null);
        currencyEnable: KnockoutObservable<boolean> = ko.observable(false);
        mode: KnockoutObservable<number> = ko.observable(MODE.NORMAL);
        periodStartYm: KnockoutObservable<string> = ko.observable(null);
        periodEndYm: KnockoutObservable<string> = ko.observable(null);
        salPerUnitPriceName: KnockoutObservable<string> = ko.observable(null);
        salPerUnitPriceCode: KnockoutObservable<string> = ko.observable(null);
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
        selectedItem: KnockoutObservable<string> = ko.observable(null);
        tabindex: number;

        constructor() {
            let self = this;
            //VM
            self.columns = [
                {key: 'index', length: 0, hidden: true},
                {key: 'period', length: 8},
                {key: 'amount', length: 6, template: "<div style='text-align: right'>${amount}</div>"}
            ];

            //subscribe
            self.selectedHistoryCode.subscribe((historyCode) => {
                if (historyCode != null) {
                    let index = _.findIndex(self.dataSource(), (obj) => {
                        return obj.code === self.selectedCode();
                    });
                    self.salPerUnitPriceCode(self.dataSource()[index].code);
                    self.salPerUnitPriceName(self.dataSource()[index].name);
                    self.A5_6(' ～ ');
                    self.periodEndYm(self.formatYM(self.historyList()[historyCode].periodEndYm));
                    self.periodStartYm(self.formatYM(self.historyList()[historyCode].periodStartYm));
                    self.currencyValue(self.historyList()[historyCode].amount);
                    self.currencyEnable(true);
                } else {
                    self.salPerUnitPriceCode(null);
                    self.salPerUnitPriceName(null);
                    self.A5_6(null);
                    self.periodEndYm(null);
                    self.periodStartYm(null);
                    self.currencyValue(null);
                    self.currencyEnable(false);
                }
                if(self.mode() === MODE.ADD_HISTORY) {
                    self.selectedCode.valueHasMutated();
                }
            });

            self.selectedCode.subscribe((code) => {
                let dto = {
                    perUnitPriceCode: code,
                    employeeId: self.selectedItem()
                };
                self.getIndividualEmpSalUnitPrice(dto);
            });

            self.selectedItem.subscribe((item) => {
                let dto = {
                    perUnitPriceCode: self.selectedCode(),
                    employeeId: item
                };
                self.getIndividualEmpSalUnitPrice(dto);
            });

            self.mode.subscribe((mode) => {
                if (mode === MODE.NORMAL) {
                    self.isRegistrable(true);
                    self.isEditableHis(true);
                    self.isAddableHis(true);
                    self.currencyEnable(true);
                }
                if (mode === MODE.HISTORY_UNREGISTERED) {
                    self.isRegistrable(false);
                    self.isEditableHis(false);
                    self.isAddableHis(true);
                    self.currencyEnable(false);
                    self.selectedHistoryCode(null);
                }
                if (mode === MODE.ADD_HISTORY) {
                    self.isRegistrable(true);
                    self.isEditableHis(false);
                    self.isAddableHis(false);
                    self.currencyEnable(true);
                }
            });

            //CCG001
            self.selectedEmployee = ko.observableArray([]);
            self.ccgComponent = {
                /** Common properties */
                systemType: 1,
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

            //KCP009
            self.employeeInputList = ko.observableArray([]);
            self.systemReference = ko.observable(SystemType.SALARY);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
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
        }

        formatYM(intYM) {
            return nts.uk.time.parseYearMonth(intYM).format();
        }

        formatYMToInt(stringYM: string) {
            let arr = stringYM.split('/');
            return parseInt(arr[0]) * 100 + parseInt(arr[1]);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getInfoEmpLogin().done((emp) => {
                service.getWpName().done((wp) => {
                    service.getBaseDate().done((baseDate) => {
                        service.getSalPerUnitPriceName().done((data) => {
                            if (data.length > 0) {
                                self.dataSource(data);
                                self.selectedCode(data[0].code);
                            } else {
                                self.isAddableHis(false);
                                nts.uk.ui.dialog.alertError({messageId: "MsgQ_170"});
                            }
                            self.employeeInputList.push(new EmployeeModel(emp.sid,
                                emp.employeeCode, emp.employeeName, wp.name, ""));
                            if (baseDate) {
                                self.ccgComponent.baseDate = baseDate;
                            }
                            $('#com-ccg001').ntsGroupComponent(self.ccgComponent);
                            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
                            let dto = {
                                perUnitPriceCode: self.selectedCode(),
                                employeeId: emp.sid
                            };
                            self.getIndividualEmpSalUnitPrice(dto);
                            dfd.resolve();
                        }).fail((res) => {
                            dfd.reject();
                        });
                    }).fail((res) => {
                        dfd.reject();
                    });
                }).fail((res) => {
                    dfd.reject();
                });
                $('#emp-component').focus();
            }).fail((res) => {
                dfd.reject();
            });
            return dfd.promise();
        }

        getIndividualEmpSalUnitPrice(dto) {
            let self = this;
            service.getIndividualEmpSalUnitPrices(dto).done((data) => {
                self.historyList([]);
                if (data.length > 0) {
                    for (let i = 0; i < data.length; i++) {
                        self.historyList.push(
                            new HistoryModel(
                                i,
                                data.perUnitPrice,
                                data.employeeId,
                                data.historyId,
                                data.periodStartYm,
                                data.periodEndYm,
                                format(getText("QMM039_18"), self.formatYM(data.periodStartYm), self.formatYM(data.periodEndYm)),
                                data.amountOfMoney));
                    }
                    self.selectedHistoryCode(self.historyList()[0].index);
                    self.mode(MODE.NORMAL);
                } else {
                    self.mode(MODE.HISTORY_UNREGISTERED);
                }
            }).fail((res) => {
                nts.uk.ui.dialog.bundledErrors(res);
            });
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
            })
        }

        public toScreenB(): void {
            let self = this;
            let params = {};
            if (self.mode() === MODE.NORMAL) {
                params = {
                    historyId: self.historyList()[0].historyId,
                    period: {
                        periodStartYm: self.historyList()[0].periodStartYm,
                        periodEndYm: 999912
                    }
                }
            } else {
                params = {
                    period: {
                        periodStartYm: null,
                        periodEndYm: 999912
                    }
                }
            }
            self.openModalB(params);
        }

        openModalB(params) {
            let self = this;
            setShared("QMM041_A_PARAMS", params);
            modal('/view/qmm/041/b/index.xhtml').onClosed(() => {
                let params = getShared("QMM041_B_RES_PARAMS");
                if (params) {
                    self.periodStartYm(nts.uk.time.parseYearMonth(params.periodStartYm).format());
                    self.periodEndYm(nts.uk.time.parseYearMonth(params.periodEndYm).format());

                    if (params.takeoverMethod === 0) {
                        self.currencyValue(null);
                    } else {
                        self.currencyValue(parseInt(self.historyList()[0].amount));
                    }
                    self.historyList.unshift(new HistoryModel(
                        0,
                        self.selectedCode(),
                        self.selectedItem(),
                        params.historyId,
                        params.periodStartYm,
                        params.periodEndYm,
                        format(getText("QMM039_18"), nts.uk.time.parseYearMonth(params.periodStartYm).format(), nts.uk.time.parseYearMonth(params.periodEndYm).format()),
                        self.currencyValue()
                    ));
                    if (self.historyList().length > 1) {
                        self.historyList()[1].periodEndYm = (params.periodStartYm - 1) % 100 == 0 ? params.periodStartYm - 101 + 12 : params.periodStartYm - 1;
                        self.historyList()[1].period = format(getText("QMM039_18"), nts.uk.time.parseYearMonth(self.historyList()[1].periodStartYm).format(), nts.uk.time.parseYearMonth(self.historyList()[1].periodEndYm).format());
                    }
                    for (let i = 0; i < self.historyList().length; i++) {
                        self.historyList()[i].index = i;
                    }
                    self.selectedHistoryCode(0);
                    self.mode(MODE.ADD_HISTORY);
                }
            });
        }

        public toScreenC(): void {
            let self = this;
            let self = this;
            let params = {
                employeeInfo: {
                    empId: self.selectedItem(),
                    personalValode: self.individualPriceCode(),
                    itemClass: self.itemClas()
                },
                period: {
                    historyId: self.selectedHis().historyID,
                    periodStartYm: self.selectedHis().periodStartYm,
                    periodEndYm: self.selectedHis().periodEndYm
                },

                lastHistoryId: self.historyList().length == (self.historyList()[self.selectedHistoryCode()].index + 1) ? null : self.historyList()[parseInt(self.selectedHistoryCode()) + 1].historyID,
                lastPeriodEndYm: self.historyList().length == (self.historyList()[self.selectedHistoryCode()].index + 1) ? null : self.historyList()[parseInt(self.selectedHistoryCode()) + 1].periodEndYm,
                lastPeriodStartYm: self.historyList().length == (self.historyList()[self.selectedHistoryCode()].index + 1) ? null : self.historyList()[parseInt(self.selectedHistoryCode()) + 1].periodStartYm

            }
            setShared("QMM041_C_PARAMS", params);
            modal('/view/qmm/041/c/index.xhtml', {title: '',}).onClosed(function (): any {
                let params = getShared('QMM041_C_RES_PARAMS');
                if (params) {
                    if (params.modifyMethod == MODIFY_METHOD.DELETE) {
                        self.historyProcess(self.individualPriceCode(), 0);
                    } else {
                        self.historyProcess(self.individualPriceCode(), self.selectedHis().index);
                    }
                }
                self.mode(MODE.NORMAL);
            });
        }

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
                personalValCode: self.individualPriceCode(),
                personalValName: self.individualPriceCode(),
                cateIndicator: self.classificationCategory(),
                salBonusCate: self.salaryBonusCategory()
            }
            setShared("QMM041_D_PARAMS", params);
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
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }

    class HistoryModel {
        index: number;
        perUnitPrice: string;
        employeeId: string;
        historyId: string;
        periodStartYm: number;
        periodEndYm: number;
        period: string;
        amount: number;

        constructor(index: number, perUnitPrice: string, employeeId: string, historyId: string, periodStartYm: number, periodEndYm: number, period: string, amount: number) {
            this.index = index;
            this.perUnitPrice = perUnitPrice;
            this.employeeId = employeeId;
            this.historyId = historyId;
            this.periodStartYm = periodStartYm;
            this.periodEndYm = periodEndYm;
            this.period = period;
            this.amount = amount;
        }
    }

}