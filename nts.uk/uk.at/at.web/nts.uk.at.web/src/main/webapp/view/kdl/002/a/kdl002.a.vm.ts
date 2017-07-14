module kdl002.a.viewmodel {
    export class ScreenModel {

        isMulti: boolean;
        items: KnockoutObservableArray<model.ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservableArray<any>;
        posibleItems: Array<string>;
        dataSoure: Array<model.ItemModel>;

        constructor() {
            var self = this;
            self.isMulti = true;
            self.items = ko.observableArray([]);
            //header
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL002_3"), prop: 'workTypeCode', width: 70 },
                { headerText: nts.uk.resource.getText("KDL002_4"), prop: 'name', width: 200 ,formatter: _.escape},
                { headerText: nts.uk.resource.getText("KDL002_5"), prop: 'memo', width: 230 ,formatter: _.escape}
            ]);
            self.currentCodeList = ko.observableArray([]);;
            self.posibleItems = [];
            self.start();
        }
        //load data
        start() {
            var self = this;
            
            self.isMulti = nts.uk.ui.windows.getShared('KDL002_Multiple');
            //all possible items
            self.posibleItems = nts.uk.ui.windows.getShared('KDL002_AllItemObj');
            //selected items
            var selectCode = nts.uk.ui.windows.getShared('KDL002_SelectedItemId');
            self.currentCodeList(selectCode);
            //set source
            if(self.posibleItems == null || self.posibleItems === undefined){
                self.items();
                return;
            }
            if (self.posibleItems.length > 0) {
                service.getItemSelected(self.posibleItems).done(function(lstItem: Array<model.ItemModel>) {
                    $("input").focus();
                    let lstItemMapping =  _.map(lstItem , item => {
                        return new model.ItemModel(item.workTypeCode, item.name, item.memo);
                    });
                    self.items(lstItemMapping);
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });

            }
        }
        //event When click to 決定 ボタン
        register() {
            var self = this;
            if(self.isMulti == true){
                let lstObj = [];
                for (let i =0, length = self.currentCodeList().length; i< length ;i++) {
                    let objectNew = self.findItem(self.currentCodeList()[i]);
                    if(objectNew != undefined && objectNew != null){
                        lstObj.push({"code": objectNew.workTypeCode, "name":objectNew.name});
                    }
                }
                if (lstObj.length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_10"});
                    return;
                }

                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', lstObj,true);
            }else{
                let objectNew2 = self.findItem(self.currentCodeList().toString());
                if(objectNew2 != undefined && objectNew2 != null){
                    var lstObj2 ={ "code": objectNew2.workTypeCode, "name":objectNew2.name};
                }
                let listnew =[];
                listnew.push(lstObj2);
                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', listnew,true);
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
                return obj.workTypeCode == value;
            })
        }
        //Close Dialog
        close() {
            var self = this;
            let selectCode = nts.uk.ui.windows.getShared('KDL002_SelectedItemId');
            if(self.isMulti == true){
                let lstObj = [];
                for (let i =0, length = selectCode.length; i< length ;i++) {
                    let objectNew = self.findItem(selectCode[i]);
                    if(objectNew != undefined && objectNew != null){
                        lstObj.push({"code": objectNew.workTypeCode, "name":objectNew.name});
                    }
                }

                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', lstObj,true);
            }else{
                let objectNew2 = self.findItem(selectCode.toString());
                if(objectNew2 != undefined && objectNew2 != null){
                    var lstObj2 ={ "code": objectNew2.workTypeCode, "name":objectNew2.name};
                }
                let listnew =[];
                listnew.push(lstObj2);
                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', listnew,true);
            }
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