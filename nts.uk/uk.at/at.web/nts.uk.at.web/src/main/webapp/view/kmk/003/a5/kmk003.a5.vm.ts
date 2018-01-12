module a5 {
    import FixTableOption = nts.fixedtable.FixTableOption;
    import FlowRestTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowRestTimezoneModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import DeductionTimeDto = nts.uk.at.view.kmk003.a.service.model.common.DeductionTimeDto;
    import WorkTimeSettingModel = nts.uk.at.view.kmk003.a.viewmodel.worktimeset.WorkTimeSettingModel;
    import FixHalfDayWorkTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixHalfDayWorkTimezoneModel;
    import DiffTimeHalfDayWorkTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.DiffTimeHalfDayWorkTimezoneModel;
    import FlexHalfDayWorkTimeModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.FlexHalfDayWorkTimeModel;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;

    class ScreenModel {
        mainSettingModel: MainSettingModel;

        // flex timezones
        oneDayFlexTimezones: KnockoutObservableArray<any>;
        morningFlexTimezones: KnockoutObservableArray<any>;
        afternoonFlexTimezones: KnockoutObservableArray<any>;

        // diff time timezones
        oneDayDiffTimezones: KnockoutObservableArray<any>;
        morningDiffTimezones: KnockoutObservableArray<any>;
        afternoonDiffTimezones: KnockoutObservableArray<any>;

        // fixed timezones
        oneDayFixedTimezones: KnockoutObservableArray<any>;
        morningFixedTimezones: KnockoutObservableArray<any>;
        afternoonFixedTimezones: KnockoutObservableArray<any>;

        // flow timezones
        flowTimezones: KnockoutObservableArray<any>;

        // flex restSet
        oneDayFlexRestSet: FlowRestTimezoneModel;
        morningFlexRestSet: FlowRestTimezoneModel;
        afternoonFlexRestSet: FlowRestTimezoneModel;

        // flow restSet
        flowRestSet: FlowRestTimezoneModel;

        // ntsFixTableCustom options

        // flex timezones option
        oneDayFlexTimezoneOption: FixTableOption;
        morningFlexTimezoneOption: FixTableOption;
        afternoonFlexTimezoneOption: FixTableOption;

        // difftime timezones option
        oneDayDiffTimezoneOption: FixTableOption;
        morningDiffTimezoneOption: FixTableOption;
        afternoonDiffTimezoneOption: FixTableOption;

        // fixed timezones option
        oneDayFixedTimezoneOption: FixTableOption;
        morningFixedTimezoneOption: FixTableOption;
        afternoonFixedTimezoneOption: FixTableOption;

        // flex rest set option
        oneDayFlexRestSetOption: FixTableOption;
        morningFlexRestSetOption: FixTableOption;
        afternoonFlexRestSetOption: FixTableOption;

        // flow timezones and rest set option
        flowTimezoneOption: FixTableOption;
        flowRestSetOption: FixTableOption;

        // switch
        switchDs: Array<any>;
        flexFixedRestTime: KnockoutObservable<boolean>;
        flowFixedRestTime: KnockoutObservable<boolean>;

        // flag
        isFlex: KnockoutComputed<boolean>;
        isFlow: KnockoutComputed<boolean>;
        isFixed: KnockoutComputed<boolean>;
        isDiffTime: KnockoutComputed<boolean>;
        isDetailMode: KnockoutObservable<boolean>;
        useHalfDay: KnockoutObservable<boolean>;

        // show/hide
        //isFlexOrFlow: KnockoutComputed<boolean>; // a5_2 flex or a5_4 flow *19
        //isTzOfFlexOrFixedOrDiff: KnockoutComputed<boolean>; // ( flex and suru ) or (fix or diff) *23
        isFlowTimezone: KnockoutComputed<boolean>; // flow and suru *24
        isFlowRestTime: KnockoutComputed<boolean>; // flow and nashi *25
        isFlexTimezone: KnockoutComputed<boolean>; // flex and suru *26
        isFlexRestTime: KnockoutComputed<boolean>; // flex and nashi *26
        display27: KnockoutComputed<boolean>; // A23_7 is checked *27

        constructor(valueAccessor: any) {
            let self = this;
            // switch button
            self.switchDs = [
                { code: true, name: nts.uk.resource.getText("KMK003_142") },
                { code: false, name: nts.uk.resource.getText("KMK003_143") }
            ];

            // load data from main setting model
            self.mainSettingModel = valueAccessor.mainSettingModel;
            self.isDetailMode = valueAccessor.isDetailMode;
            self.useHalfDay = valueAccessor.useHalfDay;
            self.loadData();

            // fix table option
            self.setFixedTableOption();

        }

        /**
         * Load data from main screen
         */
        public loadData(): void {
            let self = this;

            let flex = self.mainSettingModel.flexWorkSetting;

            let flexOneday = flex.getHDWtzOneday();
            let flexMorning = flex.getHDWtzMorning();
            let flexAfternoon = flex.getHDWtzAfternoon();

            let fixedOneday = self.mainSettingModel.fixedWorkSetting.getHDWtzOneday();
            let fixedMorning = self.mainSettingModel.fixedWorkSetting.getHDWtzMorning();
            let fixedAfternoon = self.mainSettingModel.fixedWorkSetting.getHDWtzAfternoon();

            // set switch button value
            self.flexFixedRestTime = flex.fixRestTime;
            self.flowFixedRestTime = ko.observable(true); // TODO chua lam flow

            // set flex timezones
            self.oneDayFlexTimezones = flexOneday.restTimezone.fixedRestTimezone.convertedList;
            self.morningFlexTimezones = flexMorning.restTimezone.fixedRestTimezone.convertedList;
            self.afternoonFlexTimezones = flexAfternoon.restTimezone.fixedRestTimezone.convertedList;

            // set fixed timezones
            self.oneDayFixedTimezones = fixedOneday.restTimezone.convertedList;
            self.morningFixedTimezones = fixedMorning.restTimezone.convertedList;
            self.afternoonFixedTimezones = fixedAfternoon.restTimezone.convertedList;

            // set flex rest set value
            self.oneDayFlexRestSet = flexOneday.restTimezone.flowRestTimezone;
            self.morningFlexRestSet = flexMorning.restTimezone.flowRestTimezone;
            self.afternoonFlexRestSet = flexAfternoon.restTimezone.flowRestTimezone;

            // set flow rest set value
            //TODO: chua lam

            // computed value initial
            self.initComputed();
        }

        /**
         * Initial computed.
         */
        private initComputed(): void {
            let self = this;
            let workTimeSetting = self.mainSettingModel.workTimeSetting;

            // set flag
            self.isFlex = workTimeSetting.isFlex;
            self.isFlow = workTimeSetting.isFlow;
            self.isFixed = workTimeSetting.isFixed;
            self.isDiffTime = workTimeSetting.isDiffTime;

            self.isFlowTimezone = ko.computed(() => {
                //return self.isFlow() && self.flowFixedRestTime() == 1;//TODO: hien tai chi lam flex va fixed.
                return false;
            });
            self.isFlowRestTime = ko.computed(() => {
                //return self.isFlow() && self.flowFixedRestTime() == 0; //TODO: hien tai chi lam flex va fixed.
                return false;
            });
            self.isFlexTimezone = ko.computed(() => {
                return self.isFlex() && self.flexFixedRestTime() == true;
            });
            self.isFlexRestTime = ko.computed(() => {
                return self.isFlex() && self.flexFixedRestTime() == false;
            });
        }

        /**
         * Force to add fixed table event listener
         */
        public forceAddFixedTableEvent(): void {
            let self = this;
            self.mainSettingModel.workTimeSetting.workTimeDivision.workTimeDailyAtr.valueHasMutated();
        }

        /**
         * Set fixed table option
         */
        private setFixedTableOption(): void {
            let self = this;

            // flex timezone option
            self.oneDayFlexTimezoneOption = self.getDefaultTimezoneOption();
            self.oneDayFlexTimezoneOption.dataSource = self.oneDayFlexTimezones;
            self.morningFlexTimezoneOption = self.getDefaultTimezoneOption();
            self.morningFlexTimezoneOption.dataSource = self.morningFlexTimezones;
            self.afternoonFlexTimezoneOption = self.getDefaultTimezoneOption();
            self.afternoonFlexTimezoneOption.dataSource = self.afternoonFlexTimezones;

            // difftime timezone option
            self.oneDayDiffTimezoneOption = self.getDefaultTimezoneOption();
            self.oneDayDiffTimezoneOption.dataSource = self.oneDayDiffTimezones;
            self.morningDiffTimezoneOption = self.getDefaultTimezoneOption();
            self.morningDiffTimezoneOption.dataSource = self.morningDiffTimezones;
            self.afternoonDiffTimezoneOption = self.getDefaultTimezoneOption();
            self.afternoonDiffTimezoneOption.dataSource = self.afternoonDiffTimezones;

            // fixed timezone option
            self.oneDayFixedTimezoneOption = self.getDefaultTimezoneOption();
            self.oneDayFixedTimezoneOption.dataSource = self.oneDayFixedTimezones;
            self.morningFixedTimezoneOption = self.getDefaultTimezoneOption();
            self.morningFixedTimezoneOption.dataSource = self.morningFixedTimezones;
            self.afternoonFixedTimezoneOption = self.getDefaultTimezoneOption();
            self.afternoonFixedTimezoneOption.dataSource = self.afternoonFixedTimezones;

            // flex restSet option
            self.oneDayFlexRestSetOption = self.getDefaultRestSetOption();
            self.oneDayFlexRestSetOption.dataSource = self.oneDayFlexRestSet.convertedList;
            self.morningFlexRestSetOption = self.getDefaultRestSetOption();
            self.morningFlexRestSetOption.dataSource = self.morningFlexRestSet.convertedList;
            self.afternoonFlexRestSetOption = self.getDefaultRestSetOption();
            self.afternoonFlexRestSetOption.dataSource = self.afternoonFlexRestSet.convertedList;

            // flow timezone option
            self.flowTimezoneOption = self.getDefaultTimezoneOption();
            self.flowTimezoneOption.dataSource = self.flowTimezones;

            // flow restSet option
            //self.flowRestSetOption = self.getDefaultRestSetOption();
            //self.flowRestSetOption.dataSource = self.flowRestSet.flowRestSets;
        }

        private getDefaultTimezoneOption(): FixTableOption {
            let self = this;
            return {
                maxRow: 10,
                minRow: 0,
                maxRowDisplay: 10,
                isShowButton: true,
                dataSource: ko.observableArray([]),
                isMultipleSelect: true,
                columns: self.getTimezoneColumns(),
                tabindex: 1,
            };
        }

        private getDefaultRestSetOption(): FixTableOption {
            let self = this;
            return {
                maxRow: 5,
                minRow: 0,
                maxRowDisplay: 5,
                isShowButton: true,
                dataSource: ko.observableArray([]),
                isMultipleSelect: true,
                columns: self.getRestSetColumns(),
                tabindex: 1,
                helpImageUrl: 'img/IMG_KMK003_2.png'
            };
        }

        private getRestSetColumns(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_174"),
                    key: "startCol",
                    defaultValue: ko.observable(0),
                    width: 110,
                    template: `<input class="time-edior-column" data-bind="ntsTimeEditor: { constraint: 'AttendanceTime', value: flowPassageTime,
                        required: true, inputFormat: 'time', mode: 'time', enable: true, name: '#[KMK003_174]' }" />`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_176"),
                    key: "endCol",
                    defaultValue: ko.observable(0),
                    width: 110,
                    template: `<input class="time-edior-column" data-bind="ntsTimeEditor: { constraint: 'AttendanceTime', value: flowRestTime,
                        required: true, inputFormat: 'time', mode: 'time', enable: true, name: '#[KMK003_176]' }" />`
                }
            ];
        }

        private getTimezoneColumns(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"),
                    key: "column1",
                    defaultValue: ko.observable({ startTime: 0, endTime: 0 }),
                    width: 243,
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        startConstraint: 'TimeWithDayAttr', endConstraint: 'TimeWithDayAttr',
                        required: true, enable: true, inputFormat: 'time',
                        startTimeNameId: '#[KMK003_163]', endTimeNameId: '#[KMK003_164]',paramId: 'KMK003_20'}"/>`
                }
            ];
        }

    }

    class KMK003A5BindingHandler implements KnockoutBindingHandler {

        constructor() {
        }

        init(element: any,
            valueAccessor: () => any): void {
            let webserviceLocator = nts.uk.request.location.siteRoot
                .mergeRelativePath(nts.uk.request.WEB_APP_NAME["at"] + '/')
                .mergeRelativePath('/view/kmk/003/a5/index.xhtml').serialize();

            let screenModel = new ScreenModel(valueAccessor());

            $(element).load(webserviceLocator, function() {
                ko.cleanNode($(element)[0]);
                ko.applyBindingsToDescendants(screenModel, $(element)[0]);
                _.defer(() => screenModel.forceAddFixedTableEvent());
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A5'] = new KMK003A5BindingHandler();

}
