module a2 {

    import MainModel = nts.uk.com.view.cmm048.a.viewmodel.MainModel;
    
    class ScreenModel {


        
        /**
         * Constructor
         */
        constructor(model: MainModel) {
            let _self = this;
            //TODO
        }

        /**
         * Start tab
         */
        public startTab(): void {
            let _self = this;
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