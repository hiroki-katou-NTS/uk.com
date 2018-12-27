module nts.uk.at.view.kmk013.h {
    
    import blockUI = nts.uk.ui.block;
    
    export module viewmodel {
        export class ScreenModel {
            itemListH2_3: KnockoutObservableArray<any>;
            selectedH2_3: KnockoutObservable<number>;
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
                self.itemListH2_3 = ko.observableArray([
                    { code: 1, name: nts.uk.resource.getText('KMK013_209') },
                    { code: 0, name: nts.uk.resource.getText('KMK013_210') }
                ]);
                self.selectedH2_3 = ko.observable(1);
                
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
                    new BoxModel(1, nts.uk.resource.getText('KMK013_296')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_297'))
                ]);
                self.selectedIdH7_2 = ko.observable(0);
                self.enableH7_2 = ko.observable(true);
                
                self.itemListH5_2 = ko.observableArray([
                    new BoxModel(1, nts.uk.resource.getText('KMK013_287')),
                    new BoxModel(0, nts.uk.resource.getText('KMK013_288'))
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
                $.when(service.findByCompanyId(), 
                        service.findOutManageByCID(),
                        service.findManageEntryExitByCID()).done((data: any, dataOutManage: any, dataManageEntryExit: any) => {
                   if (data) {
                        self.selectedIdH3_2(data.autoStampReflectionClass);
                        self.selectedIdH4_2(data.autoStampForFutureDayClass);
                        self.selectedIdH7_2(data.goBackOutCorrectionClass);
                        self.selectedIdH5_2(data.reflectWorkingTimeClass);
                        self.selectedIdH8_2(data.actualStampOfPriorityClass);
                        self.selectedIdH9_2(data.breakSwitchClass);
                   }
                    
                   if (dataOutManage) {
                        self.selectedCodeH6_5(dataOutManage.initValueReasonGoOut);
                        self.valueH6_3(dataOutManage.maxUsage);
                   } 
                    
                   if (dataManageEntryExit) {
                       self.selectedH2_3(dataManageEntryExit.useClassification);
                   }
                });
            }
            saveData(): void {
                let self = this;
                let data = <any>{};
                let dataOutManage = <any>{};
                let dataManageEntryExit = <any>{};
                
                blockUI.grayout();
                
                if (nts.uk.ui.errors.hasError()) {
                    blockUI.clear();
                    return;
                }
                
                data.autoStampReflectionClass = self.selectedIdH3_2();
                data.autoStampForFutureDayClass =self.selectedIdH4_2();
                data.goBackOutCorrectionClass =self.selectedIdH7_2();
                data.reflectWorkingTimeClass =self.selectedIdH5_2();
                data.actualStampOfPriorityClass = self.selectedIdH8_2();
                data.breakSwitchClass =self.selectedIdH9_2();
                
                dataOutManage.initValueReasonGoOut =self.selectedCodeH6_5();
                dataOutManage.maxUsage = self.valueH6_3();
                
                dataManageEntryExit.useClassification1 = self.selectedH2_3();
                
                $.when(service.save(data), service.saveOutManage(dataOutManage), service.saveManageEntryExit(dataManageEntryExit)).done(() => {
                    nts.uk.ui.dialog.info({messageId: 'Msg_15'});
                        $( "#h2_3" ).focus();
                        blockUI.clear();
                });
                
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