module nts.uk.com.view.qmm005.f.viewmodel {
    import model = nts.uk.com.view.qmm005.share.model;
    import resource = nts.uk.resource;
    export class ScreenModel {
        itemsSwap: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
        currentCodeListSwap: KnockoutObservableArray<any>;
        test: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.itemsSwap = ko.observableArray([]);

            var array = [];
            for (var i = 0; i < 50; i++) {
                array.push(new ItemModel(i, '基本給基本給'));
            }
            this.itemsSwap(array);
            this.columns = ko.observableArray([
                { headerText: resource.getText('QMM005_93'), key: 'code', width: 90 },
                { headerText: resource.getText('QMM005_94'), key: 'name', width: 200  }
            ]);
            this.currentCodeListSwap = ko.observableArray([]);
            this.test = ko.observableArray([]);
        }

        startPage(): JQueryPromise<any> {
            var dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }
        remove(){
            this.itemsSwap.shift();
        }

        decision(){

        }

        cancel(){
            nts.uk.ui.windows.close();
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