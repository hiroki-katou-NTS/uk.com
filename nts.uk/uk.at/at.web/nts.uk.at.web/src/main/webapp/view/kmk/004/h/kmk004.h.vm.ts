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
//                let params: NormalSetParams = nts.uk.ui.windows.getShared("DeformSetParams");
                let params: DeformSetParams = new DeformSetParams();
                
                
                
                self.startWeekList = ko.observableArray([new ItemModel(2, '月曜日'),
                    new ItemModel(3, '火曜日'),
                    new ItemModel(4, '水曜日'),
                    new ItemModel(5, '木曜日'),
                    new ItemModel(6, '金曜日'),
                    new ItemModel(7, '土曜日'),
                    new ItemModel(0, '日曜日'),
                    new ItemModel(1, '締め開始日')]);
                
                self.includeExtraAggrOpt = ko.observableArray([
                    new ItemModel(1, nts.uk.resource.getText("KMK004_58")),
                    new ItemModel(0, nts.uk.resource.getText("KMK004_59"))]);
                
                self.includeAggrOpt = ko.observableArray([
                    new ItemModel(1, nts.uk.resource.getText("KMK004_63")),
                    new ItemModel(0, nts.uk.resource.getText("KMK004_64"))]);
                
                // Initialize vars
                self.strMonth = ko.observable(params.strMonth ? params.strMonth : (moment(new Date()).toDate().getMonth()));
                self.period = ko.observable(params.period ? params.period : 1);
                self.repeatCls = ko.observable(params.repeatCls ? params.repeatCls : false);
                self.startWeek = ko.observable(params.startWeek ? params.startWeek : StartWeek.MONDAY);
                self.isIncludeExtraAggr = ko.observable(params.isIncludeExtraAggr ? params.isIncludeExtraAggr : false);
                self.isIncludeLegalAggr = ko.observable(params.isIncludeLegalAggr ? params.isIncludeLegalAggr : false);
                self.isIncludeHolidayAggr = ko.observable(params.isIncludeHolidayAggr ? params.isIncludeHolidayAggr : false);
                self.isIncludeExtraExcessOutside = ko.observable(params.isIncludeExtraExcessOutside ? params.isIncludeExtraExcessOutside : false);
                self.isIncludeLegalExcessOutside = ko.observable(params.isIncludeLegalExcessOutside ? params.isIncludeLegalExcessOutside : false);
                self.isIncludeHolidayExcessOutside = ko.observable(params.isIncludeHolidayExcessOutside ? params.isIncludeHolidayExcessOutside : false);
                
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

            public decideData(): void {
                let self = this;
                nts.uk.ui.windows.setShared("Deform_Set_Output", {
                    startWeek: self.startWeek(),
                    isIncludeExtraAggr: self.isIncludeExtraAggr(),
                    isIncludeLegalAggr: self.isIncludeLegalAggr(),
                    isIncludeHolidayAggr: self.isIncludeHolidayAggr(),
                    isIncludeExtraExcessOutside: self.isIncludeExtraExcessOutside(),
                    isIncludeLegalExcessOutside: self.isIncludeLegalExcessOutside(),
                    isIncludeHolidayExcessOutside: self.isIncludeHolidayExcessOutside(),
                    strMonth: self.strMonth(),
                    period: self.period(),
                    repeatCls: self.repeatCls()
                } , true);
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
            static MONDAY = 2;
            static TUESDAY = 3;
            static WEDNESDAY = 4;
            static THURSDAY = 5;
            static FRIDAY = 6;
            static SATURDAY = 7;
            static SUNDAY = 0;
            static CLOSURE_STR_DATE = 1;
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