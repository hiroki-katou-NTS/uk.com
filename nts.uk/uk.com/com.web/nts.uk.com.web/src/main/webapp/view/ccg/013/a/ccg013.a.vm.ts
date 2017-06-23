module ccg013.a.viewmodel {
    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        items: KnockoutObservableArray<ItemModel>;
        columns2: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        itemMenu: KnockoutObservableArray<any>;
        contextmenu1: KnockoutObservable<string>;
        simpleValue: KnockoutObservable<string>;
        tabs: KnockoutObservableArray<any>;
        selectedTab: KnockoutObservable<string>;
        currentWebMenu: KnockoutObservable<WebMenu>;
        isCreated: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            
            self.isCreated = ko.observable(true);
            self.currentWebMenu = ko.observable(new WebMenu("", "", false, []));

            self.tabs = ko.observableArray([
                { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-4', title: 'Tab Title 4', content: '.tab-content-4', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-2');
            self.simpleValue = ko.observable("123");
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
                { headerText: '既定', key: 'icon', width: 50 },
                { headerText: 'コード', key: 'webMenuCode', width: 50 },
                { headerText: '名称', key: 'webMenuName', width: 50 }
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
            self.currentCode.subscribe(function(newValue) {
                self.isCreated(false);
                self.findWebMenu(newValue);
            });

        }

        startPage(): JQueryPromise<void> {
            var self = this;
            var dfd = $.Deferred<void>();
            service.loadWebMenu().done(function(data) {
                var list001: Array<ItemModel> = [];
                _.forEach(data, function(item) {
                    list001.push(new ItemModel(item.webMenuCode, item.webMenuName, item.defaultMenu));
                });
                self.items(list001);
                dfd.resolve();
            });
            return dfd.promise();
        }

        addWebMenu(): any {
            var self = this;
            if (self.currentWebMenu().isDefaultMenu()) {
                self.currentWebMenu().defaultMenu(0);    
            } else {
                self.currentWebMenu().defaultMenu(1);
            }
            var webMenu = ko.toJSON(self.currentWebMenu);
            if (!self.isCreated()) {
                service.updateWebMenu(webMenu);
            } else {
                service.addWebMenu(webMenu);
            }
        }

        /**
         * Find a web menu by web menu code
         */
        findWebMenu(webMenuCode: string): any {
            var self = this;       
            service.findWebMenu(webMenuCode).done(function(res) {
                var defaultMenu = true;
                if (res.defaultMenu == 1) {
                    defaultMenu = false;
                }
                self.currentWebMenu(new WebMenu(res.webMenuCode, res.webMenuName, defaultMenu, res.menuBars));
            });
        }
        
        /**
         * Clean all control in form
         */
        cleanForm(): void {
            var self = this;
            self.isCreated(true);
            self.currentWebMenu(new WebMenu("", "", false, []));
            self.currentCode("");
        }
    }

    class ItemModel {
        webMenuCode: string;
        webMenuName: string;
        defaultMenu: number;
        icon: string;
        constructor(webMenuCode: string, webMenuName: string, defaultMenu: number) {
            this.webMenuCode = webMenuCode;
            this.webMenuName = webMenuName;
            this.defaultMenu = defaultMenu;
            if (defaultMenu == 1) {
                this.icon = "";    
            } else {
                this.icon = '<i class="icon icon-dot"></i>';
            }
        }
    }

    class WebMenu {
        webMenuCode: KnockoutObservable<string>;
        webMenuName: KnockoutObservable<string>;
        isDefaultMenu: KnockoutObservable<boolean>;
        defaultMenu: KnockoutObservable<number>;
        menuBars: KnockoutObservableArray<any>;

        constructor(webMenuCode: string, webMenuName: string, defaultMenu: boolean, menuBars: any) {
            this.webMenuCode = ko.observable(webMenuCode);
            this.webMenuName = ko.observable(webMenuName);
            this.isDefaultMenu = ko.observable(defaultMenu);
            this.defaultMenu = ko.observable(1); 
            this.menuBars = ko.observableArray(menuBars);
        }
    }
}