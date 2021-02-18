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
            itemListD610: KnockoutObservableArray<any>;
            itemListD510: KnockoutObservableArray<any>;
            selectedId: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;

            selectedD62: KnockoutObservable<number> = ko.observable(0);
            selectedD310: KnockoutObservable<number>  = ko.observable(0);
            checkedD34: KnockoutObservable<boolean>  = ko.observable(false);
            enableD34: KnockoutObservable<boolean> = ko.observable(true);
            selectedD410: KnockoutObservable<number> = ko.observable(0);
            checkedD44: KnockoutObservable<boolean> = ko.observable(false);
            enableD44: KnockoutObservable<boolean> = ko.observable(true);
            selectedD52: KnockoutObservable<number> = ko.observable(0);
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
                self.itemListD610 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_268')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_269')),
                ]);
                self.itemListD510 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_264')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_265')),
                    new BoxModel(2, nts.uk.resource.getText('KMK013_266')),
                ]);
                
                self.selectedId = ko.observable(1);
                self.enable = ko.observable(true);

                self.selectedD310.subscribe(value => {
                   if (value == 0) {
                       self.enableD34(true);
                       self.checkedD34(true);
                   } else {
                       self.enableD34(false);
                       self.checkedD34(false);
                   }
                });

                self.selectedD410.subscribe(value => {
                    if (value == 0) {
                        self.enableD44(true);
                        self.checkedD44(true);
                    } else {
                        self.enableD44(false);
                        self.checkedD44(false);
                    }
                });
                 
                self.suppDays = ko.observable(2);
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
                        // 非勤務日計算.設定
                        self.selectedD62(data.flexNonworkingDayCalc);
                        //半日勤務の計算方法.半日休日時.不足計算
                        self.selectedD310(data.missCalcHd);
                        //半日勤務の計算方法.半日休日時.割増計算
                        self.checkedD34(data.premiumCalcHd == 1);
                        //半日勤務の計算方法.半日代休時.不足計算
                        self.selectedD410(data.missCalcSubhd);
                        //半日勤務の計算方法.半日代休時.割増計算
                        self.checkedD44(data.premiumCalcSubhd == 1);
                        // 法定労働控除時間計算.設定
                        self.selectedD52(data.flexDeductTimeCalc);
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
                    data.premiumCalcHd = self.checkedD34() ? 1 : 0;
                    data.missCalcHd = self.selectedD310();
                    data.premiumCalcSubhd = self.checkedD44() ? 1 : 0;
                    data.missCalcSubhd = self.selectedD410();
                    data.flexDeductTimeCalc = self.selectedD52();
                    data.flexNonworkingDayCalc = self.selectedD62();
    
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