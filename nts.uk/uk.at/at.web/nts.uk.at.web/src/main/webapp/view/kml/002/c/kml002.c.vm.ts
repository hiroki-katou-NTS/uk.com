module nts.uk.at.view.kml002.c.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        allItem: KnockoutObservableArray<ItemModel>;
        items: KnockoutObservableArray<ItemModel>;
        categoryItems: KnockoutObservableArray<any>;
        catCode: KnockoutObservable<number>;
        checked: KnockoutObservable<boolean>;
        enable: KnockoutObservable<boolean>;
        rightItemcolumns: KnockoutObservable<any>;
        currentRightCodeList: KnockoutObservableArray<any>;
        rightItems: KnockoutObservableArray<NewItemModel>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;
        enableReturn: KnockoutObservable<boolean>;
        
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            
            self.items = ko.observableArray([]);
            self.allItem = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 200, formatter: _.escape }
            ]);
            
            self.currentCodeList = ko.observableArray([]);
            
            self.categoryItems = ko.observableArray([
                { catCode: 0, catName: nts.uk.resource.getText("KML002_29") },
                { catCode: 1, catName: nts.uk.resource.getText("KML002_32") }
            ]);
            
            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'code', width: 50, hidden: true },
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'operatorAtr', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 200, formatter: _.escape }
            ]);
            
            self.currentRightCodeList = ko.observableArray([]);
            
            self.catCode = ko.observable(0);
            
            self.checked = ko.observable(true);
            self.enable = ko.observable(true);
            
            if(self.catCode() === 0) {
                self.enable(true);
            } else {
                self.enable(false);
            }
            
            self.catCode.subscribe(function(value) {
                if(value == 0){
                    self.enable(true);
                    var temp = [];
                    
                    for(var i = 0; i < self.allItem().length; i++) {
                        if(self.allItem()[i].itemType == 0 || self.allItem()[i].itemType == 1) {                            
                            temp.push(new ItemModel(self.allItem()[i].code, self.allItem()[i].name, self.allItem()[i].itemType));
                        }
                    }
                    
                    self.items.removeAll();
                    self.items(temp);
                } else {
                    self.enable(false);
                    self.allItem(self.items());
                    var temp = [];
                    
                    for(var i = 0; i < self.allItem().length; i++) {
                        if(self.allItem()[i].itemType == 2) {
                            temp.push(new ItemModel(self.allItem()[i].code, self.allItem()[i].name, self.allItem()[i].itemType));
                        }
                    }
                    
                    self.items.removeAll();
                    self.items(temp);
                }
            }); 
            
            self.enableReturn = ko.observable(true);
            
            if(self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            $.when(self.getData()).done(function() {
                                
                if (self.allItem().length > 0) {
                    self.items(self.allItem());
                }
                
                dfd.resolve();
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            dfd.resolve();
            
            return dfd.promise();
        }
        
        /**
         * Get data from db.
         */
        getData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.allItem([]);
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            service.getDailyItems(data.attributeId).done(function(data) {
                let temp = [];
                let dailyItems = _.sortBy(data.dailyAttItems, ['companyId', 'dispOrder']);
                let scheduleItems = _.sortBy(data.scheduleItems, ['companyId', 'dispOrder']);              
                let externalItems = _.sortBy(data.externalItems, ['companyId', 'dispOrder']);
                
                _.forEach(dailyItems, function(item) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_43");
                    temp.push(new ItemModel(item.id, name, item.itemType));
                });
                
                _.forEach(scheduleItems, function(item) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_42");
                    temp.push(new ItemModel(item.id, name, item.itemType));
                });
                
                _.forEach(externalItems, function(item) {
                    var name = item.itemName + nts.uk.resource.getText("KML002_44");
                    temp.push(new ItemModel(item.id, name, item.itemType));
                });
                
                self.allItem(temp);
                
                dfd.resolve(data);
            }).fail(function(res) {
                dfd.reject(res);    
            });
            
            return dfd.promise();
        }
        
        /**
         * Addition function.
         */
        addition() {
            let self = this;
            
            if(self.currentCodeList().length + self.rightItems().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeList(), function(item){
                    var item = _.find(self.items(), function(o) { return o.code == Number(item); });
                    
                    let i = self.rightItems().length;
                    
                    self.rightItems.push({
                        code: i.toString(),
                        operatorAtr: nts.uk.resource.getText("KML002_37"),
                        name: item.name,    
                    });
                    
                    i = i + 1;
                });
            }
            
            if(self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }
        
        /**
         * Subtraction function.
         */
        subtraction() {
            let self = this;
            
            if(self.currentCodeList().length + self.rightItems().length > 100) {
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            } else {
                _.forEach(self.currentCodeList(), function(item){
                    var item = _.find(self.items(), function(o) { return o.code == Number(item); });
                    
                    let i = self.rightItems().length;
                    
                    self.rightItems.push({
                        code: i.toString(),
                        operatorAtr: nts.uk.resource.getText("KML002_38"),
                        name: item.name,    
                    });
                    
                    i = i + 1;
                });
            }
            
            if(self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }
        
        /**
         * Return items.
         */
        returnItems() {
            let self = this;
            let array =[];

            self.rightItems(_.filter(self.rightItems(), function(item) {
                return _.indexOf(self.currentRightCodeList(), item.code) < 0;
            }));
            
            for(let i = 0; i < self.rightItems().length; i++){
                self.rightItems()[i].code = i.toString();    
            }
            
            self.currentRightCodeList.removeAll();
            self.rightItems(self.rightItems());
            
            if(self.rightItems().length >= 1) {
                self.enableReturn(true);
            } else {
                self.enableReturn(false);
            }
        }
        
        /**
         * Submit data to A screen.
         */
        submit() {
            var self = this;
            
            nts.uk.ui.windows.close();
        }
        
        /**
         * Close dialog.
         */
        cancel() {
            nts.uk.ui.windows.close();
        }
    }
    
    class ItemModel {
        code: string;
        name: string;
        itemType: number;
        constructor(code: string, name: string, itemType: number) {
            this.code = code;
            this.name = name;  
            this.itemType = itemType;  
        }
    } 
    
    class NewItemModel {
        code: string;
        operatorAtr: string;
        name: string;
        constructor(code: string, operatorAtr: string, name: string) {
            this.code = code;
            this.operatorAtr = operatorAtr;
            this.name = name;       
        }
    } 
}