module kcp009.viewmodel {
    
    export class ScreenModel {
        empList: KnockoutObservableArray<EmployeeModel>;
        systemType: SystemType;
        isDisplayOrganizationName: boolean;
        
        selectedItem: KnockoutObservable<string>;
        empDisplayCode: KnockoutObservable<string>;
        empBusinessName: KnockoutObservable<string>;
        selectedNumberOfPeople: KnockoutObservable<string>;
        selectedOrdinalNumber: KnockoutObservable<number>;
        organizationDesignation: KnockoutObservable<string>;
        organizationName: KnockoutObservable<string>;
        isActivePreviousBtn: KnockoutObservable<boolean>;
        isActiveNextBtn: KnockoutObservable<boolean>;   
        isActivePersonalProfile: KnockoutObservable<boolean>;
        keySearch: KnockoutObservable<string>;
        gridColumns: KnockoutObservableArray<any>;
        isDisplay: KnockoutObservable<boolean>;
        gridStyle: GridStyle;
        maxRows: number;
        
        constructor() {
            var self = this;
            self.empList = ko.observableArray([]);
            self.selectedItem = ko.observable(null);
            self.selectedItem.subscribe(function(data: string) {
                if (data) {
                    var existItem = self.empList().filter((item) => {
                        return item.id == self.selectedItem();
                    })[0];
                    if (existItem) {
                        self.empDisplayCode(existItem.code);
                        self.empBusinessName(existItem.businessName);
                    }
                }
            });
            self.empDisplayCode = ko.observable(null);
            self.empBusinessName = ko.observable(null);
            self.organizationDesignation = ko.observable(null);
            self.selectedOrdinalNumber = ko.computed(function() {
                var existItem = self.empList().filter((item) => {
                    return item.id == self.selectedItem();
                })[0];
                return self.empList().indexOf(existItem) + 1;
            });
            self.organizationName = ko.observable(null);
            self.isActivePersonalProfile = ko.computed(function() {
                return self.empList().length > 0;
            }, self);
            self.isActivePreviousBtn = ko.computed(function() {
                return (self.empList().length > 0) && self.selectedOrdinalNumber() > 1;
            }, self);
            self.isActiveNextBtn = ko.computed(function() {
                return (self.empList().length > 0) && self.selectedOrdinalNumber() < self.empList().length;
            }, self);
            self.selectedNumberOfPeople = ko.computed(function() {
                return self.selectedOrdinalNumber().toString() + "/" + self.empList().length.toString();
            });
            self.keySearch = ko.observable(null);
            self.gridColumns = ko.observableArray([
                { headerText: '', key: 'code', width: 150},
                { headerText: '', key: 'businessName', width: 170},
                { headerText: '', key: 'id', hidden: true},
                ]);
            
            self.isDisplay = ko.observable(true);
        }
        
        // Initialize Component
        public init($input: JQuery, data: ComponentOption) :JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            ko.cleanNode($input[0]);
            var self = this;
            
            self.maxRows = data.maxRows ? data.maxRows : 10;
//            var height = 21;
            self.gridStyle = {
                height: 21 * self.maxRows + 24
            };
            self.systemType = data.systemReference;
            self.empList(data.employeeInputList());
            self.isDisplayOrganizationName = data.isDisplayOrganizationName;
            if (data.systemReference == SystemType.EMPLOYMENT) {
                
                // Set Organization Designation if System Reference is Employment
                self.organizationDesignation(nts.uk.resource.getText("Com_Workplace"));
                
                // Set Organization name
                if (data.employeeInputList().length > 0) {
                    self.organizationName(data.employeeInputList()[0].workplaceName);
                } else {
                    self.organizationName(null);
                }
            } else {
                // Set Organization Designation if System Reference is others
                self.organizationDesignation(nts.uk.resource.getText("Com_Department"));
                
                // Set Organization name
                if (data.employeeInputList().length > 0) {
                    self.organizationName(data.employeeInputList()[0].depName);
                } else {
                    self.organizationName(null);
                }
            }
            
            // Set SelectedItem: First Item
            self.selectedItem(data.employeeInputList().length > 0 ? data.employeeInputList()[0].id : null);
            
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/009/kcp009.xhtml').serialize();
            $input.load(webserviceLocator, function() {
                ko.applyBindings(self, $input[0]);
                 // Add profile Icon
                var iconLink = nts.uk.request.location.siteRoot
                    .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                    .mergeRelativePath('/view/kcp/share/icon/7.png').serialize();
                $('#profile-icon').attr('style', "background: url('" + iconLink + "'); width: 30px; height: 30px; background-size: 30px 30px;")
                dfd.resolve();
            });
//            $(window).on('click', function(e) {
//                if (self.isDisplay()) {
//                        // Hide component.
//                        self.isDisplay(false);
//                        $('#items-list').hide();
//                    }
//            });
            return dfd.promise();
        }
        
        
        private showEmpList(): void {
            var self = this;
            $("#items-list").toggle();
//            if (self.isDisplay()) {
//                return;
//            }
//            $('#items-list').hide();
//            self.isDisplay(true);
        }
        
        private previousEmp(): void {
            var self = this;
            var existItem = self.empList().filter((item) => {
                return item.id == self.selectedItem();
            })[0];
            var nextId = self.empList()[self.empList().indexOf(existItem) - 1].id;
                self.selectedItem(nextId);
        }
        
        private nextEmp(): void {
            var self = this;
            var existItem = self.empList().filter((item) => {
                return item.id == self.selectedItem();
            })[0];
            var prevId = self.empList()[self.empList().indexOf(existItem) + 1].id;
                self.selectedItem(prevId);
        }
    }
    
    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList?: KnockoutObservableArray<EmployeeModel>;
        maxRows?: number;
    }
    
    
    export class SystemType {
        static EMPLOYMENT = 1;
        static SALARY = 2;
        static PERSONNEL = 3;
        static ACCOUNTING = 4;
        static OH = 6;
    }
    
    export interface EmployeeModel {
        id: string;
        code: string;
        businessName: string;
        depName?: string;
        workplaceName?: string;
    }
    
    export interface GridStyle {
        height: number;
    }
    
    export module service {
        var paths: any = {
            // 
            findEmployee: ''
        }
    }
}

interface JQuery {
    ntsListComponent(option: kcp009.viewmodel.ComponentOption): JQueryPromise<void>;
}

(function($: any) {
    $.fn.ntsListComponent = function(option: kcp009.viewmodel.ComponentOption): JQueryPromise<void> {

        // Return.
        return new kcp009.viewmodel.ScreenModel().init(this, option);
    }
    
} (jQuery));