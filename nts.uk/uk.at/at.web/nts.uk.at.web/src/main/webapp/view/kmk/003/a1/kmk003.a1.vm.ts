module a1 {
    
    import WorkTimeSettingEnumDto = nts.uk.at.view.kmk003.a.service.model.worktimeset.WorkTimeSettingEnumDto;
    
    import PredetemineTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.predset.PredetemineTimeSettingModel;
    import TimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.predset.TimezoneModel;
    import EmTimezoneChangeExtentModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.EmTimezoneChangeExtentModel;
    import CoreTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.CoreTimeSettingModel
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;
    
    class ScreenModel {

        dayStartTime: KnockoutObservable<number>;
        dayStartTimeOption: KnockoutObservable<any>;

        oneDayRangeTimeOption: KnockoutObservable<any>;

        beforeUpdateWorkTimeOption: KnockoutObservable<any>;

        afterUpdateWorkTimeOption: KnockoutObservable<any>;


        secondTimes: KnockoutObservable<boolean>;

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

            self.secondTimes = ko.observable(true);

            self.isFlexMode = self.mainSettingModel.workTimeSetting.isFlex;
            self.isDiffTimeMode = self.mainSettingModel.workTimeSetting.isDiffTime;
            self.isDiffTimeMode.subscribe(function(isDifftime: boolean){
                if(isDifftime && !(self.changeExtent)){
                    self.changeExtent = self.mainSettingModel.diffWorkSetting.changeExtent;
                }
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
