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
                    let lstItemOrder = self.sortbyList(lstItem);
                    $("input").focus();
                    let lstItemMapping =  _.map(lstItemOrder , item => {
                        return new model.ItemModel(item.workTypeCode, item.name, item.memo);
                    });
                    self.items(lstItemMapping);
                }).fail(function(res) {
                    nts.uk.ui.dialog.alert(res.message);
                });

            }
        }
        /**
         * sort list by:
         * 1. dispOrder
         * 2. Code
         */
        sortbyList(lstItem: Array<any>): Array<any>{
            let lstA: Array<any> = [];//list order != null
            let lstB: Array<any> = [];//list order = null
            let result: Array<any> = [];//list sort
            _.each(lstItem, function(item){
                if(item.dispOrder != null){
                    lstA.push(item);
                }
                if(item.dispOrder == null){
                    lstB.push(item);
                }
            });
            let sortByA =  _.orderBy(lstA, ["dispOrder"], ["asc"]);
            let sortByB =  _.orderBy(lstB, ["workTypeCode"], ["asc"]);
                //push list A (common)
                _.each(sortByA, function(obj){
                    result.push(obj);
                });
            _.each(sortByB, function(obj){
                    result.push(obj);
                });
            return result;
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
                let lstItem2 = _.orderBy(lstObj,['code'],['asc']);
                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', lstItem2);
            }else{
                let lstObj2 = [];
                let objectNew2 = self.findItem(self.currentCodeList());
                if(objectNew2 != undefined && objectNew2 != null){
                    lstObj2.push({ "code": objectNew2.workTypeCode, "name":objectNew2.name});
                }
                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', lstObj2);
            }
            nts.uk.ui.windows.close();
        }
        /**
         * find item is selected
         */
        findItem(value: any): model.ItemModel {
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
                let lstItem2 = _.orderBy(lstObj,['code'],['asc']);
                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', lstItem2,true);
            }else{
                let lstCancel = [];
                if(selectCode != null && selectCode !== undefined){
                    let objectNew2 = self.findItem(selectCode);
                    if(objectNew2 != undefined && objectNew2 != null){
                        lstCancel.push({ "code": objectNew2.workTypeCode, "name":objectNew2.name});
                    }
                }
                nts.uk.ui.windows.setShared('KDL002_SelectedNewItem', lstCancel,true);
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