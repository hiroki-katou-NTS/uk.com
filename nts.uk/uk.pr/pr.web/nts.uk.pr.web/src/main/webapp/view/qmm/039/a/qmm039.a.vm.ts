module nts.uk.pr.view.qmm039.a.viewmodel {
    export class ScreenModel {
        referenceDate: KnockoutObservable<string> = ko.observable('');
        //Help Button
        enable: KnockoutObservable<boolean>;
        ccgcomponent: GroupOption;

        // Options
        isQuickSearchTab: KnockoutObservable<boolean>;
        isAdvancedSearchTab: KnockoutObservable<boolean>;
        isAllReferableEmployee: KnockoutObservable<boolean>;
        isOnlyMe: KnockoutObservable<boolean>;
        isEmployeeOfWorkplace: KnockoutObservable<boolean>;
        isEmployeeWorkplaceFollow: KnockoutObservable<boolean>;
        isMutipleCheck: KnockoutObservable<boolean>;
        isSelectAllEmployee: KnockoutObservable<boolean>;
        periodStartDate: KnockoutObservable<moment.Moment>;
        periodEndDate: KnockoutObservable<moment.Moment>;
        baseDate: KnockoutObservable<moment.Moment>;
        selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;
        showEmployment: KnockoutObservable<boolean>; // 雇用条件
        showWorkplace: KnockoutObservable<boolean>; // 職場条件
        showClassification: KnockoutObservable<boolean>; // 分類条件
        showJobTitle: KnockoutObservable<boolean>; // 職位条件
        showWorktype: KnockoutObservable<boolean>; // 勤種条件
        inService: KnockoutObservable<boolean>; // 在職区分
        leaveOfAbsence: KnockoutObservable<boolean>; // 休職区分
        closed: KnockoutObservable<boolean>; // 休業区分
        retirement: KnockoutObservable<boolean>; // 退職区分
        systemType: KnockoutObservable<number>;
        showClosure: KnockoutObservable<boolean>; // 就業締め日利用
        showBaseDate: KnockoutObservable<boolean>; // 基準日利用
        showAllClosure: KnockoutObservable<boolean>; // 全締め表示
        showPeriod: KnockoutObservable<boolean>; // 対象期間利用
        periodFormatYM: KnockoutObservable<boolean>; // 対象期間精度


        selectedEmployeeCode: KnockoutObservableArray<string>;
        employeeList: KnockoutObservableArray<TargetEmployee>;

        // date
        date: KnockoutObservable<string>;

        constructor() {
            let self = this;
            // initial ccg options
            self.setDefaultCcg001Option();
            // Init component.
            self.reloadCcg001();
        }

        public startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve(self);
            return dfd.promise();
        }

        /**
         * Set default ccg001 options
         */
        public setDefaultCcg001Option(): void {
            let self = this;
            self.isQuickSearchTab = ko.observable(true);
            self.isAdvancedSearchTab = ko.observable(true);
            self.isAllReferableEmployee = ko.observable(true);
            self.isOnlyMe = ko.observable(true);
            self.isEmployeeOfWorkplace = ko.observable(true);
            self.isEmployeeWorkplaceFollow = ko.observable(true);
            self.isMutipleCheck = ko.observable(true);
            self.isSelectAllEmployee = ko.observable(true);
            self.baseDate = ko.observable(moment());
            self.periodStartDate = ko.observable(moment());
            self.periodEndDate = ko.observable(moment());
            self.showEmployment = ko.observable(true); // 雇用条件
            self.showWorkplace = ko.observable(true); // 職場条件
            self.showClassification = ko.observable(true); // 分類条件
            self.showJobTitle = ko.observable(true); // 職位条件
            self.showWorktype = ko.observable(true); // 勤種条件
            self.inService = ko.observable(true); // 在職区分
            self.leaveOfAbsence = ko.observable(true); // 休職区分
            self.closed = ko.observable(true); // 休業区分
            self.retirement = ko.observable(true); // 退職区分
            self.systemType = ko.observable(1);
            self.showClosure = ko.observable(false); // 就業締め日利用
            self.showBaseDate = ko.observable(true); // 基準日利用
            self.showAllClosure = ko.observable(false); // 全締め表示
            self.showPeriod = ko.observable(false); // 対象期間利用
            self.periodFormatYM = ko.observable(false); // 対象期間精度
        }

        public reloadCcg001(): void {
            let self = this;
            let periodStartDate, periodEndDate: string;
            if (self.showBaseDate()) {
                periodStartDate = moment(self.periodStartDate()).format("YYYY-MM-DD");
                periodEndDate = moment(self.periodEndDate()).format("YYYY-MM-DD");
            } else {
                periodStartDate = moment(self.periodStartDate()).format("YYYY-MM");
                periodEndDate = moment(self.periodEndDate()).format("YYYY-MM"); // 対象期間終了日
            }

            if (!self.showBaseDate() && !self.showClosure() && !self.showPeriod()) {
                nts.uk.ui.dialog.alertError("Base Date or Closure or Period must be shown!");
                return;
            }
            self.ccgcomponent = {
                /** Common properties */
                systemType: self.systemType(), // システム区分
                showEmployeeSelection: self.isSelectAllEmployee(), // 検索タイプ
                showQuickSearchTab: self.isQuickSearchTab(), // クイック検索
                showAdvancedSearchTab: self.isAdvancedSearchTab(), // 詳細検索
                showBaseDate: self.showBaseDate(), // 基準日利用
                showClosure: self.showClosure(), // 就業締め日利用
                showAllClosure: self.showAllClosure(), // 全締め表示
                showPeriod: self.showPeriod(), // 対象期間利用
                periodFormatYM: self.periodFormatYM(), // 対象期間精度

                /** Required parameter */
                baseDate: moment(self.baseDate()).format("YYYY-MM-DD"), // 基準日
                periodStartDate: periodStartDate, // 対象期間開始日
                periodEndDate: periodEndDate, // 対象期間終了日
                inService: self.inService(), // 在職区分
                leaveOfAbsence: self.leaveOfAbsence(), // 休職区分
                closed: self.closed(), // 休業区分
                retirement: self.retirement(), // 退職区分

                /** Quick search tab options */
                showAllReferableEmployee: self.isAllReferableEmployee(), // 参照可能な社員すべて
                showOnlyMe: self.isOnlyMe(), // 自分だけ
                showSameWorkplace: self.isEmployeeOfWorkplace(), // 同じ職場の社員
                showSameWorkplaceAndChild: self.isEmployeeWorkplaceFollow(), // 同じ職場とその配下の社員

                /** Advanced search properties */
                showEmployment: self.showEmployment(), // 雇用条件
                showWorkplace: self.showWorkplace(), // 職場条件
                showClassification: self.showClassification(), // 分類条件
                showJobTitle: self.showJobTitle(), // 職位条件
                showWorktype: self.showWorktype(), // 勤種条件
                isMutipleCheck: self.isMutipleCheck(), // 選択モード

                /** Return data */
                returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                    self.selectedEmployee(data.listEmployee);
                    self.applyKCP005ContentSearch(data.listEmployee).done(() => {
                        setTimeout(function() {
                            $("#employeeSearch").find("div[id *= 'scrollContainer']").scrollTop(0);
                        }, 1000);
                    });
                    self.referenceDate(moment.utc(data.baseDate).format("YYYY/MM/DD"));
                }
            }
            $('#com-ccg001').ntsGroupComponent(self.ccgcomponent);
        }

        public applyKCP005ContentSearch(dataList: EmployeeSearchDto[]): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let employeeSearchs: TargetEmployee[] = [];
            self.selectedEmployeeCode([]);
            for (let i = 0; i < dataList.length; i++) {
                let employeeSearch = dataList[i];
                let employee : UnitModel = {
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
}