module nts.uk.pr.view.qmm039.a.viewmodel {
    import SalIndAmountHis = nts.uk.pr.view.qmm039.share.model.SalIndAmountHis;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    import format = nts.uk.text.format;
    import GenericHistYMPeriod = nts.uk.pr.view.qmm039.share.model.GenericHistYMPeriod;
    import SalIndAmount = nts.uk.pr.view.qmm039.share.model.SalIndAmount;
    import ITEM_CLASS = nts.uk.pr.view.qmm039.share.model.ITEM_CLASS;
    import PERVALUECATECLS = nts.uk.pr.view.qmm039.share.model.PERVALUECATECLS;
    import SALBONUSCATE = nts.uk.pr.view.qmm039.share.model.SALBONUSCATE;
    import getShared = nts.uk.ui.windows.getShared;
    import MODE = nts.uk.pr.view.qmm039.share.model.MODE;
    export class ScreenModel {
        mode: KnockoutObservable<number> = ko.observable(0);
        classificationCategory: KnockoutObservable<number> = ko.observable(PERVALUECATECLS.SUPPLY);
        salaryBonusCategory: KnockoutObservable<number> = ko.observable(SALBONUSCATE.SALARY);
        itemList: KnockoutObservableArray<ItemModel>;
        individualPriceCode: KnockoutObservable<any> = ko.observable('');
        individualPriceName: KnockoutObservable<any> = ko.observable('');
        periodStartYM: KnockoutObservable<any> = ko.observable('');
        periodEndYM: KnockoutObservable<any> = ko.observable('');
        individualPriceName: KnockoutObservable<any> = ko.observable('');
        onTab: KnockoutObservable<number> = ko.observable(ITEM_CLASS.SALARY_SUPLY);
        itemClas: KnockoutObservable<number> = ko.observable(0);
        titleTab: KnockoutObservable<string> = ko.observable('');
        isEnableHis: KnockoutObservable<boolean> = ko.observable(true);
        itemClassLabel: KnockoutObservable<string> = ko.observable('');
        SalBonusCate: KnockoutObservable<boolean> = ko.observable(0);
        CategoryIndicator: KnockoutObservable<boolean> = ko.observable(0);
        selectedTab: KnockoutObservable<string>;
        salHis: any;
        dataSource: any = ko.observableArray([]);
        selectedHis: KnockoutObservable<ItemModel>;
        selectedHisCode: any;
        singleSelectedCode: any;
        listComponentOption: ComponentOption;
        tabindex: number;
        enable: KnockoutObservable<boolean>;
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

