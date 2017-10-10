module cps002.f.vm {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import close = nts.uk.ui.windows.close;
    import modal = nts.uk.ui.windows.sub.modal;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    

    export class ViewModel {
        lstCategory: KnockoutObservableArray<PerInfoCtg>;
        currentPerInfoCtg: KnockoutObservable<string> = ko.observable("");
        lstPerInfoItemDef: KnockoutObservableArray<PerInforItemDef>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        columnPerInfoItemDef: KnockoutObservableArray<NtsGridListColumn>;
        currentPerInfoItem: KnockoutObservableArray<string> = ko.observableArray([]);
        txtSearch: KnockoutObservable<string> = ko.observable("");
         
        constructor(){
            let self = this;
            self.lstCategory = ko.observableArray([]);
            self.lstPerInfoItemDef = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                {headerText: "", key: 'id', width: 45, hidden: true},
                {headerText: nts.uk.resource.getText("CPS002_70"), key: 'alreadyCopy', width: 45, formatter: setPerInfoCtgFormat},
                {headerText: nts.uk.resource.getText("CPS002_71"), key: 'categoryName', width: 150}
            ]);
            
            self.columnPerInfoItemDef = ko.observableArray([
                {headerText: "", key: 'id', width: 45, hidden: true},
                {headerText: nts.uk.resource.getText("CPS002_75"), key: 'itemName', width: 250}
            ]);
            
            
            self.currentPerInfoCtg.subscribe(id => {
                service.getPerInfoItemDef(id).done(function(data){
                    self.lstPerInfoItemDef(data);    
                    let perItemCopy = _.filter(data, function(item){ return item.alreadyItemDefCopy == true;}).map(function(item){return item.id;});
                    self.currentPerInfoItem(perItemCopy);         
                });
//                let dataArr = _.filter(self.data.lstPerItem, function(item){ return item.perCtgId == id;});
//                let perItemCopy = _.filter(dataArr, function(item){ return item.alreadyItemDefCopy == true;}).map(function(item){return item.id;});
//                self.currentPerInfoItem(perItemCopy);
//                self.lstPerInfoItemDef(dataArr);  
            });
            
            self.start();
           
        }
        register(){
            let self = this;
           // let dataBind = self.lstPerInfoItemDef();
            let itemIds = self.currentPerInfoItem();
            let categoryId = self.currentPerInfoCtg();
            service.updatePerInfoCtgCopy(categoryId).done(function(){ service.updatePerInfoItemCopy(categoryId, itemIds);});
           
        }
        close(){
            close();
        }
        start(){
            let self = this;
            //let dataArr = self.data.listPerCtg;
            //self.lstCategory(dataArr);
            //self.currentPerInfoCtg(dataArr[0].id);
            let ctgName = "";
            service.getPerInfoCtgHasItems(ctgName).done(function(data){
                self.lstCategory(data);
                self.currentPerInfoCtg(data[0].id);
            }).fail(function(){
                alertError({ messageId: "Msg_352" });
            });
           
        }
        
        searchByName(){
            let self = this;
            service.getPerInfoCtgHasItems(self.txtSearch()).done(function(data){
                self.lstCategory(data);
            }).fail(function(){
                alertError({ messageId: "Msg_352" });
            });
        }
        
