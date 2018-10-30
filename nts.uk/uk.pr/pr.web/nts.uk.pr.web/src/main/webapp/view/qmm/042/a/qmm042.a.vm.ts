module nts.uk.pr.view.qmm042.a.viewmodel {
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {

        ccgcomponent: GroupOption;
        salaryPerUnitPriceNames: KnockoutObservableArray<IndividualPriceName> = ko.observableArray([]);
        salaryPerUnitPriceNamesSelectedCode: KnockoutObservable<string> = ko.observable('');


        perUnitPriceName: KnockoutObservable<string> = ko.observable('');
        perUnitPriceCode: KnockoutObservable<string> = ko.observable('');

        yearMonthFilter = ko.observable(201802);


        constructor() {
            var self = this;



            self.salaryPerUnitPriceNamesSelectedCode.subscribe(function (selectcode) {
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

        }


        registerAmount(): void {

        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.employeeReferenceDate().done(function (data) {
                self.reloadCcg001(data.empExtraRefeDate);
            });

            service.salaryPerUnitPriceName().done(function (individualPriceName) {

                self.salaryPerUnitPriceNames(individualPriceName);
                self.salaryPerUnitPriceNamesSelectedCode(self.salaryPerUnitPriceNames()[0].code);


            });

            dfd.resolve(self);
            return dfd.promise();
        }

        reloadCcg001(empExtraRefeDate: string): void {
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
                showEmployment: false,
                showWorkplace: false,
                showClassification: false,
                showJobTitle: false,
                showWorktype: false,
                isMutipleCheck: true,

                returnDataFromCcg001: function (data: Ccg001ReturnedData) {

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
}


