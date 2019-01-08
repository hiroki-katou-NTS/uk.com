module nts.uk.at.view.kmk013.i {
    
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            roundingRules: KnockoutObservableArray<any>;
            selectedRuleCode: KnockoutObservable<number>;
            isCreated: KnockoutObservable<boolean>;
            // deformLaborSetting
            data = {};
            constructor() {
                var self = this;
                self.roundingRules = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_209') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_210') }
                ]);
                self.selectedRuleCode = ko.observable(1);
                self.isCreated = ko.observable(false);
            }
            startPage() {
                var self = this;
                var dfd = $.Deferred();
                self.initData().done(() => {
                    dfd.resolve();
                });
                return dfd.promise();
            }
            initData() : JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                service.findByCompanyId().done(arr => {
                    let data = arr[0];
                    if(data != null) {
                        // 変形法定内残業を計算する
                        self.selectedRuleCode(data.legalOtCalc);
                        self.isCreated(true);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            saveData(): void {
                let self = this,
                    data: any = self.data;
                blockUI.grayout();
                
                data.legalOtCalc = self.selectedRuleCode();
                if (self.isCreated()){
                    service.update(data).done(
                        () => {
                            blockUI.clear();
                            nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(() => {
                                $("#switch").focus();
                            });
                        }
                    );
                } else {
                    service.save(data).done(
                        () => {
                            blockUI.clear();
                            nts.uk.ui.dialog.info({ messageId: 'Msg_15' }).then(() => {
                                $("#switch").focus();
                            });
                        }
                    );
                }
                
            }
        }
    }
}