module a2_old {

    import MainModel = nts.uk.com.view.cmm048.a_old.viewmodel.MainModel;
    import EmployeeInfoContactModel = nts.uk.com.view.cmm048.a_old.viewmodel.EmployeeInfoContactModel;
    import PersonContactModel = nts.uk.com.view.cmm048.a_old.viewmodel.PersonContactModel;
    import UserInfoUseMethodModel = nts.uk.com.view.cmm048.a_old.viewmodel.UserInfoUseMethodModel;
    import UseContactSettingModel = nts.uk.com.view.cmm048.a_old.viewmodel.UseContactSettingModel;
    import bservice = nts.uk.com.view.cmm048.b.service;
    import FunctionSettingDto = nts.uk.com.view.cmm048.b.model.FunctionSettingDto;
    class ScreenModel {

        mainModel: MainModel;
        
        employeeInfoContact: EmployeeInfoContactModel;
        personContact: PersonContactModel;
        
        userInfoCompanyPcMail: UserInfoUseMethodModel;
        userInfoPersonalPcMail: UserInfoUseMethodModel;
        userInfoCompanyMobileMail: UserInfoUseMethodModel;
        userInfoPersonalMobileMail: UserInfoUseMethodModel;
        userInfoCompanyMobilePhone: UserInfoUseMethodModel;
        userInfoPersonalMobilePhone: UserInfoUseMethodModel;
        
        useContactCompanyPcMail: UseContactSettingModel;
        useContactPersonalPcMail: UseContactSettingModel;
        useContactCompanyMobileMail: UseContactSettingModel;
        useContactPersonalMobileMail: UseContactSettingModel;
        
        listSelfEdit: KnockoutObservableArray<any>;
        
        enableSettingCompanyPcMail: KnockoutObservable<boolean>;
        enableSettingPersonalPcMail: KnockoutObservable<boolean>;
        enableSettingCompanyMobileMail: KnockoutObservable<boolean>;
        enableSettingPersonalMobileMail: KnockoutObservable<boolean>;
        
        /**
         * Constructor
         */
        constructor(model: MainModel) {
            let _self = this;
            
            _self.mainModel = model;
            _self.employeeInfoContact = model.employeeInfoContact;
            _self.personContact = model.personContact;           
        
            _self.setUserInfo(UserInfoItem.COMPANY_PC_MAIL);
            _self.setUserInfo(UserInfoItem.PERSONAL_PC_MAIL);
            _self.setUserInfo(UserInfoItem.COMPANY_MOBILE_MAIL);
            _self.setUserInfo(UserInfoItem.PERSONAL_MOBILE_MAIL);
            _self.setUserInfo(UserInfoItem.COMPANY_MOBILE_PHONE);
            _self.setUserInfo(UserInfoItem.PERSONAL_MOBILE_PHONE);
            _self.setUseContact(UserInfoItem.COMPANY_PC_MAIL);
            _self.setUseContact(UserInfoItem.PERSONAL_PC_MAIL);
            _self.setUseContact(UserInfoItem.COMPANY_MOBILE_MAIL);
            _self.setUseContact(UserInfoItem.PERSONAL_MOBILE_MAIL);
            
            // Init data                 
            _self.listSelfEdit = ko.observableArray([
                { value: true, localizedName: nts.uk.resource.getText("CMM048_32") },
                { value: false, localizedName: nts.uk.resource.getText("CMM048_33") }            
            ]); 
            _self.enableSettingCompanyPcMail = ko.computed(() => {
                return _self.userInfoCompanyPcMail.isUse || (_self.userInfoCompanyPcMail.isPersonal && _self.useContactCompanyPcMail.useMailSetting());
            });
            _self.enableSettingPersonalPcMail = ko.computed(() => {
                return _self.userInfoPersonalPcMail.isUse || (_self.userInfoPersonalPcMail.isPersonal && _self.useContactPersonalPcMail.useMailSetting());
            });
            _self.enableSettingCompanyMobileMail = ko.computed(() => {
                return _self.userInfoCompanyMobileMail.isUse || (_self.userInfoCompanyMobileMail.isPersonal && _self.useContactCompanyMobileMail.useMailSetting());
            });
            _self.enableSettingPersonalMobileMail = ko.computed(() => {
                return _self.userInfoPersonalMobileMail.isUse || (_self.userInfoPersonalMobileMail.isPersonal && _self.useContactPersonalMobileMail.useMailSetting());
            });
        }

