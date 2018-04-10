module nts.uk.at.view.kmk004.f {
    export module viewmodel {

        export class ScreenModel {
            startWeekList: KnockoutObservableArray<any>;
            startWeek: KnockoutObservable<number>;// F1_2
            isIncludeExtraAggr: KnockoutObservable<boolean>;// F2_3////
            includeExtraAggrOpt: KnockoutObservableArray<any>;// Opt for F2_3 (F2_4, F2_5)
            isIncludeLegalAggr: KnockoutObservable<boolean>; // F2_8
            includeAggrOpt: KnockoutObservableArray<any>;
            isIncludeHolidayAggr: KnockoutObservable<boolean>; // F2_12

            isIncludeExtraExcessOutside: KnockoutObservable<boolean>;// F2_16////
            isIncludeLegalExcessOutside: KnockoutObservable<boolean>; // F2_21
            isIncludeHolidayExcessOutside: KnockoutObservable<boolean>; // F2_25

            // Enable/Disable variables
            isEnableExtraSelection: KnockoutObservable<boolean>;
            isEnableExtraExcessOutside: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                let params: NormalSetParams = nts.uk.ui.windows.getShared("NORMAL_SET_PARAM");

                self.startWeekList = ko.observableArray([
                    new ItemModel(StartWeek.MONDAY, '月曜日'),
                    new ItemModel(StartWeek.TUESDAY, '火曜日'),
                    new ItemModel(StartWeek.WEDNESDAY, '水曜日'),
                    new ItemModel(StartWeek.THURSDAY, '木曜日'),
                    new ItemModel(StartWeek.FRIDAY, '金曜日'),
                    new ItemModel(StartWeek.SATURDAY, '土曜日'),
                    new ItemModel(StartWeek.SUNDAY, '日曜日'),
                    new ItemModel(StartWeek.CLOSURE_STR_DATE, '締め開始日')]);

                self.includeExtraAggrOpt = ko.observableArray([
                    new ItemModelBoolean(true, nts.uk.resource.getText("KMK004_58")),
                    new ItemModelBoolean(false, nts.uk.resource.getText("KMK004_59"))]);

                self.includeAggrOpt = ko.observableArray([
                    new ItemModelBoolean(true, nts.uk.resource.getText("KMK004_63")),
                    new ItemModelBoolean(false, nts.uk.resource.getText("KMK004_64"))]);

                self.startWeek = ko.observable(params && params.startWeek ? params.startWeek : StartWeek.MONDAY);
                self.isIncludeExtraAggr = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isIncludeExtraAggr) ? params.isIncludeExtraAggr : false);
                self.isIncludeLegalAggr = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isIncludeLegalAggr) ? params.isIncludeLegalAggr : false);
                self.isIncludeHolidayAggr = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isIncludeHolidayAggr) ? params.isIncludeHolidayAggr : false);
                self.isIncludeExtraExcessOutside = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isIncludeExtraExcessOutside) ? params.isIncludeExtraExcessOutside : false);
                self.isIncludeLegalExcessOutside = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isIncludeLegalExcessOutside) ? params.isIncludeLegalExcessOutside : false);
                self.isIncludeHolidayExcessOutside = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isIncludeHolidayExcessOutside) ? params.isIncludeHolidayExcessOutside : false);

                // Initialize Enable/Disable variables
                // Enable/Disable Var Depend on F2_3
                self.isEnableExtraSelection = ko.observable(self.isIncludeExtraAggr());
                self.isIncludeExtraAggr.subscribe(function(data: boolean) {
                    if (data) {
                        self.isEnableExtraSelection(true);
                    } else {
                        self.isEnableExtraSelection(false);
                    }
                });
                // Enable/Disable Var Depend on F2_16
                self.isEnableExtraExcessOutside = ko.observable(self.isIncludeExtraExcessOutside());
                self.isIncludeExtraExcessOutside.subscribe(function(data: boolean) {
                    if (data) {
                        self.isEnableExtraExcessOutside(true);
                    } else {
                        self.isEnableExtraExcessOutside(false);
                    }
                });
            }

            /**
             * Decide Data
             */
            public decideData(): void {
                let self = this;
                let param: NormalSetParams = nts.uk.ui.windows.getShared("NORMAL_SET_PARAM");

                let normalSetOutput: NormalSetParams = new NormalSetParams();
                normalSetOutput.startWeek = self.startWeek();
                normalSetOutput.isIncludeExtraAggr = self.isIncludeExtraAggr();
                normalSetOutput.isIncludeLegalAggr = self.isIncludeExtraAggr() ? self.isIncludeLegalAggr() : param.isIncludeLegalAggr;
                normalSetOutput.isIncludeHolidayAggr = self.isIncludeExtraAggr() ? self.isIncludeHolidayAggr() : param.isIncludeHolidayAggr;
                normalSetOutput.isIncludeExtraExcessOutside = self.isIncludeExtraExcessOutside();
                normalSetOutput.isIncludeLegalExcessOutside = self.isIncludeExtraExcessOutside() ? self.isIncludeLegalExcessOutside() : param.isIncludeLegalExcessOutside;
                normalSetOutput.isIncludeHolidayExcessOutside = self.isIncludeExtraExcessOutside() ? self.isIncludeHolidayExcessOutside() : param.isIncludeHolidayExcessOutside;
                nts.uk.ui.windows.setShared("NORMAL_SET_OUTPUT", normalSetOutput, true);
                nts.uk.ui.windows.close();
            }

            /**
             * Event on click cancel button.
             */
            public cancel(): void {
                nts.uk.ui.windows.close();
            }
        }


        /**
         * 週開始
         */
        export class StartWeek {
            static MONDAY = 0;
            static TUESDAY = 1;
            static WEDNESDAY = 2;
            static THURSDAY = 3;
            static FRIDAY = 4;
            static SATURDAY = 5;
            static SUNDAY = 6;
            static CLOSURE_STR_DATE = 7;
        }

        export class ItemModelBoolean {
            code: boolean;
            name: string;

            constructor(code: boolean, name: string) {
                this.code = code;
                this.name = name;
            }
        }

        export class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        /**
         * Normal Setting Params Model
         */
        export class NormalSetParams {
            startWeek: number;
            isIncludeExtraAggr: boolean;
            isIncludeLegalAggr: boolean;
            isIncludeHolidayAggr: boolean;
            isIncludeExtraExcessOutside: boolean;
            isIncludeLegalExcessOutside: boolean;
            isIncludeHolidayExcessOutside: boolean;

            constructor() {
                let self = this;
                self.startWeek = 0;
                self.isIncludeExtraAggr = false;
                self.isIncludeLegalAggr = false;
                self.isIncludeHolidayAggr = false;
                self.isIncludeExtraExcessOutside = false;
                self.isIncludeLegalExcessOutside = false;
                self.isIncludeHolidayExcessOutside = false;
            }
        }
    }
}