        constructor() {
            let self = this;
            self.selectedTab = ko.observable('tab-1');
            // initial ccg options
            self.itemList = ko.observableArray([]);
            self.itemList.subscribe(function (newValue) {
                if (newValue.length > 0) {
                    self.isEnableHis(true);
                } else {
                    self.isEnableHis(false);
                }
            });
            self.salHis = new SalIndAmountHis({historyID: 1, periodStartYm: 201801, periodEndYm: 201806});
            self.selectedHis = ko.observable(null);
            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedCode.subscribe(function (newValue) {
                if (newValue != null) {
                    let dto = {
                        perValCode: newValue,
                        empId: self.selectedItem(),
                        cateIndicator: self.classificationCategory(),
                        salBonusCate: self.salaryBonusCategory()
                    }
                    self.historyProcess(dto);
                }
            });
            self.selectedHisCode = ko.observable(1);
            self.selectedHisCode.subscribe(function (newValue) {
                self.changeHistory(self.itemList()[newValue - 1]);
            });
            self.currencyeditor = {
                value: ko.observable(0),
                constraint: '',
                option: new nts.uk.ui.option.CurrencyEditorOption({
                    grouplength: 3,
                    decimallength: 2,
                    currencyformat: "JPY"
                }),
                required: ko.observable(false),
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
                showWorkplace: false,
                showClassification: false,
                showJobTitle: false,
                showWorktype: false,
                isMutipleCheck: false,

                /**
                 * Self-defined function: Return data from CCG001
                 * @param: data: the data return from CCG001
                 */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    self.selectedEmployee(data.listEmployee);
                    self.convertEmployeeCcg01ToKcp009(data.listEmployee);
                }

            }
            $('#com-ccg0011').ntsGroupComponent(self.ccgcomponent);

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
                    self.itemClassLabel(format(getText('QMM039_21'), '給与支給'));
                    break;
                case ITEM_CLASS.SALARY_DEDUCTION:
                    self.classificationCategory(PERVALUECATECLS.DEDUCTION);
                    self.salaryBonusCategory(SALBONUSCATE.SALARY);
                    self.onTab(ITEM_CLASS.SALARY_DEDUCTION);
                    self.titleTab(getText('QMM039_7'));
                    $("#sidebar").ntsSideBar("active", param);
                    self.changeItemClass(PERVALUECATECLS.DEDUCTION);
                    self.itemClas(ITEM_CLASS.SALARY_DEDUCTION);
                    self.itemClassLabel(format(getText('QMM039_21'), '給与控除'));
                    break;
                case ITEM_CLASS.BONUS_SUPLY:
                    self.classificationCategory(PERVALUECATECLS.SUPPLY);
                    self.salaryBonusCategory(SALBONUSCATE.BONUSES);
                    self.onTab(ITEM_CLASS.BONUS_SUPLY);
                    self.titleTab(getText('QMM039_8'));
                    $("#sidebar").ntsSideBar("active", param);
                    self.changeItemClass(PERVALUECATECLS.SUPPLY);
                    self.itemClas(ITEM_CLASS.BONUS_SUPLY);
                    self.itemClassLabel(format(getText('QMM039_21'), '賞与支給'));
                    break;
                case ITEM_CLASS.BONUS_DEDUCTION:
                    self.classificationCategory(PERVALUECATECLS.DEDUCTION);
                    self.salaryBonusCategory(SALBONUSCATE.BONUSES);
                    self.onTab(ITEM_CLASS.BONUS_DEDUCTION);
                    self.titleTab(getText('QMM039_9'));
                    $("#sidebar").ntsSideBar("active", param);
                    self.changeItemClass(PERVALUECATECLS.DEDUCTION);
                    self.itemClas(ITEM_CLASS.BONUS_DEDUCTION);
                    self.itemClassLabel(format(getText('QMM039_21'), '賞与控除'));
                    break;
                default:
                    self.classificationCategory(PERVALUECATECLS.SUPPLY);
                    self.salaryBonusCategory(SALBONUSCATE.SALARY);
                    self.onTab(ITEM_CLASS.SALARY_SUPLY);
                    self.titleTab(getText('QMM039_9'));
                    $("#sidebar").ntsSideBar("active", 1);
                    self.changeItemClass(PERVALUECATECLS.SUPPLY);
                    self.itemClas(ITEM_CLASS.SALARY_SUPLY);
                    self.itemClassLabel(format(getText('QMM039_21'), '給与支給'));
                    break;
            }
        }
        //TODO SELECT  ITEM CLASSFICATION
        changeItemClass(item_class: number): void {
            let self = this;
            service.getPersonalMoneyName(item_class).done(function (data) {
                if (data.length > 0) {
                    let array = [];
                    _.forEach(data, function (salIndAmountName) {
                        array.push(new Node(salIndAmountName.individualPriceCode, salIndAmountName.individualPriceName));
                    });
                    self.dataSource(array);

                    let dto = {
                        perValCode: data[0].individualPriceCode,
                        empId: self.selectedItem(),
                        cateIndicator: PERVALUECATECLS.SUPPLY,
                        salBonusCate: SALBONUSCATE.SALARY
                    }
                    self.individualPriceName(data[0].individualPriceName);
                    self.individualPriceCode(data[0].individualPriceCode);
                    self.historyProcess(dto);
                } else {
                    nts.uk.ui.dialog.alertError({messageId: "MsgQ_169"});
                    self.itemList([]);
                    self.dataSource([]);
                    self.singleSelectedCode(null);
                }
            });
        }

        //TODO CHANGE ITEM
        historyProcess(dto): void {
            let self = this;
            service.getSalIndAmountHis(dto).done(function (data) {
                if (data != null) {
                    self.mode(MODE.NORMAL);
                    let array = [];
                    for (let i = 0; i < data.period.length; i++) {
                        array.push(
                            new ItemModel(
                                data.period[i].historyID,
                                data.period[i].periodStartYm,
                                data.period[i].periodEndYm,
                                format(getText("QMM039_18"), self.formatYM(data.period[i].periodStartYm), self.formatYM(data.period[i].periodEndYm)), data.salIndAmountList[i].amountOfMoney))
                    }
                    self.itemList(array);
                    self.changeHistory(array[0]);
                } else {
                    self.itemList([]);
                    self.periodStartYM(null);
                    self.periodEndYM(null);
                    self.currencyeditor.value(null);
                    self.mode(MODE.HISTORY_UNREGISTERED);
                }
            });
        }

        //TODO CHANGE HISTORY
        changeHistory(selectedHis) {
            let self = this;
            self.selectedHis(selectedHis);
            self.periodStartYM(self.formatYM(self.selectedHis().periodStartYm));
            self.periodEndYM(self.formatYM(self.selectedHis().periodEndYm));
            self.currencyeditor.value(self.selectedHis().amount);
        }

        formatYM(intYM) {
            return intYM.toString().substr(0, 4) + '/' + intYM.toString().substr(4, intYM.length);
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getInfoEmLogin().done(function (emp) {
                service.getWpName().done(function (wp) {
                    if (wp == null || wp.workplaceId == null || wp.workplaceId == "") {
                    } else {
                        self.employeeInputList.push(new EmployeeKcp009(emp.sid,
                            emp.employeeCode, emp.employeeName, wp.name, wp.name));
                        self.initKCP009();
                        dfd.resolve();
                    }
                    self.onSelectTab(ITEM_CLASS.SALARY_SUPLY);
                }).fail(function (result) {
                    dfd.reject();
                });
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
                tabIndex: self.tabindex
            };
            $('#emp-component1').ntsLoadListComponent(self.listComponentOption);
        }

        convertEmployeeCcg01ToKcp009(dataList: EmployeeSearchDto[]): void {
            let self = this;
            self.employeeInputList([]);
            _.each(dataList, function (item) {
                self.employeeInputList.push(new EmployeeKcp009(item.employeeId, item.employeeCode, item.employeeName, item.workplaceName, ""));
            });
            $('#emp-component1').ntsLoadListComponent(self.listComponentOption);
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
                let employee: UnitModel = {
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

        public toScreenB(): void {
            let self = this;
            let params = {};
            if (self.mode() == MODE.NORMAL) {
                params = {
                    historyID: self.itemList()[0].historyID,
                    period: {
                        periodStartYm: self.itemList()[0].periodStartYm,
                        periodEndYm: self.itemList()[0].periodEndYm
                    }
                }
            } else {
                service.processYearFromEmp(self.individualPriceCode()).done(function (data) {
                    params = {
                        period: {
                            periodStartYm: data,
                            periodEndYm: 999912
                        }
                    }
                });
            }
            setShared("QMM039_A_PARAMS", params);
            modal('/view/qmm/039/b/index.xhtml', {title: '',}).onClosed(function (): any {
                let params = getShared("QMM039_B_RES_PARAMS");
                if (params) {
                    self.periodStartYM(params.periodStartYm);
                    self.periodEndYM(params.periodEndYm);
                    if (params.takeoverMethod == 1) {
                        self.currencyeditor.value(0);
                    } else {
                        self.currencyeditor.value(self.itemList()[0].amount);
                    }
                }
            });
        }

        public toScreenC(): void {
            let self = this;
            let params = {
                employeeInfo: {
                    empId: '000001',
                    personalValcode: '000001',
                    itemClass: self.itemClas()
                },
                period: {
                    historyId: '111',
                    periodStartYm: self.salHis.periodStartYm(),
                    periodEndYm: self.salHis.periodEndYm()
                },
            }
            setShared("QMM039_C_PARAMS", params);
            modal('/view/qmm/039/c/index.xhtml', {title: '',}).onClosed(function (): any {
                self.mode(MODE.NORMAL);
            });
        }

        public toScreenD(): void {
            let self = this;
            let params = {
                empId: '000001',
                personalValcode: '000001',
                period: {
                    periodStartYm: self.salHis.periodStartYm(),
                    periodEndYm: self.salHis.periodEndYm()
                },
                itemClass: 2,
            }
            setShared("QMM039_D_PARAMS", params);
            modal('/view/qmm/039/d/index.xhtml', {title: '',}).onClosed(function (): any {

            });
        }

        registration(): void {
            //TODO REGISTRATION
            let self = this;
            if (self.mode() == MODE.NORMAL) {
                let command = {
                    historyId: self.selectedHis().historyID,
                    amountOfMoney: self.currencyeditor.value()
                }
                service.updateHistory(command).done(function (data) {
                    nts.uk.ui.dialog.info({messageID: "Msg_15"});
                });
            } else {
                let command = {
                    salIndAmountHisCommand: {
                        cateIndicator: self.classificationCategory(),
                        salBonusCate: self.salaryBonusCategory(),
                        perValCode: self.individualPriceCode(),
                        empId: self.selectedItem()
                    },
                    salIndAmountCommand: {
                        amountOfMoney: self.currencyeditor.value()
                    }
                }
                service.addHistory(command).done(function (data) {
                    nts.uk.ui.dialog.info({messageID: "Msg_15"});
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
        code: string;
        name: string;

        constructor(code: string, name: string) {
            var self = this;
            self.code = code;
            self.name = name;
        }
    }
    class ItemModel {
        historyID: string;
        periodStartYm: number;
        periodEndYm: number;
        period: string;
        amount: number;

        constructor(historyID: string, periodStartYm: number, periodEndYm: number, period: string, amount: number) {
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