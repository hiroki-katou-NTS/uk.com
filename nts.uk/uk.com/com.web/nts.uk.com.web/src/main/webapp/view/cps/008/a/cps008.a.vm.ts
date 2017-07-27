module cps008.a.viewmodel {


    export class ScreenModel {

        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        A_INP_LAYOUT_CODE: KnockoutObservable<string>;
        A_INP_LAYOUT_NAME: KnockoutObservable<string>;
        A_INP_LAYOUT_CODE_ENABLE: KnockoutObservable<boolean>;
        A_INP_LAYOUT_NAME_ENABLE: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.items = ko.observableArray([]);

            for (let i = 1; i < 100; i++) {
                self.items.push(new ItemModel('00' + i, '基本給'));
            }
            self.columns = ko.observableArray([
                { headerText: 'コード', key: 'code', width: 120, hidden: false },
                { headerText: '名称', key: 'name', width: 171, hidden: false }
            ]);

            self.currentCode = ko.observable();
            self.A_INP_LAYOUT_CODE = ko.observable(null);
            self.A_INP_LAYOUT_NAME = ko.observable(null);
            self.A_INP_LAYOUT_CODE_ENABLE = ko.observable(true);
            self.A_INP_LAYOUT_NAME_ENABLE = ko.observable(true);
        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred<any>();
            return null;
        }

        openDialogCoppy() {
            var self = this;
            nts.uk.ui.windows.sub.modal('/view/cps/008/c/index.xhtml', { title: '他のレイアウトへ複製' }).onClosed(function(): any {
                

            });
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