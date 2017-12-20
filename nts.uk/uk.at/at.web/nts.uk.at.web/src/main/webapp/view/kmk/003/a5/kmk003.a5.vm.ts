module a5 {
    import FixTableOption = nts.uk.at.view.kmk003.base.fixedtable.FixTableOption;
    import FlowRestTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowRestTimezoneModel;
    import FlowRestSettingModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowRestSettingModel;
    import DeductionTimeModel = nts.uk.at.view.kmk003.a.viewmodel.common.DeductionTimeModel;
    import DeductionTimeDto = nts.uk.at.view.kmk003.a.service.model.common.DeductionTimeDto;
    import FixHalfDayWorkTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixHalfDayWorkTimezoneModel;
    import DiffTimeHalfDayWorkTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.difftimeset.DiffTimeHalfDayWorkTimezoneModel;
    import FlexHalfDayWorkTimeModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.FlexHalfDayWorkTimeModel;
    import MainSettingModel = nts.uk.at.view.kmk003.a.viewmodel.MainSettingModel;

    class ScreenModel {

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
        oneDayFlexRestSets: KnockoutObservableArray<FlowRestSettingModel>;
        oneDayAfterRestSet: FlowRestSettingModel;
        oneDayAfterRestSetUse: KnockoutObservable<boolean>;
        morningFlexRestSets: KnockoutObservableArray<FlowRestSettingModel>;
        morningAfterRestSet: FlowRestSettingModel;
        morningAfterRestSetUse: KnockoutObservable<boolean>;
        afternoonFlexRestSets: KnockoutObservableArray<FlowRestSettingModel>;
        afternoonAfterRestSet: FlowRestSettingModel;
        afternoonAfterRestSetUse: KnockoutObservable<boolean>;

        // flow restSet
        flowRestSets: KnockoutObservableArray<FlowRestSettingModel>;
        afterFlowRestSet: FlowRestTimezoneModel;
        flowRestSetUse: KnockoutObservable<boolean>;

        // ntsFixTableCustom options

        // flex timezones option
        oneDayFlexTimezoneOption: FixTableOption;
        morningFlexTimezoneOption: FixTableOption;
        afternoonFlexTimezoneOption: FixTableOption;

        // flex timezones option
        oneDayDiffTimezoneOption: FixTableOption;
        morningDiffTimezoneOption: FixTableOption;
        afternoonDiffTimezoneOption: FixTableOption;

        // flex timezones option
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
        flexFixedRestTime: KnockoutObservable<number>; // 0 = not use, 1 = use
        flowFixedRestTime: KnockoutObservable<number>; // 0 = not use, 1 = use

        // flag
        isFlex: KnockoutObservable<boolean>;
        isFlow: KnockoutObservable<boolean>;
        isFixed: KnockoutObservable<boolean>;
        isDiffTime: KnockoutObservable<boolean>;

        // show/hide
        //isFlexOrFlow: KnockoutComputed<boolean>; // a5_2 flex or a5_4 flow *19
        //isTzOfFlexOrFixedOrDiff: KnockoutComputed<boolean>; // ( flex and suru ) or (fix or diff) *23
        isFlowTimezone: KnockoutComputed<boolean>; // flow and suru *24
        isFlowRestTime: KnockoutComputed<boolean>; // flow and nashi *25
        isFlexRestTime: KnockoutComputed<boolean>; // flex and nashi *26
        display27: KnockoutComputed<boolean>; // A23_7 is checked *27

        constructor(valueAccessor: any) {
            let self = this;

            // nts fix table data source
            self.oneDayFlexTimezones = ko.observableArray([]);self.oneDayFlexTimezones.subscribe(vl => console.log(vl));
            self.morningFlexTimezones = ko.observableArray([]);
            self.afternoonFlexTimezones = ko.observableArray([]);
            self.oneDayFlexRestSets = ko.observableArray([]);self.oneDayFlexTimezones.subscribe(vl => console.log(vl));
            self.morningFlexRestSets = ko.observableArray([]);
            self.afternoonFlexRestSets = ko.observableArray([]);
            self.oneDayFixedTimezones = ko.observableArray([]);self.oneDayFixedTimezones.subscribe(vl => console.log(vl));
            self.morningFixedTimezones = ko.observableArray([]);
            self.afternoonFixedTimezones = ko.observableArray([]);
            self.oneDayDiffTimezones = ko.observableArray([]);
            self.morningDiffTimezones = ko.observableArray([]);
            self.afternoonDiffTimezones = ko.observableArray([]);
            self.flowTimezones = ko.observableArray([]);
            self.flowRestSets = ko.observableArray([]);

            // checkbox
            self.oneDayAfterRestSetUse = ko.observable(false);
            self.morningAfterRestSetUse = ko.observable(false);
            self.afternoonAfterRestSetUse = ko.observable(false);
            self.flowRestSetUse = ko.observable(false);

            // switch button
            self.switchDs = [
                { code: 1, name: nts.uk.resource.getText("KMK003_142") }, // used
                { code: 0, name: nts.uk.resource.getText("KMK003_143") } // not used
            ];
            self.flexFixedRestTime = ko.observable(0);
            self.flowFixedRestTime = ko.observable(0);
            self.flexFixedRestTime.subscribe(vl => console.log(vl));

            // init computed
            self.initComputed(valueAccessor);

            // fix table option
            self.setFixedTableOption();

        }

        public toTimeRangeArray(before: DeductionTimeModel): any {
            return {startTime: before.start(), endTime: before.end()};
        }

        public toDeductionTimeDto(before: any): DeductionTimeDto {
            let dto =  <DeductionTimeDto>{};
            dto.start = before.startTime;
            dto.end = before.endTime;
            return dto;
        }

        public loadData(): void {
            let self = this;
            // TODO need to check
//            if (self.display23()) {
//                $('#ntsft-a5-oneday').ntsFixTableCustom(self.oneDayTimezoneOption);
//                $('#ntsft-a5-morning').ntsFixTableCustom(self.morningTimezoneOption);
//                $('#ntsft-a5-afternoon').ntsFixTableCustom(self.afternoonTimezoneOption);
//            }
//            if (self.display26()) {
//                $('#ntsft-a5-onedayFlex').ntsFixTableCustom(self.oneDayFlexOption);
//                $('#ntsft-a5-morningFlex').ntsFixTableCustom(self.morningFlexOption);
//                $('#ntsft-a5-afternoonFlex').ntsFixTableCustom(self.afternoonFlexOption);
//
//                // single list
//                $('#ntsft-a5-onedayFlex2').ntsFixTableCustom(self.oneDayFlexOption2);
//                $('#ntsft-a5-morningFlex2').ntsFixTableCustom(self.morningFlexOption2);
//                $('#ntsft-a5-afternoonFlex2').ntsFixTableCustom(self.afternoonFlexOption2);
//            }
        }

        /**
         * Initial computed.
         */
        private initComputed(valueAccessor: any): void {
            let self = this;
            let workTimeAtr = valueAccessor.workTimeAtr; // flex = 1
            let workTimeMethod = valueAccessor.workTimeMethod; // fix = 1, diff = 2, flow = 3
            workTimeAtr.subscribe(vl => console.log(vl));
            workTimeMethod.subscribe(vl => console.log(vl));

            let ms: MainSettingModel = valueAccessor.mainSettingModel;
            let test = ms.fixedWorkSetting.getHDWtzOneday();
//            test.restTimezone.lstTimezone.subscribe(newList => {
//                let mapped = _.map(newList, item => self.toTimeRangeArray(item));
//                self.oneDayFixedTimezones(mapped);
//            });
//            self.oneDayFixedTimezones.subscribe(newList => {
//                let mapped = _.map(newList, item => self.toDeductionTimeDto(item));
//                test.restTimezone.updateData({lstTimezone: mapped});
//            });
            //TODO testing

            let flexHdWtOneday: FlexHalfDayWorkTimeModel;
            let flexHdWtMorning: FlexHalfDayWorkTimeModel;
            let flexHdWtAfternoon: FlexHalfDayWorkTimeModel;

            let fixedHdWtOneday: FixHalfDayWorkTimezoneModel;
            let fixedHdWtMorning: FixHalfDayWorkTimezoneModel;
            let fixedHdWtAfternoon: FixHalfDayWorkTimezoneModel;

            let diffHdWtOneday: DiffTimeHalfDayWorkTimezoneModel;
            let diffHdWtMorning: DiffTimeHalfDayWorkTimezoneModel;
            let diffHdWtAfternoon: DiffTimeHalfDayWorkTimezoneModel;

            // get one day, morning, afternoon;

            self.isFlex = ko.computed(() => {
                return workTimeAtr() == 1;
            });
            self.isFlow = ko.computed(() => {
                return workTimeAtr() != 1 && workTimeMethod() == 2;
            });
            self.isFixed = ko.computed(() => {
                return workTimeAtr() != 1 && workTimeMethod() == 0;
            });
            self.isDiffTime = ko.computed(() => {
                return workTimeAtr() != 1 && workTimeMethod() == 1;
            });

            self.isFlowTimezone = ko.computed(() => {
                return self.isFlow() && self.flowFixedRestTime() == 1;
            });
            self.isFlowRestTime = ko.computed(() => {
                return self.isFlow() && self.flowFixedRestTime() == 0;
            });
            self.isFlexRestTime = ko.computed(() => {
                return self.isFlex() && self.flexFixedRestTime() == 0;
            });
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
            self.oneDayFlexRestSetOption.dataSource = self.oneDayFlexRestSets;
            self.morningFlexRestSetOption = self.getDefaultRestSetOption();
            self.morningFlexRestSetOption.dataSource = self.morningFlexRestSets;
            self.afternoonFlexRestSetOption = self.getDefaultRestSetOption();
            self.afternoonFlexRestSetOption.dataSource = self.afternoonFlexRestSets;

            // flow timezone option
            self.flowTimezoneOption = self.getDefaultTimezoneOption();
            self.flowTimezoneOption.dataSource = self.flowTimezones;

            // flow restSet option
            self.flowRestSetOption = self.getDefaultRestSetOption();
            self.flowRestSetOption.dataSource = self.flowRestSets;
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
                tabindex: -1
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
                tabindex: -1
            };
        }

        private getRestSetColumns(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_174"),
                    key: "startCol",
                    //defaultValue: ko.observable(0),
                    width: 200,
                    template: `<input data-bind="ntsTimeEditor: { constraint: 'AttendanceTime', value: flowRestTime,
                        required: true, inputFormat: 'time', mode: 'time', enable: true }" />`
                },
                {
                    headerText: nts.uk.resource.getText("KMK003_176"),
                    key: "endCol",
                    //defaultValue: ko.observable(0),
                    width: 200,
                    template: `<input data-bind="ntsTimeEditor: { constraint: 'AttendanceTime', value: flowPassageTime,
                        required: true, inputFormat: 'time', mode: 'time', enable: true }" />`
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
                        required: true, enable: true, inputFormat: 'time'}"/>`
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
                screenModel.loadData();
            });
        }

    }
    ko.bindingHandlers['ntsKMK003A5'] = new KMK003A5BindingHandler();

}
