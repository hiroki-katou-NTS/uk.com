module ccg013.a.viewmodel {
    import randomId = nts.uk.util.randomId;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import menu = nts.uk.ui.contextmenu.ContextMenuItem;
    import contextMenu = nts.uk.ui.contextmenu.ContextMenu;

    const menuBarHTML: string = '<li class="context-menu-bar" data-bind="attr: {\'id\': menuBarId}"><a data-bind="attr: {href: targetContent}, style: {color: textColor, \'background-color\': backgroundColor}, text: menuBarName"></a></li>';
    const treeMenuHTML: string = '<li class="context-menu-tree" data-bind="attr:{id: treeMenuId},text: name"></li>';

    export class ScreenModel {
        // WebMenu
        listWebMenu: KnockoutObservableArray<WebMenuModel>;
        webMenuColumns: KnockoutObservableArray<any>;
        currentWebMenuCode: KnockoutObservable<any>;
        currentWebMenu: KnockoutObservable<WebMenu>;
        index: KnockoutObservable<number>;

        // MenuBar
        currentMenuBar: KnockoutObservable<MenuBar>;
        
        // UI
        isCreated: KnockoutObservable<boolean>;
        isDefaultMenu: KnockoutObservable<boolean>;
        widthTab: KnockoutObservable<string> = ko.observable('800px');
        checkDisabled: KnockoutObservable<boolean>;

        constructor() {
            var self = this;
            // WebMenu
            self.listWebMenu = ko.observableArray([]);
            self.webMenuColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_8"), key: 'icon', width: 50 },
                { headerText: nts.uk.resource.getText("CCG013_9"), key: 'webMenuCode', width: 50 },
                { headerText: nts.uk.resource.getText("CCG013_10"), key: 'webMenuName', width: 50 }
            ]);
            self.currentWebMenu = ko.observable(new WebMenu({
                webMenuCode: "",
                webMenuName: "",
                defaultMenu: 0,
                menuBars: []
            }));
            self.currentWebMenuCode = ko.observable();
            self.currentWebMenuCode.subscribe(function(newValue) {
                var index = _.findIndex(self.listWebMenu(), function(item: WebMenuModel) {
                    return item.webMenuCode == newValue;
                });
                self.index(index);
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
                    webmenu.defaultMenu(res.defaultMenu);
                    self.isDefaultMenu(!!res.defaultMenu);
                    webmenu.menuBars.removeAll();
                    service.findStandardMenuList().done((menuNames: Array<any>) => {
                        _.each(_.orderBy(res.menuBars, 'displayOrder', 'asc'), x => {
                            x.menuNames = menuNames;
                            webmenu.menuBars.push(new MenuBar(x));
                        });
                        self.initDisplay();
                        if (webmenu.menuBars().length > 0)
                            self.currentMenuBar(webmenu.menuBars()[0]);
                    });
                });
            });
            self.currentWebMenu().menuBars([]);
            self.index = ko.observable(0);

            // MenuBar
            self.currentMenuBar = ko.observable(null);
            
            // UI
            self.isCreated = ko.observable(false);
            self.isDefaultMenu = ko.observable(false);
            self.checkDisabled = ko.observable(false);
        }

        /** StartPage */
        startPage(): JQueryPromise<void> {
            var self = this;
            var dfd = $.Deferred<void>();

            self.getWebMenu().done(function() {
                if (self.listWebMenu().length > 0) {
                    self.currentWebMenuCode(self.listWebMenu()[0].webMenuCode);
                }
                else {
                    self.cleanForm();
                }
                dfd.resolve();
            });
            return dfd.promise();
        }

        /** Registry Webmenu */
        addWebMenu(): any {
            nts.uk.ui.block.invisible();
            var self = this;
            var webMenu = self.currentWebMenu();
            var menuBars = webMenu.menuBars();
            var activeid = $('#tabs li[aria-expanded=true]').attr('id');

            if (self.isDefaultMenu()) {
                webMenu.defaultMenu(1);
            } else {
                webMenu.defaultMenu(0);
            }

            $('#tabs li.context-menu-bar').each((bi, be) => {
                let bid = be.attributes['id'].value,
                    menubar = _.find(menuBars, (x: MenuBar) => x.menuBarId() == bid);
                if (menubar) {
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
                    self.currentWebMenuCode(webMenu.webMenuCode());
                    $("#tabs li#" + activeid + ' a').trigger('click');
                });
            }).fail(function(error) {
                self.isCreated(false);
                self.isDefaultMenu(true);
                nts.uk.ui.dialog.alertError(error.message);
            }).always(function() {
                nts.uk.ui.block.clear();
            });
        }

        /** Remove web menu */
        removeWebMenu(): void {
            nts.uk.ui.block.invisible();
            let self = this,
                webMenuCode = self.currentWebMenuCode();
            if (self.currentWebMenu().defaultMenu()) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_72" });
            } else {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.deleteWebMenu(webMenuCode).done(function() {
                        nts.uk.ui.dialog.info(nts.uk.resource.getMessage('Msg_16'));
                        self.getWebMenu().done(() => {
                            if (self.index() == self.listWebMenu().length) {
                                self.currentWebMenuCode(self.listWebMenu()[self.index() - 1].webMenuCode);
                            } else {
                                self.currentWebMenuCode(self.listWebMenu()[self.index()].webMenuCode);
                            }
                        });
                    }).fail(function(error) {
                        self.isCreated(false);
                        self.isDefaultMenu(true);
                        nts.uk.ui.dialog.alertError(error.message);
                    });
                });
            }
            nts.uk.ui.block.clear();
        }

        /** Remove menu bar */
        private removeMenuBar(menuBarId: string): void {
            let self = this;
            self.currentWebMenu().menuBars.remove((item) => {
                return item.menuBarId() == menuBarId;
            });
            self.calculateMenuBarOrder();
        }

        /** Remove title bar */
        private removeTitleBar(titleMenuId: string): void {
            var self = this;

            var titleMenu: Array<TitleMenu> = [];
            _.forEach(self.currentMenuBar().titleMenu(), (item: TitleMenu) => {
                titleMenu.push(item);
            });
            _.remove(titleMenu, (item: TitleMenu) => {
                return item.titleMenuId() == titleMenuId;
            });
            self.currentMenuBar().titleMenu.removeAll();
            self.currentMenuBar().titleMenu(titleMenu);
            self.calculateTitleMenuOrder();
            self.setupMenuBar();
        }

        /** Remove TreeMenu */
        private removeTreeMenu(ui: any): void {
            var self = this;
            var treeMenuId = ui.attr("id");
            var titleMenuId = ui.closest(".title-menu-column").attr("id");
            _.forEach(self.currentMenuBar().titleMenu(), function(titleMenu: TitleMenu) {
                titleMenu.treeMenu.remove((x: TreeMenu) => {
                    return x.treeMenuId() == treeMenuId;
                }); 
                titleMenu.treeMenu.valueHasMutated();
            });
            self.calculateTreeMenuOrder(titleMenuId);
        }

        /** Clean all control in form */
        private cleanForm(): void {
            var self = this;
            self.checkDisabled(true);
            self.isCreated(true);
            self.currentWebMenu(new WebMenu({
                webMenuCode: "",
                webMenuName: "",
                defaultMenu: 0,
                menuBars: []
            }));
            self.currentWebMenuCode("");
        }

        /** Get Webmenu */
        private getWebMenu(): any {
            var self = this;
            var dfd = $.Deferred();
            service.loadWebMenu().done(function(data) {
                self.listWebMenu.removeAll();
                _.forEach(data, function(item) {
                    self.listWebMenu.push(new WebMenuModel(item.webMenuCode, item.webMenuName, item.defaultMenu));
                });
                dfd.resolve(data);
            }).fail((res) => { });
            return dfd.promise();
        }

        /** 
         *  Init Display
         *  Call when start load done 
         */
        private initDisplay(): void {
            var self = this;
            self.setupMenuBar();
            self.setupTitleMenu();
            self.setupTreeMenu();
            self.setupContextMenu();
        }

        /** Setup MenuBar */
        private setupMenuBar(): void {
            var self = this;
            var active = (nts.uk.util.isNullOrUndefined(self.currentMenuBar())) ? 0 : self.currentMenuBar().displayOrder() - 1;
            $("#menubar-tabs.ui-tabs").tabs("destroy");
            $("#menubar-tabs").tabs({
                active: active,
                create: function(event: Event, ui: any) {
                    $("#menubar-tabs").find('.ui-tabs-panel').addClass('disappear');
                    ui.panel.removeClass('disappear');
                },
                activate: function(evt: Event, ui: any) {
                    $("#menubar-tabs").find('.ui-tabs-panel').addClass('disappear');
                    ui.newPanel.removeClass('disappear');
                    self.currentMenuBar(_.find(self.currentWebMenu().menuBars(), (item) => {
                        return item.menuBarId() == ui.newTab.attr("id");
                    }));
                }
            });
            $(".menubar-navigation.ui-sortable").sortable("destroy");
            $(".menubar-navigation").sortable({
                opacity: 0.8,
                distance: 25,
                axis: "x",
                revert: true,
                tolerance: "pointer",
                placeholder: "menubar-navigation-placeholder",
                stop: function(event, ui) {
                    self.rebindMenuBar();
                }
            });
        }

        /** Setup TitleMenu */
        private setupTitleMenu(): void {
            var self = this;
            $(".title-menu.ui-sortable").sortable("destroy");
            $(".title-menu").sortable({
                opacity: 0.8,
                distance: 25,
                axis: "x",
                revert: true,
                items: ".title-menu-column",
                handle: ".title-menu-name",
                tolerance: "pointer",
                placeholder: "menubar-navigation-placeholder",
                stop: function(event, ui) {
                    self.calculateTitleMenuOrder();
                }
            });
        }

        /** Setup TreeMenu */
        private setupTreeMenu(): void {
            var self = this;
            $(".tree-menu.ui-sortable").sortable("destroy");
            $(".tree-menu").sortable({
                opacity: 0.8,
                distance: 25,
                axis: "y",
                revert: true,
                items: ".context-menu-tree",
                tolerance: "pointer",
                placeholder: "menubar-navigation-placeholder",
                stop: function(event, ui) {
                    self.rebindTreeMenu(ui.item.closest(".title-menu-column"));
                }
            });
        }

        /** Setup ContextMenu */
        private setupContextMenu(): void {
            var self = this;
            new contextMenu(".context-menu-bar", [
                new menu("edit", "メニューバーの編集(U)", (ui) => {
                    let li = $(ui).parent('li');
                    self.openIdialog(li.attr('id'));
                }),
                new menu("delete", "メニューバーの削除(D)", (ui) => {
                    let element = $(ui).parent();
                    self.removeMenuBar(element.attr("id"));
                })
            ]);
            new contextMenu(".context-menu-title", [
                new menu("edit", "タイトルメニューの編集(U)", (ui) => {
                    let div = $(ui).parent('div');
                    self.openJdialog(div.attr('id'));
                }),
                new menu("delete", "タイトルメニューの削除(D)", (ui) => {
                    let element = $(ui).parent();
                    let id = element.attr('id');
                    self.removeTitleBar(id);
                })
            ]);
            new contextMenu(".context-menu-tree", [
                new menu("delete", "メニューの削除(D)", (ui) => {
                    self.removeTreeMenu($(ui));
                })
            ]);
        }

        /** Rebind Knockout Menubar */
        private rebindMenuBar(): void {
            var self = this;
            ko.cleanNode($(".menubar-navigation")[0]);
            self.calculateMenuBarOrder();
            $(".menubar-navigation").html(menuBarHTML);
            ko.applyBindingsToNode($(".menubar-navigation")[0], {
                foreach: self.currentWebMenu().menuBars
            });
            self.setupMenuBar();
        }

        /** Calculate MenuBar Index */
        private calculateMenuBarOrder(): void {
            var self = this;
            _.forEach(self.currentWebMenu().menuBars(), (item: MenuBar) => {
                item.displayOrder($("#" + item.menuBarId(), ".menubar-navigation").index() + 1);
            });
            self.currentWebMenu().menuBars.sort((left: MenuBar, right: MenuBar) => {
                return left.displayOrder() == right.displayOrder() ? 0 : (left.displayOrder() < right.displayOrder() ? -1 : 1);
            });
        }
        
        /** Rebind Knockout TreeMenu */
        private rebindTreeMenu(ui: any): void {
            var self = this;
            var titleMenuId = ui.attr("id");
            var titleMenu = _.find(self.currentMenuBar().titleMenu(), (item: TitleMenu) => {
                return item.titleMenuId() == titleMenuId;
            });
            ko.cleanNode($("#" + titleMenuId + " .tree-menu")[0]);
            self.calculateTreeMenuOrder(ui.attr("id"));
            $("#" + titleMenuId + " .tree-menu").html(treeMenuHTML);
            ko.applyBindingsToNode($("#" + titleMenuId + " .tree-menu")[0], {
                foreach: titleMenu.treeMenu
            });
            self.setupTreeMenu();
        }

        /** Calculate TreeMenu Index */
        private calculateTreeMenuOrder(titleMenuId: string): void {
            var self = this;
            var titleMenu = _.find(self.currentMenuBar().titleMenu(), (item: TitleMenu) => {
                return item.titleMenuId() == titleMenuId;
            });
            _.forEach(titleMenu.treeMenu(), (item: TreeMenu) => {
                item.displayOrder($("#" + item.treeMenuId(), "#" + titleMenuId + " .tree-menu").index() + 1);
            });
            titleMenu.treeMenu().sort((left: TreeMenu, right: TreeMenu) => {
                return left.displayOrder() == right.displayOrder() ? 0 : (left.displayOrder() < right.displayOrder() ? -1 : 1);
            });
        }

        /** Calculate MenuBar Index */
        private calculateTitleMenuOrder(): void {
            var self = this;
            _.forEach(self.currentMenuBar().titleMenu(), (item: TitleMenu) => {
                item.displayOrder($("#" + item.titleMenuId(), ".title-menu").index() + 1);
            });
            self.currentMenuBar().titleMenu.sort((left: TitleMenu, right: TitleMenu) => {
                return left.displayOrder() == right.displayOrder() ? 0 : (left.displayOrder() < right.displayOrder() ? -1 : 1);
            });
        }

        /** Add MenuBar Dialog */
        openBdialog(): any {
            var self = this;
            var webmenu = self.currentWebMenu();
            modal("/view/ccg/013/b/index.xhtml").onClosed(function() {
                let id = randomId();
                let data = getShared("CCG013B_MenuBar");
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
                    self.setupMenuBar();
                    $("#tabs li#" + id + " a").click();
                }
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
                    $("#tabs li#" + menuBar.menuBarId() + " a").click();
                }
            });
        }

        openDdialog(titleMenu: TitleMenu): void {
            let self = this;
            let titleBar = {
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
            var self = this;
            var data = self.currentWebMenu();
            setShared("CCG013E_COPY", data);
            modal("/view/ccg/013/e/index.xhtml").onClosed(function() {
                self.getWebMenu();
            });
        }

        optionFDialog(): void {
            var self = this;
            var dataTranfer = self.listWebMenu();

            setShared("CCG013F_JOB_TITLE", dataTranfer);
            modal("/view/ccg/013/f/index.xhtml");
        }

        optionGDialog(): void {
            var self = this;
            var dataTranfer = self.listWebMenu();
            setShared("CCG013G_WEB_MENU", dataTranfer);
            modal("/view/ccg/013/g/index.xhtml");
        }

        openKdialog(): any {
            var self = this;
            modal("/view/ccg/013/k/index.xhtml");
        }

        openIdialog(id): any {
            var self = this;
            var datas: Array<any> = ko.toJS(self.currentWebMenu().menuBars);
            var menu = _.find(datas, x => x.menuBarId == id);
            setShared("CCG013I_MENU_BAR1", menu);
            modal("/view/ccg/013/i/index.xhtml").onClosed(function() {
                let data = getShared("CCG013I_MENU_BAR");
                if (data) {
                    let menuBars: Array<MenuBar> = self.currentWebMenu().menuBars();
                    _.forEach(menuBars, function(item: MenuBar) {
                        if (item.menuBarId() == id) {
                            item.menuBarName(data.menuBarName);
                            item.backgroundColor(data.backgroundColor);
                            item.textColor(data.textColor);
                        }
                    });
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


                }
            });
        }
    }

    export class WebMenuModel {
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