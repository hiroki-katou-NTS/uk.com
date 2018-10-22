module nts.uk.pr.view.qmm040.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {

        //SalIndAmountName

        salIndAmountNames: KnockoutObservableArray<SalIndAmountName>;
        salIndAmountNamesSelectedCode: KnockoutObservable<string>;

        personalAmount: PersonalAmount = new PersonalAmount();
        salIndAmountUpdateCommandList:KnockoutObservableArray<Amount>=ko.observableArray([]);

        //onSelected
        cateIndicator: KnockoutObservable<number>;
        salBonusCate: KnockoutObservable<number>;
        //ccg001
        referenceDate: KnockoutObservable<string> = ko.observable('');
        ccgcomponent: GroupOption;
        employeeList: KnockoutObservableArray<ItemModel>;
        tilteTable: KnockoutObservableArray<any>;
        selectedEmployeeCode: KnockoutObservable<string>;
        //amount
        amountList: KnockoutObservableArray<Amount>;


        yearMonthFilter: KnockoutObservable<number>;
        onTab: KnockoutObservable<number> = ko.observable(0);
        titleTab: KnockoutObservable<string> = ko.observable('');
        itemClassification: KnockoutObservable<string> = ko.observable('');
        individualPriceCode: KnockoutObservable<string>;
        individualPriceName: KnockoutObservable<string>;


        constructor() {
            var self = this;
            $("#A5_9").ntsFixedTable({height: 350, width: 700});
            self.tilteTable = ko.observableArray([
                {headerText: getText('QMM040_8'), key: 'individualPriceCode', width: 100},
                {headerText: getText('QMM040_9'), key: 'individualPriceName', width: 200}

            ]);


            self.yearMonthFilter= ko.observable(parseInt(moment(Date.now()).format("YYYYMM")));
            self.cateIndicator = ko.observable(0);
            self.salBonusCate = ko.observable(0);

            self.salIndAmountNames = ko.observableArray([]);
            self.salIndAmountNamesSelectedCode = ko.observable('');


            self.amountList = ko.observableArray([]);

            self.selectedEmployeeCode = ko.observable('1');
            self.individualPriceCode = ko.observable('');
            self.individualPriceName = ko.observable('');
            self.onSelectTab(self.onTab);

            self.employeeList = ko.observableArray([]);

            self.salIndAmountNamesSelectedCode.subscribe(function (data) {

                if (!data)
                    return;
                let temp = _.find(self.salIndAmountNames(), function (o) {
                    return o.individualPriceCode == data;
                });
                if (temp) {
                    self.individualPriceCode(temp.individualPriceCode);
                    self.individualPriceName(temp.individualPriceName);
                    self.onSelected(self.individualPriceCode());


                }


            });
            self.reloadCcg001();



        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();

            dfd.resolve(self);
            return dfd.promise();
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

                    $("#sidebar").ntsSideBar("active", param)
                    break;
                case 1:
                    //TODO
                    self.onTab(1);
                    self.titleTab(getText('QMM040_4'));
                    self.itemClassification(getText('QMM040_4'));
                    self.loadSalIndAmountName(PerValueCateCls.DEDUCTION);
                    self.cateIndicator(CategoryIndicator.DEDUCTION);
                    self.salBonusCate(SalBonusCate.SALARY);
                    $("#sidebar").ntsSideBar("active", param)
                    break;
                case 2:
                    //TODO
                    self.onTab(2);
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    self.cateIndicator(CategoryIndicator.PAYMENT);
                    self.salBonusCate(SalBonusCate.BONUSES);
                    self.titleTab(getText('QMM040_5'));
                    self.itemClassification(getText('QMM040_5'));
                    $("#sidebar").ntsSideBar("active", param)
                    break;
                case 3:
                    //TODO
                    self.onTab(3);
                    self.titleTab(getText('QMM040_6'));
                    self.itemClassification(getText('QMM040_6'));
                    self.loadSalIndAmountName(PerValueCateCls.DEDUCTION);
                    self.cateIndicator(CategoryIndicator.DEDUCTION);
                    self.salBonusCate(SalBonusCate.BONUSES);
                    $("#sidebar").ntsSideBar("active", param)
                    break;
                default:
                    //TODO
                    self.onTab(0);
                    self.titleTab(getText('QMM040_3'));
                    self.itemClassification(getText('QMM040_3'));
                    self.loadSalIndAmountName(PerValueCateCls.SUPPLY);
                    $("#sidebar").ntsSideBar("active", 0)
                    break;
            }
        }

        public loadSalIndAmountName(cateIndicator: number): void {
            let self = this;
            service.salIndAmountNameByCateIndicator(cateIndicator).done((data) => {

                if (data) {
                    self.salIndAmountNames(data);
                    if (data.length > 0) {
                        self.salIndAmountNamesSelectedCode(self.salIndAmountNames()[0].individualPriceCode);
                        self.salIndAmountNamesSelectedCode.valueHasMutated();
                    }
                    else {
                        nts.uk.ui.dialog.alertError({messageId: "MsgQ_169"});
                    }


                }

            });
        }

        public onSelected(pelValCode: string) {
            let self = this;
            self.personalAmount.periodAndAmountDisplay.removeAll();
            self.salIndAmountUpdateCommandList.removeAll();
            $("#substituteDataGrid").igGrid("dataSourceObject", self.personalAmount.periodAndAmountDisplay()).igGrid("dataBind");
            service.salIndAmountHisByPeValCode(pelValCode, self.cateIndicator(), self.salBonusCate()).done(function (data) {

                self.personalAmount = new PersonalAmount();
                self.personalAmount.setData(data)
                console.log(self.personalAmount);

            })

        }


        filterData(): void {
            let self = this;
            self.yearMonthFilter();
            let temp = new Array();
            for (let i = 0; i < self.personalAmount.periodAndAmount().length; i++) {
                if (self.personalAmount.periodAndAmount()[i].periodStartYm <= this.yearMonthFilter() && self.personalAmount.periodAndAmount()[i].periodEndYm >= this.yearMonthFilter())
                    temp.push(self.personalAmount.periodAndAmount()[i])
            }
            self.personalAmount.periodAndAmountDisplay(temp);
            self.salIndAmountUpdateCommandList(temp);

            $("#substituteDataGrid").igGrid("dataSourceObject", ko.mapping.toJS(self.personalAmount.periodAndAmountDisplay)).igGrid("dataBind");

            $("#grid").igGrid("dataSourceObject", ko.mapping.toJS(self.personalAmount.periodAndAmountDisplay)).igGrid("dataBind");
        }

        registerAmount():void{
            let self=this;

            service.salIndAmountUpdateAll({salIndAmountUpdateCommandList: ko.toJS(self.salIndAmountUpdateCommandList)}).done(function () {
                dialog.info({messageId: "Msg_15"});
            })
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
                baseDate: moment(new Date('06/05/2017')).format("YYYY-MM-DD"), // 基準日
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
                showEmployment: false,
                showWorkplace: false,
                showClassification: false,
                showJobTitle: false,
                showWorktype: false,
                isMutipleCheck: true,
                /** Return data */
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    //self.selectedEmployee(data.listEmployee);
                    if (data) {
                        console.log(data);
                    }
                    self.referenceDate(moment.utc(data.baseDate).format("YYYY/MM/DD"));
                }
            }
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


    export interface IitemModel {
        employeeCode: string,
        employeeId: string,
        employeeName: string,
        workplaceCode: string,
        workplaceId: string,
        workplaceName: string
    }

    export class ItemModel {

        employeeCode: string;
        employeeId: string;
        employeeName: string
        workplaceCode: string
        workplaceId: string
        workplaceName: string;

        constructor(param: IitemModel) {
            this.employeeCode = param.employeeCode;
            this.employeeId = param.employeeId;
            this.employeeName = param.employeeName;
            this.workplaceCode = param.workplaceCode;
            this.workplaceId = param.workplaceId;
            this.workplaceName = param.workplaceName;
        }
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


    export class PersonalAmount {
        empId: string;
        perValCode: string;
        cateIndicator: number;
        salBonusCate: number;
        periodAndAmount: KnockoutObservableArray<Amount> = ko.observableArray([]);
        periodAndAmountDisplay: KnockoutObservableArray<Amount> = ko.observableArray([]);

        constructor() {
        }

        setData(param: IPersonalAmount) {
            var self = this;
            if (param) {
                self.perValCode = param.perValCode;
                self.empId = param.empId;
                self.cateIndicator = param.cateIndicator;
                self.salBonusCate = param.salBonusCate;
                let temp: Array<Amount> = new Array<Amount>();
                for (let i = 0; i < param.periodAndAmount.length; i++) {
                    temp.push(new Amount(param.periodAndAmount[i]));
                }
                self.periodAndAmount(temp);
            }
        }

    }

    export class Amount {
        historyID: string;
        employeeCode: string = '';
        employeeName: string = '';
        periodStartYm: number;
        periodEndYm: number;
        amountOfMoney: number;
        period: string;

        constructor(param: IAmount) {
            this.historyID = param.historyID;
            this.periodStartYm = param.periodStartYm;
            this.periodEndYm = param.periodEndYm;
            this.amountOfMoney = ko.observable(param.amountOfMoney);
            this.period = nts.uk.time.formatYearMonth(param.periodStartYm) + ' ~ ' + nts.uk.time.formatYearMonth(param.periodEndYm);
        }
    }


    export interface IPersonalAmount {
        cateIndicator: number,
        empId: string,
        perValCode: string,
        periodAndAmount: Array<IAmount>,
        salBonusCate: number
    }

    interface IAmount {
        historyID: string,
        periodStartYm: number,
        periodEndYm: number,
        amountOfMoney: number
    }


}