module a5 {
    import FixTableOption = nts.uk.at.view.kmk003.base.fixedtable.FixTableOption;
    import FlowWorkRestTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.common.FlowWorkRestTimezoneModel;
    import FixHalfDayWorkTimezoneModel = nts.uk.at.view.kmk003.a.viewmodel.fixedset.FixHalfDayWorkTimezoneModel;
    import FlexHalfDayWorkTimeModel = nts.uk.at.view.kmk003.a.viewmodel.flexset.FlexHalfDayWorkTimeModel;

    class ScreenModel {

        // flex or difftime or fix timezones
        oneDayTimezones: KnockoutObservableArray<any>;
        morningTimezones: KnockoutObservableArray<any>;
        afternoonTimezones: KnockoutObservableArray<any>;

        // flex restSet
        oneDayRestSets: KnockoutObservableArray<any>;
        morningRestSets: KnockoutObservableArray<any>;
        afternoonRestSets: KnockoutObservableArray<any>;
        oneDayHereAfterRestSet: any;
        morningHereAfterRestSet: any;
        afternoonHereAfterRestSet: any;

        // flow timezones
        flowTimezones: KnockoutObservableArray<any>;

        // flow restSet
        flowRestSets: KnockoutObservableArray<any>;
        hereAfterRestSet: any;

        // ntsFixTableCustom options
        // timezone option
        oneDayTimezoneOption: FixTableOption;
        morningTimezoneOption: FixTableOption;
        afternoonTimezoneOption: FixTableOption;
        // restSet flex option
        oneDayRestSetOption: FixTableOption;
        morningRestSetOption: FixTableOption;
        afternoonRestSetOption: FixTableOption;
        // flow timezone option
        flowTimezoneOption: FixTableOption;
        flowRestSetOption: FixTableOption;


        // switch
        switchDs: Array<any>;
        fixedRestTime: KnockoutObservable<number>; // 0 = not use, 1 = use

        // checkbox
        checkOneDay: KnockoutObservable<boolean>;
        checkMorning: KnockoutObservable<boolean>;
        checkAfternoon: KnockoutObservable<boolean>;

        // flag
        isFlex: KnockoutObservable<boolean>;
        isFlow: KnockoutObservable<boolean>;
        isFixed: KnockoutObservable<boolean>;
        isDiffTime: KnockoutObservable<boolean>;

        // show/hide
        isFlexOrFlow: KnockoutComputed<boolean>; // a5_2 flex or a5_4 flow *19
        isTzOfFlexOrFixedOrDiff: KnockoutComputed<boolean>; // ( flex and suru ) or (fix or diff) *23
        isFlowTimezone: KnockoutComputed<boolean>; // flow and suru *24
        isFlowRestTime: KnockoutComputed<boolean>; // flow and nashi *25
        isFlexRestTime: KnockoutComputed<boolean>; // flex and nashi *26
        display27: KnockoutComputed<boolean>; // A23_7 is checked *27

