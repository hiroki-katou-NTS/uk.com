module kcp009.a.viewmodel {
    import ComponentOption = kcp009.viewmodel.ComponentOption;
    import EmployeeModel = kcp009.viewmodel.EmployeeModel;
    import SystemType = kcp009.viewmodel.SystemType;
    
    export class ScreenModel {
        empList: KnockoutObservableArray<any>;
        systemType: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        
        selectedItem: KnockoutObservable<string>;
        empDisplayCode: KnockoutObservable<string>;
        empBusinessName: KnockoutObservable<string>;
        selectedNumberOfPeople: KnockoutObservable<string>;
        selectedOrdinalNumber: KnockoutObservable<number>;
        organizationDesignation: KnockoutObservable<string>;
        organizationName: KnockoutObservable<string>;
        keySearch: KnockoutObservable<string>;
        
        listComponentOption: ComponentOption;
        
        constructor() {
            var self = this;
            self.empList = ko.observableArray([{id: '01', name: 'name1', businessName: 'BusinessName1', workplaceName: 'workplaceName1'}, 
            {id: '02', name: 'name2', businessName: 'BusinessName2', workplaceName: 'workplaceName2'}]);
            self.systemType = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(true);
            self.selectedItem = ko.observable(null);
            self.empDisplayCode = ko.observable('empcode');
            self.empBusinessName = ko.observable("Name");
            self.selectedOrdinalNumber = ko.observable(0);
            self.organizationName = ko.observable(null);
            self.selectedNumberOfPeople = ko.observable(null);
            self.keySearch = ko.observable(null);
            
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemType(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.empList
            };
        }
        
        
    }
     

}
