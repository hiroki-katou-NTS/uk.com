module nts.uk.pr.view.qmm039.a.viewmodel {
    import SalIndAmountHis = nts.uk.pr.view.qmm039.share.model.SalIndAmountHis;
    import setShared = nts.uk.ui.windows.setShared;
    import modal = nts.uk.ui.windows.sub.modal;
    import getText = nts.uk.resource.getText;
    import format = nts.uk.text.format;
    import GenericHistYMPeriod = nts.uk.pr.view.qmm039.share.model.GenericHistYMPeriod;
    import SalIndAmount = nts.uk.pr.view.qmm039.share.model.SalIndAmount;
    export class ScreenModel {
        itemList: KnockoutObservableArray<>;
        isEnable: KnockoutObservable<boolean>;
        salHis: any;

        dataSource: any;
        singleSelectedCode: any;
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        systemReference: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;

        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;


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
        date: KnockoutObservable<string>;
        value: KnockoutObservable<number>;
        currencyeditor: any;

        constructor() {
            let self = this;
            // initial ccg options
            self.setDefaultCcg001Option();
            // Init component.
            self.reloadCcg001();
            self.isEnable = ko.observable(true);
            self.salHis = new SalIndAmountHis({historyID: 1, periodStartYm: 201801, periodEndYm: 201806});
            let sal = new SalIndAmount({amountOfMoney: 2013});
            self.itemList = ko.observableArray([
                new ItemModel(self.salHis.historyID(), self.salHis.periodStartYm(), self.salHis.periodEndYm(), format(getText("QMM039_18"), self.salHis.periodStartYm(), self.salHis.periodEndYm()), sal.amountOfMoney()),
            ]);
            self.dataSource = ko.observableArray([new Node('code1', 'name1'), new Node('code2', 'name3')]);
            self.singleSelectedCode = ko.observable(null);
            self.currencyeditor = {
                value: ko.observable(1200),
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
            self.selectedEmployee = ko.observableArray([]);
            self.selectedEmployeeCode = ko.observableArray([]);
            self.employeeInputList = ko.observableArray([
                {id: '01', code: 'A000000000001', businessName: '日通　純一郎1', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '04', code: 'A000000000004', businessName: '日通　純一郎4', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '05', code: 'A000000000005', businessName: '日通　純一郎5', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '06', code: 'A000000000006', businessName: '日通　純一郎6', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '07', code: 'A000000000007', businessName: '日通　純一郎7', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '08', code: 'A000000000008', businessName: '日通　純一郎8', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {id: '09', code: 'A000000000009', businessName: '日通　純一郎9', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {
                    id: '10',
                    code: 'A000000000010',
                    businessName: '日通　純一郎10',
                    workplaceName: '名古屋支店',
                    depName: 'Dep Name'
                },
                {
                    id: '11',
                    code: 'A000000000011',
                    businessName: '日通　純一郎11',
                    workplaceName: '名古屋支店',
                    depName: 'Dep Name'
                },
                {id: '02', code: 'A000000000002', businessName: '日通　純一郎2', workplaceName: '名古屋支店', depName: 'Dep Name'},
                {
                    id: '03',
                    code: 'A000000000003',
                    businessName: '日通　純一郎3',
                    workplaceName: '名古屋支店',
                    depName: 'Dep Name'
                }]);

            self.systemReference = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(false);
            self.targetBtnText = nts.uk.resource.getText("QMM039_10");
            self.selectedItem = ko.observable(null);
            self.tabindex = 1;
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemReference(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.employeeInputList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
            };
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
            console.log(JSON.stringify(self.listComponentOption));
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
                returnDataFromCcg001: function (data: Ccg001ReturnedData) {
                    self.selectedEmployee(data.listEmployee);
                    self.applyKCP005ContentSearch(data.listEmployee).done(() => {
                        setTimeout(function () {
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
            let params = {
                historyID: self.salHis.historyID(),
                period: {
                    periodStartYm: self.salHis.periodStartYm(),
                    periodEndYm: self.salHis.periodEndYm()
                }
            }
            setShared("QMM039_A_PARAMS", params);
            modal('/view/qmm/039/b/index.xhtml', {title: '',}).onClosed(function (): any {

            });
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
        employeeInputList: KnockoutObservableArray<EmployeeModel>;
        targetBtnText: string;
        selectedItem: KnockoutObservable<string>;
        tabIndex: number;
    }
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }
    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
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
}