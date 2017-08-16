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
//        empAmount: KnockoutObservable<number>;
        organizationDesignation: KnockoutObservable<string>;
        organizationName: KnockoutObservable<string>;
        isActivePreviousBtn: KnockoutObservable<boolean>;
        isActiveNextBtn: KnockoutObservable<boolean>;   
        isActivePersonalProfile: KnockoutObservable<boolean>;
        keySearch: KnockoutObservable<string>;
        
        
        constructor() {
            this.empList = ko.observableArray([]);
            this.selectedItem = ko.observable(null);
            this.empDisplayCode = ko.observable(null);
            this.empBusinessName = ko.observable(null);
            this.organizationDesignation = ko.observable(null);
//            this.selectedNumberOfPeople = ko.observable("0 / 0");
            this.selectedOrdinalNumber = ko.observable(0);
//            this.empAmount = ko.observable(0);
            this.organizationName = ko.observable(null);
            this.isActivePersonalProfile = ko.computed(function() {
                return this.empList().length > 0;
            }, this);
            this.isActivePreviousBtn = ko.computed(function() {
                return (this.empList().length > 0) && this.selectedOrdinalNumber() > 0;
            }, this);
            this.isActiveNextBtn = ko.computed(function() {
                return (this.empList().length > 0) && this.selectedOrdinalNumber() <= this.empList().length;
            }, this);
            this.selectedNumberOfPeople = ko.computed(function() {
                return this.selectedOrdinalNumber().toString() + "/" + this.empList().length.toString();
            });
            this.keySearch = ko.observable(null);
        }
        
        // Initialize Component
        public init($input: JQuery, data: ComponentOption) :JQueryPromise<void> {
            var dfd = $.Deferred<void>();
            ko.cleanNode($input[0]);
            var self = this;
            
            self.systemType = data.systemReference;
            self.isDisplayOrganizationName = data.isDisplayOrganizationName;
            if (data.systemReference == SystemType.EMPLOYMENT) {
                self.organizationDesignation(nts.uk.resource.getText("Com_Workplace"));
//                self.organizationDesignation("Workplace");
            } else {
                self.organizationDesignation(nts.uk.resource.getText("Com_Department"));   
//                self.organizationDesignation("Department"); 
            }
            self.empList(data.employeeInputList());
            
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/kcp/009/kcp009.xhtml').serialize();
            $input.load(webserviceLocator, function() {
                ko.applyBindings(self, $input[0]);

                dfd.resolve();
            });
            
            return dfd.promise();
        }
    }
    
    export interface ComponentOption {
        systemReference: SystemType;
        isDisplayOrganizationName: boolean;
        employeeInputList?: KnockoutObservableArray<EmployeeModel>;
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
    
    
    export module service {
        
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