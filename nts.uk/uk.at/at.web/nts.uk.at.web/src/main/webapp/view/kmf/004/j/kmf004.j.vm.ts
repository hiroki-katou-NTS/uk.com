module nts.uk.at.view.kmf004.j.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentCodeList: KnockoutObservableArray<any>;
        selectedItems: KnockoutObservableArray<any>;
        
        constructor() {
            let self = this;
            
            self.selectedItems = nts.uk.ui.windows.getShared("KMF004_A_SELECTED_ITEMS");
            
            self.items = ko.observableArray([]);
            
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText('KMF004_148'), key: 'code', width: 100, hidden: true },
                { headerText: nts.uk.resource.getText('KMF004_148'), key: 'name', width: 320 }
            ]); 
            
            self.currentCodeList = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            
            for(let i = 1; i <= 10; i++) {
                self.items.push(new ItemModel('00' + i, '基本給 ' + i));
            }
            
            if(self.items().length > 0) {
                if(self.selectedItems.length > 0) {
                    self.currentCodeList.removeAll();
                    _.forEach(self.selectedItems, function(item) {
                        self.currentCodeList.push(item);
                    });
                }
            } else {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1267" });
            }
            
            dfd.resolve();

            return dfd.promise();
        }
        
        submit() {
            let self = this;
            
            if(self.currentCodeList().length > 0) {
                nts.uk.ui.windows.setShared("KMF004_J_SELECTED_FRAME", self.currentCodeList());
                nts.uk.ui.windows.close();
            } else {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1268" });
            }
        }
        
        cancel() {
            nts.uk.ui.windows.close();
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
}