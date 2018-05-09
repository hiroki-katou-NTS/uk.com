module nts.uk.com.view.cmm048.a {

    import MainDto = nts.uk.com.view.cmm048.a.model.MainDto;
    
    export module viewmodel {

        export class ScreenModel {
            
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            
            mainModel: MainModel;
            
            constructor() {
                let _self = this;
                _self.tabs = ko.observableArray([
                    {id: 'tab-1', title: nts.uk.resource.getText("CMM048_4"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true)},
                    {id: 'tab-2', title: nts.uk.resource.getText("CMM048_5"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true)}
                ]);
                _self.selectedTab = ko.observable('tab-1');
                _self.mainModel = new MainModel();              
            }
            
            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                

                
                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * Set focus
             */
            public setInitialFocus(): void {
                $('button-save').focus();
            }
            
            /**
             * Save
             */
            public save() {
                
            }
            
        }
        
        export class MainModel {
            employee: EmployeeModel;
            employeeInfoContact: EmployeeInfoContactModel;
            personContact: PersonContactModel;
            passwordPolicy: PasswordPolicyModel;
            
            constructor() {
                let _self = this;
                _self.employee = new EmployeeModel();
                _self.employeeInfoContact = new EmployeeInfoContactModel();
                _self.personContact = new PersonContactModel();
                _self.passwordPolicy = new PasswordPolicyModel();
            }
        }
        
        export class EmployeeModel {
            employeeId: KnockoutObservable<string>;
            employeeCode: KnockoutObservable<string>;
            employeeName: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                _self.employeeId = ko.observable("");
                _self.employeeCode = ko.observable("");
                _self.employeeName = ko.observable("");
            }
        }
        
        export class EmployeeInfoContactModel {
            employeeId: KnockoutObservable<string>;
            mailAddress: KnockoutObservable<string>;
            mobileMailAddress: KnockoutObservable<string>;
            cellPhoneNo: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                _self.employeeId = ko.observable("");
                _self.mailAddress = ko.observable("emp@pcmail.com");
                _self.mobileMailAddress = ko.observable("emp@mobilemail.com");
                _self.cellPhoneNo = ko.observable("080-XXXX-XXXX");
            }
        }
        
        export class PersonContactModel {
            personId: KnockoutObservable<string>;
            mailAddress: KnockoutObservable<string>;
            mobileMailAddress: KnockoutObservable<string>;
            cellPhoneNo: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                _self.personId = ko.observable("");
                _self.mailAddress = ko.observable("person@pcmail.com");
                _self.mobileMailAddress = ko.observable("person@mobilemail.com");
                _self.cellPhoneNo = ko.observable("090-XXXX-XXXX");
            }
        }
        
        export class PasswordPolicyModel {
            lowestDigits: KnockoutObservable<number>;
            complexity: ComplexityModel;
            historyCount: KnockoutObservable<number>;
            validityPeriod: KnockoutObservable<number>;
            
            constructor() {
                let _self = this;
                _self.lowestDigits = ko.observable(0);
                _self.complexity = new ComplexityModel();
                _self.historyCount = ko.observable(0);
                _self.validityPeriod = ko.observable(0);
            }
        }
        
        export class ComplexityModel {
            alphabetDigit: KnockoutObservable<number>;
            numberOfDigits: KnockoutObservable<number>;
            numberOfChar: KnockoutObservable<number>;
            
            constructor() {
                let _self = this;
                _self.alphabetDigit = ko.observable(0);
                _self.numberOfDigits = ko.observable(0);
                _self.numberOfChar = ko.observable(0);
            }
        }
    }
}