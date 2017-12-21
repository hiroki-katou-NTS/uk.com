module a1 {
    
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    
    import PredetemineTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.predset.PredetemineTimeSettingModel;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;
    import EmTimezoneChangeExtentModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.EmTimezoneChangeExtentModel;
    import CoreTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.CoreTimeSettingModel
    import SettingModel = nts.uk.at.view.kmk003.a.viewmodel.SettingModel;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    class ScreenModel {

        dayStartTime: KnockoutObservable<number>;
        dayStartTimeOption: KnockoutObservable<any>;

        oneDayRangeTimeOption: KnockoutObservable<any>;

        beforeUpdateWorkTimeOption: KnockoutObservable<any>;

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

        isDiffTimeMode: KnockoutObservable<boolean>;
        isDetailMode: KnockoutObservable<boolean>;
        isViewTimezoneTwo: KnockoutObservable<boolean>;
        isFlexMode: KnockoutObservable<boolean>;
        mainSettingModel: MainSettingModel;
        predseting: PredetemineTimeSettingModel;
        changeExtent: EmTimezoneChangeExtentModel;
        timeZoneModelOne: TimezoneModel;
        timeZoneModelTwo: TimezoneModel;
        coreTimeSettingModel: CoreTimeSettingModel;
        settingEnum: WorkTimeSettingEnumDto;
        /**
        * Constructor.
        */
        constructor(screenMode: any, settingEnum: WorkTimeSettingEnumDto, mainSettingModel: MainSettingModel) {
            let self = this;
            self.mainSettingModel = mainSettingModel;
            self.predseting = mainSettingModel.predetemineTimeSetting;
            self.timeZoneModelOne = mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.getTimezoneOne();
            self.timeZoneModelTwo = mainSettingModel.predetemineTimeSetting.prescribedTimezoneSetting.getTimezoneTwo();
            self.coreTimeSettingModel = mainSettingModel.flexWorkSetting.coreTimeSetting;
            self.settingEnum = settingEnum;
            //day start Time
            self.dayStartTime = ko.observable(0);
            self.dayStartTimeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                width: "50"
            }));

            //one day range Time
            self.oneDayRangeTimeOption = ko.observable(new nts.uk.ui.option.TextEditorOption({
                width: "50",
                textmode: "text",
            }));


            self.beforeUpdateWorkTimeOption = ko.observable(new nts.uk.ui.option.TimeEditorOption({
                width: "50"
            }));
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
            self.isFlexMode = ko.observable(SettingModel.isFlex(self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr()));
            self.isDiffTimeMode = ko.observable(SettingModel.isDifftime(self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeMethodSet()));
            self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeMethodSet.subscribe(function(settingMethod: number){
                self.isDiffTimeMode(SettingModel.isDifftime(settingMethod));
            });
            self.isDiffTimeMode.subscribe(function(isDifftime: boolean){
                if(isDifftime && !(self.changeExtent)){
                    self.changeExtent = self.mainSettingModel.diffWorkSetting.changeExtent;
                }
            });
            self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr.subscribe(function(settingMethod: number){
                self.isFlexMode(SettingModel.isFlex(settingMethod));
            });
            self.isViewTimezoneTwo = ko.observable(false);
            self.isDetailMode = ko.observable(false);
            screenMode.subscribe(function(value: any) {
                self.isDetailMode(value == "2");
                self.isViewTimezoneTwo(self.isDetailMode() || self.predseting.predetermine());
            });
            self.predseting.predetermine.subscribe(function(predetermine: boolean) {
                self.isViewTimezoneTwo(predetermine || self.isDetailMode());
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
            // service.findWorkTimeSetByCode()
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
            let mainSettingModel: MainSettingModel = input.mainSettingModel;
            var settingEnum: WorkTimeSettingEnumDto = input.enum;

            let screenModel = new ScreenModel(screenMode, settingEnum, mainSettingModel);
            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A1'] = new KMK003A1BindingHandler();

}
