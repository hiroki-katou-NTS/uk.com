module ccg013.a.viewmodel {
    import randomId = nts.uk.util.randomId;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import menu = nts.uk.ui.contextmenu.ContextMenuItem;
    import contextMenu = nts.uk.ui.contextmenu.ContextMenu;

    export class ScreenModel {
        paymentDateProcessingList: KnockoutObservableArray<any>;
        selectedPaymentDate: KnockoutObservable<any>;
        items: KnockoutObservableArray<ItemModel>;
        columns2: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        itemMenu: KnockoutObservableArray<any>;
        contextmenu1: KnockoutObservable<string>;
        currentWebMenu: KnockoutObservable<WebMenu>;
        isCreated: KnockoutObservable<boolean>;
        isDefaultMenu: KnockoutObservable<boolean>;
        widthTab: KnockoutObservable<string> = ko.observable('800px');
        checkDisabled: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            self.isCreated = ko.observable(false);
            self.isDefaultMenu = ko.observable(false);
            self.checkDisabled = ko.observable(false);

            self.currentWebMenu = ko.observable(new WebMenu({
                webMenuCode: "",
                webMenuName: "",
                defaultMenu: 0,
                menuBars: []
            }));

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
                { headerText: nts.uk.resource.getText("CCG013_8"), key: 'icon', width: 50 },
                { headerText: nts.uk.resource.getText("CCG013_9"), key: 'webMenuCode', width: 50 },
                { headerText: nts.uk.resource.getText("CCG013_10"), key: 'webMenuName', width: 50 }
            ]);

            new contextMenu(".context-menu-bar", [
                new menu("edit", "メニューバーの編集(U)", (ui) => {
                    let li = $(ui).parent('li');
                    self.openIdialog(li.attr('id'));
                }),
                new menu("delete", "メニューバーの削除(D)", (ui) => {
                    var element = $(ui).parent();

                    self.removeMenuBar(element.attr("id"));
                })
            ]);

            new contextMenu(".context-menu-title", [
                new menu("edit", "タイトルメニューの編集(U)", (ui) => {
                    let div = $(ui).parent('div');
                    self.openJdialog(div.attr('id'));
                }),
                new menu("delete", "タイトルメニューの削除(D)", (ui) => {
                    let element = $(ui).parent(),
                        id = element.attr('id');
                    self.removeTitleBar(id);
                })
            ]);

            new contextMenu(".context-menu-tree", [
                new menu("delete", "メニューの削除(D)", (ui) => {

                    let id = $(ui).attr('id');

                    self.removeTreeMenu(id);
                })
            ]);

            self.currentCode = ko.observable();
            self.currentCode.subscribe(function(newValue) {
                service.findWebMenu(newValue).done(function(res: service.WebMenuDto) {
                    let webmenu = self.currentWebMenu();
                    if (!newValue) {
                        self.isCreated(true);
                        self.checkDisabled(true);
                    } else {
                        self.isCreated(false);
                        self.checkDisabled(false);
                    }
                    webmenu.webMenuCode(res.webMenuCode);
                    webmenu.webMenuName(res.webMenuName);
                    self.isDefaultMenu(!!res.defaultMenu);

                    webmenu.menuBars.removeAll();

                    service.findStandardMenuList().done((menuNames: Array<any>) => {
                        _.each(_.orderBy(res.menuBars, 'displayOrder', 'asc'), x => {

                            // push list name of tree menu to IMenuBar
                            x.menuNames = menuNames;

                            webmenu.menuBars.push(new MenuBar(x));
                        });
                        bindSortable();
                    });
                });
            });

            self.currentWebMenu().menuBars([]);
        }

        startPage(): JQueryPromise<void> {
            var self = this,
                dfd = $.Deferred<void>();

            self.getWebMenu().done(function() {
                if (self.items().length > 0) {
                    self.currentCode(self.items()[0].webMenuCode);
                }
                else {
                    self.cleanForm();
                }

                dfd.resolve();
            });

            return dfd.promise();
        }


        getWebMenu(): any {
            let self = this,
                dfd = $.Deferred();

            service.loadWebMenu().done(function(data) {
                self.items.removeAll();

                _.forEach(data, function(item) {
                    self.items.push(new ItemModel(item.webMenuCode, item.webMenuName, item.defaultMenu));
                });
                dfd.resolve(data);
            }).fail((res) => { });

            return dfd.promise();
        }



        addWebMenu(): any {
            nts.uk.ui.block.invisible();
            var self = this,
                webMenu = self.currentWebMenu(),
                menuBars = webMenu.menuBars(),
                activeid = $('#tabs li[aria-expanded=true]').attr('id');

            if (self.isDefaultMenu()) {
                webMenu.defaultMenu(1);
            } else {
                webMenu.defaultMenu(0);
            }

            $('#tabs li.context-menu-bar').each((bi, be) => {
                let bid = be.attributes['id'].value,
                    menubar = _.find(menuBars, (x: MenuBar) => x.menuBarId() == bid);
                if (menubar) {
                    menubar.displayOrder(bi + 1);

                    $('#tab-content-' + bid + ' .title-menu-column.ui-sortable-handle').each((ti, te) => {
                        let tid = te.attributes['id'].value,
                            titlemenu = _.find(menubar.titleMenu(), x => x.titleMenuId() == tid);
                        if (titlemenu) {
                            titlemenu.displayOrder(ti + 1);
                            //context-menu-tree ui-sortable-handle
                            $('#' + tid + ' li.context-menu-tree.ui-sortable-handle').each((mi, me) => {
                                let mid = me.attributes['id'].value,
                                    treemenu = _.find(titlemenu.treeMenu(), x => x.treeMenuId() == mid);

                                if (treemenu) {
                                    treemenu.displayOrder(mi + 1);
                                }
                            });
                        }
                    });
                }
            });
            service.addWebMenu(self.isCreated(), ko.toJS(webMenu)).done(function() {
                nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_15'));
                self.getWebMenu().done(() => {
                    bindSortable();
                    self.currentCode(webMenu.webMenuCode());
                    $("#tabs li#" + activeid + ' a').trigger('click');
                });
            }).fail(function(error) {
                self.isCreated(false);
                self.checkDisabled(true);
                nts.uk.ui.dialog.alertError(error.message);
            }).always(function() {
                nts.uk.ui.block.clear();
            });

        }



        /**
         * Remove menu bar
         */
        removeMenuBar(menuBarId: string): void {
            let self = this,
                menu = self.currentWebMenu(),
                menuBars = menu.menuBars();
            self.currentCode();
            _.remove(menuBars, (item: MenuBar) => item.menuBarId() == menuBarId);
            menu.menuBars.valueHasMutated();
        }

        /**
         * Remove title bar
         */
        removeTitleBar(titleBarId: string): void {
            let self = this,
                menu = self.currentWebMenu(),
                menuBars = menu.menuBars();

            _.forEach(menuBars, (item: MenuBar) => {
                _.remove(item.titleMenu(), (x: TitleMenu) => x.titleMenuId() == titleBarId);
                item.titleMenu.valueHasMutated();
            });
        }


        removeTreeMenu(treeMenuId: string): void {
            let self = this,
                menu = self.currentWebMenu(),
                menuBars = menu.menuBars();

            _.forEach(menuBars, function(item: MenuBar) {
                _.forEach(item.titleMenu(), function(v: TitleMenu) {
                    _.remove(v.treeMenu(), (x: TreeMenu) => x.treeMenuId() == treeMenuId);
                    v.treeMenu.valueHasMutated();
                });
            });
        }


        /**
         * Remove web menu
         */
        removeWebMenu(): void {
            nts.uk.ui.block.invisible();
            let self = this,
                webMenuCode = self.currentCode();
            let index = 0;
            service.deleteWebMenu(webMenuCode).done(function() {
                self.getWebMenu().done(() => {
                    if (self.items().length > 0) {
                        index = self.items().length - 1;
                        if (index < 0) {
                            index = 0;
                        }
                    }
                    self.currentCode(self.items()[index].webMenuCode);
                });
            }).fail(function(error) {
                self.isCreated(false);
                nts.uk.ui.dialog.alertError(error.message);
            }).always(function() {
                nts.uk.ui.block.clear();
            });
        }


        /**
         * Clean all control in form
         */
        cleanForm(): void {
            var self = this;
            self.checkDisabled(true);
            self.isCreated(true);

            self.currentWebMenu(new WebMenu({
                webMenuCode: "",
                webMenuName: "",
                defaultMenu: 0,
                menuBars: []
            }));
            self.currentCode("");
        }


        openBdialog(): any {
            nts.uk.ui.block.invisible();
            var self = this,
                webmenu = self.currentWebMenu();
            modal("/view/ccg/013/b/index.xhtml").onClosed(function() {
                let id = randomId(),
                    data = getShared("CCG013B_MenuBar");

                if (data) {
                    webmenu.menuBars.push(new MenuBar({
                        menuBarId: id,
                        code: data.code,
                        menuBarName: data.nameMenuBar,
                        selectedAtr: data.selectedRadioAtcClass,
                        system: data.system,
                        menuCls: data.menuCls,
                        backgroundColor: data.backgroundColor,
                        textColor: data.letterColor,
                        displayOrder: self.currentWebMenu().menuBars().length + 1,
                        titleMenu: []
                    }));
                    bindSortable();
                    $("#tabs li#" + id + " a").click();
                }

                nts.uk.ui.block.clear();
            });
        }

        openCdialog(menuBar: MenuBar): any {
            var self = this,
                webmenu = self.currentWebMenu();
            modal("/view/ccg/013/c/index.xhtml").onClosed(function() {
                var data = getShared("CCG013C_TitleBar");
                if (data) {
                    let id = randomId(),
                        displayOrder = menuBar.titleMenu().length + 1;

                    menuBar.titleMenu.push(new TitleMenu({
                        menuBarId: menuBar.menuBarId(),
                        titleMenuId: id,
                        titleMenuName: data.nameTitleBar,
                        backgroundColor: data.backgroundColor,
                        imageFile: data.imageId,
                        textColor: data.letterColor,
                        titleMenuAtr: data.selectedTitleAtr,
                        titleMenuCode: data.titleMenuCode,
                        displayOrder: displayOrder,
                        treeMenu: [],
                        imageName: data.imageName,
                        imageSize: data.imageSize,
                    }));

                    bindSortable();
                    $("#tabs li#" + menuBar.menuBarId() + " a").click();
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

            setShared("titleBar", titleBar);
            modal("/view/ccg/013/d/index.xhtml").onClosed(function() {
                let data = getShared("CCG013D_MENUS");

                titleMenu.treeMenu.removeAll();
                if (data && data.length > 0) {
                    _.forEach(data, x => {
                        var treeMenuId = randomId();
                        titleMenu.treeMenu.push(new TreeMenu({
                            titleMenuId: titleMenu.titleMenuId(),
                            code: x.code,
                            name: x.name,
                            displayOrder: x.order,
                            classification: x.menu_cls,
                            system: x.system
                        }));
                    });
                }
            });
        }


        optionEDialog(): void {
            var self = this,
                data = self.currentWebMenu();

            setShared("CCG013E_COPY", data);
            modal("/view/ccg/013/e/index.xhtml").onClosed(function() {
                self.getWebMenu();
            });
        }

        optionFDialog(): void {
            let self = this,
                dataTranfer = self.items();

            setShared("CCG013F_JOB_TITLE", dataTranfer);
            modal("/view/ccg/013/f/index.xhtml").onClosed(function() { });
        }

        optionGDialog(): void {
            let self = this,
                dataTranfer = self.items();

            setShared("CCG013G_WEB_MENU", dataTranfer);
            modal("/view/ccg/013/g/index.xhtml").onClosed(function() { });
        }

        openKdialog(): any {
            var self = this;
            modal("/view/ccg/013/k/index.xhtml").onClosed(function() { });
        }

        openIdialog(id): any {
            let self = this,
                datas: Array<any> = ko.toJS(self.currentWebMenu().menuBars),
                menu = _.find(datas, x => x.menuBarId == id);
            setShared("CCG013I_MENU_BAR1", menu);
            modal("/view/ccg/013/i/index.xhtml").onClosed(function() {
                let data = getShared("CCG013I_MENU_BAR");
                if (data) {
                    let menuBars: Array<MenuBar> = self.currentWebMenu().menuBars();
                    //self.currentWebMenu().menuBars([]);
                    _.forEach(menuBars, function(item: MenuBar) {
                        if (item.menuBarId() == id) {
                            item.menuBarName(data.menuBarName);
                            item.backgroundColor(data.backgroundColor);
                            item.textColor(data.textColor);
                        }
                        // self.currentWebMenu().menuBars.push(item);
                    });

                    bindSortable();
                    $("#tabs li#" + id + " a").click();
                }
            });
        }

        openJdialog(id): any {
            let activeid = $('#tabs li[aria-expanded=true]').attr('id');
            let self = this,
                datas: Array<any> = ko.toJS(self.currentWebMenu().menuBars),
                menu = _.find(datas, x => x.menuBarId == activeid),
                dataTitleMenu: Array<any> = menu.titleMenu,
                titleMenu = _.find(dataTitleMenu, y => y.titleMenuId == id);
            setShared("CCG013A_ToChild_TitleBar", titleMenu);
            modal("/view/ccg/013/j/index.xhtml").onClosed(function() {
                let data = getShared("CCG013J_ToMain_TitleBar");
                if (data) {
                    let menuBars: Array<MenuBar> = self.currentWebMenu().menuBars(),
                        menuBar = _.find(menuBars, x => x.menuBarId() == activeid);

                    _.forEach(menuBar.titleMenu(), function(item: TitleMenu) {
                        if (item.titleMenuId() == id) {
                            item.titleMenuName(data.nameTitleBar);
                            item.backgroundColor(data.backgroundColor);
                            item.imageFile(data.imageId);
                            item.textColor(data.letterColor);
                            item.imageName(data.imageName);
                            item.imageSize(data.imageSize);
                        }
                    });

                    bindSortable();
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
            if (defaultMenu == 0) {
                this.icon = "";
            } else {
                this.icon = '<i class="icon icon-dot"></i>';
            }
        }
    }

    export interface IWebMenu {
        webMenuCode: string;
        webMenuName: string;
        defaultMenu: number;
        menuBars: Array<IMenuBar>;
    }

    export class WebMenu {
        webMenuCode: KnockoutObservable<string>;
        webMenuName: KnockoutObservable<string>;
        defaultMenu: KnockoutObservable<number>;
        menuBars: KnockoutObservableArray<MenuBar>;

        constructor(param: IWebMenu) {
            this.webMenuCode = ko.observable(param.webMenuCode);
            this.webMenuName = ko.observable(param.webMenuName);
            this.defaultMenu = ko.observable(param.defaultMenu);
            this.menuBars = ko.observableArray(param.menuBars.map(x => new MenuBar(x)));

            this.menuBars.subscribe(x => {
                if (__viewContext['viewModel']) {
                    let vm = __viewContext['viewModel'],
                        pw = x.length * 160 + 360,
                        tm = _.maxBy(x, m => m.titleMenu().length),
                        cw = tm && tm.titleMenu().length * 160 + 360;
                    if (!x.length) {
                        vm.widthTab('800px');
                    } else {
                        vm.widthTab((pw > cw ? pw : cw) + 'px');
                    }
                }
            });
        }
    }

    export interface IMenuBar {
        menuBarId?: string;
        code?: string;
        menuBarName?: string;
        selectedAtr?: number;
        system?: number;
        menuCls?: number;
        backgroundColor?: string;
        textColor?: string;
        displayOrder?: number;
        targetContent?: string;
        titleMenu?: Array<ITitleMenu>;
        menuNames?: Array<any>;
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

        constructor(param: IMenuBar) {
            this.menuBarId = ko.observable(param.menuBarId);
            this.code = ko.observable(param.code);
            this.menuBarName = ko.observable(param.menuBarName || 'なし');
            this.selectedAtr = ko.observable(param.selectedAtr);
            this.system = ko.observable(param.system);
            this.menuCls = ko.observable(param.menuCls);
            this.backgroundColor = ko.observable(param.backgroundColor);
            this.textColor = ko.observable(param.textColor);
            this.displayOrder = ko.observable(param.displayOrder);
            this.titleMenu = ko.observableArray(_.orderBy(param.titleMenu, 'displayOrder', 'asc').map(x => {
                x.menuNames = param.menuNames || [];
                return new TitleMenu(x);
            }));
            this.targetContent = ko.observable("#tab-content-" + param.menuBarId);

            this.titleMenu.subscribe(x => {
                let vm = __viewContext['viewModel'];
                if (vm) {
                    let cm = vm.currentWebMenu();
                    cm.menuBars.valueHasMutated();
                }
            });
        }
    }

    export interface ITitleMenu {
        menuBarId: string;
        titleMenuId: string;
        titleMenuName?: string;
        backgroundColor: string;
        imageFile: string;
        textColor: string;
        titleMenuAtr: number;
        titleMenuCode: string;
        displayOrder: number;
        treeMenu: Array<ITreeMenu>;
        menuNames?: Array<any>;
        imageName?: string;
        imageSize?: string;
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
        imageName: KnockoutObservable<string>;
        imageSize: KnockoutObservable<string>;

        constructor(param: ITitleMenu) {
            this.menuBarId = ko.observable(param.menuBarId);
            this.titleMenuId = ko.observable(param.titleMenuId);
            this.titleMenuName = ko.observable(param.titleMenuName || 'なし');
            this.backgroundColor = ko.observable(param.backgroundColor);
            this.imageFile = ko.observable(param.imageFile);
            this.textColor = ko.observable(param.textColor);
            this.titleMenuAtr = ko.observable(param.titleMenuAtr);
            this.titleMenuCode = ko.observable(param.titleMenuCode);
            this.displayOrder = ko.observable(param.displayOrder);
            this.treeMenu = ko.observableArray(_.orderBy(param.treeMenu, 'displayOrder', 'asc').map(x => {
                let name = _.find(param.menuNames, c => c.code == x.code && c.system == x.system && c.classification == x.classification);
                x.name = name && name.displayName;
                return new TreeMenu(x);
            }));
            this.imageName = ko.observable(param.imageName);
            this.imageSize = ko.observable(param.imageSize);
        }
    }

    export interface ITreeMenu {
        titleMenuId: string;
        code: string;
        name?: string;
        displayOrder: number;
        classification: number;
        system: number;
    }


    export class TreeMenu {
        treeMenuId: KnockoutObservable<string>;
        titleMenuId: KnockoutObservable<string>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        displayOrder: KnockoutObservable<number>;
        classification: KnockoutObservable<number>;
        system: KnockoutObservable<number>;
        constructor(param: ITreeMenu) {
            this.treeMenuId = ko.observable(randomId());
            this.titleMenuId = ko.observable(param.titleMenuId);
            this.code = ko.observable(param.code);
            this.name = ko.observable(param.name || 'なし');
            this.displayOrder = ko.observable(param.displayOrder);
            this.classification = ko.observable(param.classification);
            this.system = ko.observable(param.system);
        }
    }
}