module a5 {
    class ScreenModel {

        oneDayOption: any;
        morningOption: any;
        afternoonOption: any;

        oneDayFlexOption: any;
        morningFlexOption: any;
        afternoonFlexOption: any;

        oneDayFlexOption2: any;
        morningFlexOption2: any;
        afternoonFlexOption2: any;

        oneDay: KnockoutObservableArray<any>;
        morning: KnockoutObservableArray<any>;
        afternoon: KnockoutObservableArray<any>;
        oneDayFlex: KnockoutObservableArray<any>;
        morningFlex: KnockoutObservableArray<any>;
        afternoonFlex: KnockoutObservableArray<any>;
        oneDayFlex2: KnockoutObservableArray<any>;
        morningFlex2: KnockoutObservableArray<any>;
        afternoonFlex2: KnockoutObservableArray<any>;

        // switch
        switchDs: Array<any>;
        switchVl: KnockoutObservable<any>;

        // checkbox
        checkOneDay: KnockoutObservable<boolean>;
        checkMorning: KnockoutObservable<boolean>;
        checkAfternoon: KnockoutObservable<boolean>;

        // flag
        settingMethod: KnockoutObservable<any>;
        settingWorkFrom: KnockoutObservable<any>;

        // enable
        display19: KnockoutComputed<boolean>; // a5_2 flex or a5_4 flow
        display23: KnockoutComputed<boolean>; // ( flex and suru ) or (fix or diff)
        display24: KnockoutComputed<boolean>; // flow and suru
        display25: KnockoutComputed<boolean>; // flow and nashi
        display26: KnockoutComputed<boolean>; // flex and nashi

        constructor(valueAccessor: any) {
            let self = this;

            // nts fix table data source
            self.oneDay = ko.observableArray([]);
            self.morning = ko.observableArray([]);
            self.afternoon = ko.observableArray([]);
            self.oneDayFlex = ko.observableArray([]);
            self.morningFlex = ko.observableArray([]);
            self.afternoonFlex = ko.observableArray([]);
            self.oneDayFlex2 = ko.observableArray([]);
            self.morningFlex2 = ko.observableArray([]);
            self.afternoonFlex2 = ko.observableArray([]);

            // init computed
            self.initComputed(valueAccessor);

            self.switchDs = [
                { code: 1, name: nts.uk.resource.getText("KMK003_142") }, // used
                { code: 0, name: nts.uk.resource.getText("KMK003_143") } // not used
            ];
            self.switchVl = ko.observable(0);

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
//                $('#ntsft-a5-oneday').ntsFixTableCustom(self.oneDayOption);
//                $('#ntsft-a5-morning').ntsFixTableCustom(self.morningOption);
//                $('#ntsft-a5-afternoon').ntsFixTableCustom(self.afternoonOption);
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

        private initComputed(valueAccessor: any): void {
            let self = this;
            let settingWorkFrom = valueAccessor.settingWorkFrom; // flex = 2
            let settingMethod = valueAccessor.settingMethod; // fix = 1, diff = 2, flow = 3

            self.display19 = ko.computed(() => {
                if (settingWorkFrom() == '2' || settingMethod() == '3') {
                    return true;
                }
                return false;
            });
            self.display23 = ko.computed(() => {
                if (settingMethod() == 1
                    || settingMethod() == 2
                    || (settingWorkFrom() == '2' && self.switchVl() == 1)
                ) {
                    return true;
                }
                return false;
            });
            self.display24 = ko.computed(() => {
                return settingMethod() == 3 && self.switchVl() == 1;
            });
            self.display25 = ko.computed(() => {
                return settingMethod() == 3 && self.switchVl() == 0;
            });
            self.display26 = ko.computed(() => {
                return settingWorkFrom() == 2 && self.switchVl() == 0;
            });
        }

        private setFixedTableOption(): void {
            const maxRowDefault = 1;
            const minRowDefault = 1;
            const maxRowDisplayDefault = 5;

            let self = this;
            self.oneDayOption = {
                maxRow: maxRowDefault,
                minRow: minRowDefault,
                maxRowDisplay: maxRowDisplayDefault,
                isShowButton: true,
                dataSource: self.oneDay,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.morningOption = {
                maxRow: maxRowDefault,
                minRow: minRowDefault,
                maxRowDisplay: maxRowDisplayDefault,
                isShowButton: true,
                dataSource: self.morning,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.afternoonOption = {
                maxRow: maxRowDefault,
                minRow: minRowDefault,
                maxRowDisplay: maxRowDisplayDefault,
                isShowButton: true,
                dataSource: self.afternoon,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };

            // flex
            self.oneDayFlexOption = {
                maxRow: maxRowDefault,
                minRow: minRowDefault,
                maxRowDisplay: maxRowDisplayDefault,
                isShowButton: true,
                dataSource: self.oneDayFlex,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.morningFlexOption = {
                maxRow: maxRowDefault,
                minRow: minRowDefault,
                maxRowDisplay: maxRowDisplayDefault,
                isShowButton: true,
                dataSource: self.morningFlex,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.afternoonFlexOption = {
                maxRow: maxRowDefault,
                minRow: minRowDefault,
                maxRowDisplay: maxRowDisplayDefault,
                isShowButton: true,
                dataSource: self.afternoonFlex,
                isMultipleSelect: true,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };

            // single no button list
            self.oneDayFlexOption2 = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: self.oneDayFlex2,
                isMultipleSelect: false,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.morningFlexOption2 = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: self.morningFlex2,
                isMultipleSelect: false,
                columns: self.getColumnSettingDefault(),
                tabindex: -1
            };
            self.afternoonFlexOption2 = {
                maxRow: 1,
                minRow: 1,
                maxRowDisplay: 1,
                isShowButton: false,
                dataSource: self.afternoonFlex2,
                isMultipleSelect: false,
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
            valueAccessor: () => any,
            allBindingsAccessor: () => any,
            viewModel: any,
            bindingContext: KnockoutBindingContext): void {
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

        update(element: any,
            valueAccessor: () => any,
            allBindingsAccessor: () => any,
            viewModel: any,
            bindingContext: KnockoutBindingContext): void {
        }

    }
    ko.bindingHandlers['ntsKMK003A5'] = new KMK003A5BindingHandler();

}
