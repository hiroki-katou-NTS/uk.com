module nts.uk.pr.view.qpp._005.b {

    export module viewmodel {

        export class SwitchButton {
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: KnockoutObservable<string>;
            
            constructor() {
                var self = this;
                 self.roundingRules = ko.observableArray([
                    { code: '1', name: '縦方向' },
                    { code: '2', name: '横方向' }
                ]);
                self.selectedRuleCode = ko.observable('2');
            }
            
//             checkSwitchValue() {
//                var self = this;
//                self.visible = self.selectedRuleCode() == '1' ? ko.observable(true) : ko.observable(false);
//            }
        }

        export class ScreenModel {
            switchButton: SwitchButton;
            visible: KnockoutObservable<any>;

            /**
             * Init screen
             */
            constructor() {
                var self = this;
                self.switchButton = new SwitchButton();
                self.visible = ko.observable(self.switchButton.selectedRuleCode() == '1');
                self.switchButton.selectedRuleCode.subscribe(function(newValue) {
                    self.visible(newValue == '1');
                });
            }            
        }
    }
}