module qmm012.a.viewmodel {
    export class ScreenModel {
        //Switch
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any = ko.observable(0);
        enable: KnockoutObservable<boolean> = ko.observable(true);
        lBL_004_Text: KnockoutObservable<string> = ko.observable('社員に対して支払う金額の種類を登録します。」と表記');
        constructor() {
            let self = this;
            //start Switch Data
            self.enable
            self.roundingRules = ko.observableArray([
                { code: 0, name: '支給項目' },
                { code: 1, name: '控除項目' },
                { code: 2, name: '勤怠項目' }
            ]);
            //endSwitch Data
            self.selectedRuleCode.subscribe(function(NewValue) {
                self.lBL_004_Text(Gen_LBL_004_Text(NewValue))
            });
            function Gen_LBL_004_Text(NewValue) {
                let text;
                switch (NewValue) {
                    case 0:
                        text = "社員に対して支払う金額の種類を登録します。」と表記"
                        break;
                    case 1:
                        text = "社員から徴収する金額の種類を登録します。"
                        break;
                    case 2:
                        text = "社員の勤怠実績（日数・回数,時間）の種類を登録します"
                        break;
                }
                return text;
            }

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