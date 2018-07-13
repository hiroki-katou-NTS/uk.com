module nts.uk.com.view.cmf002.g.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog = nts.uk.ui.dialog;
    import model = cmf002.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;

    export class ScreenModel {
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        
        currentCode: KnockoutObservable<any>;

        itemList: KnockoutObservableArray<ItemModelDetail>;

        constructor() {
            let self = this;

            self.itemList = ko.observableArray();
            $("#fixed-table").ntsFixedTable({ height: 300, width: 600 });

            self.items = ko.observableArray([]);

            for (let i = 0; i < 5; i++) {
                self.items.push(new ItemModel('00' + i, '基本給'));
            }

            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 100, hidden: false },
                { headerText: '名称', key: 'name', width: 150, hidden: false }
            ]);

            self.currentCode = ko.observable();
        }

        addItem() {
            let self = this;
            self.itemList.push(new ItemModelDetail('', ''));
        }

        removeItem() {
            let self = this;
            self.itemList.pop();
        }

        start(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
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

    class ItemModelDetail {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;

        constructor(code: string, name: string) {
            this.code = ko.observable();
            this.name = ko.observable();
        }
    }
}
