module nts.uk.com.view.cmm049.a {

    import ListUserInfoUseMethodDto = nts.uk.com.view.cmm049.a.service.UserInfoUseMethodDto;
    import serviceB = nts.uk.com.view.cmm049.b.service
    export module viewmodel {

        export class ScreenModel {
            enums: any;
            sendMailSetOptions: KnockoutObservableArray<any>;
            selfEditSetOptions: KnockoutObservableArray<any>;

            selectedPcComSendMailSet: KnockoutObservable<number>;
            selectedPcComSelfEditSet: KnockoutObservable<number>;

            selectedPcPersonalSendMailSet: KnockoutObservable<number>;
            selectedPcPersonalSelfEditSet: KnockoutObservable<number>;

            selectedMobileComSendMailSet: KnockoutObservable<number>;
            selectedMobileComSelfEditSet: KnockoutObservable<number>;

            selectedMobilePersonalSendMailSet: KnockoutObservable<number>;
            selectedMobilePersonalSelfEditSet: KnockoutObservable<number>;

            selectedMobilePhoneComSelfEditSet: KnockoutObservable<number>;
            selectedMobilePhonePersonalSelfEditSet: KnockoutObservable<number>;

            selectedPasswordSelfEditSet: KnockoutObservable<number>;

            //controls
            controlMailPcCom: KnockoutObservable<boolean>;
            controlMailPcPersonal: KnockoutObservable<boolean>;
            controlMailMobileCom: KnockoutObservable<boolean>;
            controlMailMobilePersonal: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                //switch button options
                self.sendMailSetOptions = ko.observableArray([]);
                self.selfEditSetOptions = ko.observableArray([]);

                //Mail Company
                self.selectedPcComSendMailSet = ko.observable(SettingUseSendMail.NOT_USE);
                self.selectedPcComSelfEditSet = ko.observable(SelfEditUserInfo.CAN_NOT_EDIT);
                self.controlMailPcCom = ko.computed(function() {
                    return self.selectedPcComSendMailSet() == SettingUseSendMail.USE || self.selectedPcComSendMailSet() == SettingUseSendMail.PERSONAL_SELECTABLE;
                });

                //Mail Personal
                self.selectedPcPersonalSendMailSet = ko.observable(SettingUseSendMail.NOT_USE);
                self.selectedPcPersonalSelfEditSet = ko.observable(SelfEditUserInfo.CAN_NOT_EDIT);
                self.controlMailPcPersonal = ko.computed(function() {
                    return self.selectedPcPersonalSendMailSet() == SettingUseSendMail.USE || self.selectedPcPersonalSendMailSet() == SettingUseSendMail.PERSONAL_SELECTABLE;
                });

                //Mobile Com
                self.selectedMobileComSendMailSet = ko.observable(SettingUseSendMail.NOT_USE);
                self.selectedMobileComSelfEditSet = ko.observable(SelfEditUserInfo.CAN_NOT_EDIT);
                self.controlMailMobileCom = ko.computed(function() {
                    return self.selectedMobileComSendMailSet() == SettingUseSendMail.USE || self.selectedMobileComSendMailSet() == SettingUseSendMail.PERSONAL_SELECTABLE;
                });

                //Mobile Personal
                self.selectedMobilePersonalSendMailSet = ko.observable(SettingUseSendMail.NOT_USE);
                self.selectedMobilePersonalSelfEditSet = ko.observable(SelfEditUserInfo.CAN_NOT_EDIT);
                self.controlMailMobilePersonal = ko.computed(function() {
                    return self.selectedMobilePersonalSendMailSet() == SettingUseSendMail.USE || self.selectedMobilePersonalSendMailSet() == SettingUseSendMail.PERSONAL_SELECTABLE;
                });

                // Phone
                self.selectedMobilePhoneComSelfEditSet = ko.observable(SelfEditUserInfo.CAN_NOT_EDIT);
                self.selectedMobilePhonePersonalSelfEditSet = ko.observable(SelfEditUserInfo.CAN_NOT_EDIT);