//        data = {
//            listPerCtg : [{id: "123a", alreadyCopy: false, categoryName: "A"},
//                    {id: "124a", alreadyCopy: false, categoryName: "B"},
//                    {id: "125a", alreadyCopy: true, categoryName: "C"},
//                    {id: "126a", alreadyCopy: true, categoryName: "D"},
//                    {id: "127a", alreadyCopy: false, categoryName: "E"},
//                    {id: "211a", alreadyCopy: false, categoryName: "F"},
//                    {id: "212a", alreadyCopy: true, categoryName: "G"},
//                    {id: "213a", alreadyCopy: false, categoryName: "H"},         
//                    {id: "214a", alreadyCopy: true, categoryName: "I"},
//                    {id: "216a", alreadyCopy: true, categoryName: "J"},
//                    {id: "217a", alreadyCopy: false, categoryName: "K"},
//                    {id: "218a", alreadyCopy: false, categoryName: "L"},
//                    {id: "219a", alreadyCopy: true, categoryName: "M"},
//                    {id: "200a", alreadyCopy: false, categoryName: "N"},
//                    {id: "112a", alreadyCopy: true, categoryName: "O"}
//            ],
//           lstPerItem : [  {id: "1", itemName: "AA", perCtgId: "123a", alreadyItemDefCopy: true},
//                    {id: "2", itemName: "BB", perCtgId: "124a", alreadyItemDefCopy: true},
//                    {id: "3", itemName: "CC", perCtgId: "127a", alreadyItemDefCopy: false},
//                    {id: "4", itemName: "DD", perCtgId: "123a", alreadyItemDefCopy: false},
//                    {id: "5", itemName: "FF", perCtgId: "212a", alreadyItemDefCopy: false},
//                    {id: "6", itemName: "EE", perCtgId: "213a", alreadyItemDefCopy: false},
//                    {id: "7", itemName: "GG", perCtgId: "123a", alreadyItemDefCopy: true},
//                    {id: "8", itemName: "HH", perCtgId: "124a", alreadyItemDefCopy: true},
//                    {id: "9", itemName: "II", perCtgId: "126a", alreadyItemDefCopy: false},
//                    {id: "11", itemName: "JJ", perCtgId: "125a", alreadyItemDefCopy: true},
//                    {id: "12", itemName: "KK", perCtgId: "213a", alreadyItemDefCopy: false},
//                    {id: "13", itemName: "QW", perCtgId: "211a", alreadyItemDefCopy: false},
//                    {id: "14", itemName: "RT", perCtgId: "211a", alreadyItemDefCopy: true},
//                    {id: "15", itemName: "DF", perCtgId: "213a", alreadyItemDefCopy: false},
//                    {id: "16", itemName: "EERRR", perCtgId: "212a", alreadyItemDefCopy: false},
//                    {id: "17", itemName: "YU", perCtgId: "125a", alreadyItemDefCopy: true},
//                    {id: "18", itemName: "ER", perCtgId: "214a", alreadyItemDefCopy: false},
//                    {id: "19", itemName: "BS", perCtgId: "127a", alreadyItemDefCopy: true},
//                    {id: "21", itemName: "ER", perCtgId: "211a", alreadyItemDefCopy: false},
//                    {id: "22", itemName: "AAAE", perCtgId: "212a", alreadyItemDefCopy: false},
//                    {id: "23", itemName: "RT", perCtgId: "214a", alreadyItemDefCopy: true},
//                    {id: "24", itemName: "RAA", perCtgId: "214a", alreadyItemDefCopy: true},
//                    {id: "25", itemName: "YBB", perCtgId: "216a", alreadyItemDefCopy: true},
//                    {id: "26", itemName: "ICC", perCtgId: "214a", alreadyItemDefCopy: false},
//                    {id: "27", itemName: "PDD", perCtgId: "216a", alreadyItemDefCopy: true},
//                    {id: "28", itemName: "NFF", perCtgId: "200a", alreadyItemDefCopy: false},
//                    {id: "29", itemName: "MEE", perCtgId: "112a", alreadyItemDefCopy: false},
//                    {id: "30", itemName: "AGG", perCtgId: "218a", alreadyItemDefCopy: true},
//                    {id: "32", itemName: "LHH", perCtgId: "219a", alreadyItemDefCopy: true},
//                    {id: "221", itemName: "OII", perCtgId: "200a", alreadyItemDefCopy: false},
//                    {id: "325", itemName: "PJJ", perCtgId: "217a", alreadyItemDefCopy: true},
//                    {id: "125", itemName: "WKK", perCtgId: "219a", alreadyItemDefCopy: false},
//                    {id: "88", itemName: "TQW", perCtgId: "218a", alreadyItemDefCopy: false},
//                    {id: "74", itemName: "ART", perCtgId: "217a", alreadyItemDefCopy: true}
//]
//        };
        
    }
    
    interface IPerInfoCtg{
        id: string,
        alreadyCopy: boolean,
        categoryName: string
    }
    class PerInfoCtg{
        id: KnockoutObservable<string> = ko.observable("");
        alreadyCopy: KnockoutObservable<boolean> = ko.observable(false);
        categoryName: KnockoutObservable<string> = ko.observable("");
        constructor(param: IPerInfoCtg){
            let self = this;
            self.id(param.id || "");
            self.alreadyCopy(param.alreadyCopy || false);
            self.categoryName(param.categoryName || "");
        }
    }
    
    interface IPerInfoItemDef{
        id:string,
        itemName: string,
        perCtgId: string,
        alreadyItemDefCopy: boolean
    }
    class PerInforItemDef{
        id: KnockoutObservable<string> = ko.observable("");
        itemName: KnockoutObservable<string> = ko.observable("");
        perCtgId: KnockoutObservable<string> = ko.observable("");
        alreadyItemDefCopy: KnockoutObservable<boolean> = ko.observable(false);
        constructor(param: IPerInfoItemDef){
            let self = this;
            self.id(param.id || "");
            self.itemName(param.itemName || ""); 
            self.itemName(param.perCtgId || "");
            self.alreadyItemDefCopy(param.alreadyItemDefCopy || false);
        }
    }
}

function setPerInfoCtgFormat(value){
    if (value == "true")
        return '<i class="icon icon-dot setStyleDot"></i>';
    return '';
}
