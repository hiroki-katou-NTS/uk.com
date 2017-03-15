module qmm012.a.viewmodel {
    export class ScreenModel {
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        enable: KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            let self = this;
            //start Switch Data
            self.enable 
            self.roundingRules = ko.observableArray([
                { code: 0 , name: 'éxããçÄñ⁄' },
                { code: 1 , name: 'çTèúçÄñ⁄' },
                { code: 2 , name: 'ãŒë”çÄñ⁄' }
            ]);
            self.selectedRuleCode = ko.observable(0);
            //endSwitch Data
        }
        submitInfo() {
            let self = this;
            let groupCode = self.selectedRuleCode();
            nts.uk.ui.windows.setShared('groupCode', groupCode);
            nts.uk.ui.windows.close();
        }
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
}