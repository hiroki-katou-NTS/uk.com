module nts.uk.at.view.kml002.c.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
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
        
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            
            self.items = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 180, formatter: _.escape }
            ]);
            
            self.currentCodeList = ko.observableArray([]);
            
            self.categoryItems = ko.observableArray([
                { catCode: 0, catName: nts.uk.resource.getText("KML002_29") },
                { catCode: 1, catName: nts.uk.resource.getText("KML002_32") }
            ]);
            
            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'operatorAtr', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 160, formatter: _.escape }
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
                } else {
                    self.enable(false);
                }
            });  
        }

        /**
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            
            self.items([]);
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            service.getDailyItems(data.attributeId).done(function(data) {
                _.forEach(data, function(item) {
                    self.items.push(new ItemModel(item.id, item.itemName));
                });
                
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
            var self = this;
            
            
        }
        
        /**
         * Subtraction function.
         */
        subtraction() {
            var self = this;
            
            
        }
        
        /**
         * Return items.
         */
        returnItems() {
            var self = this;
            
            
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
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;       
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