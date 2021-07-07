module nts.uk.at.view.kdw006.h.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        currentCodeList: KnockoutObservableArray<any>;
        multicheck: boolean;

        constructor() {
            let self = this;
            self.items = ko.observableArray([]);
            self.currentCodeList = ko.observableArray([]);
            self.multicheck = false;

        }

        returnData() {
            let self = this;
            nts.uk.ui.windows.setShared('kdw006HResult', self.currentCodeList());
            nts.uk.ui.windows.close();
        }

        closeDialog() {
            nts.uk.ui.windows.close();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            var dfd = $.Deferred();
            let getData = nts.uk.ui.windows.getShared("kdw006CResult");
            let appTypeEnum = __viewContext.enums.ApplicationType;
            _.forEach(appTypeEnum, (obj) => {
                if (obj.value == 0 || 
                    obj.value == 1 ||
                    obj.value == 2 ||
                    obj.value == 3 ||
                    obj.value == 4 ||
                    obj.value == 5 ||
                    obj.value == 6 ||
                    obj.value == 7 ||
                    obj.value == 8 ||
                    obj.value == 13 ||
                    obj.value == 14 ||
                    obj.value == 15) {
                    self.items.push(new ItemModel(obj.value, obj.name));
                }
            });
            self.currentCodeList(getData.appTypes);
            self.multicheck = getData.multi;
            dfd.resolve();
            return dfd.promise();
        }
    }

    class ItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}