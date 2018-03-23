module nts.uk.at.view.kmk013.h {
    export module viewmodel {
        export class ScreenModel {
            roundingRules1: KnockoutObservableArray<any>;
            selectedRuleCode1: any;
            itemListH6_5: KnockoutObservableArray<any>;
            selectedCodeH6_5: KnockoutObservable<any>;
            
            valueH6_3: KnockoutObservable<number>;
            
            itemListH3_2: KnockoutObservableArray<any>;
            selectedIdH3_2: KnockoutObservable<number>;
            enableH3_2: KnockoutObservable<boolean>;
            
            itemListH4_2: KnockoutObservableArray<any>;
            selectedIdH4_2: KnockoutObservable<number>;
            enableH4_2: KnockoutObservable<boolean>;
            
            itemListH7_2: KnockoutObservableArray<any>;
            selectedIdH7_2: KnockoutObservable<number>;
            enableH7_2: KnockoutObservable<boolean>;
            
            itemListH5_2: KnockoutObservableArray<any>;
            selectedIdH5_2: KnockoutObservable<number>;
            enableH5_2: KnockoutObservable<boolean>;
            
            itemListH8_2: KnockoutObservableArray<any>;
            selectedIdH8_2: KnockoutObservable<number>;
            enableH8_2: KnockoutObservable<boolean>;
            
            itemListH9_2: KnockoutObservableArray<any>;
            selectedIdH9_2: KnockoutObservable<number>;
            enableH9_2: KnockoutObservable<boolean>;
            
            constructor() {
                var self = this;
                self.roundingRules1 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_229') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_230') }
                ]);
                self.selectedRuleCode1 = ko.observable(1);
                
                self.valueH6_3 = ko.observable(3);   
                self.selectedCodeH6_5 = ko.observable(0);
                self.itemListH6_5 = ko.observableArray([
                    new ItemModel(0, '私用'),
                    new ItemModel(1, '公用'),
                    new ItemModel(2, '有償'),
                    new ItemModel(3, '組合')
                ]);
                
                self.itemListH3_2 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_279')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_280'))
                ]);
                self.selectedIdH3_2 = ko.observable(0);
                self.enableH3_2 = ko.observable(true);
                
                self.itemListH4_2 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_283')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_284'))
                ]);
                self.selectedIdH4_2 = ko.observable(0);
                self.enableH4_2 = ko.observable(true);
                
                
                self.itemListH7_2 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_297')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_296'))
                ]);
                self.selectedIdH7_2 = ko.observable(0);
                self.enableH7_2 = ko.observable(true);
                
                self.itemListH5_2 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_288')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_287'))
                ]);
                self.selectedIdH5_2 = ko.observable(0);
                self.enableH5_2 = ko.observable(true);
                
                self.itemListH8_2 = ko.observableArray([
                    new BoxModel(0, nts.uk.resource.getText('KMK013_300')),
                    new BoxModel(1, nts.uk.resource.getText('KMK013_301'))
                ]);
                self.selectedIdH8_2 = ko.observable(0);
                self.enableH8_2 = ko.observable(true);
                
                self.itemListH9_2 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_304')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_305'))
                ]);
                self.selectedIdH9_2 = ko.observable(0);
                self.enableH9_2 = ko.observable(true);
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                self.initData();
                $( "#h2_3" ).focus();
                dfd.resolve();
                return dfd.promise();
            }
            initData(): void {
                let self = this;
                service.findByCompanyId().done((data) => {
                    if (data) {
//                        self.data = data;
                        //入退門の管理をする
                        self.selectedRuleCode1(data.managementOfEntrance);
                        // 外出管理.外出理由の初期値
                        self.selectedCodeH6_5(data.outingAtr);
                        // 最大使用回数       
                        self.valueH6_3(data.maxUseCount);
                        // 自動打刻セット反映.自動打刻反映区分
                        self.selectedIdH3_2(data.autoStampReflectionClass);
                        // 自動打刻セット反映.未来日区分
                        self.selectedIdH4_2(data.autoStampForFutureDayClass);
                        // 自動打刻セット反映.外出補正区分
                        self.selectedIdH7_2(data.goBackOutCorrectionClass);
                        // 打刻の反映.就業時間帯反映区分
                        self.selectedIdH5_2(data.reflectWorkingTimeClass);
                        // 打刻の反映.実打刻と申請の優先区分
                        self.selectedIdH8_2(data.actualStampOfPriorityClass);
                        // 打刻の反映.休出切替区分
                        self.selectedIdH9_2(data.breakSwitchClass);
                    }
                });
            }
            saveData(): void {
                let self = this;
                let data = <any>{};
                data.managementOfEntrance = self.selectedRuleCode1();
                data.outingAtr =self.selectedCodeH6_5();
                data.maxUseCount = self.valueH6_3();
                data.autoStampReflectionClass = self.selectedIdH3_2();
                data.autoStampForFutureDayClass =self.selectedIdH4_2();
                data.goBackOutCorrectionClass =self.selectedIdH7_2();
                data.reflectWorkingTimeClass =self.selectedIdH5_2();
                data.actualStampOfPriorityClass = self.selectedIdH8_2();
                data.breakSwitchClass =self.selectedIdH9_2();
                service.save(data).done(
                    () => {
                        nts.uk.ui.dialog.info({messageId: 'Msg_15'});
                        $( "#h2_3" ).focus();
                    }
                );
            }
        }
        
        class ItemModel {
            code: number;
            name: string;
        
            constructor(code: number, name: string) {
                this.code = code;
                this.name = name;
            }
        }
        
        class BoxModel {
            id: number;
            name: string;
            constructor(id, name){
                var self = this;
                self.id = id;
                self.name = name;
            }
        }
    }
}