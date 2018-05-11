module nts.uk.com.view.cmm048.a {

    import MainDto = nts.uk.com.view.cmm048.a.model.MainDto;
    import PasswordPolicyDto = nts.uk.com.view.cmm048.a.model.PasswordPolicyDto;
    import ComplexityDto = nts.uk.com.view.cmm048.a.model.ComplexityDto;
    import UseContactSettingDto = nts.uk.com.view.cmm048.a.model.UseContactSettingDto;
    
    export module viewmodel {

        export class ScreenModel {
            
            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            tab1Visible: KnockoutObservable<boolean>;
            tab2Visible: KnockoutObservable<boolean>;
            selectedTab: KnockoutObservable<string>;
            
            mainModel: MainModel;
            
            constructor() {
                let _self = this;
                
                _self.tab1Visible = ko.observable(true);
                _self.tab2Visible = ko.observable(true);
                _self.tabs = ko.observableArray([
                    {id: 'tab-1', title: nts.uk.resource.getText("CMM048_4"), content: '.tab-content-1', enable: ko.observable(true), visible: _self.tab1Visible},
                    {id: 'tab-2', title: nts.uk.resource.getText("CMM048_5"), content: '.tab-content-2', enable: ko.observable(true), visible: _self.tab2Visible}
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
                
//                service.getAllEnum().done((data: any) => {
//                    data.selfEditSetting.forEach((item: any, index: number) => {
//                        _self.selfEditSetOptions.push({ value: item.value, localizedName: item.fieldName });
//                    })
//                    dfd.resolve();
//                })
                
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
            listUserInfoUseMethod: Array<UserInfoUseMethodModel>;
            listUseContactSetting: Array<UseContactSettingModel>;
            
            selfEditSetOptions: KnockoutObservableArray<any>;
            
            constructor() {
                let _self = this;
                _self.employee = new EmployeeModel();
                _self.employeeInfoContact = new EmployeeInfoContactModel();
                _self.personContact = new PersonContactModel();
                _self.passwordPolicy = new PasswordPolicyModel();
                _self.listUserInfoUseMethod = [];
                _self.listUseContactSetting = [];
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
            isUse: KnockoutObservable<boolean>;
            complexity: ComplexityModel;
            lowestDigits: KnockoutObservable<number>;
            historyCount: KnockoutObservable<number>;
            validityPeriod: KnockoutObservable<number>;
            
            textLowestDigits: KnockoutObservable<string>;
            textHistoryCount: KnockoutObservable<string>;
            textValidityPeriod: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                _self.isUse = ko.observable(true);
                _self.complexity = new ComplexityModel();
                _self.lowestDigits = ko.observable(null);                
                _self.historyCount = ko.observable(null);
                _self.validityPeriod = ko.observable(null);                
                _self.textLowestDigits = ko.observable("");
                _self.textHistoryCount = ko.observable("");
                _self.textValidityPeriod = ko.observable("");
                
                _self.lowestDigits.subscribe((v) => _self.textLowestDigits(nts.uk.text.format(nts.uk.resource.getText("CMM048_13"), v)));
                _self.lowestDigits.extend({ notify: 'always' });
                _self.historyCount.subscribe((v) => _self.textHistoryCount(nts.uk.text.format(nts.uk.resource.getText("CMM048_19"), v)));
                _self.historyCount.extend({ notify: 'always' });
                _self.validityPeriod.subscribe((v) => _self.textValidityPeriod(nts.uk.text.format(nts.uk.resource.getText("CMM048_21"), v)));
                _self.validityPeriod.extend({ notify: 'always' });
                _self.lowestDigits(0);
                _self.historyCount(0);
                _self.validityPeriod(0);
            }
            
            updateData(dto: PasswordPolicyDto) {
                let _self = this;
                _self.isUse(dto.isUse);
                _self.lowestDigits(dto.lowestDigits);
                _self.complexity.updateData(dto.complexity);
                _self.historyCount(dto.historyCount);
                _self.validityPeriod(dto.validityPeriod);
            }
        }
        
        export class ComplexityModel {
            alphabetDigit: KnockoutObservable<number>;
            numberOfDigits: KnockoutObservable<number>;
            numberOfChar: KnockoutObservable<number>;
            
            textAlphabetDigit: KnockoutObservable<string>;
            textNumberOfDigits: KnockoutObservable<string>;
            textNumberOfChar: KnockoutObservable<string>;
            
            constructor() {
                let _self = this;
                _self.alphabetDigit = ko.observable(null);
                _self.numberOfDigits = ko.observable(null);
                _self.numberOfChar = ko.observable(null);               
                _self.textAlphabetDigit = ko.observable("");
                _self.textNumberOfDigits = ko.observable("");
                _self.textNumberOfChar = ko.observable("");
                    
                _self.alphabetDigit.subscribe((v) => _self.textAlphabetDigit(nts.uk.text.format(nts.uk.resource.getText("CMM048_15"), v)));
                _self.alphabetDigit.extend({ notify: 'always' });
                _self.numberOfDigits.subscribe((v) => _self.textNumberOfDigits(nts.uk.text.format(nts.uk.resource.getText("CMM048_16"), v)));
                _self.numberOfDigits.extend({ notify: 'always' });
                _self.numberOfChar.subscribe((v) => _self.textNumberOfChar(nts.uk.text.format(nts.uk.resource.getText("CMM048_17"), v)));
                _self.numberOfChar.extend({ notify: 'always' });
                _self.alphabetDigit(0);
                _self.numberOfDigits(0);
                _self.numberOfChar(0);
            }
            
            updateData(dto: ComplexityDto) {
                let _self = this;
                _self.alphabetDigit(dto.alphabetDigit);
                _self.numberOfDigits(dto.numberOfDigits);
                _self.numberOfChar(dto.numberOfChar);
            }
        }
        
        export class UserInfoUseMethodModel {
            settingItem: KnockoutObservable<number>;
            selfEdit: KnockoutObservable<number>;
            settingUseMail: KnockoutObservable<number>;
            isNotUse: boolean;
            isUse: boolean;          
            isPersonal: boolean;
            enableEdit: boolean;
            
            constructor() {
                let _self = this;
                _self.settingItem = ko.observable(0);
                _self.selfEdit = ko.observable(null);
                _self.settingUseMail = ko.observable(null);
                
                _self.selfEdit.subscribe((v) => {
                    switch (v) {
                        case 0:
                            _self.enableEdit = false;
                        break;
                        case 1:
                            _self.enableEdit = true;
                        break;
                        default:
                            _self.enableEdit = false;
                    }      
                });
                _self.selfEdit(0);
                _self.settingUseMail.subscribe((v) => {
                    switch (v) {
                        case 0:
                            _self.isNotUse = true;
                            _self.isUse = false;                           
                            _self.isPersonal = false;
                        break;
                        case 1:
                            _self.isNotUse = false;
                            _self.isUse = true;                           
                            _self.isPersonal = false;
                        break;
                        case 2:
                            _self.isNotUse = false;
                            _self.isUse = false;                           
                            _self.isPersonal = true;
                        break;
                        default:
                            _self.isNotUse = false;
                            _self.isUse = false;                           
                            _self.isPersonal = false;
                    }      
                });
                _self.settingUseMail(2);
            }
        }
        
        export class UseContactSettingModel {
            employeeId: KnockoutObservable<string>;
            settingItem: KnockoutObservable<number>;
            useMailSetting: KnockoutObservable<boolean>;
            
            constructor() {
                let _self = this;
                _self.employeeId = ko.observable("");
                _self.settingItem = ko.observable(0);
                _self.useMailSetting = ko.observable(true);
            }
            
            updateData(dto: UseContactSettingDto) {
                let _self = this;
                _self.employeeId(dto.employeeId);
                _self.settingItem(dto.settingItem);
                _self.useMailSetting(dto.useMailSetting);
            }
        }
    }
}