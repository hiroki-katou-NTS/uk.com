module nts.uk.at.view.ksm005.c {

    
    export module viewmodel {

        export class ScreenModel {
            ccgcomponent: any;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;

            // Options
            baseDate: KnockoutObservable<Date>;
            selectedEmployee: KnockoutObservableArray<any>;


            constructor() {
                var self = this;
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

                    onSearchAllClicked: function(dataList: any[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onSearchOnlyClicked: function(data: any) {
                        self.showinfoSelectedEmployee(true);
                        var dataEmployee: any[] = [];
                        dataEmployee.push(data);
                        self.selectedEmployee(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: any[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onSearchWorkplaceChildClicked: function(dataList: any[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onApplyEmployee: function(dataEmployee: any[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataEmployee);
                    }

                }

                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            }
        }

    }

}