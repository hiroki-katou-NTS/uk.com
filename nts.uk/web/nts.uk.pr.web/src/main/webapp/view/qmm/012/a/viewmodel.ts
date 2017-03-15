module qmm012.a.viewmodel {
    export class ScreenModel {
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any = ko.observable(0);
        enable: KnockoutObservable<boolean> = ko.observable(true);
        constructor() {
            let self = this;
            //start Switch Data
            self.enable
            self.roundingRules = ko.observableArray([
                { code: 0, name: '支給項目' },
                { code: 1, name: '控除項目' },
                { code: 2, name: '勤怠項目' }
            ]);
            self.selectedRuleCode = ko.observable(0);
            //endSwitch Data
        }
        submitInfo() {
            let self = this;
            let groupCode = self.selectedRuleCode();
            nts.uk.sessionStorage.setItem('groupCode', groupCode);
            nts.uk.ui.windows.close();
        }
        closeDialog() {
            nts.uk.ui.windows.close();
        }
    }
}