module kdl021.a.viewmodel {
    export class ScreenModel {
        //parameter
        isMulti: boolean;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        allItems: Array<ItemModel>;
        selectedItems: Array<string>;

        constructor() {
            var self = this;
            self.isMulti = true;
            self.items = ko.observableArray([]);
            //total 
            self.allItems = [];
            //seleted items
            self.selectedItems = [];
            //Add the fisrt Item
            self.items.push(new ItemModel("", "選択なし"))；
            
            for (let i = 1; i <= 15; i++) {
                let code = padZero(i.toString());
                let name = "残業時間" + i;
                //self.items.push(new ItemModel(code, name));
                self.allItems.push(new ItemModel(code, name));
            }
            for(let item in self.items()){
                //console.log(self.items);      
            }
            for (let i = 101; i <= 201; i++) {
                let code: string = padZero(i.toString());
                    self.selectedItems.push(i);
            };
            
            service.getPossibleItem(self.selectedItems).done(function(lstItem: Array<service.model.DivergenceItem>){
                //console.log(lstItem);
                for (let i in lstItem) {
                    self.items.push(new ItemModel(lstItem[i].divItemId.toString(), lstItem[i].divItemName.toString()));
                };
            })
             
            
            //let allAttendance : Array<any> = nts.uk.ui.windows.getShared('AllAttendanceObj');
//            for(let i in allAttendance){
//                self.items.push(new ItemModel(allAttendance[i].divItemId.toString(),allAttendance[i].divItemName.toString()));
//                let key =  Object.keys(allAttendance[i])[1];
//            };
            
            //debugger;
            //nts.uk.ui.windows.getShared('SelectedAttendanceId', listIdSel, true);
//            console.log(allAttendance);
//            debugger;
            //let returnVal : Array<any> = nts.uk.ui.windows.getShared('');
            //console.log(self.allItems);
            //console.log(self.selectedItems);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 70 },
                { headerText: '名称', prop: 'name', width: 230 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray();
            
        }
        //event When click to 設定 ボタン
        register(){
            var self = this;
//            self.selectedItems.splice(1,1);
//            console.log(self.selectedItems);
//            self.currentCodeList(self.selectedIstems);
            //console.log(self.currentCodeList());
            nts.uk.ui.windows.setShare('selectedChildAttendace',self.currentCodeList(),true);
        }
        //Close Dialog
        close(){
            nts.uk.ui.windows.close();
        }
    }
    //Format Code : Pad 0 to Code
    function padZero(code: string) {
        var length: number = code.length;
        var codeFm: string = "00000";
        return codeFm.substr(0, 5 - length) + code;
    }
    //
    class ItemModel {
        code: string;
        name: string;
        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }
    
    
}