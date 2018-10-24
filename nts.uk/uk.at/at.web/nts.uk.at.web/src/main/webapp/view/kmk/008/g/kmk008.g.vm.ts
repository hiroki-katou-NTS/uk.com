module nts.uk.at.view.kmk008.g {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import Ccg001ReturnedData = nts.uk.com.view.ccg.share.ccg.service.model.Ccg001ReturnedData;
    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

    export module viewmodel {
        export class ScreenModel {

            isShowButton: KnockoutObservable<boolean>;

            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            employeeName: KnockoutObservable<string>;

            items: KnockoutObservableArray<ItemModel>;
            items2: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            columns2: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            selectedId: KnockoutObservable<string>;


            currentCode2: KnockoutObservable<any>;

            isNewMode: KnockoutObservable<boolean>;
            isUpdateMode: KnockoutObservable<boolean>;

            //list
            listComponentOption: any;
            selectedEmpCode: KnockoutObservable<string>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;
            maxRows: number;

            //search
            ccg001ComponentOption: any;
           // selectedEmpCode: KnockoutObservableArray<string>;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;

            // Options
            baseDate: KnockoutObservable<Date>;
            isQuickSearchTab: KnockoutObservable<boolean>;
            isAdvancedSearchTab: KnockoutObservable<boolean>;
            isAllReferableEmployee: KnockoutObservable<boolean>;
            isOnlyMe: KnockoutObservable<boolean>;
            isEmployeeOfWorkplace: KnockoutObservable<boolean>;
            isEmployeeWorkplaceFollow: KnockoutObservable<boolean>;
            isMutipleCheck: KnockoutObservable<boolean>;
            isSelectAllEmployee: KnockoutObservable<boolean>;
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            constructor() {
                let self = this;

                self.isShowButton = ko.observable(true);

                self.isNewMode = ko.observable(true);
                self.isUpdateMode = ko.observable(false);
                self.isNewMode.subscribe(function(val) {
                    self.isUpdateMode(!val);
                });
                self.selectedId = ko.observable("");
                self.employeeName = ko.observable("");

                //list
                self.isShowWorkPlaceName = ko.observable(false);
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.selectedEmpCode = ko.observable("");
                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.listComponentOption = {
                    maxRows: 15,
                    isMultiSelect: false,
                    listType: 4,
                    selectType: 1,
                    selectedCode: self.selectedEmpCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    isShowWorkPlaceName: true,
                    employeeInputList: self.employeeList,
                    tabindex: 4
                };

                //search
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());
                
                self.reloadCcg001();

//                self.ccgcomponent = {
//                    baseDate: self.baseDate,
//
//                    isQuickSearchTab: true,
//                    isAdvancedSearchTab: true,
//                    isAllReferableEmployee: true,
//                    isOnlyMe: true,
//                    isEmployeeOfWorkplace: true,
//                    isEmployeeWorkplaceFollow: true,
//                    isMutipleCheck: true,
//                    isSelectAllEmployee: true,
//
//                    /**
//                    * @param dataList: list employee returned from component.
//                    * Define how to use this list employee by yourself in the function's body.
//                    */
//                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
//                        self.showinfoSelectedEmployee(true);
//                        self.selectedEmployee(dataList);
//                        self.employeeList([]);
//                        self.employeeList(_.map(dataList, item => {
//                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
//                        }));
//                        if (self.employeeList() && self.employeeList().length > 0){
//                            self.selectedCode(self.employeeList()[0].code);
//                        }
//                    },
//                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
//                        self.showinfoSelectedEmployee(true);
//                        var dataEmployee: EmployeeSearchDto[] = [];
//                        dataEmployee.push(data);
//                        self.selectedEmployee(dataEmployee);
//                        self.employeeList([]);
//                        self.employeeList.push(new UnitModel(data.employeeCode, data.employeeName, data.workplaceName, data.employeeId));
//                        if (self.employeeList() && self.employeeList().length > 0){
//                            self.selectedCode(self.employeeList()[0].code);
//                        }
//                    },
//                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
//                        self.showinfoSelectedEmployee(true);
//                        self.selectedEmployee(dataList);
//                        self.employeeList([]);
//                        self.employeeList(_.map(dataList, item => {
//                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
//                        }));
//                        if (self.employeeList() && self.employeeList().length > 0){
//                            self.selectedCode(self.employeeList()[0].code);
//                        }
//                    },
//                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
//                        self.showinfoSelectedEmployee(true);
//                        self.selectedEmployee(dataList);
//                        $('#component-items-list').ntsListComponent(self.dataList);
//                        self.employeeList([]);
//                        self.employeeList(_.map(dataList, item => {
//                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
//                        }));
//                        if (self.employeeList() && self.employeeList().length > 0){
//                            self.selectedCode(self.employeeList()[0].code);
//                        }
//                    },
//                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
//                        self.showinfoSelectedEmployee(true);
//                        self.selectedEmployee(dataEmployee);
//                        $('#component-items-list').ntsListComponent(self.dataEmployee);
//                        self.employeeList([]);
//                        self.employeeList(_.map(dataList, item => {
//                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
//                        }));
//                        if (self.employeeList() && self.employeeList().length > 0){
//                            self.selectedCode(self.employeeList()[0].code);
//                        }
//                    }
//                }

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '年月', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '年度', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');

                self.items = ko.observableArray([]);
                self.items2 = ko.observableArray([]);

                self.columns = ko.observableArray([
                    { headerText: '年度', key: 'year', width: 100 },
                    { headerText: 'エラー', key: 'error', width: 150 },
                    { headerText: 'アラーム', key: 'alarm', width: 150 }
                ]);
                self.columns2 = ko.observableArray([
                    { headerText: '年月', key: 'year', width: 100 },
                    { headerText: 'エラー', key: 'error', width: 150 },
                    { headerText: 'アラーム', key: 'alarm', width: 150 }
                ]);
                self.currentCode = ko.observable();
                self.currentCode2 = ko.observable();

                self.selectedEmpCode.subscribe(newValue => {

                    if (!nts.uk.text.isNullOrEmpty(newValue)){
                        let data = self.selectedEmployee();
                        let employee = _.find(data, function(o) {
                            return o.employeeCode == self.selectedEmpCode();
                        });
                        self.getDetail(employee.employeeId);
                        self.selectedId(employee.employeeId);
                        self.employeeName(employee.employeeName);    
                    }else{
                        self.setNewMode()
                        self.selectedId("");
                        self.employeeName("");
                        self.items([]);
                        self.items2([]);
                    }
                    

                });

                self.selectedTab.subscribe(x => {
                    if (self.selectedId()) {
                        return self.getDetail(self.selectedId());
                    } else {
                        if (self.selectedTab() == "tab-1") {
                            self.items2([]);
                            self.items2.push(new ItemModel("", "", ""));
                        } else {
                            self.items([]);
                            self.items.push(new ItemModel("", "", ""));
                        }
                    }

                });

            }
            
            public reloadCcg001(): void {
                let self = this;
                if ($('.ccg-sample-has-error').ntsError('hasError')) {
                    return;
                }
//                if (!self.showBaseDate() && !self.showClosure() && !self.showPeriod()){
//                    nts.uk.ui.dialog.alertError("Base Date or Closure or Period must be shown!" );
//                    return;
//                }
                self.ccg001ComponentOption = {
                    /** Common properties */
                    systemType: 2, // システム区分
                    showEmployeeSelection: false, // 検索タイプ
                    showQuickSearchTab: false, // クイック検索
                    showAdvancedSearchTab: true, // 詳細検索
                    showBaseDate: true, // 基準日利用
                    showClosure: false, // 就業締め日利用
                    showAllClosure: false, // 全締め表示
                    showPeriod: false, // 対象期間利用
                    periodFormatYM: true, // 対象期間精度

                    /** Required parameter */
                    baseDate: moment().toISOString(), // 基準日
                    inService: true, // 在職区分
                    leaveOfAbsence: false, // 休職区分
                    closed: false, // 休業区分
                    retirement: false, // 退職区分
                    
                    /** Quick search tab options */
                    showAllReferableEmployee: false, // 参照可能な社員すべて
                    showOnlyMe: false, // 自分だけ
                    showSameWorkplace: false, // 同じ職場の社員
                    showSameWorkplaceAndChild: false, // 同じ職場とその配下の社員

                    /** Advanced search properties */
                    showEmployment: true, // 雇用条件
                    showWorkplace: true, // 職場条件
                    showClassification: true, // 分類条件
                    showJobTitle: true, // 職位条件
                    showWorktype: true, // 勤種条件
                    isMutipleCheck: true, // 選択モード

                    /** Return data */
                    returnDataFromCcg001: function(data: Ccg001ReturnedData) {
                        self.selectedEmployee(data.listEmployee);
                        self.showinfoSelectedEmployee(true);
                        self.employeeList([]);
                        self.employeeList(_.map(data.listEmployee, item => {
                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
                        }));
                        if (self.employeeList() && self.employeeList().length > 0){
                            self.selectedEmpCode(self.employeeList()[0].code);
                        }
                    }
                }

                // Start component
//                $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption);
            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                if (!self.selectedEmpCode()) {
                    self.isShowButton(false);
                }

                dfd.resolve();
                return dfd.promise();
            }

            openDiaglog() {
                let self = this;
                let isYearMonth = false;
                if (self.selectedTab() == "tab-1") {
                    isYearMonth = true;
                }
                setShared("KMK_008_PARAMS", { employeeCode: self.selectedEmpCode(), employeeId: self.selectedId(), employeeName: self.employeeName(), isYearMonth: isYearMonth });
                modal('../../../kmk/008/k/index.xhtml').onClosed(() => {
                    //                    let data: string = getShared('KDL007_VALUES');
                    if (self.selectedId()) {
                        self.getDetail(self.selectedId());
                    }
                });
            }

            getDetail(employmentCategoryCode: string) {
                var self = this;
                self.isShowButton(true);
                if (self.selectedTab() == "tab-1") {
                    service.getMonth(employmentCategoryCode).done(function(monthData: Array<model.MonthDto>) {
                        if (monthData) {
                            self.items2([]);
                            //                            monthData = _.sortBy(monthData, item => { return monthData.yearMonthValue });
                            //                            monthData.reverse();
                            _.forEach(monthData, function(value) {
                                self.items2.push(new ItemModel(nts.uk.time.parseYearMonth(value.yearMonthValue).format(), nts.uk.time.parseTime(value.errorOneMonth, true).format(), nts.uk.time.parseTime(value.alarmOneMonth, true).format()));
                            });

                        } else {
                            self.items2([]);
                            self.items2.push(new ItemModel("", "", ""));
                        }
                    });
                } else {
                    service.getYear(employmentCategoryCode).done(function(yearData: Array<model.YearDto>) {
                        if (yearData) {
                            self.items([]);
                            //                            yearData = _.sortBy(yearData, item => { return yearData.yearValue });
                            //                            yearData.reverse();
                            _.forEach(yearData, function(value) {
                                self.items.push(new ItemModel(value.yearValue, nts.uk.time.parseTime(value.errorOneYear, true).format(), nts.uk.time.parseTime(value.alarmOneYear, true).format()));
                            });

                        } else {
                            self.items([]);
                            self.items.push(new ItemModel("", "", ""));
                        }
                    });
                }
            }

            setNewMode() {
                let self = this;
                self.isNewMode(true);
            }
        }

        export class ItemModel {
            year: string;
            error: string;
            alarm: string;
            constructor(year: string, error: string, alarm: string) {
                this.year = year;
                this.error = error;
                this.alarm = alarm;
            }
        }

        export class UnitModel {
            code: string;
            name: string;
            workplaceName: string;
            employeeId: string;
            constructor(code: string, name: string, workplaceName: string, employeeId: string) {
                this.code = code;
                this.name = name;
                this.workplaceName = workplaceName;
                this.employeeId = employeeId;
            }
        }

    }
}
