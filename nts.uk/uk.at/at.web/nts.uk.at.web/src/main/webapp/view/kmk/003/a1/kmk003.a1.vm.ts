module a1 {
    class ScreenModel {

        dayStartTime: KnockoutObservable<number>;
        dayStartTimeOption: KnockoutObservable<any>;

        oneDayRangeTime: KnockoutObservable<string>;
        oneDayRangeTimeOption: KnockoutObservable<any>;

        nightWorkShift: KnockoutObservable<boolean>;

        beforeUpdateWorkTime: KnockoutObservable<number>;
        beforeUpdateWorkTimeOption: KnockoutObservable<any>;

        afterUpdateWorkTime: KnockoutObservable<number>;
        afterUpdateWorkTimeOption: KnockoutObservable<any>;

        firstFixedStartTime: KnockoutObservable<number>;
        firstFixedEndTime: KnockoutObservable<number>;

        includeOTTime: KnockoutObservable<boolean>;
        secondTimes: KnockoutObservable<boolean>;

        secondFixedStartTime: KnockoutObservable<number>;
        secondFixedEndTime: KnockoutObservable<number>;

        useCoreTimeOptions: KnockoutObservableArray<Item>;
        useCoreTime: KnockoutObservable<string>;

        coreTimeStart: KnockoutObservable<number>;
        coreTimeEnd: KnockoutObservable<number>;

        leastWorkTime: KnockoutObservable<number>;
        morningEndTime: KnockoutObservable<number>;
        afternoonStartTime: KnockoutObservable<number>;

        oneDay: KnockoutObservable<number>;
        morning: KnockoutObservable<number>;
        afternoon: KnockoutObservable<number>;
        /**
        * Constructor.
        */
        constructor(data: any, screenMode: string, settingMethod: string,workTimeCode: string) {
            let self = this;

            //day start Time
            self.dayStartTime = ko.observable(0);
            self.dayStartTimeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                width: "50"
            }));

            //one day range Time
            self.oneDayRangeTime = ko.observable('');
            self.oneDayRangeTimeOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                width: "50",
                textmode: "text",
            }));

            self.nightWorkShift = ko.observable(true);

            self.beforeUpdateWorkTime = ko.observable(0);
            self.beforeUpdateWorkTimeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                width: "50"
            }));
            self.afterUpdateWorkTime = ko.observable(0);
            self.afterUpdateWorkTimeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                width: "50"
            }));
            self.firstFixedStartTime = ko.observable(0);
            self.firstFixedEndTime = ko.observable(0);

            self.includeOTTime = ko.observable(true);
            self.secondTimes = ko.observable(true);

            self.secondFixedStartTime = ko.observable(0);
            self.secondFixedEndTime = ko.observable(0);

            self.useCoreTimeOptions = ko.observableArray([
                new Item('0', nts.uk.resource.getText("KMK003_158")),
                new Item('1', nts.uk.resource.getText("KMK003_159"))
            ]);

            self.useCoreTime = ko.observable('0');

            self.coreTimeStart = ko.observable(0);
            self.coreTimeEnd = ko.observable(0);

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
                .mergeRelativePath('/view/kmk/003/a1/index.xhtml').serialize();

            //get data
            let input = valueAccessor();
            let data = input.data;
            let screenMode = ko.unwrap(input.screenMode);
            let settingMethod = ko.unwrap(input.settingMethod);
            let workTimeCode = input.workTimeCode;
            
            var screenModel = new ScreenModel(data, screenMode, settingMethod,workTimeCode);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }
        
        private getData()
        {
        let self =this;
            service.findWorkTimeSetByCode()    
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {

        }

    }
    ko.bindingHandlers['ntsKMK003A1'] = new KMK003A1BindingHandler();

}
