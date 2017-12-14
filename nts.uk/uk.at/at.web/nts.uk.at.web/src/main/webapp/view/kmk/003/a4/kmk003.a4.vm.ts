module a4 {
    class ScreenModel {

        priorityOptions: KnockoutObservableArray<Item>;
        priorityGoWork: KnockoutObservable<string>;
        priorityLeaveWork: KnockoutObservable<string>;

        stampComboBoxOptions: KnockoutObservableArray<Item>;
        stampGoWork: KnockoutObservable<string>;
        stampLeaveWork: KnockoutObservable<string>;

        stampRoundingOptions: KnockoutObservableArray<Item>;
        stampRoundingGoWork: KnockoutObservable<string>;
        stampRoundingLeaveWork: KnockoutObservable<string>;


        leastWorkTime: KnockoutObservable<number>;
        morningEndTime: KnockoutObservable<number>;
        afternoonStartTime: KnockoutObservable<number>;

        oneDay: KnockoutObservable<number>;
        morning: KnockoutObservable<number>;
        afternoon: KnockoutObservable<number>;
        
        /**
        * Constructor.
        */
        constructor(screenMode: string, settingMethod: string, workTimeCode: string) {
            let self = this;

            self.priorityOptions = ko.observableArray([
                new Item('0', nts.uk.resource.getText("KMK003_69")),
                new Item('1', nts.uk.resource.getText("KMK003_70"))
            ]);

            self.priorityGoWork = ko.observable('0');
            self.priorityLeaveWork = ko.observable('0');


            self.stampComboBoxOptions = ko.observableArray([
                //                new Item(),
                //                new Item()
            ]);

            self.stampGoWork = ko.observable('0');
            self.stampLeaveWork = ko.observable('0');

            self.stampRoundingOptions = ko.observableArray([
                new Item('0', nts.uk.resource.getText("KMK003_72")),
                new Item('1', nts.uk.resource.getText("KMK003_73"))
            ]);

            self.stampRoundingGoWork = ko.observable('0');
            self.stampRoundingLeaveWork = ko.observable('0');

            self.leastWorkTime = ko.observable(0);
            self.morningEndTime = ko.observable(0);
            self.afternoonStartTime = ko.observable(0);

            self.oneDay = ko.observable(0);
            self.morning = ko.observable(0);
            self.afternoon = ko.observable(0);
        }



    }
    export class Item {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    class KMK003A1BindingHandler implements KnockoutBindingHandler {
        /**
         * Constructor.
         */
        constructor() {
        }

        /**
         * Init.
         */
        init(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a4/index.xhtml').serialize();

            //get data
            let input = valueAccessor();
            let screenMode = ko.unwrap(input.screenMode);
            let settingMethod = ko.unwrap(input.settingMethod);
            let workTimeCode = input.workTimeCode;

            var screenModel = new ScreenModel(screenMode, settingMethod, workTimeCode);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
            
//             $.fn.getData = function() {
//                return {
//                    name: screenModel.leastWorkTime()
//                };
//            }
        }

        private getData() {
            let self = this;
            //            service.findWorkTimeSetByCode()    
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

        }

    }
    ko.bindingHandlers['ntsKMK003A4'] = new KMK003A1BindingHandler();

}
