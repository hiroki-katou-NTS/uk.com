module nts.uk.at.view.kmk004.g {
    export module viewmodel {

        export class ScreenModel {
            isEnableOverTime: KnockoutObservable<boolean>;
            isIncludeOverTime: KnockoutObservable<boolean>;// G1_2
            includeOverTimeOpt: KnockoutObservableArray<any>;// Opt for G1_2 (G1_3, G1_4)
            
            shortageSetting: KnockoutObservable<number>; // G2_2
            shortageSettingOpt: KnockoutObservableArray<any>;

            constructor() {
                let self = this;
                let params: FlexSetParams = nts.uk.ui.windows.getShared("FLEX_SET_PARAM");
                
                self.includeOverTimeOpt = ko.observableArray([
                    new ItemModelBoolean(true, nts.uk.resource.getText("KMK004_72")),
                    new ItemModelBoolean(false, nts.uk.resource.getText("KMK004_73"))]);
                
                self.shortageSettingOpt = ko.observableArray([
                    new ItemModel(CarryForwardSet.CURRENT_MONTH_INTEGRATION, nts.uk.resource.getText("KMK004_76")),//"当月精算"
                    new ItemModel(CarryForwardSet.NEXT_MONTH_CARRY_FORWARD, nts.uk.resource.getText("KMK004_77"))]);//"翌月繰越"
                
                self.isEnableOverTime = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isEnableOverTime) ? params.isEnableOverTime : true);
                self.isIncludeOverTime = ko.observable(params && !nts.uk.util.isNullOrEmpty(params.isIncludeOverTime) ? params.isIncludeOverTime : false);
                self.shortageSetting = ko.observable((params && !nts.uk.util.isNullOrEmpty(params.shortageSetting)) ? params.shortageSetting : 0);
                
                if (self.isEnableOverTime()){
                    $('#includeOT-switch').focus();
                } else {
                    $('#shortageSetting-switch').focus();
                }
            }

            /**
             * Decide Data
             */
            public decideData(): void {
                let self = this;
                let flexSetOutput: FlexSetParams = new FlexSetParams();
                flexSetOutput.isIncludeOverTime = self.isIncludeOverTime();
                flexSetOutput.shortageSetting = self.shortageSetting();
                flexSetOutput.isEnableOverTime = self.isEnableOverTime();
                
                nts.uk.ui.windows.setShared("FLEX_SET_OUTPUT", flexSetOutput , true);
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
         * Class ItemModel
         */
        export class ItemModel {
            code: number;
            name: string;

            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        /**
         * Class ItemModel boolean
         */
        export class ItemModelBoolean {
            code: boolean;
            name: string;

            constructor(code: boolean, name: string) {
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
            static CURRENT_MONTH_INTEGRATION = 0;
            // 翌月繰越
            static NEXT_MONTH_CARRY_FORWARD = 1;
        }
        
        /**
         * Flex Setting Params Model
         */
        export class FlexSetParams {
            isIncludeOverTime: boolean;
            shortageSetting: number;
            isEnableOverTime: boolean;
            
            constructor() {
                let self = this;
                self.isIncludeOverTime = false;
                self.shortageSetting = 1;
            }
        }
    }
}