                //password
                self.selectedPasswordSelfEditSet = ko.observable(SelfEditUserInfo.CAN_NOT_EDIT);
            }

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                nts.uk.ui.block.invisible();
                $.when(service.getAllEnum(), service.findUserinfoUseMethod()).done((dataEnums: any, dataUserinfoUseMethod: any) => {
                    //bind enums
                    _self.enums = dataEnums
                    _self.bindEnums(dataEnums);

                    //bind data to screen
                    _self.bindToScreen(dataUserinfoUseMethod);
                    dfd.resolve();
                }).fail((res: any) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                        nts.uk.request.jump("/view/ccg/008/a/index.xhtml");
                    });
                    dfd.reject();
                }).always(() => nts.uk.ui.block.clear());
                return dfd.promise();
            }

            public bindEnums(data: any) {
                let _self = this;
                data.settingUseSendMail.forEach((item: any, index: number) => {
                    _self.sendMailSetOptions().push({ code: item.value, name: item.localizedName });
                });
                data.selfEditSetting.forEach((item: any, index: number) => {
                    _self.selfEditSetOptions.push({ code: item.value, name: item.localizedName });
                });
            }

            private bindToScreen(data: any) {
                let self = this;
                data.forEach((item: any, index: any) => {
                    if (item.settingItem == UserInfoItem.COMPANY_PC_MAIL) {
                        self.selectedPcComSendMailSet(item.settingUseMail);
                        self.selectedPcComSelfEditSet(item.selfEdit);
                    }
                    if (item.settingItem == UserInfoItem.PERSONAL_PC_MAIL) {
                        self.selectedPcPersonalSendMailSet(item.settingUseMail);
                        self.selectedPcPersonalSelfEditSet(item.selfEdit);
                    }
                    if (item.settingItem == UserInfoItem.COMPANY_MOBILE_MAIL) {
                        self.selectedMobileComSendMailSet(item.settingUseMail);
                        self.selectedMobileComSelfEditSet(item.selfEdit);
                    }
                    if (item.settingItem == UserInfoItem.PERSONAL_MOBILE_MAIL) {
                        self.selectedMobilePersonalSendMailSet(item.settingUseMail);
                        self.selectedMobilePersonalSelfEditSet(item.selfEdit);
                    }
                    if (item.settingItem == UserInfoItem.COMPANY_MOBILE_PHONE) {
                        self.selectedMobilePhoneComSelfEditSet(item.selfEdit);
                    }
                    if (item.settingItem == UserInfoItem.PERSONAL_MOBILE_PHONE) {
                        self.selectedMobilePhonePersonalSelfEditSet(item.selfEdit);
                    }
                    if (item.settingItem == UserInfoItem.PASSWORD) {
                        self.selectedPasswordSelfEditSet(item.selfEdit);
                    }
                });
            }
            /**
         * Open dialog user info
         */
            public openDialogUserInfo(userInfo: number) {
                let _self = this;
                let itemName: any;
                let dfd = $.Deferred<any>();
                switch (userInfo) {
                    case UserInfoItem.COMPANY_PC_MAIL:
                        _self.getData(serviceB.getPCMailCompany, nts.uk.resource.getText("CMM049_16"),userInfo);
                        break;
                    case UserInfoItem.PERSONAL_PC_MAIL:
                        _self.getData(serviceB.getPCMailPerson, nts.uk.resource.getText("CMM049_17"),userInfo);
                        break;
                    case UserInfoItem.COMPANY_MOBILE_MAIL:
                        _self.getData(serviceB.getMobileMailCompany, nts.uk.resource.getText("CMM049_18"),userInfo);
                        break;
                    case UserInfoItem.PERSONAL_MOBILE_MAIL:
                        _self.getData(serviceB.getMobileMailPerson, nts.uk.resource.getText("CMM049_19"),userInfo);
                        break;
                    default:
                }
                dfd.resolve();
            }

            public getData(service: () => JQueryPromise<any>, text: string,userInfo: number): void {
                nts.uk.ui.block.invisible();
                service().done((data: any) => {
                    nts.uk.ui.windows.setShared("CMM049_DIALOG_B_INPUT_DATA", { listMailFunction: data, userInfoItemName: text ,userInfo :userInfo});
                    nts.uk.ui.windows.sub.modal("/view/cmm/049/b/index.xhtml");
                }).fail((res: any) => {
                    nts.uk.ui.dialog.alertError({ messageId: res.messageId });
                }).always(() => nts.uk.ui.block.clear());
            }
                
             /**
             * Save
             */
            public save() {
                let self = this;
                nts.uk.ui.block.grayout();
                let data1: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.COMPANY_PC_MAIL,
                    settingUseMail: self.selectedPcComSendMailSet(),
                    selfEdit: self.selectedPcComSelfEditSet()
                }
                let data2: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PERSONAL_PC_MAIL,
                    settingUseMail: self.selectedPcPersonalSendMailSet(),
                    selfEdit: self.selectedPcPersonalSelfEditSet()
                }
                let data3: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.COMPANY_MOBILE_MAIL,
                    settingUseMail: self.selectedMobileComSendMailSet(),
                    selfEdit: self.selectedMobileComSelfEditSet()
                }
                let data4: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PERSONAL_MOBILE_MAIL,
                    settingUseMail: self.selectedMobilePersonalSendMailSet(),
                    selfEdit: self.selectedMobilePersonalSelfEditSet()
                }
                let data5: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.COMPANY_MOBILE_PHONE,
                    settingUseMail: null,
                    selfEdit: self.selectedMobilePhoneComSelfEditSet()
                }
                let data6: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PERSONAL_MOBILE_PHONE,
                    settingUseMail: null,
                    selfEdit: self.selectedMobilePhonePersonalSelfEditSet()
                }
                let data7: ListUserInfoUseMethodDto = {
                    settingItem: UserInfoItem.PASSWORD,
                    settingUseMail: null,
                    selfEdit: self.selectedPasswordSelfEditSet()
                }

                let listData = [data1, data2, data3, data4, data5, data6, data7];
                service.saveUserinfoUseMethod({ lstUserInfoUseMethodDto: listData }).done(() => {
                    service.findUserinfoUseMethod().done((dataUserinfoUseMethod: any) => {
                        //bind data to screen
                        self.bindToScreen(dataUserinfoUseMethod);
                    });
                    nts.uk.ui.block.clear();
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
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

        export enum SettingUseSendMail {
            NOT_USE,
            USE,
            PERSONAL_SELECTABLE
        }

        export enum SelfEditUserInfo {
            CAN_NOT_EDIT,
            CAN_EDIT
        }
    }
}