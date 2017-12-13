module a12 {
    
    /**
     * Screen Model - Tab 12
     * 就業時間帯の共通設定 -> 深夜設定
     * WorkTimeCommonSet -> LateNightSetting
     */
    class ScreenModel {
        
        // Screen mode
        isDetailMode: KnockoutObservable<boolean>;
        
        // Data - Input value: LateNight 深夜設定
        lateNightSetting: TimeRoundingModel;
        
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
            _self.lateNightSetting = new TimeRoundingModel();
            
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
     * Time Rounding Model
     */
    class TimeRoundingModel {
        listRoundingTimeValue: KnockoutObservableArray<ItemModel>;
        listRoundingValue: KnockoutObservableArray<ItemModel>;
        selectRoundingTimeValue: KnockoutObservable<number>;
        selectRoundingValue: KnockoutObservable<number>;
        
        constructor() {
            let _self = this;
            //TODO replace with enum value
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
     * Knockout Binding Handler - Tab 12
     */
    class KMK003A12BindingHandler implements KnockoutBindingHandler {
        
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
                .mergeRelativePath('/view/kmk/003/a12/index.xhtml').serialize();
            // Get data
            let input = valueAccessor();

            let screenModel = new ScreenModel();
            $(element).load(webserviceLocator, () => {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    
    ko.bindingHandlers['ntsKMK003A12'] = new KMK003A12BindingHandler();
}