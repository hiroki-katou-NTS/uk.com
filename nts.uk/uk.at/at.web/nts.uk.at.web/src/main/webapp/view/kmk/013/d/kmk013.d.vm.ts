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
            selectedId310: KnockoutObservable<number>;
            selectedId410: KnockoutObservable<number>;
            selectedId62: KnockoutObservable<number>;
            selectedId52: KnockoutObservable<number>;
            enable: KnockoutObservable<boolean>;
            selectedId34: KnockoutObservable<any>;
            selectedId44: KnockoutObservable<any>;
            
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
                self.selectedId34 = ko.observable(1);
                self.selectedId44 = ko.observable(1);
                self.selectedId310 = ko.observable(1);
                self.selectedId410 = ko.observable(1);
                self.selectedId62 = ko.observable(1);
                self.selectedId52 = ko.observable(1);
                self.selectedId34.subscribe(newValue => {
                    if (newValue == 0) {
                        self.selectedId310(0);
                    }
                })
                self.selectedId310.subscribe(newValue => {
                    if (newValue == 1 && self.selectedId34() == 0) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_827" }).then(() => {
                            self.selectedId34(1);
                        });
                    }
                });
                self.selectedId44.subscribe(newValue => {
                    if (newValue == 0) {
                        self.selectedId410(0);
                    }
                })
                self.selectedId410.subscribe(newValue => {
                    if (newValue == 1 && self.selectedId44() == 0) {
                        nts.uk.ui.dialog.info({ messageId: "Msg_827" }).then(() => {
                            self.selectedId44(1);
                        });
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
                service.findByCompanyId().done((arr) => {
                    let data = arr[0];
                    if (data) {
                        //半日勤務の計算方法.半日休日時.割増計算
                        self.selectedId34(data.premiumCalcHd);
                        //半日勤務の計算方法.半日休日時.不足計算
                        self.selectedId310(data.missCalcHd);
                        //半日勤務の計算方法.半日代休時.割増計算
                        self.selectedId44(data.premiumCalcSubhd);
                        //半日勤務の計算方法.半日代休時.不足計算
                        self.selectedId410(data.missCalcSubhd);
                        //法定労働控除時間計算
                        self.selectedId52(data.flexDeductTimeCalc);
                        //非勤務日計算
                        self.selectedId62(data.flexNonworkingDayCalc);
                    }
                }).always(() => {
                    service.findRefreshByCId().done((arr) => {
                        let data = arr[0];
                        if (data) {
                            self.suppDays(data.supplementableDays);
                        }
                    }).always(() => {
                        dfd.resolve();
                    });
                });
                return dfd.promise();
            }
            saveData(): void {
                let self = this;
                blockUI.grayout();
                // Validate. 
                $('#dateInput').ntsError('check');
                
                if (!$('.nts-input').ntsError('hasError')) {
                    let data: any = {};
                    data.premiumCalcHd = self.selectedId34();
                    data.missCalcHd = self.selectedId310();
                    data.premiumCalcSubhd = self.selectedId44();
                    data.missCalcSubhd = self.selectedId410();
                    data.flexDeductTimeCalc = self.selectedId52();
                    data.flexNonworkingDayCalc = self.selectedId62();
    
                    service.save(data).done(() => {
                        let insuffData: any = {};
                        insuffData.supplementableDays = self.suppDays();
                        service.saveRefresh(insuffData).done(() => {
                            blockUI.clear();
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                // Focus on D3_4 by default
                                $('#d3_4').focus();
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