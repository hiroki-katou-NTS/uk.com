module nts.uk.com.view.cmm049.a {

    import ListUserInfoUseMethodDto = nts.uk.com.view.cmm049.a.service.UserInfoUseMethodDto;
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
            controlButtonMailPcPerson: KnockoutObservable<boolean>;
            controlButtonMailPcPersonSetting: KnockoutObservable<boolean>;
            controlMailMobilePersonalSetting: KnockoutObservable<boolean>;
            controlMailPcCompany: KnockoutObservable<boolean>;
            controlMailPcPersonal: KnockoutObservable<boolean>;
            controlButtonMailMobileCompanySetting: KnockoutObservable<boolean>;
            controlMailMobileCompany: KnockoutObservable<boolean>;
            

            constructor() {
                var self = this;
                self.sendMailSetOptions = ko.observableArray([]);
                self.selfEditSetOptions = ko.observableArray([]);
                
                //set enable,disable button
                self.selectedMailPcCom = ko.observable(1);
                self.controlMailPcCompany = ko.computed(function(){
                    return self.selectedMailPcCom() == 1 || self.selectedMailPcCom() == 2;
                    });
                self.selectedMailPcPerson = ko.observable(1);
                self.controlButtonMailPcPerson = ko.computed(function() {
                    return self.selectedMailPcPerson() == 1;
                });
                self.selectedMailPcPersonal = ko.observable(2);
                self.controlMailPcPersonal = ko.computed(function(){
                    return self.selectedMailPcPersonal() == 1 || self.selectedMailPcPersonal() == 2;
                    });
                self.selectedMailPcPersonSetting = ko.observable(1);
                self.controlButtonMailPcPersonSetting = ko.computed(function() {
                    return self.selectedMailPcPersonSetting() == 1;
                });
                self.selectedMailMobileCompany = ko.observable(1);
                self.controlMailMobileCompany = ko.computed(function(){
                    return self.selectedMailMobileCompany() == 1 || self.selectedMailMobileCompany() == 2;
                    });
                self.selectedMailMobileCompanySetting = ko.observable(1);
                self.controlButtonMailMobileCompanySetting = ko.computed(function() {
                    return self.selectedMailMobileCompanySetting() == 1;
                });
                self.selectedMailMobilePersonal = ko.observable(0);
                self.selectedMailMobilePersonalSetting = ko.observable(0);
                self.controlMailMobilePersonalSetting = ko.computed(function(){
                    return self.selectedMailMobilePersonal() == 1 || self.selectedMailMobilePersonal() == 2;
                    });
                self.controlButton = ko.computed(function() {
                    return self.selectedMailMobilePersonalSetting() == 1;
                });
                self.selectedMobilePhoneCompany = ko.observable(1);
                self.selectedMobilePhoneCompanySetting = ko.observable(0);
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
                let self =this;
                let data1: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.COMPANY_PC_MAIL,
                    settingUseMail: self.selectedMailPcCom(),
                    selfEdit: self.selectedMailPcPerson()
                }
                let data2: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PERSONAL_PC_MAIL,
                    settingUseMail : self.selectedMailPcPersonal(),
                    selfEdit: self.selectedMailPcPersonSetting()
                }
                let data3: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.COMPANY_MOBILE_MAIL,
                    settingUseMail  : self.selectedMailMobileCompany(),
                    selfEdit: self.selectedMailMobileCompanySetting()
                }
                let data4: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PERSONAL_MOBILE_MAIL,
                    settingUseMail : self.selectedMailMobilePersonal(),
                    selfEdit: self.selectedMailMobilePersonalSetting()
                }
                let data5: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.COMPANY_MOBILE_PHONE,
                    settingUseMail : null,
                    selfEdit : self.selectedMobilePhoneCompany()
                }
                let data6: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PERSONAL_MOBILE_PHONE,
                    settingUseMail : null,
                    selfEdit: self.selectedMobilePhoneCompanySetting()
                }
                let data7: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PASSWORD,
                    settingUseMail : null,
                    selfEdit: self.selectedPassword()
                }
                
                let listData = [data1,data2,data3,data4,data5,data6,data7];
                service.saveUserinfoUseMethod({lstUserInfoUseMethodDto: listData});
            }
        }
        
        export enum UserInfoItem {
            COMPANY_PC_MAIL,
            PERSONAL_PC_MAIL,
            COMPANY_MOBILE_MAIL,
            PERSONAL_MOBILE_MAIL,
            COMPANY_MOBILE_PHONE,
            PERSONAL_MOBILE_PHONE,
            PASSWORD
        }
    }
}