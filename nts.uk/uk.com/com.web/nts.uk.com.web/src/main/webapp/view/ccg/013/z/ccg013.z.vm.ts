module nts.uk.com.view.ccg013.z.viewmodel {
    export class ScreenModel {
        //Text edittor
        nameTitleBar: KnockoutObservable<string>;
        titleBar: KnockoutObservable<any>;

        //colorpicker
        letterColor: KnockoutObservable<string>;
        backgroundColor: KnockoutObservable<string>;

        systemList: KnockoutObservableArray<SystemModel>;
        systemName: KnockoutObservable<string>;
        currentSystemCode: KnockoutObservable<number>
        selectedSystemCode: KnockoutObservable<number>;

        columns: KnockoutObservableArray<any>;
        newColumns: KnockoutObservableArray<any>;

        allItems: KnockoutObservableArray<ItemModel>;
        items: KnockoutObservableArray<ItemModel>;
        newItems: KnockoutObservableArray<ItemModel>;
        tempItems: KnockoutObservableArray<ItemModel>;
        dataItems: KnockoutObservableArray<ItemModel>;

        currentCode: KnockoutObservable<any>;
        currentCodeList: KnockoutObservableArray<any>;
        newCurrentCodeList: KnockoutObservableArray<any>;

        singleSelectedCode: any;
        singleSelectedNewCode: any;
        selectedCodes: any;
        selectedNewCodes: any;
        // headers: any;

        disableSwap: KnockoutObservable<boolean>;

        titleMenuId: KnockoutObservable<string>;

        constructor() {
            var self = this;
            self.nameTitleBar = ko.observable("");
            self.titleBar = ko.observable(null);
            //color picker
            self.letterColor = ko.observable('#FFFFFF');
            self.backgroundColor = ko.observable('#FF9900');

            self.systemList = ko.observableArray([]);
            self.systemName = ko.observable('');
            self.currentSystemCode = ko.observable(0);
            self.selectedSystemCode = ko.observable(0);

            self.allItems = ko.observableArray([]);
            self.items = ko.observableArray([]);
            self.newItems = ko.observableArray([]);
            self.tempItems = ko.observableArray([]);
            self.dataItems = ko.observableArray([]);

            self.singleSelectedCode = ko.observable(null);
            self.singleSelectedNewCode = ko.observable(null);
            self.selectedCodes = ko.observableArray([]);
            self.selectedNewCodes = ko.observableArray([]);

            this.currentCode = ko.observable();
            this.currentCodeList = ko.observableArray([]);
            this.newCurrentCodeList = ko.observableArray([]);

            self.titleMenuId = ko.observable();

            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_49"), prop: 'code', key: 'code', width: 55, formatter: _.escape },
                { headerText: nts.uk.resource.getText("CCG013_50"), prop: 'name', key: 'name', width: 167, formatter: _.escape },
                { headerText: 'pk', prop: 'primaryKey', key: 'primaryKey', width: 1, hidden: true }
            ]);

            self.newColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG013_51"), prop: 'code', width: 55, formatter: _.escape },
                { headerText: nts.uk.resource.getText("CCG013_52"), prop: 'targetItem', width: 160, formatter: _.escape },
                { headerText: nts.uk.resource.getText("CCG013_53"), prop: 'name', width: 160, formatter: _.escape },
                { headerText: 'pk', prop: 'primaryKey', key: 'primaryKey', width: 1, hidden: true }
            ]);

            self.disableSwap = ko.observable(false);

            self.allItems = ko.observableArray([]);
            self.items = ko.observableArray([]);
            self.newItems = ko.observableArray([]);
            self.tempItems = ko.observableArray([]);
            self.dataItems = ko.observableArray([]);

            //Get data by system
            self.selectedSystemCode.subscribe(function (newValue) {
                self.findBySystem(Number(newValue));
            });

            self.disableSwap = ko.observable(false);

        }

        start(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            var titleBar = nts.uk.ui.windows.getShared("titleBar");
            self.titleBar(titleBar);
            self.letterColor(titleBar.textColor);
            self.backgroundColor(titleBar.backgroundColor);
            self.nameTitleBar(titleBar.name);

            var titleMenuId = nts.uk.ui.windows.getShared("titleMenuId");
            if(titleMenuId) {
                self.titleMenuId(titleMenuId);
            }

            $.when(self.findAllDisplay(), self.getSystemEnum()).done(function () {
                self.selectedSystemCode(5);
                if (titleBar.treeMenus) {
                    _.forEach(titleBar.treeMenus, function (item: any) {
                        var standardMenu = _.find(self.allItems(), function (standardMenuItem) {
                            return standardMenuItem.code == item.code && standardMenuItem.system == item.system && standardMenuItem.menu_cls == item.classification;
                        });
                        if (standardMenu) {
                            var order = self.newItems().length + 1;
                            var primaryKey = nts.uk.util.randomId();
                            var data = new ItemModel(primaryKey, standardMenu.code, standardMenu.targetItem, standardMenu.name, order, standardMenu.menu_cls, standardMenu.system);
                            self.newItems.push(data);
                            self.tempItems.push(data);
                        }
                    });
                }
                self.disableSwapButton();
                dfd.resolve();
                $("#combo-box").focus();
            }).fail(function () {
                dfd.reject();
            });

            return dfd.promise();
        }

        /**
         * Disable swap button when right contents does not have any items
         */
        disableSwapButton(): void {
            var self = this;

            if (self.newItems().length === 0) {
                self.disableSwap(false);
            } else {
                self.disableSwap(true);
            }
        }

        /**
         * Get data by system id from db
         */
        findAllDisplay(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            service.findBySystem().done(function (data) {
                var list001: Array<ItemModel> = [];
                var index = 0;
                _.forEach(data, function (item) {
                    var id = nts.uk.util.randomId();
                    self.allItems.push(new ItemModel(id, item.code, item.targetItems, item.displayName, index, item.classification, item.system));
                    if (item.system == self.selectedSystemCode()) {
                        list001.push(new ItemModel(id, item.code, item.targetItems, item.displayName, index, item.classification, item.system));
                    }
                });

                self.items(list001);
                dfd.resolve(data);
            }).fail(function (res) {
                dfd.reject(res);
            });

            return dfd.promise();
        }

        getSystemEnum(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();

            /** Get EditMenuBar*/
            service.getEditMenuBar().done(function (editMenuBar: any) {
                self.systemList.push(new SystemModel(5, nts.uk.resource.getText("CCG013_137")));
                _.forEach(editMenuBar.listSystem, function (item) {
                    self.systemList.push(new SystemModel(item.value, item.localizedName));
                });
                dfd.resolve();
            }).fail(function (error) {
                dfd.reject();
                alert(error.message);
            });
            return dfd.promise();
        }

        findBySystem(system: number): void {
            var self = this;
            var list001: Array<ItemModel> = [];
            var index = 0;
            _.forEach(self.allItems(), function (item: ItemModel) {
                if (self.selectedSystemCode() === 5) {
                    var id = nts.uk.util.randomId();
                    list001.push(new ItemModel(id, item.code, item.targetItem, item.name, index, item.menu_cls, item.system));
                    index++;
                } else {
                    if ((item.system == self.selectedSystemCode() && item.menu_cls != Menu_Cls.TopPage) || (item.system == 0 && item.menu_cls == Menu_Cls.TopPage)) {
                        var id = nts.uk.util.randomId();
                        list001.push(new ItemModel(id, item.code, item.targetItem, item.name, index, item.menu_cls, item.system));
                        index++;
                    }
                }
            });

            self.items(list001);
        }

        /**
         * Add items selected from left grid list to right grid list
         */
        add(): void {
            var self = this;

            var newItems = [];
            _.forEach(self.currentCodeList(), function (selected: any) {
                if (_.indexOf(_.map(self.newItems(), 'primaryKey'), selected) == -1) {
                    var item = _.find(self.items(), function (c) { return c.primaryKey == selected; });
                    item.order = self.newItems().length + 1;
                    //item.primaryKey = nts.uk.util.randomId();
                    self.newItems.push(new ItemModel(nts.uk.util.randomId(), item.code, item.targetItem, item.name, item.order, item.menu_cls, item.system));
                }
            });
            self.newCurrentCodeList([]);
            self.disableSwapButton();
        }

        /**
         * Remove items selected in right grid list
         */
        remove(): void {
            var self = this;
            var newItems = self.newItems();
            var items = [];
            _.remove(newItems, function (currentObject: ItemModel) {
                return _.indexOf(self.newCurrentCodeList(), currentObject.primaryKey) !== -1;
            });

            self.newItems([]);
            _.forEach(newItems, function (item) {
                item.order = self.newItems().length + 1;
                self.newItems.push(item);
            });
            self.disableSwapButton();
        }

        /**
         * Pass data to main screen
         * Close the popup
         */
        submit() {
            var self = this;
            let index = 1;
            _.each(self.newItems(), (item) => {
                item.order = index;
                index++;
            });
            nts.uk.ui.windows.setShared("CCG013C_MENUS", self.newItems());
            nts.uk.ui.windows.setShared("CCG013C_TEXT_COLOR", self.letterColor());
            nts.uk.ui.windows.setShared("CCG013C_BACKGROUND_COLOR", self.backgroundColor());
            nts.uk.ui.windows.setShared("CCG013C_TITLE_MENU_NAME", self.nameTitleBar());

            nts.uk.ui.windows.close();
        }

        // /**
        //  * Click on button d1_26
        //  * Close the popup
        //  */
        closeDialog() {
            var self = this;
            nts.uk.ui.windows.setShared("CCG013C_MENUS", self.tempItems());
            nts.uk.ui.windows.close();
        }

        removeTitleBar() {
            var self = this;
            nts.uk.ui.windows.setShared("CCG013C_MENUS_ID", self.titleMenuId());
            nts.uk.ui.windows.close();
        }
    }

    export class TitleBar {
        nameTitleBar: string;
        letterColor: string;
        backgroundColor: string;
        imageId: string;
        imageName: string;
        imageSize: string;

        constructor(nameTitleBar: string, letterColor: string, backgroundColor: string, imageId: string, imageName: string, imageSize: string) {
            this.nameTitleBar = nameTitleBar;
            this.letterColor = letterColor;
            this.backgroundColor = backgroundColor;
            this.imageId = imageId;
            this.imageName = imageName;
            this.imageSize = imageSize;
        }
    }

    export class ItemModel {
        primaryKey: string;
        code: string;
        targetItem: string;
        name: string;
        order: number;
        menu_cls: number;
        system: number;

        constructor(id: string, code: string, targetItem: string, name: string, order: number, menu_cls: number, system: number) {
            this.primaryKey = id;
            this.code = code;
            this.targetItem = targetItem;
            this.name = name;
            this.order = order;
            this.menu_cls = menu_cls;
            this.system = system;
        }
    }

    export class SystemModel {
        systemCode: number;
        systemName: string;

        constructor(systemCode: number, systemName: string) {
            this.systemCode = systemCode;
            this.systemName = systemName;
        }
    }

    enum Menu_Cls {
        Standard = 0,
        OptionalItemApplication,
        MobilePhone,
        Tablet,
        CodeName,
        GroupCompanyMenu,
        Customize,
        OfficeHelper,
        TopPage
    }
}