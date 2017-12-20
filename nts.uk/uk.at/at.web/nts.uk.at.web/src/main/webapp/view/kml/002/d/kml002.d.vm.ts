import setSharedD = nts.uk.ui.windows.setShared;
import getSharedD = nts.uk.ui.windows.getShared;
module nts.uk.at.view.kml002.d.viewmodel {
    export class ScreenModel {
        columns: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        items: KnockoutObservableArray<ItemModel>;
        // received from mother screen
        checked: KnockoutObservable<boolean> = ko.observable(false);
        rightItemcolumns: KnockoutObservable<any>;
        currentRightCodeList: KnockoutObservableArray<number>;
        // received from mother screen
        rightItems: KnockoutObservableArray<NewItemModel>;
        attrLabel: KnockoutObservable<String>;
        itemNameLabel: KnockoutObservable<String>;
        listBudget: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            self.attrLabel = ko.observable(data.attribute);
            self.itemNameLabel = ko.observable(data.itemName);
            
            self.items = ko.observableArray([]);
            self.rightItems = ko.observableArray([]);  
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 180, formatter: _.escape},
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'id', width: 180, hidden: true}
            ]);
            
            self.currentCodeList = ko.observableArray([]);
            
            self.rightItemcolumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'id', width: 170, hidden: true},
                { headerText: nts.uk.resource.getText("KML002_36"), prop: 'operatorAtr', width: 80 },
                { headerText: nts.uk.resource.getText("KML002_7"), prop: 'name', width: 120, formatter: _.escape }
            ]);
            
            self.currentRightCodeList = ko.observableArray([]);
            self.listBudget = ko.observableArray([]);
            self.checked.subscribe((value) => {
                if(self.rightItems().length > 0){
                    nts.uk.ui.dialog.confirm({ messageId: "Msg_194" }).ifYes(() => {
                        self.rightItems([]);
                        if(value){
                            let foundItem = _.find(self.listBudget(), (item: ItemModel) => {
                                return item.name ==  nts.uk.resource.getText("KML002_109");
                            });
                            self.items([]);
                            self.rightItems([]);
                            self.currentCodeList.removeAll();
                            self.items.push(foundItem);
                            self.items.push(search);
                        }else{
                            self.items(self.listBudget());
                        }
                    })
                }else{
                    if(value){
                        let foundItem = _.find(self.listBudget(), (item: ItemModel) => {
                            return item.name ==  nts.uk.resource.getText("KML002_109");
                        });
                        self.items([]);
                        self.rightItems([]);
                        self.currentCodeList.removeAll();
                        self.items.push(foundItem);
                        self.items.push(search);
                    }else{
                        self.items(self.listBudget());
                    }
                }
            })
        }

        /**   
         * Start page.
         */
        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let array = [];
            let i = 2;
            var data = nts.uk.ui.windows.getShared("KML002_A_DATA");
            let param = {
                budgetAtr: 1,   
                // received from mother screen 0: day or 1: time
                unitAtr: data.unit
            }
            
            service.getByAtr(param).done((lst) => {  
                let sortedData= _.orderBy(lst, ['externalBudgetCode'], ['asc']);
                _.map(sortedData, function(item){
                    array.push({
                        id: i,
                        code: item.externalBudgetCode,
                        name: item.externalBudgetName + nts.uk.resource.getText("KML002_44")                             
                    })
                    i = i +1;    
                });
                let a = {
                    id: 0,
                    code: (array.length+1).toString(),
                    name: nts.uk.resource.getText("KML002_109")
                }
                let b = {
                    id: 1,
                    code: (array.length+2).toString(),
                    name: nts.uk.resource.getText("KML002_110")
                }
                array.push(a);
                array.push(b);
                let sortedLst= _.orderBy(array, ['id'], ['asc']);
                self.items(sortedLst); 
                self.listBudget(sortedLst);
                
                if(data.formPeople != null) {
                    if(data.formPeople.lstPeopleFunc.length > 0) {
                        let dataItems = [];
                        self.checked(data.formPeople.actualDisplayAtr == 1? true: false);
                        _.forEach(data.formPeople.lstPeopleFunc, function(item){
                            let curItem = _.find(self.items(), function(o) { return o.code == item.externalBudgetCd; });
                            
                            let data = {
                                id: item.dispOrder,
                                code: item.externalBudgetCd,
                                operatorAtr: item.operatorAtr == 0 ? nts.uk.resource.getText("KML002_37") : nts.uk.resource.getText("KML002_38"),
                                name: curItem.name
                            }
            
                            dataItems.push(data);
                        });
                        
                        self.rightItems.removeAll();
                        var sortedItems = _.sortBy(dataItems, [function(o) { return o.id; }]);
                        self.rightItems(sortedItems);
                    }
                }
                    
                dfd.resolve();
            });
            
            return dfd.promise();
        }
        
        /**
         * event when click register button
         */
        submit() {
            var t0 = performance.now();
            var self = this;
            let dataItems = [];
            var dataA = nts.uk.ui.windows.getShared("KML002_A_DATA");
            
            _.forEach(self.rightItems(), function(item, index){
                let data = {
                    verticalCalCd: dataA.verticalCalCd,
                    verticalCalItemId: dataA.itemId,
                    externalBudgetCd: item.code,
                    categoryAtr: 1,
                    operatorAtr: item.operatorAtr == nts.uk.resource.getText("KML002_37") ? 0 : 1,
                    dispOrder: index,
                    name: item.name
                }

                dataItems.push(data);
            });

            let transfer = {
                verticalCalCd: dataA.verticalCalCd,
                verticalCalItemId: dataA.itemId,                
                actualDisplayAtr: self.checked() ? 1 : 0,
                lstPeopleFunc: dataItems
            }
            
            setSharedD('KML002_D_Budget', transfer);
            nts.uk.ui.windows.close();
            
            var t1 = performance.now();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        }
        
        /**
         * event when click close button
         */
        cancel() {
            var self = this;
            nts.uk.ui.windows.close();
        }
        
        /**
         * event when click add button 
         */
        add(){
              var t0 = performance.now();
            let self = this;
            console.log(self.rightItems());
            if((self.rightItems().length + self.currentCodeList().length) > 100){
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            }else{
                var righItems = self.rightItems();
                let i = self.rightItems().length;
                _.forEach(self.currentCodeList(), function(item){
                    var temp = _.find(self.listBudget(), function(obj) {
                        return item == obj.id;
                    });
                    
                    righItems.push({
                        id: i,
                        code: temp.code,
                        operatorAtr: nts.uk.resource.getText("KML002_37"),
                        name: temp.name,    
                    })
                    i = i+1;
                });
                self.rightItems(righItems);
                console.log(self.rightItems());
            }
             var t1 = performance.now();
            console.log("Selection process " + (t1 - t0) + " milliseconds.");
        }
        
        /**
         * event when click subtraction button
         */  
        sub(){
            let self = this; 
            if((self.rightItems().length + self.currentCodeList().length) > 100){
                nts.uk.ui.dialog.info({ messageId: "Msg_195" });
            }else{   
                var righItems = self.rightItems();
                let i = self.rightItems().length;
                _.forEach(self.currentCodeList(), function(item){
                    var temp = _.find(self.listBudget(), function(obj) {
                        return item == obj.id;
                    });
                    righItems.push({
                        id: i,
                        code: temp.code,
                        operatorAtr: nts.uk.resource.getText("KML002_38"),
                        name: temp.name,    
                    }) 
                    i = i+1;
                });
                self.rightItems(righItems);
                console.log(self.rightItems());
            }
        }
        
        /**
         * remove selected items in the right list
         */
        back(){
            let self = this;
            let array =[];
            console.log(self.rightItems());
            self.rightItems(_.filter(self.rightItems(), function(item) {
                return _.indexOf(self.currentRightCodeList(), item.id.toString()) < 0;
            }));
            for(let i = 0; i < self.rightItems().length; i++){
                self.rightItems()[i].id = i;    
            }
            self.currentRightCodeList.removeAll();
            console.log(self.rightItems());
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
        id: number;
        code: string;
        operatorAtr: string;
        name: string;
        constructor(id: number, code: string, operatorAtr: string, name: string) {
            this.id = id;
            this.code = code;
            this.operatorAtr = operatorAtr;
            this.name = name;       
        }
    } 
}