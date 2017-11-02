module nts.uk.at.view.cdl024.viewmodel {
    export class ScreenModel {
        items: KnockoutObservableArray<IItemModel> = ko.observableArray([]);
        currentCodeList: KnockoutObservableArray<string> = ko.observableArray([]);

        constructor() {
            let self = this,
                items = self.items,
                codeList = self.currentCodeList;
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        sendAttribute() {
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
                var parameter = nts.uk.ui.windows.getShared("CDL024");
                if (parameter != null && parameter.reasonCD != null) {
                    self.currentCodeList(parameter.reasonCD);
                }
                dfd.resolve();
            });


            return dfd.promise();
        }
    }

    export interface IItemModel {
        code: string;
        name: string;
    }
}