        /**
         * Start tab
         */
        public startTab() {
            let _self = this;
        }
        
        /**
         * Open dialog user info
         */
        public openDialogUserInfo(userInfo: number) {
            let _self = this;
            nts.uk.ui.block.grayout();
            let data:any = null;
            //get data
            switch (userInfo) {
                case UserInfoItem.COMPANY_PC_MAIL:
                    _self.doOpenDialog(bservice.findCompanyPcMail, userInfo);
                    break;
                case UserInfoItem.PERSONAL_PC_MAIL:
                    _self.doOpenDialog(bservice.findPersonalPcMail, userInfo);
                    break;
                case UserInfoItem.COMPANY_MOBILE_MAIL:
                    _self.doOpenDialog(bservice.findCompanyMobileMail, userInfo);
                    break;
                case UserInfoItem.PERSONAL_MOBILE_MAIL:
                    _self.doOpenDialog(bservice.findPersonalMobileMail, userInfo);
                    break;
                default: break;
            } 
        }
        
        private doOpenDialog(serviceFunction: () => JQueryPromise<any>, userInfo: any) {
            serviceFunction().done((data: Array<FunctionSettingDto>) => {
                if (data && data.length > 0) {
                    let dataObject: any = {
                        userInfo: userInfo,
                        data: data//sort ASC
                    };
                    nts.uk.ui.windows.setShared("CMM048_DIALOG_B_INPUT_DATA", dataObject);
                    nts.uk.ui.windows.sub.modal("/view/cmm/048/b/index.xhtml").onClosed(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            }).fail((res: any) => {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(() => {
                    nts.uk.ui.block.clear();
                });
            });
        }
        
        private setUserInfo(userInfo: UserInfoItem) {
            let _self = this;
            let itemModel: UserInfoUseMethodModel = _.find(_self.mainModel.listUserInfoUseMethod(), method => method.settingItem() == userInfo);
            switch (userInfo) {
                case UserInfoItem.COMPANY_PC_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.userInfoCompanyPcMail = new UserInfoUseMethodModel();
                        _self.userInfoCompanyPcMail.settingItem(UserInfoItem.COMPANY_PC_MAIL);
                        _self.mainModel.listUserInfoUseMethod.push(_self.userInfoCompanyPcMail);
                    } else {                        
                        _self.userInfoCompanyPcMail = itemModel;
                    }
                break;
                case UserInfoItem.PERSONAL_PC_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.userInfoPersonalPcMail = new UserInfoUseMethodModel();
                        _self.userInfoPersonalPcMail.settingItem(UserInfoItem.PERSONAL_PC_MAIL);
                        _self.mainModel.listUserInfoUseMethod.push(_self.userInfoPersonalPcMail);
                    } else {                        
                        _self.userInfoPersonalPcMail = itemModel;
                    }
                break;
                case UserInfoItem.COMPANY_MOBILE_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.userInfoCompanyMobileMail = new UserInfoUseMethodModel();
                        _self.userInfoCompanyMobileMail.settingItem(UserInfoItem.COMPANY_MOBILE_MAIL);
                        _self.mainModel.listUserInfoUseMethod.push(_self.userInfoCompanyMobileMail);
                    } else {                        
                        _self.userInfoCompanyMobileMail = itemModel;
                    }
                break;
                case UserInfoItem.PERSONAL_MOBILE_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.userInfoPersonalMobileMail = new UserInfoUseMethodModel();
                        _self.userInfoPersonalMobileMail.settingItem(UserInfoItem.PERSONAL_MOBILE_MAIL);
                        _self.mainModel.listUserInfoUseMethod.push(_self.userInfoPersonalMobileMail);
                    } else {                        
                        _self.userInfoPersonalMobileMail = itemModel;
                    }
                break;
                case UserInfoItem.COMPANY_MOBILE_PHONE:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.userInfoCompanyMobilePhone = new UserInfoUseMethodModel();
                        _self.userInfoCompanyMobilePhone.settingItem(UserInfoItem.COMPANY_MOBILE_PHONE);
                        _self.mainModel.listUserInfoUseMethod.push(_self.userInfoCompanyMobilePhone);
                    } else {                        
                        _self.userInfoCompanyMobilePhone = itemModel;
                    }
                break;
                case UserInfoItem.PERSONAL_MOBILE_PHONE:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.userInfoPersonalMobilePhone = new UserInfoUseMethodModel();
                        _self.userInfoPersonalMobilePhone.settingItem(UserInfoItem.PERSONAL_MOBILE_PHONE);
                        _self.mainModel.listUserInfoUseMethod.push(_self.userInfoPersonalMobilePhone);
                    } else {                        
                        _self.userInfoPersonalMobilePhone = itemModel;
                    }
                break;
                default:                    
            }       
        }
        
        private setUseContact(userInfo: UserInfoItem) {
            let _self = this;
            let itemModel: UseContactSettingModel = _.find(_self.mainModel.listUseContactSetting(), method => method.settingItem() == userInfo);
            switch (userInfo) {
                case UserInfoItem.COMPANY_PC_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.useContactCompanyPcMail = new UseContactSettingModel();
                        _self.useContactCompanyPcMail.settingItem(UserInfoItem.COMPANY_PC_MAIL);
                        _self.mainModel.listUseContactSetting.push(_self.useContactCompanyPcMail);
                    } else {                        
                        _self.useContactCompanyPcMail = itemModel;
                    }
                break;
                case UserInfoItem.PERSONAL_PC_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.useContactPersonalPcMail = new UseContactSettingModel();
                        _self.useContactPersonalPcMail.settingItem(UserInfoItem.PERSONAL_PC_MAIL);
                        _self.mainModel.listUseContactSetting.push(_self.useContactPersonalPcMail);
                    } else {                        
                        _self.useContactPersonalPcMail = itemModel;
                    }
                break;
                case UserInfoItem.COMPANY_MOBILE_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.useContactCompanyMobileMail = new UseContactSettingModel();
                        _self.useContactCompanyMobileMail.settingItem(UserInfoItem.COMPANY_MOBILE_MAIL);
                        _self.mainModel.listUseContactSetting.push(_self.useContactCompanyMobileMail);
                    } else {                        
                        _self.useContactCompanyMobileMail = itemModel;
                    }
                break;
                case UserInfoItem.PERSONAL_MOBILE_MAIL:
                    if (nts.uk.util.isNullOrUndefined(itemModel)) {
                        _self.useContactPersonalMobileMail = new UseContactSettingModel();
                        _self.useContactPersonalMobileMail.settingItem(UserInfoItem.PERSONAL_MOBILE_MAIL);
                        _self.mainModel.listUseContactSetting.push(_self.useContactPersonalMobileMail);
                    } else {                        
                        _self.useContactPersonalMobileMail = itemModel;
                    }
                break;
                default:                    
            }       
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

    /**
     * Knockout Binding Handler - Tab 1
     */
    class CMM048A2BindingHandler implements KnockoutBindingHandler {

        /**
         * Constructor
         */
        constructor() { }

        /**
         * Init
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void { }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            let webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["com"] + '/')
                .mergeRelativePath('/view/cmm/048/a2/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();
            let model: MainModel = input.model;
            
            let screenModel = new ScreenModel(model);
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.startTab();
            });
        }
    }
    ko.bindingHandlers['ntsCMM048A2'] = new CMM048A2BindingHandler();
}