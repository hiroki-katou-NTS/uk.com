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

            self.items.push(new ItemModel("", "選択なし"))；
            
            for (let i = 1; i <= 15; i++) {
                let code = padZero(i.toString());
                let name = "残業時間" + i;
                self.items.push(new ItemModel(code, name));
                self.allItems.push(new ItemModel(code, name));
            }
            console.log(self.items());
            for(let i in self.items()){
                //console.log(self.items);      
            }
            for (let i = 4; i <= 12; i++) {
                let code: string = padZero(i.toString());
                if (i % 3 == 0) {
                    self.selectedItems.push(code);
                }
            };
            //let returnVal : Array<any> = nts.uk.ui.windows.getShared('');
            //console.log(self.allItems);
            //console.log(self.selectedItems);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'code', width: 70 },
                { headerText: '名称', prop: 'name', width: 230 }
            ]);
            self.currentCode = ko.observable();
            self.currentCodeList = ko.observableArray(self.selectedItems);
        }
        //event When click to 設定 ボタン
        register(){
            var self = this;
//            self.selectedItems.splice(1,1);
//            console.log(self.selectedItems);
//            self.currentCodeList(self.selectedIstems);
            //console.log(self.currentCodeList());
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