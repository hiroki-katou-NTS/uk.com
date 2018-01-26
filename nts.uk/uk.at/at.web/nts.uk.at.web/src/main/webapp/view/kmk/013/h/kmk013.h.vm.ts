module nts.uk.at.view.kmk013.h {
    export module viewmodel {
        export class ScreenModel {
            roundingRules1: KnockoutObservableArray<any>;
            roundingRules2: KnockoutObservableArray<any>;
            selectedRuleCode1: Knock;
            selectedRuleCode2: any;
            //
            data = {};
            constructor() {
                var self = this;
                self.roundingRules1 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_229') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_230') }
                ]);
                self.roundingRules2 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_233') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_234') }
                ]);
                self.selectedRuleCode1 = ko.observable(1);
                self.selectedRuleCode2 = ko.observable(1);
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.initData();
                dfd.resolve();
                return dfd.promise();
            }
            initData(): void {
                let self = this;
                service.findByCompanyId().done((data) => {
                    if (data) {
                        self.data = data;
                        //入退門の管理をする
                        self.selectedRuleCode1(data.managementOfEntrance);
                        //就業時間帯打刻反映区分
                        self.selectedRuleCode2(data.reflectWorkingTimeClass);

                    }
                });
            }
            saveData(): void {
                let self = this;
                let data = self.data;
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