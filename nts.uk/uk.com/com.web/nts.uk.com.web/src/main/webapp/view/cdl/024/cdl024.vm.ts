module nts.uk.at.view.cdl024.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<IItemModel> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<string> = ko.observableArray([]);

        constructor() {
            let self = this,
                items = self.items,
                codeList = self.currentCodeList;
        }

        closeDialog(){
             nts.uk.ui.windows.close();
        }
        
        sendAttribute(){
            nts.uk.ui.windows.setShared("currentCodeList", this.currentCodeList);
            nts.uk.ui.windows.close();
        }    
        
        startPage(): JQueryPromise<any> {
            let self = this,
                items = self.items,
                codeList = self.currentCodeList,
                dfd = $.Deferred();

            items.removeAll();
            service.getAll().done((data: Array<IItemModel>) => {
                items(data);
                dfd.resolve();
            });
            
            //nts.uk.ui.windows.getShared("companyId");
            codeList(['A5', 'A7', 'A2']);

            return dfd.promise();
        }
    }

    export interface IItemModel {
        code: string;
        name: string;
    }
}