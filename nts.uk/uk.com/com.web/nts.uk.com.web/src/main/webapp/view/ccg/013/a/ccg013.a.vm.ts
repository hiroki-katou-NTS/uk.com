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
        menuBars: KnockoutObservableArray<MenuBar>;
        titleMenus: KnockoutObservableArray<any>;
        treeMenus: KnockoutObservableArray<TreeMenu>;
        standardMenus: KnockoutObservableArray<any>;

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

            var menu1 = new nts.uk.ui.contextmenu.ContextMenu(".context-menu-bar", [
                new nts.uk.ui.contextmenu.ContextMenuItem("edit", "メニューバーの編集(U)", (ui) => {
                    let li = $(ui).parent('li');
                    self.openIdialog(li.attr('id'));
                }),
                new nts.uk.ui.contextmenu.ContextMenuItem("delete", "メニューバーの削除(D)", (ui) => {
                    var element = $(ui).parent();
                    element.remove();
                    self.removeMenuBar(element.attr("id"));
                })
            ]);

            var menu2 = new nts.uk.ui.contextmenu.ContextMenu(".context-menu-title", [
                new nts.uk.ui.contextmenu.ContextMenuItem("edit", "タイトルメニューの編集(U)", (ui) => { alert("Cut: "); }),
                new nts.uk.ui.contextmenu.ContextMenuItem("delete", "タイトルメニューの削除(D)", (ui) => {
                    let element = $(ui).parent();
                    element.remove();
                    self.removeTitleBar(element.attr("id"));
                })
            ]);

            var menu3 = new nts.uk.ui.contextmenu.ContextMenu(".context-menu-tree", [
                new nts.uk.ui.contextmenu.ContextMenuItem("delete", "メニューの削除(D)", (ui) => {
                    debugger;
                    let element = $(ui).parent();
                    element.remove();
                    self.removeTreeMenu(element.attr("id"));
                })
            ]);

            self.currentCode = ko.observable();
            self.currentCode.subscribe(function(newValue) {
                self.isCreated(false);
                self.menuBars([]);
                self.findWebMenu(newValue);
            });

            self.menuBars = ko.observableArray([]);
            self.titleMenus = ko.observableArray([]);
            self.treeMenus = ko.observableArray([]);
            self.standardMenus = ko.observableArray([]);
        }

        startPage(): JQueryPromise<void> {
            var self = this;
            var dfd = $.Deferred<void>();
            self.getWebMenu().done(function() {
                if (self.items().length > 0) {
                    self.currentCode(self.items()[0].webMenuCode);
                }
                else {
                    self.cleanForm();
                }
            });
            dfd.resolve();
            return dfd.promise();
        }


        getWebMenu(): any {
            var self = this;
            var dfd = $.Deferred();
            service.loadWebMenu().done(function(data) {
                var list001: Array<ItemModel> = [];
                _.forEach(data, function(item) {
                    list001.push(new ItemModel(item.webMenuCode, item.webMenuName, item.defaultMenu));
                });
                self.items(list001);

                service.findStandardMenuList().done(function(res: Array<service.StandardMenuDto>) {
                    self.standardMenus(res);
                });

                dfd.resolve(data);
            }).fail(function(res) {
            })
            return dfd.promise();
        }


        addWebMenu(): any {
            var self = this;
            self.convertWebMenu();
            var webMenu = ko.toJSON(self.currentWebMenu);
            service.addWebMenu(self.isCreated(), webMenu).done(function() {
                self.getWebMenu();
                $("#tabs").tabs("refresh");
                initTitleBar();
            });
        }

        convertWebMenu(): void {
            var self = this;
            if (self.currentWebMenu().isDefaultMenu()) {
                self.currentWebMenu().defaultMenu(0);
            } else {
                self.currentWebMenu().defaultMenu(1);
            }
            self.sortMenuBar();
            self.currentWebMenu().menuBars(self.menuBars());
        }

        sortMenuBar() {
            var self = this;
            var menuBarRootList = self.menuBars();
            var menuBarIdArray = $("#tabs .ui-tabs-nav").sortable("toArray");
            var titleBarIdArray = $(".title-menu").sortable("toArray");
            if (menuBarIdArray && menuBarIdArray.length > 0) {
                self.menuBars([]);
                _.forEach(menuBarIdArray, function(menuBarId: string) {
                    var menuBar: MenuBar = _.find(menuBarRootList, function(menu: MenuBar) {
                        return menuBarId == menu.menuBarId();
                    });
                    if (menuBar) {
                        menuBar.displayOrder(self.menuBars().length + 1);
                        self.sortTitleBar(menuBar, titleBarIdArray);
                        self.menuBars.push(menuBar);
                    }
                });
            }
        }

        /**
         * Sort title bar
         */
        sortTitleBar(menuBar: MenuBar, titleBarIdArray: Array<string>) {
            var self = this;
            var titleBars: Array<TitleMenu> = menuBar.titleMenu();
            if (!titleBars || titleBars.length <= 0) {
                return;
            }

            if (!titleBarIdArray || titleBarIdArray.length <= 0) {
                return;
            }

            menuBar.titleMenu([]);
            _.forEach(titleBarIdArray, function(titleBarId: string) {
                var titleBar: TitleMenu = _.find(titleBars, function(titleBarItem: TitleMenu) {
                    return titleBarId == titleBarItem.titleMenuId();
                });
                if (titleBar) {
                    titleBar.displayOrder(menuBar.titleMenu().length + 1);
                    menuBar.titleMenu.push(titleBar);
                }
            });
        }


        /**
         * Find a web menu by web menu code
         */
        findWebMenu(webMenuCode: string): any {
            var self = this;
            service.findWebMenu(webMenuCode).done(function(res: service.WebMenuDto) {
                var defaultMenu = true;
                if (res.defaultMenu == 1) {
                    defaultMenu = false;
                }
                self.currentWebMenu(new WebMenu(res.webMenuCode, res.webMenuName, defaultMenu, res.menuBars));

                if (res.menuBars && res.menuBars.length > 0) {
                    var menuBars: Array<any> = _.orderBy(res.menuBars, 'displayOrder', 'asc');
                    _.forEach(menuBars, function(menuBar: any) {
                        var titleBars = [];
                        var titleMenu = _.orderBy(menuBar.titleMenu, 'displayOrder', 'asc');
                        _.forEach(titleMenu, function(titleBarItem: any) {
                            let treeMenus = [];
                            _.forEach(titleBarItem.treeMenu, function(treeMenuItem: any) {
                                var standardMenu: service.StandardMenuDto = _.find(self.standardMenus(), function(standardMenuItem: service.StandardMenuDto) {
                                    return standardMenuItem.code == treeMenuItem.code && standardMenuItem.system == treeMenuItem.system && standardMenuItem.classification == treeMenuItem.classification;
                                });
                                var treeMenuName = "";
                                if (standardMenu) {
                                    treeMenuName = standardMenu.displayName;
                                }
                                var treeMenuId = nts.uk.util.randomId();
                                treeMenus.push(new TreeMenu(treeMenuId, titleBarItem.titleMenuId, treeMenuItem.code, treeMenuName, treeMenuItem.displayOrder, treeMenuItem.classification, treeMenuItem.system));
                            })
                            titleBars.push(new TitleMenu(menuBar.menuBarId, titleBarItem.titleMenuId, titleBarItem.titleMenuName, titleBarItem.backgroundColor, titleBarItem.imageFile, titleBarItem.textColor, titleBarItem.titleMenuAtr, titleBarItem.titleMenuCode, titleBarItem.displayOrder, treeMenus));
                        });
                        self.menuBars.push(new MenuBar(menuBar.menuBarId, menuBar.code, menuBar.menuBarName, menuBar.selectedAtr, menuBar.system, menuBar.menuCls, menuBar.backgroundColor, menuBar.textColor, menuBar.displayOrder, titleBars));
                    });
                    //$("#tabs").tabs("refresh");  
                    $("#tabs li#" + menuBars[0].menuBarId + " a").click();
                }

                initTitleBar();
            });
        }

        /**
         * Remove menu bar
         */
        removeMenuBar(menuBarId: string): void {
            var self = this;
            _.remove(self.menuBars(), function(item: MenuBar) {
                return item.menuBarId() == menuBarId;
            });
        }

        /**
         * Remove title bar
         */
        removeTitleBar(titleBarId: string): void {
            let self = this;
            let menuBars = self.menuBars();
            _.forEach(menuBars, function(item: MenuBar) {
                _.remove(item.titleMenu(), function(itemTitleMenu: TitleMenu) {
                    return itemTitleMenu.titleMenuId() == titleBarId;
                });
            });
        }


        removeTreeMenu(treeMenuId: string): void {
            let self = this;
            let menuBars = self.menuBars();
            _.forEach(menuBars, function(item: MenuBar) {
                _.forEach(item.titleMenu(), function(itemTitleMenu: TitleMenu) {
                    _.remove(itemTitleMenu.treeMenu(), function(itemTreemenu: TreeMenu) {
                        debugger;
                        return itemTreemenu.treeMenuId() == treeMenuId;
                    });
                });
            });
        }

        /**
         * Remove web menu
         */
        removeWebMenu(): void {
            let self = this;
            let webMenuCode = self.currentCode();
            service.deleteWebMenu(webMenuCode).done(function() {
                self.getWebMenu();
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

        openBdialog(): any {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/ccg/013/b/index.xhtml").onClosed(function() {
                var data = nts.uk.ui.windows.getShared("CCG013B_MenuBar");
                if (data) {
                    var id = nts.uk.util.randomId();
                    self.menuBars.push(new MenuBar(id, data.code, data.nameMenuBar, data.selectedRadioAtcClass, data.system, data.menuCls, data.backgroundColor, data.letterColor, self.menuBars().length + 1, []));
                    $("#tabs").tabs("refresh");
                    $("#tabs li#" + id + " a").click();
                }
            });
        }

        openCdialog(menuBar: MenuBar): any {
            var self = this;
            var currentMenuBar = menuBar;
            nts.uk.ui.windows.sub.modal("/view/ccg/013/c/index.xhtml").onClosed(function() {
                var data = nts.uk.ui.windows.getShared("CCG013C_TitleBar");
                if (data) {
                    var titleMenuId = nts.uk.util.randomId();
                    var displayOrder = currentMenuBar.titleMenu().length + 1;
                    currentMenuBar.titleMenu.push(new TitleMenu(currentMenuBar.menuBarId(), titleMenuId, data.nameTitleBar, data.backgroundColor, data.imageId, data.letterColor, data.selectedTitleAtr, data.titleMenuCode, displayOrder, []));

                    var menuBars: Array<MenuBar> = self.menuBars();
                    self.menuBars([]);
                    _.forEach(menuBars, function(item: MenuBar) {
                        if (item.menuBarId() == currentMenuBar.menuBarId()) {
                            item.titleMenu = currentMenuBar.titleMenu;
                        }
                        self.menuBars.push(item);
                    });
                    $("#tabs").tabs("refresh");
                    $("#tabs li#" + currentMenuBar.menuBarId() + " a").click();
                    initTitleBar();
                }
            });
        }

        openDdialog(titleMenu: TitleMenu): void {
            let self = this,
                titleBar = {
                    name: titleMenu.titleMenuName(),
                    backgroundColor: titleMenu.backgroundColor(),
                    textColor: titleMenu.textColor(),
                    treeMenus: titleMenu.treeMenu()
                };

            nts.uk.ui.windows.setShared("titleBar", titleBar);
            nts.uk.ui.windows.sub.modal("/view/ccg/013/d/index.xhtml").onClosed(function() {
                titleMenu.treeMenu([]);
                let data = nts.uk.ui.windows.getShared("CCG013D_MENUS");
                if (data && data.length > 0) {
                    _.forEach(data, x => {
                        var treeMenuId = nts.uk.util.randomId();
                        titleMenu.treeMenu.push(new TreeMenu(
                            treeMenuId,
                            titleMenu.titleMenuId(),
                            x.code,
                            x.name,
                            x.order,
                            x.menu_cls,
                            x.system));
                    });
                }
            });
        }

        optionEDialog(): void {
            var self = this;
            nts.uk.ui.windows.setShared("CCG013E_COPY", self.currentWebMenu());
            nts.uk.ui.windows.sub.modal("/view/ccg/013/e/index.xhtml").onClosed(function() {
                self.getWebMenu();
            });
        }

        optionFDialog(): void {
            var self = this;
            var dataTranfer = self.items();
            nts.uk.ui.windows.setShared("CCG013F_JOB_TITLE", dataTranfer);
            nts.uk.ui.windows.sub.modal("/view/ccg/013/f/index.xhtml").onClosed(function() {
            });
        }

        optionGDialog(): void {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/ccg/013/g/index.xhtml").onClosed(function() {
            });
        }

        openKdialog(): any {
            var self = this;
            nts.uk.ui.windows.sub.modal("/view/ccg/013/k/index.xhtml").onClosed(function() {
            });
        }

        openIdialog(id): any {
            let self = this,
                datas: Array<any> = ko.toJS(self.menuBars),
                menu = _.find(datas, x => x.menuBarId == id);
            nts.uk.ui.windows.setShared("CCG013I_MENU_BAR1", menu);
            nts.uk.ui.windows.sub.modal("/view/ccg/013/i/index.xhtml").onClosed(function() {
                let data = nts.uk.ui.windows.getShared("CCG013I_MENU_BAR");
                debugger;
                if (data) {
                    let menuBars: Array<MenuBar> = self.menuBars();
                    self.menuBars([]);
                    _.forEach(menuBars, function(item: MenuBar) {
                        if (item.menuBarId() == id) {
                            item.menuBarName(data.menuBarName);
                            item.backgroundColor(data.backgroundColor);
                            item.textColor(data.textColor);
                        }
                        self.menuBars.push(item);
                    });
                    $("#tabs").tabs("refresh");
                    $("#tabs li#" + id + " a").click();
                    initTitleBar();
                }
            });
        }
    }

    export class ItemModel {
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

    export class WebMenu {
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

    export class MenuBar {
        menuBarId: KnockoutObservable<string>;
        code: KnockoutObservable<string>;
        menuBarName: KnockoutObservable<string>;
        selectedAtr: KnockoutObservable<number>;
        system: KnockoutObservable<number>;
        menuCls: KnockoutObservable<number>;
        backgroundColor: KnockoutObservable<string>;
        textColor: KnockoutObservable<string>;
        displayOrder: KnockoutObservable<number>;
        targetContent: KnockoutObservable<string>;
        titleMenu: KnockoutObservableArray<TitleMenu>;

        constructor(menuBarId: string, code: string, menuBarName: string, selectedAtr: number, system: number, menuCls: number, backgroundColor: string, textColor: string, displayOrder: number, titleMenu: Array<TitleMenu>) {
            this.menuBarId = ko.observable(menuBarId);
            this.code = ko.observable(code);
            this.menuBarName = ko.observable(menuBarName);
            this.selectedAtr = ko.observable(selectedAtr);
            this.system = ko.observable(system);
            this.menuCls = ko.observable(menuCls);
            this.backgroundColor = ko.observable(backgroundColor);
            this.textColor = ko.observable(textColor);
            this.displayOrder = ko.observable(displayOrder);
            this.titleMenu = ko.observableArray(titleMenu);
            this.targetContent = ko.observable("#tab-content-" + menuBarId);
        }
    }

    export class TitleMenu {
        menuBarId: KnockoutObservable<string>;
        titleMenuId: KnockoutObservable<string>;
        titleMenuName: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;
        imageFile: KnockoutObservable<string>;
        textColor: KnockoutObservable<string>;
        titleMenuAtr: KnockoutObservable<number>;
        titleMenuCode: KnockoutObservable<string>;
        displayOrder: KnockoutObservable<number>;
        treeMenu: KnockoutObservableArray<TreeMenu>;

        constructor(menuBarId: string, titleMenuId: string, titleMenuName: string, backgroundColor: string, imageFile: string, textColor: string, titleMenuAtr: number, titleMenuCode: string, displayOrder: number, treeMenu: TreeMenu[]) {
            this.menuBarId = ko.observable(menuBarId);
            this.titleMenuId = ko.observable(titleMenuId);
            this.titleMenuName = ko.observable(titleMenuName);
            this.backgroundColor = ko.observable(backgroundColor);
            this.imageFile = ko.observable(imageFile);
            this.textColor = ko.observable(textColor);
            this.titleMenuAtr = ko.observable(titleMenuAtr);
            this.titleMenuCode = ko.observable(titleMenuCode);
            this.displayOrder = ko.observable(displayOrder);
            this.treeMenu = ko.observableArray(treeMenu);
        }
    }


    export class TreeMenu {
        treeMenuId: KnockoutObservable<string>;
        titleMenuId: KnockoutObservable<string>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        displayOrder: KnockoutObservable<number>;
        classification: KnockoutObservable<number>;
        system: KnockoutObservable<number>;
        constructor(treeMenuId: string, titleMenuId: string, code: string, name: string, displayOrder: number, classification: number, system: number) {
            this.treeMenuId = ko.observable(treeMenuId);
            this.titleMenuId = ko.observable(titleMenuId);
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.displayOrder = ko.observable(displayOrder);
            this.classification = ko.observable(classification);
            this.system = ko.observable(system);
        }
    }
}