module nts.uk.at.view.kmk013.h {
    export module viewmodel {
        export class ScreenModel {
            roundingRules1: KnockoutObservableArray<any>;
            roundingRules2: KnockoutObservableArray<any>;
            selectedRuleCode1: any;
            selectedRuleCode2: any;
            constructor() {
                var self = this;
                self.roundingRules1 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_229') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_230') }
                ]);
                self.roundingRules2 = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText('KMK013_233') },
                    { code: '2', name: nts.uk.resource.getText('KMK013_234') }
                ]);
                self.selectedRuleCode1 = ko.observable(1);
                self.selectedRuleCode2 = ko.observable(1);
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }
            initData(): void {
                let self = this;
                service.findByCompanyId().done((data) => {
                    self.selectedRuleCode1(data.managementOfEntrance);
                    self.selectedRuleCode2(data.reflectWorkingTimeClass);
                });
            }
            save(): void {
                let self = this;
                var data = {};
                data.managementOfEntrance = self.selectedRuleCode1();
                data.reflectWorkingTimeClass = self.selectedRuleCode2();
                service.save(data).done(
                    () => {
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                    }
                );
            }

        }
    }
}