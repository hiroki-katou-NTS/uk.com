module kdl021.a.viewmodel {
    export class ScreenModel {
        //parameter
        isMulti: boolean;
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        allItems: Array<ItemModel>;
        posibleItems: Array<number>;

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
            
             
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 70 },
                { headerText: '名称', prop: 'name', width: 230 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray();
            //all possible attendance items
            self.posibleItems = nts.uk.ui.windows.getShared('AllAttendanceObj');
            //selected attendace items
            self.currentCodeList(nts.uk.ui.windows.getShared('SelectedAttendanceId'));
            //set source
            service.getPossibleItem(self.posibleItems).done(function(lstItem: Array<service.model.DivergenceItem>){
                for (let i in lstItem) {
                    self.items.push(new ItemModel(lstItem[i].attendanceItemId.toString(), lstItem[i].attendanceItemName.toString()));
                };
            })
        }
        //event When click to 設定 ボタン
        register(){
            var self = this;
            nts.uk.ui.windows.setShared('selectedChildAttendace',self.currentCodeList());
            nts.uk.ui.windows.close();
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