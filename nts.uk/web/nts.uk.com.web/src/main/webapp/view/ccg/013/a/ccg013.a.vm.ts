module ccg013.a.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        items: KnockoutObservableArray<ItemModel>;
        columns2: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        itemMenu: KnockoutObservableArray<any>;
        contextmenu1: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.paymentDateProcessingList = ko.observableArray([]);
            self.selectedPaymentDate = ko.observable(null);
            self.items = ko.observableArray([]);

            self.itemMenu = ko.observableArray([
                { Name: '個人設定', code: "context-menu1" },
                { Name: '導入時フロー', code: "context-menu2" },
                { Name: '個人情報の登録', code: "context-menu3" },
                { Name: '個人情報の登録', code: "context-menu3" },
                { Name: '個人情報の登録', code: "context-menu3" }
            ]);

            self.columns2 = ko.observableArray([
                { headerText: '既定', key: 'code', width: 100 },
                { headerText: 'コード', key: 'name', width: 150 },
                { headerText: '名称', key: 'description', width: 150 }
            ]);

            var menu1 = new nts.uk.ui.contextmenu.ContextMenu(".context-menu1", [
                new nts.uk.ui.contextmenu.ContextMenuItem("cut", "メニューバーの編集(U)", (ui) => { $(ui).remove(); }),
                new nts.uk.ui.contextmenu.ContextMenuItem("copy", "メニューバーの削除(D)", (ui) => { alert("Copy"); })
            ]);

            var menu2 = new nts.uk.ui.contextmenu.ContextMenu(".context-menu2", [
                new nts.uk.ui.contextmenu.ContextMenuItem("cut", "タイトルメニューの編集(U)", (ui) => { alert("Cut: "); }),
                new nts.uk.ui.contextmenu.ContextMenuItem("copy", "タイトルメニューの削除(D)", (ui) => { alert("Copy"); })
            ]);


            var menu3 = new nts.uk.ui.contextmenu.ContextMenu(".context-menu3", [
                new nts.uk.ui.contextmenu.ContextMenuItem("copy", "ニューの削除(D)", (ui) => { alert("Copy"); })
            ]);



            self.currentCode = ko.observable();


        }

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            service.getPaymentDateProcessingList().done(function(data) {
                self.paymentDateProcessingList(data);
                dfd.resolve();
            }).fail(function(res) {

            });
            return dfd.promise();
        }
    }

    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        deletable: boolean;
        constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;
            this.deletable = deletable;
        }
    }
}