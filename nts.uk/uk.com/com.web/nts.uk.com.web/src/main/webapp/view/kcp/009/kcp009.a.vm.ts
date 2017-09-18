module kcp009.a.viewmodel {
    import ComponentOption = kcp009.viewmodel.ComponentOption;
    import EmployeeModel = kcp009.viewmodel.EmployeeModel;
    import SystemType = kcp009.viewmodel.SystemType;
    
    export class ScreenModel {
        empList: KnockoutObservableArray<EmployeeModel>;
        systemType: KnockoutObservable<number>;
        isDisplayOrganizationName: KnockoutObservable<boolean>;
        targetBtnText: string;

        systemReferenceList: KnockoutObservableArray<any>;
        selectedSystem: KnockoutObservable<number>;
        
        listComponentOption: ComponentOption;
        selectedItem: KnockoutObservable<string>;
        tabindex: number;
        constructor() {
            let self = this;
//            self.empList = ko.observableArray([]);
            self.empList = ko.observableArray([
            {id: '000426a2-181b-4c7f-abc8-6fff9f4f983a', code: '1234567890BA', businessName: 'I', workplaceName: 'Webメニューの設定', depName: 'Dep Name'}, 
            {id: '90000000-0000-0000-0000-000000000001', code: '1234567890AE', businessName: 'A', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000002', code: '000000000005', businessName: 'B', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000003', code: '000000000006', businessName: 'C', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000004', code: '000000000007', businessName: 'D', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000005', code: '000000000008', businessName: 'E', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000006', code: '000000000009', businessName: 'F', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000007', code: '000000000010', businessName: 'G', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000008', code: '000000000011', businessName: 'H', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000010', code: '1234567890AD', businessName: '日通　純一郎2', workplaceName: 'Webメニューの設定', depName: 'Dep Name'},
            {id: '90000000-0000-0000-0000-000000000011', code: '1234567890AC', businessName: '日通　純一郎3', workplaceName: 'Webメニューの設定', depName: 'Dep Name'}]);
            self.systemType = ko.observable(SystemType.EMPLOYMENT);
            self.isDisplayOrganizationName = ko.observable(true);
            self.isDisplayOrganizationName.subscribe(function(value: boolean) {
                self.reloadComponent();
            });
            self.targetBtnText = nts.uk.resource.getText("KCP009_3");
            self.selectedItem = ko.observable(null);
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
            self.tabindex = 1;
            
            // Initial listComponentOption
            self.listComponentOption = {
                systemReference: self.systemType(),
                isDisplayOrganizationName: self.isDisplayOrganizationName(),
                employeeInputList: self.empList,
                targetBtnText: self.targetBtnText,
                selectedItem: self.selectedItem,
                tabIndex: self.tabindex
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
            $('#emp-component').ntsLoadListComponent(self.listComponentOption);
        }
        
//        private findAllEmp(): JQueryPromise<any> {
//            let self = this;
//            let dfd = $.Deferred<void>();
//            service.findAllEmployee().done(function(data: Array<service.model.EmployeeSearchData>) {
//                let empList = [];
//                data.forEach(function(item) {
//                    empList.push({id: item.employeeId, code: item.employeeCode, businessName: item.businessName, 
//                    workplaceName: item.workplaceName, depName: 'Dep Name'});
//                });
//                self.empList(empList);
//            });
//        }
        
    }
    
    /**
     * Module Service
     */
    export module service {
        var paths: any = {
            findAllEmployee: 'basic/organization/employee/allemployee'

        }

        export function findAllEmployee(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllEmployee, new Date());
        }

        /**
         * Module Model
         */
        export module model {
            export class EmployeeSearchData {
                employeeId: string;
                employeeCode: string;
                businessName: string;
                workplaceCode: string;
                workplaceId: string;
                workplaceName: string;
            }
        }
    }
     

}
