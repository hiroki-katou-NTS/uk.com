module nts.uk.pr.view.qmm036.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import format = nts.uk.text.format;
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {

        //ccg001
        employeeList: any;
        employeeInfoImports: any;
        referenceDate: KnockoutObservable<string> = ko.observable('');
        ccgcomponent: GroupOption;
        tilteTable: KnockoutObservableArray<any>;
        selectedEmployeeCode: KnockoutObservable<string>;
        yearMonthFilter: KnockoutObservable<number>;
        onTab: KnockoutObservable<number> = ko.observable(0);
        titleTab: KnockoutObservable<string> = ko.observable('');
        itemClassification: KnockoutObservable<string> = ko.observable('');
        individualPriceCode: KnockoutObservable<string>;
        individualPriceName: KnockoutObservable<string>;
        isRegistrationable: KnockoutObservable<boolean> = ko.observable(false);
        listComponentOption: ComponentOption;

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
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto> = ko.observableArray([]);
        targetBtnText: string = getText("KCP009_3");

        lstStatementItem: KnockoutObservableArray<IStatementItem> = ko.observableArray([]);
        selectStatementItem: KnockoutObservable<IStatementItem> = null;
        currentCodeStatement: KnockoutObservable<string> = ko.observable('');

        categoryAtr: KnockoutObservable<number> = ko.observable(null);
        itemNameCd: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        columns: KnockoutObservableArray<any> = ko.observableArray([]);

        isScreenB: KnockoutObservable<boolean> = ko.observable(true);
        isScreenC: KnockoutObservable<boolean> = ko.observable(false);

        lstHistory: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        selectedHisCode: any;
        columnsHis: Array<any>;
        period: KnockoutObservable<string> = ko.observable('');
        index: KnockoutObservable<number> = ko.observable(0);
        periodStartYM: KnockoutObservable<string> = ko.observable('');
        periodEndYM: KnockoutObservable<string> = ko.observable('');
        totalAmount: KnockoutObservable<string> = ko.observable('');

        lstBreakdownItem: Array<IBreakdownItem> = [];
        breakdownItemCode: KnockoutObservable<string> = ko.observable('');
        breakdownItemName: KnockoutObservable<string> = ko.observable('');
        amount: KnockoutObservable<number> = ko.observable('');
        bonusAtr: KnockoutObservable<number> = ko.observable(null);

        modeScreen: KnockoutObservable<number> = ko.observable(0);

        constructor() {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            self.selectedHisCode = ko.observable('');

            self.columnsHis = [
                {key: 'period', length: 17, template: "<div style='text-align: center'>${period}</div>"}
            ];

            self.columns = ko.observableArray([
                {headerText: getText('QMM036_7'), key: 'categoryAtr', width: 60, formatter: getCategoryAtrText},
                {headerText: getText('QMM036_8'), key: 'itemNameCd', width: 60},
                {headerText: getText('QMM036_9'), key: 'name', width: 100}
            ]);

            self.selectedHisCode.subscribe(item => {
                self.totalAmount('');
                if (item == null) {
                    return;
                }
                if (self.isScreenB() == false && self.isScreenC() == false) {
                    let a = _.find(self.lstHistory(), function (x) {
                        return x.index == 0
                    });
                    self.lstHistory.remove(a);
                    for (let i = 0; i < self.lstHistory().length; i++) {
                        self.lstHistory()[i].index = i;
                    }
                    self.isScreenB(true);
                    self.isScreenC(true);
                    self.selectedHisCode(0);
                    return;
                }
                let itemModel = _.find(self.lstHistory(), function (x) {
                    return x.index == item
                });
                if(self.lstHistory().length > 0){
                    self.period(itemModel.period);
                    self.getBreakdown(itemModel.historyID);
                }
            });

            self.currentCodeStatement.subscribe(item => {
                self.period('');
                self.totalAmount('');
                if (item != '') {
                    let itemModel = _.find(self.lstStatementItem(), function (x) {
                        return x.itemNameCd == item
                    });
                    self.categoryAtr(itemModel.categoryAtr);
                    self.itemNameCd(itemModel.itemNameCd);
                    self.name(itemModel.name);
                    self.isScreenB(true);
                    self.getHist();
                    if(self.lstStatementItem().length == 0){
                        self.isRegistrationable(false);
                        self.isScreenB(false);
                    }
                    else{
                        self.isRegistrationable(true);
                        self.isScreenB(true);
                    }
                    self.selectedHisCode(null);
                    self.lstBreakdownItem = [];
                    $("#gridBreakdownItem").ntsGrid("destroy");
                    self.loadGrid();
                }
            });

            self.selectedItem.subscribe(item => {
                self.getStatementData();
            });
        }

        getBreakdown(historyID) {
            let self = this,
                dfd = $.Deferred();
            service.getBreakDownAmoun(historyID, self.categoryAtr(), self.itemNameCd()).done(function (data) {
                if (data && data.length > 0) {
                    let dataSort1 = _.sortBy(data, ["breakdownItemCode"]);
                    let totolAmount = null;
                    for (let i = 0; i < dataSort1.length; i++) {
                        totolAmount = totolAmount + dataSort1[i].amount;
                    }
                    if (totolAmount > 0) {
                        totolAmount = totolAmount + "  ¥";
                        self.totalAmount(totolAmount);
                    }
                    self.lstBreakdownItem = dataSort1;
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstBreakdownItem = [];
                }
                $("#gridBreakdownItem").ntsGrid("destroy");
                self.loadGrid();
                block.clear();
                dfd.resolve(self);
            }).fail(function (res) {
                alertError({messageId: res.messageId});
                block.clear();
                dfd.reject();
            });
        }

        getHist() {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            service.getBreakdownHis(self.categoryAtr(), self.itemNameCd(), self.bonusAtr(), self.selectedItem()).done(function (data) {
                if (data) {
                    let array = [];
                    for (let i = 0; i < data.yearMonthHistory.length; i++) {
                        array.push(
                            new ItemModel(
                                i,
                                data.yearMonthHistory[i].historyID,
                                data.yearMonthHistory[i].periodStartYm,
                                data.yearMonthHistory[i].periodEndYm,
                                format(getText("QMM039_18"), self.formatYM(data.yearMonthHistory[i].periodStartYm), self.formatYM(data.yearMonthHistory[i].periodEndYm))))
                    }
                    let dataSort = _.orderBy(array, ["periodStartYm"], 'desc');
                    for (let i = 0; i < dataSort.length; i++) {
                        dataSort[i].index = i;
                    }
                    self.lstHistory(dataSort);
                    self.isScreenC(true);
                    self.isScreenB(true);
                    self.selectedHisCode(0);
                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstHistory([]);
                    self.isScreenC(false);

                }
                block.clear();
                dfd.resolve();
            }).fail(function (res) {
                alertError({messageId: res.messageId});
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        loadGrid() {
            let self = this;
            $("#gridBreakdownItem").ntsGrid({
                width: '480px',
                height: '300px',
                dataSource: self.lstBreakdownItem,
                primaryKey: 'breakdownItemCode',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                preventEditInError: false,
                hidePrimaryKey: false,
                showErrorsOnPage: false,
                columns: [
                    {
                        headerText: getText("QMM036_27"),
                        key: 'breakdownItemCode',
                        dataType: 'string',
                        width: '140px',
                        ntsControl: 'Label'
                    },
                    {
                        headerText: getText("QMM036_28"),
                        key: 'breakdownItemName',
                        dataType: 'string',
                        width: '140px',
                        ntsControl: 'Label'
                    },
                    {
                        headerText: getText("QMM036_29"), key: 'amount', dataType: 'string', width: '100px',
                        //ntsControl: 'TextEditor'
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            // primitiveValue: 'ItemPriceType',
                            cDisplayType: "Currency",
                            min: 0, max: 10,
                            required: false
                        }
                    }
                ],
                features: [
                    /*{
                        name: 'Resizing',
                        columnSettings: [{
                            columnKey: 'breakdownItemCode', allowResizing: false, minimumWidth: 0
                        }]
                    },*/
                    {
                        name: "Selection",
                        mode: "cell",
                        multipleSelection: true,
                        activation: true
                    },
                ],
                ntsControls: [
                    {name: 'TextEditor', controlType: 'TextEditor', constraint: {valueType: 'String', required: false}}
                ],
                ntsFeatures: [
                    {name: 'CellEdit'},
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        //states: cellStates
                    },
                ]
            });
        }

        formatYM(intYM) {
            return intYM.toString().substr(0, 4) + '/' + intYM.toString().substr(4, intYM.length);
        }

        formatYMToInt(stringYM: string) {
            let arr = stringYM.split('/');
            return parseInt(arr[0]) * 100 + parseInt(arr[1]);
        }

        getStatementData() {
            let self = this,
                dfd = $.Deferred();
            block.invisible();
            self.lstHistory([]);
            service.getStatemetItem().done(function (data: Array<IStatementItem>) {
                if (data && data.length > 0) {
                    let dataSort1 = _.sortBy(data, ["categoryAtr"]);
                    let dataSort2 = _.sortBy(dataSort1, ["itemNameCd"]);

                    self.lstStatementItem(dataSort2);
                    self.currentCodeStatement('');
                    self.currentCodeStatement(self.lstStatementItem()[0].itemNameCd);

                }
                else {
                    nts.uk.ui.errors.clearAll();
                    self.lstStatementItem([]);
                    self.isScreenB(false);
                }
                block.clear();
                dfd.resolve(self);
            }).fail(function (res) {
                alertError({messageId: res.messageId});
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            self.loadGrid();
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
                $('#emp-component').focus();
            }).fail(function (result) {
                dfd.reject();
            });


            dfd.resolve(self);
            return dfd.promise();
        }

        onSelectTab(param) {
            let self = this;
            switch (param) {
                case 0:
                    self.bonusAtr(param);
                    self.titleTab(getText('QMM036_3'));
                    self.reloadCcg001();
                    self.getStatementData();

                    break;
                case 1:
                    self.isScreenB(true);
                    self.bonusAtr(param);
                    self.titleTab(getText('QMM036_4'));
                    self.reloadCcg001();
                    self.getStatementData();
                    break;
                default:
                    break;
            }
        }

        public reloadCcg001(): void {
            let self = this;

            self.ccgcomponent = {
                /** Common properties */
                systemType: 1, // システム区分
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment.utc().toISOString(),
                periodStartDate: null,
                periodEndDate: null,
                inService: true,
                leaveOfAbsence: true,
                closed: true,
                retirement: true,

                /** Quick search tab options */
                showAllReferableEmployee: true,
                showOnlyMe: false,
                showSameWorkplace: false,
                showSameWorkplaceAndChild: false,

                /** Advanced search properties */
                showEmployment: false,
                showWorkplace: false,
                showClassification: false,
                showJobTitle: false,
                showWorktype: false,
                isMutipleCheck: true,
                tabindex: 2,
                /** Return data */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    nts.uk.ui.errors.clearAll();
                    self.selectedEmployee(data.listEmployee);
                    self.convertEmployeeCcg01ToKcp009(data.listEmployee);
                }
            }
            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
        }

        registration() {
            let self = this;
            let lstPeriod: Array = [];
            for (let i = 0; i < self.lstHistory().length; i++) {
                let period = {
                    historyId: self.lstHistory()[i].historyID,
                    startMonth: self.lstHistory()[i].periodStartYm,
                    endMonth: self.lstHistory()[i].periodEndYm
                }
                lstPeriod.push(period);
            }
            let command = {
                categoryAtr: self.categoryAtr(),
                itemNameCd: self.itemNameCd(),
                employeeId: self.selectedItem(),
                salaryBonusAtr: self.bonusAtr(),
                period: lstPeriod,
                breakdownAmountList: self.lstBreakdownItem,
                lastHistoryId: null
            };
            service.addBreakdownAmountHis(ko.toJS(command)).done(() => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    self.getHist();
                    self.isScreenB(true);
                    self.isScreenC(true);
                    /*if (self.selectedHisCode() == 0) {
                        self.selectedHisCode.valueHasMutated();
                    } else {
                        self.selectedHisCode(0);
                    }*/
                });
            }).fail(function (error) {
                alertError(error);
            }).always(function () {
                nts.uk.ui.windows.close();
            });


        }

        toScreenB() {
            let self = this;
            let params = {};
            if (self.lstHistory().length > 0) {
                params = {
                    historyID: self.lstHistory()[0].historyID,
                    period: {
                        periodStartYm: self.lstHistory()[0].periodStartYm,
                        periodEndYm: self.lstHistory()[0].periodEndYm
                    }
                }
            }
            else {
                params = {
                    period: {
                        periodStartYm: null,
                        periodEndYm: 999912
                    }
                }
            }
            setShared("QMM036_A_PARAMS", params);
            modal('/view/qmm/036/b/index.xhtml').onClosed(function (): any {
                let params = getShared("QMM036_B_RES_PARAMS");
                if (params) {
                    self.periodStartYM(nts.uk.time.parseYearMonth(params.periodStartYm).format());
                    self.periodEndYM(nts.uk.time.parseYearMonth(params.periodEndYm).format());
                    self.modeScreen(1);

                    let array = self.lstHistory();
                    self.lstHistory([]);
                    array.unshift(new ItemModel(
                        0,
                        null,
                        params.periodStartYm,
                        params.periodEndYm,
                        format(getText("QMM039_18"), nts.uk.time.parseYearMonth(params.periodStartYm).format(), nts.uk.time.parseYearMonth(params.periodEndYm).format())
                    ));
                    if (array.length > 1) {
                        array[1].periodEndYm = (params.periodStartYm - 1) % 100 == 0 ? params.periodStartYm - 101 + 12 : params.periodStartYm - 1;
                        array[1].period = format(getText("QMM039_18"), nts.uk.time.parseYearMonth(array[1].periodStartYm).format(), nts.uk.time.parseYearMonth(array[1].periodEndYm).format());
                    }
                    for (let i = 0; i < array.length; i++) {
                        array[i].index = i;
                    }
                    self.lstHistory(array);
                    let historyID = null;
                    self.getBreakdown(historyID);
                    if (self.selectedHisCode() == 0) {
                        self.selectedHisCode.valueHasMutated();
                    } else {
                        self.selectedHisCode(0);
                    }
                    self.isScreenB(false);
                    self.isScreenC(false);
                }
            });
        }

        toScreenC() {
            let self = this;
            let history = _.find(self.lstHistory(), function (x) {
                return x.index == self.selectedHisCode()
            });
            let lastHistoryId = null;
            if (self.lstHistory().length > 1) {
                lastHistoryId = self.lstHistory()[1].historyID
            }
            let params = {
                period: {
                    historyId: history.historyID,
                    lastHistoryId: lastHistoryId,
                    periodStartYm: history.periodStartYm,
                    periodEndYm: history.periodEndYm,
                    categoryAtr: self.categoryAtr(),
                    itemNameCd: self.itemNameCd(),
                    employeeID: self.selectedItem(),
                    salaryBonusAtr: self.bonusAtr()
                }
            }

            setShared("QMM036_C_PARAMS", params);
            modal('/view/qmm/036/c/index.xhtml', {title: '',}).onClosed(function (): any {
                self.getHist().done(() => {
                        self.initSelectedHisCode();
                        self.lstBreakdownItem = [];
                        $("#gridBreakdownItem").ntsGrid("destroy");
                        self.loadGrid();

                });

            });
        }

        initSelectedHisCode(){
            let self = this;
            if (self.selectedHisCode() == 0) {
                self.selectedHisCode.valueHasMutated();
            } else {
                self.selectedHisCode(0);
            }
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

    export enum ITEM_CLASS {
        SALARY_SUPLY = 0,
        SALARY_DEDUCTION = 1,
        BONUS_SUPLY = 2,
        BONUS_DEDUCTION = 3,
    }

    export class SystemType {
        static PERSONAL_INFORMATION = 1;
        static EMPLOYMENT = 2;
        static SALARY = 3;
        static HUMAN_RESOURCES = 4;
        static ADMINISTRATOR = 5;
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

    class ItemModel {
        index: number;
        historyID: string;
        periodStartYm: number;
        periodEndYm: number;
        period: string;

        constructor(index: number, historyID: string, periodStartYm: number, periodEndYm: number, period: string) {
            this.index = index;
            this.historyID = historyID;
            this.periodStartYm = periodStartYm;
            this.periodEndYm = periodEndYm;
            this.period = period;
        }
    }

    class IBreakdownItem {
        breakdownItemCode: string;
        breakdownItemName: string;
        amount: string;
    }

    export interface IStatementItem {
        categoryAtr: number;
        itemNameCd: string;
        name: string;
    }

    export interface IBreakdownAmountHis {
        categoryAtr: number,
        itemNameCd: string,
        employeeId: string,
        yearMonthHistory: Array<YearMonthHistoryItemDto>
        salaryBonusAtr: number
    }

    export interface YearMonthHistoryItemDto {
        historyId: string,
        startDate: number,
        endDate: number
    }

    function getCategoryAtrText(itemAtr, row) {
        switch (itemAtr) {
            case "0":
                return getText('Enum_CategoryAtr_PAYMENT_ITEM');
            case "1":
                return getText('Enum_CategoryAtr_DEDUCTION_ITEM');
            case "2":
                return getText('Enum_CategoryAtr_ATTEND_ITEM');
            case "3":
                return getText('Enum_CategoryAtr_REPORT_ITEM');
            case "4":
                return getText('Enum_CategoryAtr_OTHER_ITEM');
            default:
                return "";
        }
    }
}