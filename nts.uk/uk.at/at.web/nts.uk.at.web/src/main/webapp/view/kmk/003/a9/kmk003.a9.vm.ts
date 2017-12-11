module a9 {
    
    /**
     * Screen Model - Tab 9
     */
    class ScreenModel {
        
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        
        // Screen data
        data: KnockoutObservable<any>;

        // Data - Input value: Late 遅刻
        lateSetting: TimeRoundingModel;
        
        // Data - Input value: Leave early 早退
        leaveEarlySetting: TimeRoundingModel;     
        
        /**
         * Constructor
         */
        constructor(data: any) {
            let _self = this;
            
            _self.data = ko.observable(data());
            
            // Detail mode and simple mode is same
            _self.isDetailMode = ko.observable(null);
            _self.isDetailMode.subscribe(newValue => {
                (newValue === true) ? _self.loadDetailMode() : _self.loadSimpleMode();
            });
            
            // Init data
            _self.lateSetting = new TimeRoundingModel();
            _self.leaveEarlySetting = new TimeRoundingModel();
            
            //TODO
            _self.isDetailMode = ko.observable(true);     
        }
        
        /**
         * Bind data to screen items
         */
        public bindDataToScreen(data: any) {
            let _self = this;
            //TODO
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
     * Time Rounding Model
     */
    class TimeRoundingModel {
        listRoundingTimeValue: KnockoutObservableArray<ItemModel>;
        listRoundingValue: KnockoutObservableArray<ItemModel>;
        selectRoundingTimeValue: KnockoutObservable<number>;
        selectRoundingValue: KnockoutObservable<number>;
        
        constructor() {
            let _self = this;
            _self.listRoundingTimeValue = ko.observableArray([
                new ItemModel(1, nts.uk.resource.getText("KMK003_91")),
                new ItemModel(2, nts.uk.resource.getText("KMK003_92"))
            ]);
            _self.listRoundingValue = ko.observableArray([
                new ItemModel(1, nts.uk.resource.getText("KMK003_91")),
                new ItemModel(2, nts.uk.resource.getText("KMK003_92"))
            ]);
            _self.selectRoundingTimeValue = ko.observable(1);
            _self.selectRoundingValue = ko.observable(1);
        }
    }
    
    /**
     * Item Model
     */
    class ItemModel {
        value: number;
        text: string;

        constructor(value: number, text: string) {
            this.value = value;
            this.text = text;
        }
    }
    
    /**
     * Knockout Binding Handler - Tab 9
     */
    class KMK003A9BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a9/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();
            let data = input.data;

            let screenModel = new ScreenModel(data);
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.bindDataToScreen(data);
            });
        }

    }
    
    ko.bindingHandlers['ntsKMK003A9'] = new KMK003A9BindingHandler();
}