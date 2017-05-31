module kdl007.a.viewmodel {
    export class ScreenModel {

        isMulti: boolean;
        items: KnockoutObservableArray<model.ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;
        posibleItems: Array<number>;
        dataSoure: Array<model.ItemModel>;

        constructor() {
            var self = this;
            self.isMulti = true;
//            self.isMulti = nts.uk.ui.windows.getShared('Multiple');
            self.items = ko.observableArray([]);
            //header
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL007_2"), prop: 'workTypeCode', width: 50 },
                { headerText: nts.uk.resource.getText("KDL007_3"), prop: 'name', width: 200 ,formatter: _.escape},
            ]);
            self.currentCodeList = ko.observableArray([]);
            self.posibleItems = [];
            self.dataSoure = [];
            self.start();
        }
        //load data
        start() {
            var self = this;
            //all possible attendance items
            self.posibleItems = nts.uk.ui.windows.getShared('AllItemObj');
            //selected items
//            self.currentCodeList(nts.uk.ui.windows.getShared('SelectedItemId'));
            //the fist item 
            self.dataSoure.push(new model.ItemModel("", "選択なし",""));
            self.dataSoure.push(new model.ItemModel("1", "勤怠項目1",""));
            self.dataSoure.push(new model.ItemModel("2", "勤怠項目2",""));
            self.dataSoure.push(new model.ItemModel("3", "勤怠項目3",""));
            //set source
//            if (self.posibleItems.length > 0) {
//                service.getPossibleItem(self.posibleItems).done(function(lstItem: Array<any>) {
//                    for (let i in lstItem) {
//                        self.dataSoure.push(new ItemModel(lstItem[i].attendanceItemId.toString(), lstItem[i].attendanceItemName.toString()));
//                    };
//                    //set source
//                    self.items(self.dataSoure);
//                }).fail(function(res) {
//                    nts.uk.ui.dialog.alert(res.message);
//                });

//            }
            //勤怠項目の指定が0件の場合
            //set source
            self.items(self.dataSoure);
        }
        //event When click to 設定 ボタン
        register() {
            var self = this;
            if(self.isMulti == true){
                if (self.currentCodeList().length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_10"});
                } 
            }else {
                    nts.uk.ui.windows.setShared('selectedChildItem', self.currentCodeList());
                    nts.uk.ui.windows.close();
            }
        }

        //Close Dialog
        close() {
            nts.uk.ui.windows.close();
        }
    }
    export module model {
        export class ItemModel {
            workTypeCode: string;
            name: string;
            memo: string;
            constructor(workTypeCode: string, name: string, memo: string) {
                this.workTypeCode = workTypeCode;
                this.name = name;
                this.memo = memo;
            }
        }
    
    }

}