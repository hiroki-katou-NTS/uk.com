module nts.uk.cloud.view.cld001.a {
    export module viewmodel {
        export class ScreenModel{

        	tenantCode: KnockoutObservable<string>;
        	startDate: KnockoutObservable<string>;
        	password: KnockoutObservable<string>;

        	tenantManagerID: KnockoutObservable<string>;
        	tenantManagerPassword: KnockoutObservable<string>;
        	companyName: KnockoutObservable<string>;

        	billingTypes: KnockoutObservableArray<any> = ko.observableArray([
                { code: 0, name: '従量制' },
                { code: 1, name: '定額制' }
            ]);
        	billingType: KnockoutObservable<number>;

        	useCheckDigit_tenantCode: KnockoutObservable<boolean>;
        	useCheckDigit_optionCode: KnockoutObservable<boolean>;
        	optionCode: KnockoutObservable<string>;

        	hrContractCode: KnockoutObservable<string>;

        	numberOfLicence_at: KnockoutObservable<number>;
        	numberOfLicence_hr: KnockoutObservable<number>;
        	numberOfLicence_pr: KnockoutObservable<number>;
        	numbereditor_at: any;
        	numbereditor_hr: any;
        	numbereditor_pr: any;

        	useSSO_SAML: KnockoutObservable<boolean>;

            constructor() {
            	this.tenantCode = ko.observable('');
            	this.useCheckDigit_tenantCode = ko.observable(true);
            	this.startDate = ko.observable('');
            	this.password = ko.observable('');
            	this.tenantManagerID = ko.observable('');
            	this.tenantManagerPassword = ko.observable('');
            	this.companyName = ko.observable('');

            	this.billingType = ko.observable(0);

            	this.optionCode = ko.observable('');
            	this.useCheckDigit_optionCode = ko.observable(true);

            	this.hrContractCode = ko.observable('');

            	this.numbereditor_at = {
            			numberOfLicence_at: ko.observable(0),
            			constraint: '',
            			option: new nts.uk.ui.option.NumberEditorOption({
            				grouplength: 3,
            				symbolChar: "人"
            			}),
            			enable: ko.observable(true),
            			readonly: ko.observable(false)
            		};
            	this.numbereditor_hr = {
            			numberOfLicence_hr: ko.observable(0),
            			constraint: '',
            			option: new nts.uk.ui.option.NumberEditorOption({
            				grouplength: 3,
            				symbolChar: "人"
            			}),
            			enable: ko.observable(true),
            			readonly: ko.observable(false)
            		};
            	this.numbereditor_pr = {
            			numberOfLicence_pr: ko.observable(0),
            			constraint: '',
            			option: new nts.uk.ui.option.NumberEditorOption({
            				grouplength: 3,
            				symbolChar: "人"
            			}),
            			enable: ko.observable(true),
            			readonly: ko.observable(false)
            		};
            	this.useSSO_SAML = ko.observable(true);
            }

            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();

                dfd.resolve();
                return dfd.promise();
            }

            generatePassword() {

            }

            regist() {

            }
        }
    }
}