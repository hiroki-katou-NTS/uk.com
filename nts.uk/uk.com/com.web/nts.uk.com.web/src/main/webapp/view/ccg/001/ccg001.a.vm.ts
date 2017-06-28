module nts.uk.com.view.ccg001.a {  

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import UnitModel = kcp.share.list.UnitModel;
    import PersonModel = nts.uk.com.view.ccg.share.ccg.service.model.PersonModel;
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
            baseDate: KnockoutObservable<Date>;
            selectedEmployeeCode: KnockoutObservableArray<string>;

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
                    onSearchAllClicked: function(dataList: PersonModel[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployeeCode(new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().toPersonCodeList(dataList));
                    },
                    onSearchOnlyClicked: function(data: PersonModel) {
                        self.showinfoSelectedEmployee(true);
                        var dataEmployee: string[] = [];
                        dataEmployee.push(data.personId);
                        self.selectedEmployeeCode(dataEmployee);
                    },
                    onSearchOfWorkplaceClicked: function(dataList: PersonModel[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployeeCode(new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().toPersonCodeList(dataList));
                    },

                    onSearchWorkplaceChildClicked: function(dataList: PersonModel[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployeeCode(new nts.uk.com.view.ccg.share.ccg.viewmodel.ListGroupScreenModel().toPersonCodeList(dataList));
                    },
                    onApplyEmployee: function(dataEmployee: string[]) {
                        self.showinfoSelectedEmployee(true);
                        self.selectedEmployeeCode(dataEmployee);
                    }

                } 
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent);
            }
            
            
        }
    }
}