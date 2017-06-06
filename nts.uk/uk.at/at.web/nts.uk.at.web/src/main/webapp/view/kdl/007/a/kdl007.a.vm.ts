module kdl007.a.viewmodel {
    export class ScreenModel {

        isMulti: boolean;
        items: KnockoutObservableArray<model.ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<string>;
        posibleItems: Array<string>;
        dataSoure: Array<model.ItemModel>;

        constructor() {
            var self = this;
            self.isMulti = true;
            self.isMulti = nts.uk.ui.windows.getShared('Multiple');
            self.items = ko.observableArray([]);
            //header
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL007_2"), prop: 'code', width: 50 },
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
            self.isMulti = nts.uk.ui.windows.getShared('KDL007_Multiple');
            //all possible attendance items
            self.posibleItems = nts.uk.ui.windows.getShared('KDL007_AllItemObj');
            //selected items
            var selectCode = nts.uk.ui.windows.getShared('KDL007_SelectedItemId');
            if(selectCode==null || selectCode === undefined){
                selectCode = "";
            }
            if(self.isMulti == false){
                self.currentCodeList.push(selectCode);
            }else{
                self.currentCodeList(selectCode);
            }
            
            //get all item 
//            service.getAllItem().done(function(lstItem: Array<model.ItemModel>){
//                if(lstItem==null || lstItem === undefined || lstItem.length ==0){
//                    self.dataSoure.push(new model.ItemModel("", nts.uk.resource.getText("KDL007_6")));
//                }else{
//                    self.dataSoure = _.map(lstItem , item => {
//                        return new model.ItemModel(item.code, item.name);
//                    });
//                }
//            });
            self.dataSoure.push(new model.ItemModel("", nts.uk.resource.getText("KDL007_6")));
            if(self.posibleItems == null || self.posibleItems === undefined){
//                self.dataSoure.push(new model.ItemModel("", nts.uk.resource.getText("KDL007_6")));
            }else{
                self.dataSoure.push(new model.ItemModel("001", "勤怠項目1"));
                self.dataSoure.push(new model.ItemModel("002", "勤怠項目2"));
                self.dataSoure.push(new model.ItemModel("003", "勤怠項目3"));
            }
            
            //勤怠項目の指定が0件の場合
            //set source
            self.items(self.dataSoure);
            var selectItem = [];
                for (let i =0, length = self.currentCodeList().length; i< length ;i++) {
                    let objectNew = self.findItem(self.currentCodeList()[i]);
                    if(objectNew != undefined && objectNew != null){
                        selectItem.push(objectNew.code);
                    }
                }
            if(selectItem.length == 0) self.currentCodeList(['']);
        }
        //event When click to 設定 ボタン
        register() {
            var self = this;

            var lstObj = [];

            if(self.isMulti == true){
                for (let i =0, length = self.currentCodeList().length; i< length ;i++) {
                    let objectNew = self.findItem(self.currentCodeList()[i]);
                    if(objectNew != undefined && objectNew != null){
                        lstObj.push(objectNew.code);
                    }
                }
                if (lstObj.length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_30"});
                    return;
                } 

                nts.uk.ui.windows.setShared('SelectedNewItem', lstObj,true);
            }else{
                nts.uk.ui.windows.setShared('SelectedNewItem', self.currentCodeList(),true);
            }
            nts.uk.ui.windows.close();
        }
        /**
         * find item is selected
         */
        findItem(value: string): model.ItemModel {
            var self = this;
            var itemModel = null;
            return _.find(self.items(), function(obj: model.ItemModel) {
                return obj.code == value;
            })
        }
        //Close Dialog when click キャンセル ボタン
        close() {
            nts.uk.ui.windows.close();
        }
    }
    export module model {
        export class ItemModel {
            code: string;
            name: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }
    
    }

}