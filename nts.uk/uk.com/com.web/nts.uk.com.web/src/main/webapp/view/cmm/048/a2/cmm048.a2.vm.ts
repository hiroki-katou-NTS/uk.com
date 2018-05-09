module a2 {

    import MainModel = nts.uk.com.view.cmm048.a.viewmodel.MainModel;
    
    class ScreenModel {

        employeeInfoContact: EmployeeInfoContactModel;
        personContact: PersonContactModel;
        
        /**
         * Constructor
         */
        constructor(model: MainModel) {
            let _self = this;
            
            _self.employeeInfoContact = model.employeeInfoContact;
            _self.personContact = model.personContact;
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