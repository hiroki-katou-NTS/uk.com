module nts.uk.at.view.kml002.d.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<ItemModel>;
        checked: KnockoutObservable<boolean>;
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
            
            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'operatorAtr', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 160, formatter: _.escape }
            ]);
            
            self.currentRightCodeList = ko.observableArray([]);
            
            self.checked = ko.observable(true);
        }

        /**   
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let array = [];
            let param = {
                budgetAtr: 1,   
                unitAtr: 0
            }
            service.getByAtr(param).done((lst) => {  
                console.log(lst);
                _.map(lst, function(item){
                    array.push({
                        code: item.externalBudgetCode,
                        name: item.externalBudgetName                             
                    })    
                });
                self.items(array);   
                dfd.resolve();
            })
            return dfd.promise();
        }
        
        submit() {
            var self = this;
            
        }
        
        cancel() {
            var self = this;
            
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
        operatorAtr: number;
        name: string;
        constructor(code: string, operatorAtr: number, name: string) {
            this.code = code;
            this.operatorAtr = operatorAtr;
            this.name = name;       
        }
    } 
}