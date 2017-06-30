module nts.uk.com.view.ccg001.a {  

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import PersonModel = nts.uk.com.view.ccg.share.ccg.service.model.PersonModel;
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
            baseDate: KnockoutObservable<Date>;
            selectedEmployeeCode: KnockoutObservableArray<EmployeeSearchDto>;

            constructor() {
                var self = this;
                self.selectedCode = ko.observableArray([]);
                self.selectedEmployeeCode = ko.observableArray([]);
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

            public startPage(): JQueryPromise<any> {
                let dfd = $.Deferred<any>();
                dfd.resolve();
                return dfd.promise();
            }
            
            public applyView(): void {
                var self = this;
                self.ccgcomponent = {
                    baseDate: self.baseDate,
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
                        self.selectedEmployeeCode(dataList);
                    },
                    onSearchOnlyClicked: function(data: EmployeeSearchDto) {
                        self.showinfoSelectedEmployee(true);
                        var dataEmployee: EmployeeSearchDto[] = [];
                        dataEmployee.push(data);
                        
                        
                        self.selectedEmployeeCode(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployeeCode(dataList);
                    },

                    onSearchWorkplaceChildClicked: function(dataList: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployeeCode(dataList);
                    },
                    onApplyEmployee: function(dataEmployee: EmployeeSearchDto[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployeeCode(dataEmployee);
                    }

                } 
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            }
            
            
        }
    }
}