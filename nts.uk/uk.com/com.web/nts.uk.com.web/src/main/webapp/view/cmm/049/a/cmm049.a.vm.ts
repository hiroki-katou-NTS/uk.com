module nts.uk.com.view.cmm049.a {

    export module viewmodel {

        export class ScreenModel {
            enums: any;
            sendMailSetOptions: KnockoutObservableArray<any>;
            selfEditSetOptions: KnockoutObservableArray<any>;
            
            selectedMailPcCom: KnockoutObservable<number>;
            selectedMailPcPerson: KnockoutObservable<number>;
            selectedMailPcPersonal: KnockoutObservable<number>;
            selectedMailPcPersonSetting: KnockoutObservable<number>;
            selectedMailMobileCompany: KnockoutObservable<number>;
            selectedMailMobileCompanySetting: KnockoutObservable<number>;
            selectedMailMobilePersonal: KnockoutObservable<number>;
            selectedMailMobilePersonalSetting: KnockoutObservable<number>;
            selectedMobilePhoneCompany: KnockoutObservable<number>;
            selectedMobilePhoneCompanySetting: KnockoutObservable<number>;
            selectedPassword: KnockoutObservable<number>;
            controlButton: KnockoutObservable<boolean>;
            controlMailMobilePersonalSetting: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                self.sendMailSetOptions = ko.observableArray([]);
                self.selfEditSetOptions = ko.observableArray([]);
                
                self.selectedMailPcCom = ko.observable(1);
                self.selectedMailPcPerson = ko.observable(1);
                self.selectedMailPcPersonal = ko.observable(3);
                self.selectedMailPcPersonSetting = ko.observable(1);
                self.selectedMailMobileCompany = ko.observable(1);
                self.selectedMailMobileCompanySetting = ko.observable(1);
                self.selectedMailMobilePersonal = ko.observable(2);
                self.selectedMailMobilePersonalSetting = ko.observable(2);
                self.controlMailMobilePersonalSetting = ko.computed(function(){
                    return self.selectedMailMobilePersonal() == 1 || self.selectedMailMobilePersonal() == 2;
                    });
                self.controlButton = ko.computed(function() {
                    return self.selectedMailMobilePersonalSetting() == 1;
                });
                self.selectedMobilePhoneCompany = ko.observable(1);
                self.selectedMobilePhoneCompanySetting = ko.observable(2);
                self.selectedPassword = ko.observable(1);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                service.getAllEnum().done((data: any) => {
                    _self.enums = data
                    _self.bindEnums(data);
                    dfd.resolve();
                })
                return dfd.promise();
            }
            
            public bindEnums(data: any) {
                let _self = this;
                data.settingUseSendMail.forEach((item: any, index: number) => {
                    _self.sendMailSetOptions().push({ code: item.value, name: item.fieldName });
                    //TODO
//                    _self.sendMailSetOptions().push({ code: item.value, name: item.localizedName });
                    data.selfEditSetting.forEach((item: any, index: number) => {
                        _self.selfEditSetOptions.push({ code: item.value, name: item.fieldName });
                    })
                });
            }
            /**
         * Open dialog user info
         */
        public openDialogUserInfo(userInfo: number) {
            let _self = this;
            nts.uk.ui.block.grayout();
            
            let dataObject: any = {
                userInfo: userInfo
            };
            nts.uk.ui.windows.setShared("CMM049_DIALOG_B_INPUT_DATA", dataObject);
            nts.uk.ui.windows.sub.modal("/view/cmm/049/b/index.xhtml").onClosed(() => {
                nts.uk.ui.block.clear();
            });
        }

            public save() {
            }
        }
    }
}