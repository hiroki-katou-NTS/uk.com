module nts.uk.pr.view.qmm042.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {

        ccgcomponent: GroupOption;
        salaryPerUnitPriceNames: KnockoutObservableArray<IndividualPriceName> = ko.observableArray([]);
        salaryPerUnitPriceNamesSelectedCode: KnockoutObservable<string> = ko.observable('');

        workIndividualPrices: KnockoutObservableArray<WorkIndividualPrice> = ko.observableArray([]);
        workIndividualPricesDisplay: KnockoutObservableArray<WorkIndividualPrice> = ko.observableArray([]);

        perUnitPriceName: KnockoutObservable<string> = ko.observable('');
        perUnitPriceCode: KnockoutObservable<string> = ko.observable('');

        yearMonthFilter = ko.observable(201802);

        employeeInfoImports:any;
        constructor() {
            var self = this;
            $("#A4_7").ntsFixedTable({height: 350, width: 720});


            self.salaryPerUnitPriceNamesSelectedCode.subscribe(function (selectcode) {


                self.workIndividualPrices.removeAll();
                self.workIndividualPricesDisplay.removeAll();
                nts.uk.ui.errors.clearAll();
                $('#A4_5').ntsError('check');

                if(self.workIndividualPrices().length<=10){
                    if (/Edge/.test(navigator.userAgent)) {
                        $('.scroll-header').removeClass('edge_scroll_header');
                    } else {
                        $('.scroll-header').removeClass('ci_scroll_header');
                    }
                }

                if (!selectcode)
                    return;
                let temp = _.find(self.salaryPerUnitPriceNames(), function (o) {
                    return o.code == selectcode;
                });
                if (temp) {
                    self.perUnitPriceCode(temp.code);
                    self.perUnitPriceName(temp.name);

                }


            })


        }

        filterData(): void {
            let self=this;

            let temp = new Array();

            for(let i=0;i<self.workIndividualPrices().length;i++){
                if(self.workIndividualPrices()[i].startYaerMonth <= this.yearMonthFilter() && self.workIndividualPrices()[i].endYearMonth >= this.yearMonthFilter()){
                    temp.push(self.workIndividualPrices()[i])
                }
            }

            self.workIndividualPricesDisplay(temp);







            if (self.workIndividualPricesDisplay().length > 10) {
                if (/Edge/.test(navigator.userAgent)) {
                    $('.scroll-header').addClass('edge_scroll_header');
                    $('.nts-fixed-body-container').addClass('edge_scroll_body');
                } else {
                    $('.scroll-header').addClass('ci_scroll_header');
                    $('.nts-fixed-body-container').addClass('ci_scroll_body');
                }

            }
            if(self.workIndividualPricesDisplay().length<=10){
                if (/Edge/.test(navigator.userAgent)) {
                    $('.scroll-header').removeClass('edge_scroll_header');
                    $('.nts-fixed-body-container').removeClass('edge_scroll_body');
                } else {
                    $('.scroll-header').removeClass('ci_scroll_header');
                    $('.nts-fixed-body-container').removeClass('ci_scroll_body');
                }
            }
        }


        registerAmount(): void {
            let self = this;

            service.empSalUnitUpdateAll({
                salIndAmountUpdateCommandList: ko.toJS(self.workIndividualPrices)}).done(function () {
                dialog.info({messageId: "Msg_15"});
            })
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.employeeReferenceDate().done(function (data) {
                self.reloadCcg001(data.paymentDate);
            });

            service.salaryPerUnitPriceName().done(function (individualPriceName) {
                if (!individualPriceName) {
                    nts.uk.ui.dialog.alertError({messageId: "MsgQ_170"});
                    return;
                }
                self.salaryPerUnitPriceNames(individualPriceName);
                self.salaryPerUnitPriceNamesSelectedCode(self.salaryPerUnitPriceNames()[0].code);
            });

            dfd.resolve(self);
            return dfd.promise();
        }

        reloadCcg001(paymentDate: string): void {
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
                baseDate: moment(new Date(paymentDate)).format("YYYY-MM-DD"), // 基準日
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

                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    console.log(data.listEmployee);
                    let command = {
                        personalUnitPriceCode: self.salaryPerUnitPriceNamesSelectedCode(),
                        employeeIds: data.listEmployee.map(v => v.employeeId)
                    }
                    service.employeeSalaryUnitPriceHistory(command).done(function (dataNameAndAmount) {
                        self.employeeInfoImports = dataNameAndAmount.employeeInfoImports;
                        let personalAmountData: Array<any> = new Array();
                        personalAmountData = dataNameAndAmount.workIndividualPrices.map(x => new WorkIndividualPrice(x));
                        personalAmountData = _.sortBy(personalAmountData, function (o) {
                            return o.startYaerMonth;
                        })
                        console.log(dataNameAndAmount);
                        self.workIndividualPrices(personalAmountData);
                        self.workIndividualPricesDisplay(personalAmountData);
                        //self.personalDisplay(personalAmountData);
                        for (let i = 0; i < self.workIndividualPrices().length; i++) {
                            let index = _.findIndex(self.employeeInfoImports, function (o) {
                                return o.sid == self.workIndividualPrices()[i].employeeID
                            });
                            if (index != -1) {
                                self.workIndividualPrices()[i].employeeCode(self.employeeInfoImports[index].scd);
                                self.workIndividualPrices()[i].businessName(self.employeeInfoImports[index].businessName);
                            }
                        }


                        setTimeout(function () {
                            if (self.workIndividualPrices().length > 10) {
                                if (/Edge/.test(navigator.userAgent)) {
                                    $('.scroll-header').addClass('edge_scroll_header');
                                    $('.nts-fixed-body-container').addClass('edge_scroll_body');
                                } else {
                                    $('.scroll-header').addClass('ci_scroll_header');
                                    $('.nts-fixed-body-container').addClass('ci_scroll_body');
                                }

                            }
                            if(self.workIndividualPrices().length<=10){
                                if (/Edge/.test(navigator.userAgent)) {
                                    $('.scroll-header').removeClass('edge_scroll_header');
                                } else {
                                    $('.scroll-header').removeClass('ci_scroll_header');
                                }
                            }


                        }, 100);


                    })
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

    export interface IIndividualPriceName {
        code: string,
        name: string,
        abolition: number,
        shortName: string,
        integrationCode: string,
        note: string
    }


    export class IndividualPriceName {
        code: string;
        name: string;
        abolition: number;
        shortName: string;
        integrationCode: string;
        note: string;

        constructor(param: IIndividualPriceName) {
            if (param) {
                this.code = param.code;
                this.name = param.name;
                this.abolition = param.abolition;
                this.shortName = param.shortName;
                this.integrationCode = param.integrationCode;
                this.note = param.note;
            }
        }
    }


    export interface IWorkIndividualPrice {
        employeeID: string,
        historyID: string,
        employeeCode: string,
        businessName: string,
        startYaerMonth: number,
        endYearMonth: number,
        amountOfMoney: number,
    }

    export class WorkIndividualPrice {
        employeeID: string;
        historyID: string;
        employeeCode: KnockoutObservable<string> = ko.observable('');
        businessName: KnockoutObservable<string> = ko.observable('');

        startYaerMonth: number;
        endYearMonth: number;

        period: string;

        amountOfMoney: KnockoutObservable<number> = ko.observable(0);

        constructor(param: IWorkIndividualPrice) {
            if(param){
                this.employeeID = param.employeeID;
                this.historyID = param.historyID;
                this.employeeCode(param.employeeCode);
                this.businessName(param.businessName);
                this.startYaerMonth = param.startYaerMonth;
                this.endYearMonth = param.endYearMonth;
                this.amountOfMoney(param.amountOfMoney);
                this.period=nts.uk.time.formatYearMonth(param.startYaerMonth) + ' ~ ' + nts.uk.time.formatYearMonth(param.endYearMonth);
            }

        }

    }

    export interface IEmployeeInfoImport {
        sid: string,
        scd: string,
        businessName: string,
    }


    export class EmployeeInfoImport {
        sid: string;
        scd: string;
        businessName: string;

        constructor(param: IEmployeeInfoImport) {
            this.sid = param.sid;
            this.scd = param.scd;
            this.businessName = param.businessName;
        }
    }

}


