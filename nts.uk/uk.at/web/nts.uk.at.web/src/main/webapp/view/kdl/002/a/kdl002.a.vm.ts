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
            self.currentCodeList = ko.observableArray([]);
            self.posibleItems = [];
            self.start();
        }
        //load data
        start() {
            var self = this;
//            self.posibleItems = ['001','002','003','005','008'];
            self.isMulti = nts.uk.ui.windows.getShared('ModeMultiple');
            //all possible items
            self.posibleItems = nts.uk.ui.windows.getShared('AllItemObj');
            //selected items
            self.currentCodeList(nts.uk.ui.windows.getShared('SelectedItemId'));
            //set source
            if (self.posibleItems.length > 0) {
                service.getItemSelected(self.posibleItems).done(function(lstItem: Array<model.ItemModel>) {
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
                if (self.currentCodeList().length == 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_10"});
                    return;
                }
            }
            var lstObj = [];
            for (let i =0, length = self.currentCodeList().length; i< length ;i++) {
                let objectNew = self.findItem(self.currentCodeList()[i]);
                lstObj.push({"code": objectNew.workTypeCode, "name":objectNew.name});
            }
            console.log(lstObj);
            nts.uk.ui.windows.setShared('SelectedNewItem', lstObj);
            nts.uk.ui.windows.close();
        }

        //Close Dialog
        close() {
            nts.uk.ui.windows.close();
        }
        /**
         * find item is selected
         */
        findItem(value: string): model.ItemModel {
            let self = this;
            var itemModel = null;
            return _.find(self.items(), function(obj: model.ItemModel) {
                return obj.workTypeCode == value;
            })
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