module a11 {
    class ScreenModel {

        // 休日出勤
        timeSetHol: KnockoutObservable<TimeSetModel>;
        // 残業
        timeSetOT: KnockoutObservable<TimeSetModel>;
        
        /**
        * Constructor.
        */
        constructor() {
            let self = this;
            
            // initial data
            self.timeSetHol = ko.observable(new TimeSetModel(self));
            self.timeSetOT = ko.observable(new TimeSetModel(self));
        }

        /**
         * bindDataToScreen
         */
        public bindDataToScreen() {
            let self = this;
        }

    }
    
    /**
     * TimeSetModel
     */
    class TimeSetModel {
        
        timeOption: KnockoutObservable<any>;
        isCertainDaySet: KnockoutObservable<boolean>;
        
        checked: KnockoutObservable<boolean>;
        subTransferSelected: KnockoutObservable<number>;
        oneDayTime: KnockoutObservable<string>;
        halfDayTime: KnockoutObservable<string>;
        certainDayTime: KnockoutObservable<string>;
        nameIdRadioGroup: string;
        
        /**
         * Constructor
         */
        constructor(parentModel: ScreenModel) {
            let self = this;
            
            self.timeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                width: "50"
            }));
            
            self.checked = ko.observable(true);
            self.subTransferSelected = ko.observable(0);
            self.oneDayTime = ko.observable(null);
            self.halfDayTime = ko.observable(null);
            self.certainDayTime = ko.observable(null);
            self.nameIdRadioGroup = nts.uk.util.randomId();
            
            self.isCertainDaySet = ko.computed(() => {
                return self.subTransferSelected() == 1;
            });
        }
    }
    
    /**
     * KMK003A11BindingHandler
     */
    class KMK003A11BindingHandler implements KnockoutBindingHandler {
        
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any,
            bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a11/index.xhtml').serialize();
            //get data
            let input = valueAccessor();

            let screenModel = new ScreenModel();
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                screenModel.bindDataToScreen();
                
                // update name id for radio
                $('.inputRadioHol').attr('name', screenModel.timeSetHol().nameIdRadioGroup);
                $('.inputRadioOT').attr('name', screenModel.timeSetOT().nameIdRadioGroup);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A11'] = new KMK003A11BindingHandler();
}
