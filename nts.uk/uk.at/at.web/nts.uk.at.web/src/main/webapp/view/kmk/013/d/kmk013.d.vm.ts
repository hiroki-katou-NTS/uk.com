module nts.uk.at.view.kmk013.d {
    
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            roundingRules1: KnockoutObservableArray<any>;
            roundingRules2: KnockoutObservableArray<any>;
            selectedRuleCode1: any;
            selectedRuleCode2: any;
            itemListD310: KnockoutObservableArray<any>;
            itemListD410: KnockoutObservableArray<any>;
            itemListD510: KnockoutObservableArray<any>;
            calculationMethods: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;

            selectedD310: KnockoutObservable<number>  = ko.observable(0);
            selectedD410: KnockoutObservable<number> = ko.observable(0);
            selectedD52: KnockoutObservable<number> = ko.observable(0);
            selectedCalculationMethod: KnockoutObservable<number> = ko.observable(0);
            otsukaMode: KnockoutObservable<boolean> = ko.observable(false);
            
            suppDays: KnockoutObservable<number>;
            
            constructor() {
                var self = this;
                self.roundingRules1 = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText('KMK013_172') },
                    { code: 1, name: nts.uk.resource.getText('KMK013_173') }
                ]);
                self.roundingRules2 = ko.observableArray([
                    { code: 0, name: nts.uk.resource.getText('KMK013_176') },
                    { code: 1, name: nts.uk.resource.getText('KMK013_177') }
                ]);
                self.selectedRuleCode1 = ko.observable(1);
                self.selectedRuleCode2 = ko.observable(1);

                self.itemListD310 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_186')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_187')),
                ]);
                self.itemListD410 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_196')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_197')),
                ]);
                self.calculationMethods = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_580')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_579')),
                ]);
                self.itemListD510 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_264')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_265')),
                ]);
                
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(true);
                 
                self.suppDays = ko.observable(2);

                self.selectedCalculationMethod.subscribe(value => self.selectedD410(value === 0 ? 1 : 0));
            }
            startPage(): JQueryPromise<any> {
                var self = this;
                return self.initData();
            }
            initData(): JQueryPromise<any> {
                let self = this;
                var dfd = $.Deferred();
                $.when(service.getOptionLicenseCustomize(), service.findByCompanyId()).done((setting, flexSet) => {

                    if (flexSet && flexSet[0]) {
                        let data = flexSet[0];
                        // 代休取得時の計算方法．割増計算
                        self.selectedD410(data.premiumCalcSubhd);
                        // 代休取得時の計算方法．所定から控除するかどうか
                        self.selectedCalculationMethod(data.isDeductPred);
                        self.selectedCalculationMethod.valueHasMutated();
                        // 半日休日の計算方法．不足計算
                        self.selectedD310(data.missCalcHd);
                        // 代休取得時の計算方法．時間代休時の計算設定
                        self.selectedD52(data.calcSetTimeSubhd);
                    }

                    if (setting) {
                        self.otsukaMode(setting.optionLicenseCustomize);
                    }

                }).always(() => {
                    service.findRefreshByCId().done((arr) => {
                        let data = arr[0];
                        if (data) {
                            self.suppDays(data.supplementableDays);
                        }
                    }).always(() => {
                        dfd.resolve();
                        $('#D6_2').focus();
                    });
                });
                return dfd.promise();;

            }
            saveData(): void {
                let self = this;
                blockUI.grayout();
                // Validate. 
                $('#dateInput').ntsError('check');
                
                if (!$('.nts-input').ntsError('hasError')) {
                    let data: any = {};
                    data.missCalcSubhd = self.selectedD410();
                    data.isDeductPred = self.selectedCalculationMethod();
                    data.missCalcHd = self.selectedD310();
                    data.premiumCalcHd = self.selectedD310();
                    data.premiumCalcSubhd = self.selectedD410();
                    data.calcSetTimeSubhd = self.selectedD52();
    
                    service.save(data).done(() => {
                        let insuffData: any = {};
                        insuffData.supplementableDays = self.suppDays();
                        service.saveRefresh(insuffData).done(() => {
                            blockUI.clear();
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                // Focus on D3_4 by default
                                $('#D6_2').focus();
                            });
                        });
                    });
                }
            }
        }
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name) {
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }
}