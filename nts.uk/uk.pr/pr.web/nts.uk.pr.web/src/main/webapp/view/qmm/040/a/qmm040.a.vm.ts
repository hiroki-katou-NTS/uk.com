module nts.uk.pr.view.qmm040.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import block  = nts.uk.ui.block;
    import validation = nts.uk.ui.validation;
    import errors = nts.uk.ui.errors;

    export class ScreenModel {

        amountValidator = new validation.NumberValidator(getText("QMM040_20"), "AmountOfMoney", {required: true});
        isRegistrable: KnockoutObservable<boolean> = ko.observable(false);

        //SalIndAmountName
        // change logic, get data after click button
        ccg001ReturnData: any;


        salIndAmountNames: KnockoutObservableArray<SalIndAmountName>;
        salIndAmountNamesSelectedCode: KnockoutObservable<string>;

        personalDisplay: Array<PersonalAmount> = [];

        //onSelected
        cateIndicator: KnockoutObservable<number>;
        salBonusCate: KnockoutObservable<number>;
        //ccg001
        employeeList: any;
        employeeInfoImports: any;
        ccgcomponent: GroupOption;
        tilteTable: KnockoutObservableArray<any>;
        selectedEmployeeCode: KnockoutObservable<string>;


        yearMonthFilter: KnockoutObservable<number>;
        onTab: KnockoutObservable<number> = ko.observable(0);
        titleTab: KnockoutObservable<string> = ko.observable('');
        itemClassification: KnockoutObservable<string> = ko.observable('');
        individualPriceCode: KnockoutObservable<string>;
        individualPriceName: KnockoutObservable<string>;

        constructor() {
            let self = this;
            self.tilteTable = ko.observableArray([
                {headerText: getText('QMM040_8'), key: 'individualPriceCode', width: 100},
                {headerText: getText('QMM040_9'), key: 'individualPriceName', width: 200}
            ]);
            self.yearMonthFilter = ko.observable(parseInt(moment(Date.now()).format("YYYYMM")));
            self.cateIndicator = ko.observable(0);
            self.salBonusCate = ko.observable(0);
            self.salIndAmountNames = ko.observableArray([]);
            self.salIndAmountNamesSelectedCode = ko.observable('');
            self.selectedEmployeeCode = ko.observable('1');
            self.individualPriceCode = ko.observable('');
            self.individualPriceName = ko.observable('');

            self.salIndAmountNamesSelectedCode.subscribe(function (data) {
                self.personalDisplay = [];
                errors.clearAll();
                if (!data)
                    return;
                let temp = _.find(self.salIndAmountNames(), function (o) {
                    return o.individualPriceCode == data;
                });
                if (temp) {
                    self.individualPriceCode(temp.individualPriceCode);
                    self.individualPriceName(temp.individualPriceName);
                }
                self.filterData();
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.invisible();
            service.employeeReferenceDate().done(function (data) {
                if (data) {
                    self.reloadCcg001(data.empExtraRefeDate);
                } else {
                    self.reloadCcg001(moment(Date.now()).format("YYYY/MM/DD"));
                }
                self.loadMGrid();
                $('#A5_7').focus();
                block.clear();
                dfd.resolve(self);
                self.onSelectTab(self.onTab);
            }).fail((err) => {
                dialog.alertError(err.message);
                block.clear();
                dfd.reject();
            });
            return dfd.promise();
        }

        loadMGrid() {
            let self = this;
            let height = $(window).height() - 90 - 285;
            let width = $(window).width() + 20 - 1170;
            new nts.uk.ui.mgrid.MGrid($("#grid")[0], {
                width: "560px",
                height: "300px",
                subWidth: width + 'px',
                subHeight: height + 'px',
                headerHeight: '23px',
                dataSource: self.personalDisplay,
                primaryKey: 'historyId',
                primaryKeyDataType: 'string',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                enter: 'right',
                autoFitWindow: false,
                hidePrimaryKey: true,
                errorsOnPage: true,
                columns: [
                    {headerText: "id", key: 'historyId', dataType: 'string', hidden: true},
                    // A5_10
                    {
                        headerText: getText("QMM040_16"), key: 'employeeCode', dataType: 'string', width: '90px',
                        ntsControl: "Label"
                    },
                    // A5_11
                    {
                        headerText: getText("QMM040_17"), key: 'businessName', dataType: 'string', width: "145px",
                        ntsControl: "Label"
                    },
                    // A5_12
                    {
                        headerText: getText("QMM040_18"), key: 'period', dataType: 'string', width: "175px",
                        ntsControl: "Label"
                    },
                    // A5_13
                    {
                        headerText: getText("QMM040_19"), key: 'amount', dataType: 'string', width: "150px",
                        columnCssClass: 'currency-symbol',
                        constraint: {
                            cDisplayType: "Currency",
                            min: self.amountValidator.constraint.min,
                            max: self.amountValidator.constraint.max,
                            required: true
                        }
                    }
                ],
                features: [
                    {
                        name: "Sorting",
                        columnSettings: [
                            {columnKey: "employeeCode", allowSorting: true, type: "String"},
                            {columnKey: "businessName", allowSorting: true, type: "String"},
                            {columnKey: "period", allowSorting: true, type: "String"},
                            {columnKey: "amount", allowSorting: true, type: "Number"}
                        ]
                    },
                    {
                        name: 'HeaderStyles',
                        columns: [
                            {key: 'employeeCode', colors: ['left-align']},
                            {key: 'businessName', colors: ['left-align']},
                            {key: 'period', colors: ['left-align']},
                            {key: 'amount', colors: ['left-align']}
                        ]
                    },
                    {
                        name: 'Paging',
                        pageSize: 20,
                        currentPageIndex: 0
                    },
                ]
            }).create();
        }

        onSelectTab(param) {
            let self = this;
            switch (param) {
                case 0:
                    //TODO
                    self.onTab(0);
                    self.titleTab(getText('QMM040_3'));
                    self.itemClassification(getText('QMM040_3'));
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    self.cateIndicator(CategoryIndicator.PAYMENT);
                    self.salBonusCate(SalBonusCate.SALARY);
                    $("#sidebar").ntsSideBar("active", param);
                    self.yearMonthFilter(parseInt(moment(Date.now()).format("YYYYMM")));
                    errors.clearAll();
                    $('#A5_7').focus();
                    break;
                case 1:
                    //TODO
                    self.onTab(1);
                    self.titleTab(getText('QMM040_4'));
                    self.itemClassification(getText('QMM040_4'));
                    self.loadSalIndAmountName(PerValueCateCls.DEDUCTION);
                    self.cateIndicator(CategoryIndicator.DEDUCTION);
                    self.salBonusCate(SalBonusCate.SALARY);
                    $("#sidebar").ntsSideBar("active", param);
                    self.yearMonthFilter(parseInt(moment(Date.now()).format("YYYYMM")));
                    errors.clearAll();
                    $('#A5_7').focus();
                    break;
                case 2:
                    //TODO
                    self.onTab(2);
                    self.titleTab(getText('QMM040_5'));
                    self.itemClassification(getText('QMM040_5'));
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    self.cateIndicator(CategoryIndicator.PAYMENT);
                    self.salBonusCate(SalBonusCate.BONUSES);
                    $("#sidebar").ntsSideBar("active", param);
                    self.yearMonthFilter(parseInt(moment(Date.now()).format("YYYYMM")));
                    errors.clearAll();
                    $('#A5_7').focus();
                    break;
                case 3:
                    //TODO
                    self.onTab(3);
                    self.titleTab(getText('QMM040_6'));
                    self.itemClassification(getText('QMM040_6'));
                    self.loadSalIndAmountName(PerValueCateCls.DEDUCTION);
                    self.cateIndicator(CategoryIndicator.DEDUCTION);
                    self.salBonusCate(SalBonusCate.BONUSES);
                    $("#sidebar").ntsSideBar("active", param);
                    self.yearMonthFilter(parseInt(moment(Date.now()).format("YYYYMM")));
                    errors.clearAll();
                    $('#A5_7').focus();
                    break;
                default:
                    //TODO
                    self.onTab(0);
                    self.titleTab(getText('QMM040_3'));
                    self.itemClassification(getText('QMM040_3'));
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    $("#sidebar").ntsSideBar("active", 0);
                    break;
            }
        }

        public loadSalIndAmountName(cateIndicator: number): void {
            let self = this;
            block.invisible();
            service.salIndAmountNameByCateIndicator(cateIndicator).done((data) => {
                if (data && data.length > 0) {
                    self.salIndAmountNames(data);
                    self.isRegistrable(true);
                    self.salIndAmountNamesSelectedCode(self.salIndAmountNames()[0].individualPriceCode);
                    self.salIndAmountNamesSelectedCode.valueHasMutated();
                } else {
                    self.isRegistrable(false);
                    dialog.alertError({messageId: "MsgQ_169"});
                    errors.clearAll();
                    self.individualPriceCode('');
                    self.individualPriceName('');
                }
            }).fail((err) => {
                dialog.alertError({message: err.messageId});
            }).always(() => {
                block.clear();
            });
        }

        filterData() {
            let self = this;
            self.yearMonthFilter();
            $('#A5_7').ntsError('check');
            if (!self.employeeList) return;
            block.invisible();
            service.salIndAmountHisByPeValCode({
                perValCode: self.individualPriceCode(),
                cateIndicator: self.cateIndicator(),
                salBonusCate: self.salBonusCate(),
                standardYearMonth: self.yearMonthFilter(),
                employeeIds: self.employeeList.map(x => x.employeeId)
            }).done(function (dataNameAndAmount) {
                self.employeeInfoImports = dataNameAndAmount.employeeInfoImports;
                let personalAmountData = dataNameAndAmount.personalAmount.map(x => new PersonalAmount(x));
                for (let personalAmount of personalAmountData) {
                    let employeeInfo = _.find(self.employeeInfoImports, x => x.sid === personalAmount.empId);
                    personalAmount.employeeCode = employeeInfo.scd;
                    personalAmount.businessName = employeeInfo.businessName;
                }
                personalAmountData = _.sortBy(personalAmountData, ['employeeCode']);
                self.personalDisplay = personalAmountData;
                $("#grid").mGrid("destroy");
                self.loadMGrid();
            }).always(() => {
                block.clear();
            }).fail((err) => {
                dialog.alertError({message: err.messageId});
            });
        }

        registerAmount(): void {
            block.invisible();
            if (errors.hasError() || !this.isValidForm()) {
                block.clear();
                return;
            }
            service.salIndAmountUpdateAll({
                salIndAmountUpdateCommandList: $("#grid").mGrid("dataSource", true)
            }).done(function () {
                dialog.info({messageId: "Msg_15"});
            }).always(() => {
                block.clear();
            });
        }

        isValidForm(): boolean {
            return _.isEmpty($("#grid").mGrid("errors", true));
        }

        public reloadCcg001(empExtraRefeDate: string): void {
            let self = this;
            self.ccgcomponent = {
                /** Common properties */
                systemType: 3, // システム区分
                showEmployeeSelection: true,
                showQuickSearchTab: true,
                showAdvancedSearchTab: true,
                showBaseDate: true,
                showClosure: false,
                showAllClosure: false,
                showPeriod: false,
                periodFormatYM: false,

                /** Required parameter */
                baseDate: moment(new Date(empExtraRefeDate)).format("YYYY-MM-DD"), // 基準日
                periodStartDate: moment(new Date('06/05/1990')).format("YYYY-MM-DD"), // 対象期間開始日
                periodEndDate: moment(new Date('06/05/2018')).format("YYYY-MM-DD"), // 対象期間終了日
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
                showEmployment: true,
                showDepartment: true,
                showWorkplace: false,
                showClassification: false,
                showJobTitle: false,
                showWorktype: false,
                isMutipleCheck: true,
                showOnStart: true,
                tabindex: 2,
                /** Return data */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    errors.clearAll();
                    if (data && data.listEmployee.length > 0) {
                        self.employeeList = data.listEmployee;
                        self.filterData();
                    }
                }
            };
            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
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
        showOnStart: boolean;
        tabindex: number;


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


    export interface ISalIndAmountName {
        individualPriceCode: string,
        individualPriceName: string
    }


    export class SalIndAmountName {
        individualPriceCode: string;
        individualPriceName: string;

        constructor(param: ISalIndAmountName) {
            this.individualPriceCode = param.individualPriceCode;
            this.individualPriceName = param.individualPriceName;
        }
    }

    export enum PerValueCateCls {
        SUPPLY = 0,
        DEDUCTION = 1
    }

    export enum SalBonusCate {
        //給与 = 0
        //賞与 = 1
        SALARY = 0,
        BONUSES = 1
    }

    export enum CategoryIndicator {
        //支給 = 0
        //控除 = 1
        PAYMENT = 0,
        DEDUCTION = 1
    }

    export interface IPersonalAmount {
        empId: string,
        historyId: string,
        employeeCode: string,
        businessName: string,
        startYearMonth: number,
        endYearMonth: number,
        amount: number
    }

    export class PersonalAmount {
        empId: string;
        historyId: string;
        employeeCode: string = '';
        businessName: string = '';
        startYearMonth: number;
        endYearMonth: number;
        period: string;
        amount: string = "0";

        constructor(param: IPersonalAmount) {
            let self = this;
            self.empId = param.empId;
            self.historyId = param.historyId;
            self.startYearMonth = param.startYearMonth;
            self.endYearMonth = param.endYearMonth;
            self.period = nts.uk.time.formatYearMonth(param.startYearMonth) + ' ～ ' + nts.uk.time.formatYearMonth(param.endYearMonth);
            self.amount = param.amount.toString();
        }


    }


}