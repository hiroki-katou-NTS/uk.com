module nts.uk.at.view.kmk004.g {
    export module viewmodel {

        export class ScreenModel {
            //***********
            isIncludeOverTime: KnockoutObservable<boolean>;// G1_2
            includeOverTimeOpt: KnockoutObservableArray<any>;// Opt for G1_2 (G1_3, G1_4)
            
            shortageSetting: KnockoutObservable<number>; // G2_2
            shortageSettingOpt: KnockoutObservableArray<any>;

            constructor() {
                let self = this;
//                let params: NormalSetParams = nts.uk.ui.windows.getShared("FlexSetParams");
                let params: FlexSetParams = new FlexSetParams();
                
                self.includeOverTimeOpt = ko.observableArray([
                    new ItemModel(1, nts.uk.resource.getText("KMK004_72")),
                    new ItemModel(0, nts.uk.resource.getText("KMK004_73"))]);
                
                self.shortageSettingOpt = ko.observableArray([
                    new ItemModel(1, "当月精算"),
                    new ItemModel(2, "翌月繰越")]);
                
                self.isIncludeOverTime = ko.observable(params.isIncludeOverTime ? params.isIncludeOverTime : false);
                self.shortageSetting = ko.observable(params.shortageSetting ? params.shortageSetting : 1);
            }

            public decideData(): void {
                let self = this;
                nts.uk.ui.windows.setShared("Flex_Set_Output", {
                    isIncludeExtraAggr: self.isIncludeOverTime(),
                } , true);
            }

            /**
             * Event on click cancel button.
             */
            public cancel(): void {
                nts.uk.ui.windows.close();
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
         * フレックス不足時の繰越設定
         * ShortageSetting
         */
        export class CarryForwardSet {
            // 当月精算
            static CURRENT_MONTH_INTEGRATION = 1;
            // 翌月繰越
            static NEXT_MONTH_CARRY_FORWARD = 2;
        }
        
        /**
         * Flex Setting Params Model
         */
        export class FlexSetParams {
            isIncludeOverTime: boolean;
            shortageSetting: number;
            
            constructor() {
                let self = this;
                self.isIncludeOverTime = false;
                self.shortageSetting = 1;
            }
        }
    }
}