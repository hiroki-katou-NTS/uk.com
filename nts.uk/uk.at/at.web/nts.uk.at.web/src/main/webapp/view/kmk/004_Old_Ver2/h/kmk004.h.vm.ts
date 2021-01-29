module nts.uk.at.view.kmk004.h {
    export module viewmodel {

        export class ScreenModel {
            //***********
            strMonth: KnockoutObservable<number>;
            period: KnockoutObservable<number>;
            repeatCls: KnockoutObservable<boolean>;
            
            startWeekList: KnockoutObservableArray<any>;
            startWeek: KnockoutObservable<number>;// H1_2
            isIncludeExtraAggr: KnockoutObservable<boolean>;// H3_3
            includeExtraAggrOpt: KnockoutObservableArray<any>;// Opt for H3_3 (H3_4, H3_5)
            isIncludeLegalAggr: KnockoutObservable<boolean>; // H3_8
            includeAggrOpt: KnockoutObservableArray<any>;
            isIncludeHolidayAggr: KnockoutObservable<boolean>; // H3_12
            
            isIncludeExtraExcessOutside: KnockoutObservable<boolean>;// H3_16
            isIncludeLegalExcessOutside: KnockoutObservable<boolean>; // H3_21
            isIncludeHolidayExcessOutside: KnockoutObservable<boolean>; // H3_25
            
            // Enable/Disable variables
            isEnableExtraSelection: KnockoutObservable<boolean>;
            isEnableExtraExcessOutside: KnockoutObservable<boolean>;

            constructor() {
                let self = this;
                let params: DeformSetParams = nts.uk.ui.windows.getShared("DEFORM_SET_PARAM");
                
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
                
                // Initialize variables
                self.strMonth = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.strMonth) ? params.strMonth : (moment(new Date()).toDate().getMonth()));
                self.period = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.period) ? params.period : 1);
                self.repeatCls = ko.observable(params && !nts.uk.util.isNullOrUndefined(params.repeatCls) ? params.repeatCls : false);
                self.startWeek = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.startWeek) ? params.startWeek : StartWeek.MONDAY);
                self.isIncludeExtraAggr = ko.observable(params && !nts.uk.util.isNullOrUndefined(params.isIncludeExtraAggr) ? params.isIncludeExtraAggr : false);
                self.isIncludeLegalAggr = ko.observable(params && !nts.uk.util.isNullOrUndefined(params.isIncludeLegalAggr) ? params.isIncludeLegalAggr : false);
                self.isIncludeHolidayAggr = ko.observable(params && !nts.uk.util.isNullOrUndefined(params.isIncludeHolidayAggr) ? params.isIncludeHolidayAggr : false);
                self.isIncludeExtraExcessOutside = ko.observable(params && !nts.uk.util.isNullOrUndefined(params.isIncludeExtraExcessOutside) ? params.isIncludeExtraExcessOutside : false);
                self.isIncludeLegalExcessOutside = ko.observable(params && !nts.uk.util.isNullOrUndefined(params.isIncludeLegalExcessOutside) ? params.isIncludeLegalExcessOutside : false);
                self.isIncludeHolidayExcessOutside = ko.observable(params && !nts.uk.util.isNullOrUndefined(params.isIncludeHolidayExcessOutside) ? params.isIncludeHolidayExcessOutside : false);
                
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
                let params: DeformSetParams = nts.uk.ui.windows.getShared("DEFORM_SET_PARAM");
                let deformParams: DeformSetParams = new DeformSetParams();
                deformParams.startWeek = self.startWeek();
                deformParams.isIncludeExtraAggr = self.isIncludeExtraAggr();
                deformParams.isIncludeLegalAggr = self.isIncludeExtraAggr() ? self.isIncludeLegalAggr() : params.isIncludeLegalAggr;
                deformParams.isIncludeHolidayAggr = self.isIncludeExtraAggr() ? self.isIncludeHolidayAggr() : params.isIncludeHolidayAggr;
                deformParams.isIncludeExtraExcessOutside = self.isIncludeExtraExcessOutside();
                deformParams.isIncludeLegalExcessOutside = self.isIncludeExtraExcessOutside() ? self.isIncludeLegalExcessOutside() : params.isIncludeLegalExcessOutside;
                deformParams.isIncludeHolidayExcessOutside = self.isIncludeExtraExcessOutside() ? self.isIncludeHolidayExcessOutside() : params.isIncludeHolidayExcessOutside;
                deformParams.strMonth = self.strMonth();
                deformParams.period = self.period();
                deformParams.repeatCls = self.repeatCls();
                
                nts.uk.ui.windows.setShared("DEFORM_SET_OUTPUT", deformParams , true);
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
        
        /**
         * Item Model
         */
        export class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        export class ItemModelBoolean {
            code: boolean;
            name: string;

            constructor(code: boolean, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        /**
         * Deformed Labor Setting Params Model
         */
        export class DeformSetParams {
            strMonth: number;
            period: number;
            repeatCls: boolean;
            startWeek: number;
            isIncludeExtraAggr: boolean;
            isIncludeLegalAggr: boolean;
            isIncludeHolidayAggr: boolean;
            isIncludeExtraExcessOutside: boolean;
            isIncludeLegalExcessOutside: boolean;
            isIncludeHolidayExcessOutside: boolean;
            
            constructor() {
                let self = this;
                self.strMonth = moment(new Date()).toDate().getMonth();
                self.period = 1;
                self.repeatCls = false;
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