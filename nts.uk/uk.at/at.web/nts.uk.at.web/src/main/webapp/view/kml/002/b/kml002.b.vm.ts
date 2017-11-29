module nts.uk.at.view.kml002.b.viewmodel {
    export class ScreenModel {
        radioForm: KnockoutObservableArray<any>;
        settingMethod1: KnockoutObservable<number>;
        settingMethod2: KnockoutObservable<number>;
        verticalCalItems: KnockoutObservableArray<any>;
        selectedItem1: KnockoutObservable<any>;
        selectedItem2: KnockoutObservable<any>;
        verticalInputItem1: KnockoutObservable<number>;
        verticalInputItem2: KnockoutObservable<number>;
        operatorItem: KnockoutObservableArray<any>;
        selectedOperator: KnockoutObservable<any>;
        enableVerticalCalItem1: KnockoutObservable<boolean>;
        enableVerticalCalItem2: KnockoutObservable<boolean>;
        enableVerticalInputItem1: KnockoutObservable<boolean>;
        enableVerticalInputItem2: KnockoutObservable<boolean>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;
        
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            
            self.radioForm = ko.observableArray([
                { id: 0, name: nts.uk.resource.getText("KML002_25") },
                { id: 1, name: nts.uk.resource.getText("KML002_152") }
            ]);
            
            self.settingMethod1 = ko.observable(0);            
            self.settingMethod2 = ko.observable(0);
            
            //Fill data to selector items
            self.getFillSelectorData(data.verticalItems);
            
            self.selectedItem1 = ko.observable(0);
            self.selectedItem2 = ko.observable(0);
            
            self.verticalInputItem1 = ko.observable(0);
            self.verticalInputItem2 = ko.observable(0);
            
            self.operatorItem = ko.observableArray([
                { operatorCode: 0, operatorName: nts.uk.resource.getText("Enum_OperatorAtr_ADD") },
                { operatorCode: 1, operatorName: nts.uk.resource.getText("Enum_OperatorAtr_SUBTRACT") },
                { operatorCode: 2, operatorName: nts.uk.resource.getText("Enum_OperatorAtr_MULTIPLY") },
                { operatorCode: 3, operatorName: nts.uk.resource.getText("Enum_OperatorAtr_DIVIDE") }
            ]);
            
            self.selectedOperator = ko.observable(0);
            
            self.enableVerticalCalItem1 = ko.observable(true);
            self.enableVerticalCalItem2 = ko.observable(true);
            self.enableVerticalInputItem1 = ko.observable(false);
            self.enableVerticalInputItem2 = ko.observable(false);
            
            self.settingMethod1.subscribe(function(value) {
                if(value == 0){
                    self.enableVerticalCalItem1(true);
                    self.enableVerticalInputItem1(false);
                } else {
                    self.enableVerticalCalItem1(false);
                    self.enableVerticalInputItem1(true);
                }
            });  
            
            self.settingMethod2.subscribe(function(value) {
                if(value == 0){
                    self.enableVerticalCalItem2(true);
                    self.enableVerticalInputItem2(false);
                } else {
                    self.enableVerticalCalItem2(false);
                    self.enableVerticalInputItem2(true);
                }
            }); 
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            if(data.formBuilt != null) {
                self.settingMethod1(data.formBuilt.settingMethod1);            
                self.settingMethod2(data.formBuilt.settingMethod2);
                self.selectedItem1(data.formBuilt.verticalCalItem1);
                self.selectedItem2(data.formBuilt.verticalCalItem2);
                self.verticalInputItem1(data.formBuilt.verticalInputItem1);
                self.verticalInputItem2(data.formBuilt.verticalInputItem2);
                self.selectedOperator(data.formBuilt.operatorAtr);
            }
            
            dfd.resolve();
            return dfd.promise();
        }
        
        /**
         * Pass data to A screen.
         */
        submit() {
            var self = this;
            
            var resultValidate = self.validate();
            
            if(resultValidate) {
                var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
                var item1 = _.find(self.verticalCalItems(), function(o) { return o.itemCode == self.selectedItem1(); });
                var item2 = _.find(self.verticalCalItems(), function(o) { return o.itemCode == self.selectedItem2(); });
                
                var formulaItem : IFormulaItem = {
                    verticalCalCd: data.verticalCalCd,
                    verticalCalItemId: data.itemId,
                    settingMethod1: self.settingMethod1(),
                    verticalCalItem1: self.selectedItem1(),
                    verticalCalItemName1: item1.itemName,
                    verticalInputItem1: self.settingMethod1() == 1 ? self.verticalInputItem1() : null,
                    settingMethod2: self.settingMethod2(),
                    verticalCalItem2: self.selectedItem2(),
                    verticalCalItemName2: item2.itemName,
                    verticalInputItem2: self.settingMethod2() == 1 ? self.verticalInputItem2() : null,
                    operatorAtr: self.selectedOperator()
                };
                
                nts.uk.ui.windows.setShared("KML002_B_DATA", formulaItem);
                
                nts.uk.ui.windows.close();
            }
        }
        
        /**
         * Close dialog.
         */
        cancel() {
            nts.uk.ui.windows.close();
        }
        
        /**
         * Validate input form.
         */
        validate() {
            var self = this;
            var success = true;
            
            //設定方法が数値入力の場合、縦計入力項目が入力しないといけない。
            if(self.settingMethod1() == 1 && (self.verticalInputItem1() == null)) {
                success = false;
                nts.uk.ui.dialog.alert({ messageId: "Msg_419" });
            } else if(self.settingMethod2() == 1 && (self.verticalInputItem2() == null)) {
                success = false;
                nts.uk.ui.dialog.alert({ messageId: "Msg_419" });
            }
            
            //「計算方法」が2つとも「数値入力」に設定することができない
            if(self.settingMethod1() == 1 && self.settingMethod2() == 1) {
                success = false;
                nts.uk.ui.dialog.alert({ messageId: "Msg_420" });
            }
            
            if(nts.uk.ui.errors.hasError()) {
                success = false;
            }
            
            return success;
        }
        
        /**
         * Fill data to selector items.
         */
        getFillSelectorData(data: any) {
            var self = this;
            
            var verticalCalItems = [];
            
            if(data.length == 0) {
                var item : IVerticalCalItem = {
                    itemCode: "00",
                    itemName: "Not set"
                };
                
                verticalCalItems.push(item);
            } else {
                for(var i = 0; i < data.length; i++) {
                    var item : IVerticalCalItem = {
                        itemCode: data[i].itemId,
                        itemName: data[i].itemName
                    };
                    
                    verticalCalItems.push(item);
                }
            }            
            
            self.verticalCalItems = ko.observableArray(verticalCalItems);
        }
    }
    
    export class VerticalCalItem {
        itemCode: KnockoutObservable<string>;
        itemName: KnockoutObservable<string>;
        
        constructor(param: IVerticalCalItem) {
            var self = this;
            self.itemCode = ko.observable(param.itemCode);
            self.itemName = ko.observable(param.itemName);
        }
    } 
    
    export interface IVerticalCalItem {
        itemCode: string;
        itemName: string;
    }
    
    export class FormulaItem {
        verticalCalCd: KnockoutObservable<string>;
        verticalCalItemId: KnockoutObservable<string>;
        settingMethod1: KnockoutObservable<number>;
        verticalCalItem1: KnockoutObservable<number>;
        verticalCalItemName1: KnockoutObservable<string>;
        verticalInputItem1: KnockoutObservable<number>;
        settingMethod2: KnockoutObservable<number>;
        verticalCalItem2: KnockoutObservable<number>;
        verticalCalItemName2: KnockoutObservable<string>;
        verticalInputItem2: KnockoutObservable<number>;
        operatorAtr: KnockoutObservable<number>;
        
        constructor(param: IFormulaItem) {
            var self = this;
            self.verticalCalCd = ko.observable(param.verticalCalCd);
            self.verticalCalItemId = ko.observable(param.verticalCalItemId);
            self.settingMethod1 = ko.observable(param.settingMethod1);
            self.verticalCalItem1 = ko.observable(param.verticalCalItem1);
            self.verticalCalItemName1 = ko.observable(param.verticalCalItemName1);
            self.verticalInputItem1 = ko.observable(param.verticalInputItem1);
            self.settingMethod2 = ko.observable(param.settingMethod2);
            self.verticalCalItem2 = ko.observable(param.verticalCalItem2);
            self.verticalCalItemName2 = ko.observable(param.verticalCalItemName2);
            self.verticalInputItem2 = ko.observable(param.verticalInputItem2);
            self.operatorAtr = ko.observable(param.operatorAtr);
        }
    } 
    
    export interface IFormulaItem {
        verticalCalCd: string;
        verticalCalItemId: string;
        settingMethod1: number;
        verticalCalItem1: number;
        verticalCalItemName1: string;
        verticalInputItem1: number;
        settingMethod2: number;
        verticalCalItem2: number;
        verticalCalItemName2: string;
        verticalInputItem2: number;
        operatorAtr: number;
    }
}