        constructor(valueAccessor: any) {
            let self = this;

            // nts fix table data source
            self.oneDayTimezones = ko.observableArray([]);
            self.morningTimezones = ko.observableArray([]);
            self.afternoonTimezones = ko.observableArray([]);
            self.oneDayRestSets = ko.observableArray([]);
            self.morningRestSets = ko.observableArray([]);
            self.afternoonRestSets = ko.observableArray([]);
            self.oneDayHereAfterRestSet = ko.observableArray([]);
            self.morningHereAfterRestSet = ko.observableArray([]);
            self.afternoonHereAfterRestSet = ko.observableArray([]);

            // switch button
            self.switchDs = [
                { code: 1, name: nts.uk.resource.getText("KMK003_142") }, // used
                { code: 0, name: nts.uk.resource.getText("KMK003_143") } // not used
            ];
            self.fixedRestTime = ko.observable(0);

            // init computed
            self.initComputed(valueAccessor);

            // check box
            self.checkOneDay = ko.observable(false);
            self.checkMorning = ko.observable(false);
            self.checkAfternoon = ko.observable(false);

            // fix table option
            self.setFixedTableOption();

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
            let settingWorkFrom = valueAccessor.settingWorkFrom; // flex = 2
            let settingMethod = valueAccessor.settingMethod; // fix = 1, diff = 2, flow = 3

            let fixHalfDayWorkTimes: Array<FixHalfDayWorkTimezoneModel> = [];
            let diffHalfDayWorkTimes: Array<FixHalfDayWorkTimezoneModel> = []; //TODO chua co model
            let flexHalfDayWorkTimes: Array<FlexHalfDayWorkTimeModel> = []
            let flowWorkRestTimezone: FlowWorkRestTimezoneModel = new FlowWorkRestTimezoneModel();

            self.isFlex = ko.computed(() => {
                return true;
            });
            self.isFlow = ko.computed(() => {
                return false;
            });
            self.isFixed = ko.computed(() => {
                return false;
            });
            self.isDiffTime = ko.computed(() => {
                return false;
            });

            self.isFlexOrFlow = ko.computed(() => {
                return self.isFlex() || self.isFlow();
            });
            self.isTzOfFlexOrFixedOrDiff = ko.computed(() => {
                return (self.isFlex() && self.fixedRestTime() == 0) || self.isFixed() || self.isDiffTime();
            });
            self.isFlowTimezone = ko.computed(() => {
                return self.isFlow() && self.fixedRestTime() == 1;
            });
            self.isFlowRestTime = ko.computed(() => {
                return self.isFlow() && self.fixedRestTime() == 0;
            });
            self.isFlexRestTime = ko.computed(() => {
                return self.isFlex() && self.fixedRestTime() == 0;
            });
        }

        /**
         * Set fixed table option
         */
        private setFixedTableOption(): void {
            const maxRowTimezone = 10;
            const minRowDefault = 0;
            const maxRowRestSet = 5;
            let self = this;

            // timezone option
            self.oneDayTimezoneOption = {
                maxRow: maxRowTimezone,
                minRow: minRowDefault,
                maxRowDisplay: maxRowTimezone,
                isShowButton: true,
                dataSource: self.oneDayTimezones,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.morningTimezoneOption = {
                maxRow: maxRowTimezone,
                minRow: minRowDefault,
                maxRowDisplay: maxRowTimezone,
                isShowButton: true,
                dataSource: self.morningTimezones,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.afternoonTimezoneOption = {
                maxRow: maxRowTimezone,
                minRow: minRowDefault,
                maxRowDisplay: maxRowTimezone,
                isShowButton: true,
                dataSource: self.afternoonTimezones,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };

            // flex restSet option
            self.oneDayRestSetOption = {
                maxRow: maxRowRestSet,
                minRow: minRowDefault,
                maxRowDisplay: maxRowRestSet,
                isShowButton: true,
                dataSource: self.oneDayRestSets,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.morningRestSetOption = {
                maxRow: maxRowRestSet,
                minRow: minRowDefault,
                maxRowDisplay: maxRowRestSet,
                isShowButton: true,
                dataSource: self.morningRestSets,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.afternoonRestSetOption = {
                maxRow: maxRowRestSet,
                minRow: minRowDefault,
                maxRowDisplay: maxRowRestSet,
                isShowButton: true,
                dataSource: self.afternoonRestSets,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };

            // flow timezone option
            self.flowTimezoneOption= {
                maxRow: maxRowTimezone,
                minRow: minRowDefault,
                maxRowDisplay: maxRowTimezone,
                isShowButton: true,
                dataSource: self.afternoonRestSets,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };

            // flow restSet option
            self.flowRestSetOption = {
                maxRow: maxRowRestSet,
                minRow: minRowDefault,
                maxRowDisplay: maxRowRestSet,
                isShowButton: true,
                dataSource: self.afternoonRestSets,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
        }

        private getColumnSettingDefault(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"),
                    key: "columnOneDay1",
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                    width: 243,
                    template: `<div data-bind="ntsTimeRangeEditor: { 
                        required: true, enable: true, inputFormat: 'time'}"/>`
                }
            ];
        }

        private getColumnSettingSingle(): Array<any> {
            let self = this;
            return [
                {
                    headerText: nts.uk.resource.getText("KMK003_54"),
                    key: "columnOneDay1",
                    defaultValue: ko.observable({ startTime: "10:00", endTime: "12:00" }),
                    width: 243,
                    template: `<div data-bind="ntsTimeRangeEditor: { 
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
