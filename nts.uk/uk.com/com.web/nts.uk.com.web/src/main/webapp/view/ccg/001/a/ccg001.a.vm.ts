module nts.uk.com.view.ccg001.a {  

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import PersonModel = nts.uk.com.view.ccg.share.ccg.service.model.PersonModel;
    export module viewmodel {
        export class ScreenModel {
           
            ccgcomponent: any;
            personinfo: any;
            selectedCode: KnockoutObservableArray<string>;
            visiblePersonInfo: KnockoutObservable<boolean>;
            personLoginInfo: PersonModel;

            // Options
            isQuickSearchTab: KnockoutObservable<boolean>;
            isAdvancedSearchTab: KnockoutObservable<boolean>;
            isAllReferableEmployee: KnockoutObservable<boolean>;
            isOnlyMe: KnockoutObservable<boolean>;
            isEmployeeOfDepartment: KnockoutObservable<boolean>;
            isEmployeeDepartmentFollow: KnockoutObservable<boolean>;
            isMutipleCheck: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.selectedCode = ko.observableArray([]);
                self.personLoginInfo = new PersonModel();

                // Options
                self.isQuickSearchTab = ko.observable(true);
                self.isAdvancedSearchTab = ko.observable(true);
                self.isAllReferableEmployee = ko.observable(true);
                self.isOnlyMe = ko.observable(true);
                self.isEmployeeOfDepartment = ko.observable(true);
                self.isEmployeeDepartmentFollow = ko.observable(true);
                self.isMutipleCheck = ko.observable(true);

                // Init component.
                self.ccgcomponent = {
                    isQuickSearchTab: self.isQuickSearchTab(),
                    isAdvancedSearchTab: self.isAdvancedSearchTab(),
                    isAllReferableEmployee: self.isAllReferableEmployee(),
                    isOnlyMe: self.isOnlyMe(),
                    isEmployeeOfDepartment: self.isEmployeeOfDepartment(),
                    isEmployeeDepartmentFollow: self.isEmployeeDepartmentFollow(),
                    isMutipleCheck: self.isMutipleCheck(),
                    onSearchAllClicked: function(dataList: PersonModel[]) {
                        self.personinfo = {
                            isShowAlreadySet: false,
                            isMultiSelect: self.ccgcomponent.isMutipleCheck,
                            listType: ListType.EMPLOYEE,
                            employeeInputList: new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().toUnitModelList(dataList),
                            selectType: SelectType.SELECT_BY_SELECTED_CODE,
                            selectedCode: self.selectedCode,
                            isDialog: false,
                            isShowNoSelectRow: false,
                        }
                        $('#personinfo').ntsListComponent(self.personinfo);
                    },
                    onSearchOnlyClicked: function(data: PersonModel){
                        self.personLoginInfo = data;
                        self.visiblePersonInfo(true);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: PersonModel[]){
                          self.personinfo = {
                            isShowAlreadySet: false,
                            isMultiSelect: self.ccgcomponent.isMutipleCheck,
                            listType: ListType.EMPLOYEE,
                            employeeInputList: new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().toUnitModelList(dataList),
                            selectType: SelectType.SELECT_BY_SELECTED_CODE,
                            selectedCode: self.selectedCode,
                            isDialog: false,
                            isShowNoSelectRow: false,
                        }
                        $('#personinfo').ntsListComponent(self.personinfo);  
                    }
                }
                self.visiblePersonInfo = ko.observable(false);
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
               
            }

            public startPage(): JQueryPromise<any> {
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
        }
    }
}