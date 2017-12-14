module a14 {
    class ScreenModel {

        // 育児時間リスト
        lstChildCareEnum: KnockoutObservableArray<EnumModel>;
        selectedChildCare: KnockoutObservable<number>;
        
        // 介護時間リスト
        lstNursingTimeEnum: KnockoutObservableArray<EnumModel>;
        selectedNursingTime: KnockoutObservable<number>;
        
        /**
        * Constructor.
        */
        constructor() {
            let self = this;
            
            // initial data
            self.lstChildCareEnum = ko.observableArray([
                {value: 0, localizedName: nts.uk.resource.getText("KMK003_116")},
                {value: 1, localizedName: nts.uk.resource.getText("KMK003_117")}
            ]);
            self.selectedChildCare = ko.observable(0);
            
            self.lstNursingTimeEnum = ko.observableArray([
                {value: 0, localizedName: nts.uk.resource.getText("KMK003_196")},
                {value: 1, localizedName: nts.uk.resource.getText("KMK003_197")}
            ]);
            self.selectedNursingTime = ko.observable(0);
        }

        /**
         * bindDataToScreen
         */
        public bindDataToScreen() {
            let self = this;
        }

    }
    
    /**
     * EnumModel
     */
    interface EnumModel {
        value: number;
        localizedName: string;
    }
    
    /**
     * KMK003A11BindingHandler
     */
    class KMK003A14BindingHandler implements KnockoutBindingHandler {
        
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
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A14'] = new KMK003A14BindingHandler();
}
