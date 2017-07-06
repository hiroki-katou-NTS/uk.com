module nts.uk.at.view.kmk008.g {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            employeeName: KnockoutObservable<string>;

            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            selectedId: KnockoutObservable<string>;

            items2: KnockoutObservableArray<ItemModel>;
            currentCode2: KnockoutObservable<any>;

            isNewMode: KnockoutObservable<boolean>;
            isUpdateMode: KnockoutObservable<boolean>;

            //list
            listComponentOption: any;
            selectedCode: KnockoutObservable<string>;
            isDialog: KnockoutObservable<boolean>;
            isShowNoSelectRow: KnockoutObservable<boolean>;
            isShowWorkPlaceName: KnockoutObservable<boolean>;
            employeeList: KnockoutObservableArray<UnitModel>;

            //search
            ccgcomponent: GroupOption;
            selectedCode: KnockoutObservableArray<string>;
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
                self.selectedCode = ko.observable("");
                self.isDialog = ko.observable(false);
                self.isShowNoSelectRow = ko.observable(false);
                self.listComponentOption = {
                    isMultiSelect: false,
                    listType: 4,
                    selectType: 1,
                    selectedCode: self.selectedCode,
                    isDialog: self.isDialog(),
                    isShowNoSelectRow: self.isShowNoSelectRow(),
                    isShowWorkPlaceName: true,
                    employeeInputList: self.employeeList,
                };

                //search
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                self.baseDate = ko.observable(new Date());

                self.ccgcomponent = {
                    baseDate: self.baseDate,

                    isQuickSearchTab: true,
                    isAdvancedSearchTab: true,
                    isAllReferableEmployee: true,
                    isOnlyMe: true,
                    isEmployeeOfWorkplace: true,
                    isEmployeeWorkplaceFollow: true,
                    isMutipleCheck: true,
                    isSelectAllEmployee: true,

                    /**
                    * @param dataList: list employee returned from component.
                    * Define how to use this list employee by yourself in the function's body.
                    */
                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                        self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
                        }));
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        self.showinfoSelectedEmployee(true);
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        self.selectedEmployee(dataEmployee);
                        self.employeeList([]);
                        self.employeeList.push(new UnitModel(data.employeeCode, data.employeeName, data.workplaceName, data.employeeId));
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                        self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
                        }));
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                        $('#component-items-list').ntsListComponent(self.dataList);
                        self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
                        }));
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataEmployee);
                        $('#component-items-list').ntsListComponent(self.dataEmployee);
                        self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode, item.employeeName, item.workplaceName, item.employeeId);
                        }));
                    }
                }

                self.tabs = ko.observableArray([
                    { id: 'tab-1', title: '年度', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab-2', title: '年月', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab-1');

                self.items = ko.observableArray([]);
                self.items2 = ko.observableArray([]);

                self.columns = ko.observableArray([
                    { headerText: '年度', key: 'year', width: 100 },
                    { headerText: 'エラー', key: 'error', width: 150 },
                    { headerText: 'アラーム', key: 'alarm', width: 150 }
                ]);
                self.currentCode = ko.observable();
                self.currentCode2 = ko.observable();

                self.selectedCode.subscribe(newValue => {
                    if (nts.uk.text.isNullOrEmpty(newValue)) return;
                    let data = $('#component-items-list').getDataList();
                    let employee = _.find(data, function(o) {
                        return o.code == self.selectedCode();
                    });
                    self.getDetail(employee.employeeId);
                    self.selectedId(employee.employeeId);
                    self.employeeName(employee.name);
                    let empSelect = _.find(self.items(), emp => {
                        return emp.code == newValue;
                    });
                    //if (empSelect) { self.currentEmpName(empSelect.name); }

                });

                self.selectedTab.subscribe(self.selectedCode => {
                    return self.getDetail(self.selectedCode);    
                });

            }

            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();

                dfd.resolve();
                return dfd.promise();
            }

            openDiaglog() {
                let self = this;
                let isYearMonth = true;
                if(self.selectedTab() == "tab-1" ){
                    isYearMonth = false;
                }
                setShared("KMK_008_PARAMS", { employeeId: self.selectedId(), employeeName: self.employeeName(), isYearMonth : isYearMonth });
                modal('../../../kmk/008/k/index.xhtml').onClosed(() => {
                    let data: string = getShared('KDL007_VALUES');
                    self.getDetail(data);
                });
            }

            getDetail(employmentCategoryCode: string) {
                var self = this;
                if (self.selectedTab() == "tab-1") {
                    service.getMonth(employmentCategoryCode).done(function(monthData: Array<model.MonthDto>) {
                        if (monthData) {
                            _.forEach(monthData, function(value) {
                                self.items([]);
                                self.items.push(new ItemModel(value.yearMonthValue, value.errorOneMonth, value.alarmOneMonth));
                            });

                        } else {
                            self.items([]);
                            self.items.push(new ItemModel("", "", ""));
                        }
                    });
                } else {
                    service.getYear(employmentCategoryCode).done(function(yearData: Array<model.YearDto>) {
                        if (yearData) {
                            _.forEach(yearData, function(value) {
                                self.items2([]);
                                self.item2.push(new ItemModel(value.yearValue, value.errorOneYear, value.alarmOneYear));
                            });

                        } else {
                            self.items2([]);
                            self.items2.push(new ItemModel("", "", ""));
                        }
                    });
                }
            }

            setNewMode() {
                var self = this;
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
