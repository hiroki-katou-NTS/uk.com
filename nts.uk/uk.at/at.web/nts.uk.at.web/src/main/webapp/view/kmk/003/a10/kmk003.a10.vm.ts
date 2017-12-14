module a10 {
    
    /**
     * Screen Model - Tab 10
     * 
     * TODO
     */
    class ScreenModel {
        
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        
        // Data - Text value: bonusPaySettingCode 加給時間帯コード
        bonusPaySettingCode: KnockoutObservable<string>;
        
        /**
         * Constructor
         */
        constructor() {
            let _self = this;
            
            // Detail mode and simple mode is same
            _self.isDetailMode = ko.observable(null);
            _self.isDetailMode.subscribe(newValue => {
                (newValue === true) ? _self.loadDetailMode() : _self.loadSimpleMode();
            });
            
            // Init data
            _self.bonusPaySettingCode = ko.observable("");
            
            //TODO
            _self.isDetailMode = ko.observable(true);     
        }
        
        /**
         * UI: change to Detail mode
         */
        private loadDetailMode(): void {
            let _self = this;
            //TODO
        }
        
        /**
         * UI: change to Simple mode
         */
        private loadSimpleMode(): void {
            let _self = this;
            //TODO
        }
    }
    
    
    
    /**
     * Knockout Binding Handler - Tab 10
     */
    class KMK003A10BindingHandler implements KnockoutBindingHandler {
        
        /**
         * Constructor
         */
        constructor() {}

        /**
         * Init
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {}

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {          
            let webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a10/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();

            let screenModel = new ScreenModel();
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    
    ko.bindingHandlers['ntsKMK003A10'] = new KMK003A10BindingHandler();
}