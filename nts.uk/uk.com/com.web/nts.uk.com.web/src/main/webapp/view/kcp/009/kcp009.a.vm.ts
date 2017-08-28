module kcp009.a.viewmodel {
    import ComponentOption = kcp009.viewmodel.ComponentOption;
    import EmployeeModel = kcp009.viewmodel.EmployeeModel;
    import SystemType = kcp009.viewmodel.SystemType;
    
    export class ScreenModel {
        empList: KnockoutObservableArray<EmployeeModel>;
        systemType: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;
        
        empDisplayCode: KnockoutObservable<string>;
        empBusinessName: KnockoutObservable<string>;
        selectedNumberOfPeople: KnockoutObservable<string>;
        organizationName: KnockoutObservable<string>;
        keySearch: KnockoutObservable<string>;
        systemReferenceList: KnockoutObservableArray<any>;
        selectedSystem: KnockoutObservable<number>;
        
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        constructor() {
            let self = this;
//            self.empList = ko.observableArray([]);
            self.empList = ko.observableArray([
            {id: '01', code: 'A000000000001', businessName: '日通　純一郎1', workplaceName: '名古屋支店', depName: 'Dep Name'}, 
            {id: '04', code: 'A000000000004', businessName: '日通　純一郎4', workplaceName: '名古屋支店', depName: 'Dep Name'},
            {id: '02', code: 'A000000000002', businessName: '日通　純一郎2', workplaceName: '名古屋支店', depName: 'Dep Name'},
            {id: '03', code: 'A000000000003', businessName: '日通　純一郎3', workplaceName: '名古屋支店', depName: 'Dep Name'}]);
            self.systemType = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(true);
            self.isDisplayOrganizationName.subscribe(function(value: boolean) {
                self.reloadComponent();
            });
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.empDisplayCode = ko.observable('empcode');
            self.empBusinessName = ko.observable("Name");
            self.organizationName = ko.observable(null);
            self.selectedItem = ko.observable(null);
            self.selectedNumberOfPeople = ko.observable(null);
            self.keySearch = ko.observable(null);
            self.systemReferenceList = ko.observableArray([
                { code: 1, name: 'Employment System' },
                { code: 2, name: 'Other Systems' },
            ]);
            self.selectedSystem = ko.observable(1);
            self.selectedSystem.subscribe(function(value: number) {
                if (value == 1) {
                    self.systemType(SystemType.EMPLOYMENT);
                } else {
                    // Other System Type
                    self.systemType(SystemType.SALARY);
                }
                // Reload Component
                self.reloadComponent();
            });
//            self.isShowProfile = ko.computed(function() {
//                return self.empList().length > 0;
//            });
            
            
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemType(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.empList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem
            };
            
            
            
        }
        
        // Reload component Method
        private reloadComponent() {
            let self = this;
            self.listComponentOption.systemReference = self.systemType();
            self.listComponentOption.isDisplayOrganizationName = self.isDisplayOrganizationName();
            self.listComponentOption.targetBtnText = self.targetBtnText;
            self.listComponentOption.employeeInputList(self.empList());
            // Load listComponent
            $('#emp-list').ntsLoadListComponent(self.listComponentOption);
        }
    }
     

}
