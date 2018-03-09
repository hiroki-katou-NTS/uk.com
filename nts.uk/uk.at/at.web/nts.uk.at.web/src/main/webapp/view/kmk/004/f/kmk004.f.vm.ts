module nts.uk.at.view.kmk004.f {

    import UsageUnitSettingDto = service.model.UsageUnitSettingDto;

    export module viewmodel {

        export class ScreenModel {
            //***********
            startWeekList: KnockoutObservableArray<any>;
            startWeek: KnockoutObservable<number>;// F1_2
            isIncludeExtraAggr: KnockoutObservable<boolean>;// F2_3
            isIncludeLegalAggr: KnockoutObservable<boolean>; // F2_8
            isIncludeHolidayAggr: KnockoutObservable<boolean>; // F2_12
            
            isIncludeExtraExcessOutside: KnockoutObservable<boolean>;// F2_16
            isIncludeLegalExcessOutside: KnockoutObservable<boolean>; // F2_21
            isIncludeHolidayExcessOutside: KnockoutObservable<boolean>; // F2_25

            constructor() {
                let self = this;
//                let params: NormalSetParams = nts.uk.ui.windows.getShared("NormalSetParams");
                let params: NormalSetParams = new NormalSetParams();
                self.startWeekList = ko.observableArray([new ItemModel(2, '月曜日'),
                    new ItemModel(3, '火曜日'),
                    new ItemModel(4, '水曜日'),
                    new ItemModel(5, '木曜日'),
                    new ItemModel(6, '金曜日'),
                    new ItemModel(7, '土曜日'),
                    new ItemModel(0, '日曜日'),
                    new ItemModel(1, '締め開始日')]);
                self.startWeek = ko.observable(params.startWeek ? params.startWeek : StartWeek.MONDAY);
                self.isIncludeExtraAggr = ko.observable(params.isIncludeExtraAggr ? params.isIncludeExtraAggr : false);
                self.isIncludeLegalAggr = ko.observable(params.isIncludeLegalAggr ? params.isIncludeLegalAggr : false);
                self.isIncludeHolidayAggr = ko.observable(params.isIncludeHolidayAggr ? params.isIncludeHolidayAggr : false);
                self.isIncludeExtraExcessOutside = ko.observable(params.isIncludeExtraExcessOutside ? params.isIncludeExtraExcessOutside : false);
                self.isIncludeLegalExcessOutside = ko.observable(params.isIncludeLegalExcessOutside ? params.isIncludeLegalExcessOutside : false);
                self.isIncludeHolidayExcessOutside = ko.observable(params.isIncludeHolidayExcessOutside ? params.isIncludeHolidayExcessOutside : false);
            }

            public decideData(): void {
                let self = this;
                nts.uk.ui.windows.setShared("NormalSet_Output", {
                    startWeek: self.startWeek(),
                    isIncludeExtraAggr: self.isIncludeExtraAggr(),
                    isIncludeLegalAggr: self.isIncludeLegalAggr(),
                    isIncludeHolidayAggr: self.isIncludeHolidayAggr(),
                    isIncludeExtraExcessOutside: self.isIncludeExtraExcessOutside(),
                    isIncludeLegalExcessOutside: self.isIncludeLegalExcessOutside(),
                    isIncludeHolidayExcessOutside: self.isIncludeHolidayExcessOutside()
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