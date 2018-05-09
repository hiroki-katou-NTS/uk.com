module nts.uk.com.view.cmm049.a {

    export module viewmodel {

        export class ScreenModel {
            mailPcCompany: KnockoutObservableArray<any>;
            mailPcPersonal: KnockoutObservableArray<any>;
            mailPcCompanyPerson: KnockoutObservableArray<any>;
            mailPcPersonSetting: KnockoutObservableArray<any>;
            mailMobileCompany: KnockoutObservableArray<any>;
            mailMobileCompanySetting: KnockoutObservableArray<any>;
            mailMobilePersonal: KnockoutObservableArray<any>;
            mailMobilePersonalSetting: KnockoutObservableArray<any>;
            mobilePhoneCompany: KnockoutObservableArray<any>;
            mobilePhoneCompanySetting: KnockoutObservableArray<any>;
            password: KnockoutObservableArray<any>;
            selectedMailPcCom: any;
            selectedMailPcPerson: any;
            selectedMailPcPersonal: any;
            selectedMailPcPersonSetting: any;
            selectedMailMobileCompany: any;
            selectedMailMobileCompanySetting: any;
            selectedMailMobilePersonal: any;
            selectedMailMobilePersonalSetting: any;
            selectedMobilePhoneCompany: any;
            selectedMobilePhoneCompanySetting: any;
            selectedPassword: any;
            controlButton: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.mailPcCompany = ko.observableArray([
                    { code: 1, name: '利用する' },
                    { code: 2, name: '利用しない' },
                    { code: 3, name: '個人選択可' }
                ]);
                self.selectedMailPcCom = ko.observable(1);

                self.mailPcCompanyPerson = ko.observableArray([
                    { code: 1, name: '編集可' },
                    { code: 2, name: '編集不可' },
                ]);
                self.selectedMailPcPerson = ko.observable(1);

                self.mailPcPersonal = ko.observableArray([
                    { code: 1, name: '利用する' },
                    { code: 2, name: '利用しない' },
                    { code: 3, name: '個人選択可' }
                ]);
                self.selectedMailPcPersonal = ko.observable(3);

                self.mailPcPersonSetting = ko.observableArray([
                    { code: 1, name: '編集可' },
                    { code: 2, name: '編集不可' },
                ]);
                self.selectedMailPcPersonSetting = ko.observable(1);

                self.mailMobileCompany = ko.observableArray([
                    { code: 1, name: '利用する' },
                    { code: 2, name: '利用しない' },
                    { code: 3, name: '個人選択可' }
                ]);

                self.selectedMailMobileCompany = ko.observable(1);

                self.mailMobileCompanySetting = ko.observableArray([
                    { code: 1, name: '編集可' },
                    { code: 2, name: '編集不可' },
                ]);
                self.selectedMailMobileCompanySetting = ko.observable(1);

                self.mailMobilePersonal = ko.observableArray([
                    { code: 1, name: '利用する' },
                    { code: 2, name: '利用しない' },
                    { code: 3, name: '個人選択可' }
                ]);
                self.selectedMailMobilePersonal = ko.observable(2);
                
                self.mailMobilePersonalSetting = ko.observableArray([
                    { code: 1, name: '編集可' },
                    { code: 2, name: '編集不可' },
                ]);
                self.selectedMailMobilePersonalSetting = ko.observable(2);

                self.controlButton = ko.computed(function() {
                    return self.selectedMailMobilePersonalSetting() == 1;
                });
                
                self.mobilePhoneCompany = ko.observableArray([
                    { code: 1, name: '編集可' },
                    { code: 2, name: '編集不可' },
                ]);
                self.selectedMobilePhoneCompany = ko.observable(1);
                
                 self.mobilePhoneCompanySetting = ko.observableArray([
                    { code: 1, name: '編集可' },
                    { code: 2, name: '編集不可' },
                ]);
                self.selectedMobilePhoneCompanySetting = ko.observable(2);
                
                 self.password = ko.observableArray([
                    { code: 1, name: '編集可' },
                    { code: 2, name: '編集不可' },
                ]);
                self.selectedPassword = ko.observable(1);

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
    }
}