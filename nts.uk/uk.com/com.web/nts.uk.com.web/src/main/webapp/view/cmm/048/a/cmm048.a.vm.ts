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
            
            public save() {
                
            }
            
        }
        
        export class MainModel {
            employeeName: KnockoutObservable<string>;
            passwordPolicy: PasswordPolicyModel;
            
            constructor() {
                let _self = this;
                _self.employeeName = ko.observable("Tung");
                _self.passwordPolicy = new PasswordPolicyModel();
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