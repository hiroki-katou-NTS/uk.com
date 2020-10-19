module nts.uk.cloud.view.cld001.a {
    export module viewmodel {
        export class ScreenModel{

        	tenantCode: KnockoutObservable<string>;
        	startDate: KnockoutObservable<string>;
        	password: KnockoutObservable<string>;

        	tenantManagerID: KnockoutObservable<string>;
        	tenantManagerPassword: KnockoutObservable<string>;
        	companyName: KnockoutObservable<string>;

        	contractType: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, name: '従量制' },
                { code: 1, name: '定額制' }
            ]);

        	useCheckDigit: KnockoutObservable<boolean>;
        	optionCode: KnockoutObservable<string>;

        	hrContractCode: KnockoutObservable<string>;

        	numberOfLicence1: KnockoutObservable<int>;
        	numberOfLicence2: KnockoutObservable<int>;
        	numberOfLicence3: KnockoutObservable<int>;

            constructor() {
            	this.tenantCode = ko.observable('');
            	this.startDate = ko.observable('');
            	this.password = ko.observable('');
            	this.tenantManagerID = ko.observable('');
            	this.tenantManagerPassword = ko.observable('');
            	this.companyName = ko.observable('');

            	this.contractType = ko.observable(0);

            	this.useCheckDigit = ko.observable(true);
            	this.optionCode = ko.observable('');

            	this.hrContractCode = ko.observable('');

            	this.numberOfLicence_at = ko.observable(0);
            	this.numberOfLicence_hr = ko.observable(0);
            	this.numberOfLicence_pr = ko.observable(0);
            }

            generatePassword() {

            }

            regist() {

            }
        }
    }
}