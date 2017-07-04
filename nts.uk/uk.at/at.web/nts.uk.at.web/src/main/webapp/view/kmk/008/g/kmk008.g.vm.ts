module nts.uk.at.view.kmk008.g {
    export module viewmodel {
        export class ScreenModel {
            tabs: KnockoutObservableArray<NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;

            items: KnockoutObservableArray<ItemModel>;
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;

            selectedCode: KnockoutObservable<string>

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
                        //self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode,item.employeeName, item.workplaceName);    
                        }));
                       
//                        let listComponentOption = {
//                            isMultiSelect: false,
//                            listType: 4,
//                            selectType: 1,
//                            selectedCode: self.selectedCode,
//                            isDialog: self.isDialog(),
//                            isShowNoSelectRow: self.isShowNoSelectRow(),
//                            isShowWorkPlaceName: true,
//                            employeeInputList:  self.employeeList,
//                        };
                        //$('#component-items-list').ntsListComponent(listComponentOption);
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        self.showinfoSelectedEmployee(true);
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        self.selectedEmployee(dataEmployee);
                        self.employeeList([]);
                        self.employeeList.push(new UnitModel(data.employeeCode,data.employeeName, data.workplaceName));
                        let listComponentOption = {
                            isMultiSelect: false,
                            listType: 4,
                            selectType: 1,
                            selectedCode: self.selectedCode,
                            isDialog: self.isDialog(),
                            isShowNoSelectRow: self.isShowNoSelectRow(),
                            isShowWorkPlaceName: true,
                            employeeInputList:  self.employeeList,
                        };
                       // $('#component-items-list').ntsListComponent(listComponentOption);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                        self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode,item.employeeName, item.workplaceName);    
                        }));
                       
                        let listComponentOption = {
                            isMultiSelect: false,
                            listType: 4,
                            selectType: 1,
                            selectedCode: self.selectedCode,
                            isDialog: self.isDialog(),
                            isShowNoSelectRow: self.isShowNoSelectRow(),
                            isShowWorkPlaceName: true,
                            employeeInputList:  self.employeeList,
                        };
                       // $('#component-items-list').ntsListComponent(listComponentOption);
                    },
                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                        $('#component-items-list').ntsListComponent(self.dataList);
                        self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode,item.employeeName, item.workplaceName);    
                        }));
                       
                        let listComponentOption = {
                            isMultiSelect: false,
                            listType: 4,
                            selectType: 1,
                            selectedCode: self.selectedCode,
                            isDialog: self.isDialog(),
                            isShowNoSelectRow: self.isShowNoSelectRow(),
                            isShowWorkPlaceName: true,
                            employeeInputList:  self.employeeList,
                        };
                        //$('#component-items-list').ntsListComponent(listComponentOption);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataEmployee);
                        $('#component-items-list').ntsListComponent(self.dataEmployee);
                        self.employeeList([]);
                        self.employeeList(_.map(dataList, item => {
                            return new UnitModel(item.employeeCode,item.employeeName, item.workplaceName);    
                        }));
                       
                        let listComponentOption = {
                            isMultiSelect: false,
                            listType: 4,
                            selectType: 1,
                            selectedCode: self.selectedCode,
                            isDialog: self.isDialog(),
                            isShowNoSelectRow: self.isShowNoSelectRow(),
                            isShowWorkPlaceName: true,
                            employeeInputList:  self.employeeList,
                        };
                        //$('#component-items-list').ntsListComponent(listComponentOption);
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
                    self.getDetail(newValue);
                    let empSelect = _.find(self.items(), emp => {
                        return emp.code == newValue;
                    });
                    //if (empSelect) { self.currentEmpName(empSelect.name); }

                });

            }
            startPage(): JQueryPromise<any> {
                let self = this;
                let dfd = $.Deferred();


//                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
//                .done(function(){
//                    self.ccgcomponent.onSearchAllClicked();   
//                });
//                $('#component-items-list').ntsListComponent(listComponentOption);
//                $('#component-items-list').ntsListComponent(self.listComponentOption);

                dfd.resolve();
                return dfd.promise();
            }

            getDetail(employmentCategoryCode: string) {
                var self = this;
                if (self.selectedTab() == "tab-2") {
                    service.getMonth(employmentCategoryCode).done(function(monthData: Array<model.MonthDto>) {
                        if (monthData) {
                            self.items2.push(new ItemModel(monthData.yearMonthValue, monthData.errorOneMonth, monthData.alarmOneMonth));
                        } else {
                            self.items2.push(new ItemModel("", "", ""));
                        }
                    });
                } else {
                    service.getYear(employmentCategoryCode).done(function(yearData: Array<model.YearDto>) {
                        if (yearData) {
                            self.item.push(new ItemModel(yearData.yearValue, yearData.errorOneYear, yearData.alarmOneYear));
                        } else {
                            self.items.push(new ItemModel("", "", ""));
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
            constructor(code: string, name: string, workplaceName: string) {
                this.code = code;
                this.name = name;
                this.workplaceName = workplaceName;
            }
        }

    }
}
