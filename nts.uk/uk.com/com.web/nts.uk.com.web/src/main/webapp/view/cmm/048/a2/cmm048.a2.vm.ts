module a2 {

    import MainModel = nts.uk.com.view.cmm048.a.viewmodel.MainModel;
    import EmployeeInfoContactModel = nts.uk.com.view.cmm048.a.viewmodel.EmployeeInfoContactModel;
    import PersonContactModel = nts.uk.com.view.cmm048.a.viewmodel.PersonContactModel;
    import UserInfoUseMethodModel = nts.uk.com.view.cmm048.a.viewmodel.UserInfoUseMethodModel;
    
    class ScreenModel {

        employeeInfoContact: EmployeeInfoContactModel;
        personContact: PersonContactModel;
        listUserInfoUseMethod: Array<UserInfoUseMethodModel>;
        
        userInfoCompanyPcMail: UserInfoUseMethodModel;
        userInfoPersonalPcMail: UserInfoUseMethodModel;
        userInfoCompanyMobileMail: UserInfoUseMethodModel;
        userInfoPersonalMobileMail: UserInfoUseMethodModel;
        
        listSelfEdit: KnockoutObservableArray<any>;
        
        /**
         * Constructor
         */
        constructor(model: MainModel) {
            let _self = this;
            
            _self.employeeInfoContact = model.employeeInfoContact;
            _self.personContact = model.personContact;
            _self.listUserInfoUseMethod = model.listUserInfoUseMethod;  
            
            // Init data                 
            _self.listSelfEdit = ko.observableArray([
                { value: 1, localizedName: nts.uk.resource.getText("CMM048_32") },
                { value: 0, localizedName: nts.uk.resource.getText("CMM048_33") }            
            ]); 
            
            //TODO fake data           
            let user1 = new UserInfoUseMethodModel();
            user1.settingItem(UserInfoItem.COMPANY_PC_MAIL);
            user1.settingUseMail(1);
            _self.listUserInfoUseMethod.push(user1);
            
            let user2 = new UserInfoUseMethodModel();
            user2.settingItem(UserInfoItem.PERSONAL_PC_MAIL);
            user2.settingUseMail(2);
            _self.listUserInfoUseMethod.push(user2);
            
            let user3 = new UserInfoUseMethodModel();
            user3.settingItem(UserInfoItem.COMPANY_MOBILE_MAIL);
            user3.settingUseMail(0);
            _self.listUserInfoUseMethod.push(user3);
            
            let user4 = new UserInfoUseMethodModel();
            user4.settingItem(UserInfoItem.PERSONAL_MOBILE_MAIL);
            user4.settingUseMail(2);
            _self.listUserInfoUseMethod.push(user4);
            
            _self.userInfoCompanyPcMail = _.find(_self.listUserInfoUseMethod, method => method.settingItem() == UserInfoItem.COMPANY_PC_MAIL);
            _self.userInfoPersonalPcMail = _.find(_self.listUserInfoUseMethod, method => method.settingItem() == UserInfoItem.PERSONAL_PC_MAIL);
            _self.userInfoCompanyMobileMail = _.find(_self.listUserInfoUseMethod, method => method.settingItem() == UserInfoItem.COMPANY_MOBILE_MAIL);
            _self.userInfoPersonalMobileMail = _.find(_self.listUserInfoUseMethod, method => method.settingItem() == UserInfoItem.PERSONAL_MOBILE_MAIL);
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
            
            let dataObject: any = {
                userInfo: userInfo
            };
            nts.uk.ui.windows.setShared("CMM048_DIALOG_B_INPUT_DATA", dataObject);
            nts.uk.ui.windows.sub.modal("/view/cmm/048/b/index.xhtml").onClosed(() => {
                nts.uk.ui.block.clear();
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