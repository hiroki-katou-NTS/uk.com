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

        isDetailMode: KnockoutObservable<boolean>;
        /**
        * Constructor.
        */
        constructor(screenMode: any, settingMethod: string, workTimeCode: string, isClickSave: any) {
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

            self.isDetailMode = ko.observable(true);
            screenMode.subscribe(function(value: any) {
                value == "2" ? self.isDetailMode(true) : self.isDetailMode(false);
            });
            isClickSave.subscribe(function(value: any) {
                if (value) {
                }
            });
        }

        //bind data to screen items
        public bindDataToScreen(data: any) {
            let self = this;
//            self.dayStartTime(data().startDateClock);
//            self.oneDayRangeTime(data().rangeTimeDay);
//            self.nightWorkShift(data().nightShift);
//            // self.beforeUpdateWorkTime();//diff time
//            // self.afterUpdateWorkTime();//diff time
//            let timezone1 = data().prescribedTimezoneSetting.timezone[0];
//            let timezone2 = data().prescribedTimezoneSetting.timezone[1];

//            self.firstFixedStartTime(timezone1.start.inDayTimeWithFormat);
//            self.firstFixedEndTime(timezone1.end.inDayTimeWithFormat);
//            self.secondFixedStartTime(timezone2.start.inDayTimeWithFormat);
//            self.secondFixedEndTime(timezone2.end.inDayTimeWithFormat);
            self.useCoreTime();
            self.coreTimeStart();
            self.coreTimeEnd();
            self.leastWorkTime();
            self.morningEndTime();
            self.afternoonStartTime();
            self.oneDay();
            self.morning();
            self.afternoon();
        }

        public collectData(oldData: any) {
            let self = this;
            oldData.startDateClock = self.dayStartTime();
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
        }

        private getData() {
            let self = this;
            //            service.findWorkTimeSetByCode()
        }

        /**
         * Update
         */
        update(element: any, valueAccessor: () => any, allBindingsAccessor: () => any, viewModel: any, bindingContext: KnockoutBindingContext): void {
            var webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a1/index.xhtml').serialize();
            //get data
            let input = valueAccessor();
            let screenMode = input.screenMode;
            let settingMethod = ko.unwrap(input.settingMethod);
            let workTimeCode = input.workTimeCode;
            let isClickSave = input.saveAction;

            let screenModel = new ScreenModel(screenMode, settingMethod, workTimeCode, isClickSave);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A1'] = new KMK003A1BindingHandler();

}
