module nts.uk.at.view.kml002.b.viewmodel {
    export class ScreenModel {
        radioForm: KnockoutObservableArray<any>;
        selectedLeftId: KnockoutObservable<number>;
        selectedRightId: KnockoutObservable<number>;
        projectItem: KnockoutObservableArray<any>;
        selectedItem1: KnockoutObservable<any>;
        selectedItem2: KnockoutObservable<any>;
        numerical1: KnockoutObservable<number>;
        numerical2: KnockoutObservable<number>;
        operatorItem: KnockoutObservableArray<any>;
        selectedOperator: KnockoutObservable<any>;
        enableProjectItem1: KnockoutObservable<boolean>;
        enableProjectItem2: KnockoutObservable<boolean>;
        enableNumerical1: KnockoutObservable<boolean>;
        enableNumerical2: KnockoutObservable<boolean>;
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
            
            self.selectedLeftId = ko.observable(0);            
            self.selectedRightId = ko.observable(0);
            
            self.projectItem = ko.observableArray([
                { itemCode: 0, itemName: '123456789012345' },
                { itemCode: 1, itemName: '123456789012345' }
            ]);
            
            self.selectedItem1 = ko.observable(0);
            self.selectedItem2 = ko.observable(0);
            
            self.numerical1 = ko.observable(0);
            self.numerical2 = ko.observable(0);
            
            self.operatorItem = ko.observableArray([
                { operatorCode: 0, operatorName: 'X' },
                { operatorCode: 1, operatorName: ':' }
            ]);
            
            self.selectedOperator = ko.observable(0);
            
            self.enableProjectItem1 = ko.observable(true);
            self.enableProjectItem2 = ko.observable(true);
            self.enableNumerical1 = ko.observable(false);
            self.enableNumerical2 = ko.observable(false);
            
            self.selectedLeftId.subscribe(function(value) {
                if(value == 0){
                    self.enableProjectItem1(true);
                    self.enableNumerical1(false);
                } else {
                    self.enableProjectItem1(false);
                    self.enableNumerical1(true);
                }
            });  
            
            self.selectedRightId.subscribe(function(value) {
                if(value == 0){
                    self.enableProjectItem2(true);
                    self.enableNumerical2(false);
                } else {
                    self.enableProjectItem2(false);
                    self.enableNumerical2(true);
                }
            }); 
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            
            
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
            if(self.selectedLeftId() == 1 && (self.numerical1() == null) || self.numerical1().toString() == "") {
                success = false;
                nts.uk.ui.dialog.alert({ messageId: "Msg_419" });
            } else if(self.selectedRightId() == 1 && (self.numerical1() == null) || self.numerical1().toString() == "") {
                success = false;
                nts.uk.ui.dialog.alert({ messageId: "Msg_419" });
            }
            
            //「計算方法」が2つとも「数値入力」に設定することができない
            if(self.selectedLeftId() == 1 && self.selectedRightId() == 1) {
                success = false;
                nts.uk.ui.dialog.alert({ messageId: "Msg_420" });
            }
            
            return success;
        }
    }
}