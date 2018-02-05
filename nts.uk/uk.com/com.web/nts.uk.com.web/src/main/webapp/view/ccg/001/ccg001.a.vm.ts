module nts.uk.com.view.ccg001.a {  

    import EmployeeSearchDto = nts.uk.com.view.ccg.share.ccg.service.model.EmployeeSearchDto;
    import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
    export module viewmodel {
        export class ScreenModel {
           
            ccgcomponent: GroupOption;
            selectedCode: KnockoutObservableArray<string>;
            showinfoSelectedEmployee: KnockoutObservable<boolean>;

            // Options
            isQuickSearchTab: KnockoutObservable<boolean>;
            isAdvancedSearchTab: KnockoutObservable<boolean>;
            isAllReferableEmployee: KnockoutObservable<boolean>;
            isOnlyMe: KnockoutObservable<boolean>;
            isEmployeeOfWorkplace: KnockoutObservable<boolean>;
            isEmployeeWorkplaceFollow: KnockoutObservable<boolean>;
            isMutipleCheck: KnockoutObservable<boolean>;
            isSelectAllEmployee: KnockoutObservable<boolean>;
            periodStartDate: KnockoutObservable<Date>;
            periodEndDate: KnockoutObservable<Date>;
            baseDate: KnockoutObservable<Date>;                
            selectedEmployee: KnockoutObservableArray<EmployeeSearchDto>;

            constructor() {
                var self = this;
                self.selectedCode = ko.observableArray([]);
                self.selectedEmployee = ko.observableArray([]);
                self.showinfoSelectedEmployee = ko.observable(false);
                
                // Options
                self.isQuickSearchTab = ko.observable(true);
                self.isAdvancedSearchTab = ko.observable(true);
                self.isAllReferableEmployee = ko.observable(true);
                self.isOnlyMe = ko.observable(true);
                self.isEmployeeOfWorkplace = ko.observable(true);
                self.isEmployeeWorkplaceFollow = ko.observable(true);
                self.isMutipleCheck = ko.observable(true);
                self.isSelectAllEmployee = ko.observable(true);
                self.baseDate = ko.observable(new Date());
                self.periodStartDate = ko.observable(new Date());
                self.periodEndDate = ko.observable(new Date());
                
                // Init component.
                self.applyView();
                
                self.isQuickSearchTab.subscribe(function(){
                   self.applyView(); 
                });
                
                self.isAdvancedSearchTab.subscribe(function(){
                    self.applyView();    
                });
                
                self.isAllReferableEmployee.subscribe(function() {
                    self.applyView();
                });
                
                
                self.isOnlyMe.subscribe(function(){
                    self.applyView();   
                });
                
                self.isEmployeeOfWorkplace.subscribe(function(){
                   self.applyView(); 
                });
                
                self.isEmployeeWorkplaceFollow.subscribe(function(){
                   self.applyView(); 
                });
                
                self.isMutipleCheck.subscribe(function(){
                    self.applyView();
                });
                
                self.isSelectAllEmployee.subscribe(function(){
                   self.applyView(); 
                });
            }

            /**
             * apply view
             */
            public applyView(): void {
                var self = this;
                self.baseDate(new Date());
                self.ccgcomponent = {
                    showClosure: true,
                    baseDate: self.baseDate,
                    periodStartDate: self.baseDate,
                    periodEndDate: self.baseDate,
                    isQuickSearchTab: self.isQuickSearchTab(),
                    isAdvancedSearchTab: self.isAdvancedSearchTab(),
                    isAllReferableEmployee: self.isAllReferableEmployee(),
                    isOnlyMe: self.isOnlyMe(),
                    isEmployeeOfWorkplace: self.isEmployeeOfWorkplace(),
                    isEmployeeWorkplaceFollow: self.isEmployeeWorkplaceFollow(),
                    isMutipleCheck: self.isMutipleCheck(),
                    isSelectAllEmployee: self.isSelectAllEmployee(),
                    onSearchAllClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        self.showinfoSelectedEmployee(true);
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        
                        
                        self.selectedEmployee(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },

                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataList);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployee(dataEmployee);
                    }

                } 
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            }
            
            
        }
    }
}