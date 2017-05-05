var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var ccg015;
                (function (ccg015) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.useDivisionOptions = ko.observableArray([
                                        { code: '1', name: '利用する' },
                                        { code: '0', name: '利用しない' }
                                    ]);
                                    self.permissionDivisionOptions = ko.observableArray([
                                        { code: '1', name: '許可する' },
                                        { code: '0', name: '許可しない' }
                                    ]);
                                    self.selectedUsingMyPage = ko.observable(0);
                                    self.tabs = ko.observableArray([
                                        { id: 'tab_widget', title: 'ウィジェット', content: '#widget', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab_dash_board', title: 'ダッシュボード', content: '#dash_board', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab_flow_menu', title: 'フローメニュー', content: '#flow_menu', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab_url', title: '外部URL', content: '#url', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    self.selectedTab = ko.observable('tab_widget');
                                    self.myPageSettingModel = ko.observable(new MyPageSettingModel());
                                    self.columns = ko.observableArray([
                                        { headerText: "コード", width: "50px", key: 'itemCode', dataType: "string", hidden: false },
                                        { headerText: "名称", width: "100px", key: 'itemName', dataType: "string" },
                                        { headerText: "マイページ利用設定", key: 'useItem', width: "200px", controlType: 'switch' }
                                    ]);
                                    this.currentCode = ko.observable("w1");
                                    self.data = ko.observable(null);
                                    self.selectedTab.subscribe(function () {
                                        self.data(self.collectData());
                                        self.setData(self.data());
                                    });
                                }
                                ScreenModel.prototype.start = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.initData = function () {
                                    var self = this;
                                    var companyId;
                                    b.service.loadMyPageSetting("123456789ABC-0001").done(function (data) {
                                        self.data(data);
                                        self.loadDataToScreen(data).done(function () {
                                            self.setData(data);
                                        });
                                        self.selectedTab("tab_dash_board");
                                        self.selectedTab("tab_flow_menu");
                                        self.selectedTab("tab_widget");
                                    });
                                };
                                ScreenModel.prototype.loadDataToScreen = function (data) {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.myPageSettingModel().topPagePartSettingItems()[0].settingItems([]);
                                    self.myPageSettingModel().topPagePartSettingItems()[1].settingItems([]);
                                    self.myPageSettingModel().topPagePartSettingItems()[2].settingItems([]);
                                    self.myPageSettingModel().useMyPage(data.useMyPage);
                                    self.myPageSettingModel().topPagePartSettingItems()[0].usePart(data.useWidget);
                                    self.myPageSettingModel().topPagePartSettingItems()[1].usePart(data.useDashboard);
                                    self.myPageSettingModel().topPagePartSettingItems()[2].usePart(data.useFlowMenu);
                                    self.myPageSettingModel().topPagePartSettingItems()[3].usePart(data.externalUrlPermission);
                                    data.topPagePartUseSettingDto.forEach(function (item, index) {
                                        if (item.partType == TopPagePartsType.Widget) {
                                            self.myPageSettingModel().topPagePartSettingItems()[0].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision));
                                        }
                                        if (item.partType == TopPagePartsType.Dashboard) {
                                            self.myPageSettingModel().topPagePartSettingItems()[1].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision));
                                        }
                                        if (item.partType == TopPagePartsType.FolowMenu) {
                                            self.myPageSettingModel().topPagePartSettingItems()[2].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision));
                                        }
                                    });
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenModel.prototype.setData = function (data) {
                                    data.topPagePartUseSettingDto.forEach(function (item, index) {
                                        if (item.partType == TopPagePartsType.Widget) {
                                            $("#widget-list").ntsGridListFeature('switch', 'setValue', item.partItemCode, 'useItem', item.useDivision);
                                        }
                                        if (item.partType == TopPagePartsType.Dashboard) {
                                            $("#dashboard-list").ntsGridListFeature('switch', 'setValue', item.partItemCode, 'useItem', item.useDivision);
                                        }
                                        if (item.partType == TopPagePartsType.FolowMenu) {
                                            $("table#flow-list").ntsGridListFeature('switch', 'setValue', item.partItemCode, 'useItem', item.useDivision);
                                        }
                                    });
                                };
                                ScreenModel.prototype.collectData = function () {
                                    var self = this;
                                    var items = [];
                                    var collectData = {
                                        companyId: "",
                                        useMyPage: self.myPageSettingModel().useMyPage(),
                                        useWidget: self.myPageSettingModel().topPagePartSettingItems()[0].usePart(),
                                        useDashboard: self.myPageSettingModel().topPagePartSettingItems()[1].usePart(),
                                        useFlowMenu: self.myPageSettingModel().topPagePartSettingItems()[2].usePart(),
                                        externalUrlPermission: self.myPageSettingModel().topPagePartSettingItems()[3].usePart(),
                                        topPagePartUseSettingDto: []
                                    };
                                    self.myPageSettingModel().topPagePartSettingItems().forEach(function (item, index) {
                                        item.settingItems().forEach(function (item2, index2) {
                                            if (item2.useItem != UseType.Use && item2.useItem != UseType.NotUse) {
                                                var settingItem = {
                                                    companyId: "",
                                                    partItemCode: item2.itemCode,
                                                    partItemName: item2.itemName,
                                                    useDivision: item2.useItem(),
                                                    partType: self.convertPartType(item.partType())
                                                };
                                            }
                                            else {
                                                var settingItem = {
                                                    companyId: "",
                                                    partItemCode: item2.itemCode,
                                                    partItemName: item2.itemName,
                                                    useDivision: item2.useItem,
                                                    partType: self.convertPartType(item.partType())
                                                };
                                            }
                                            items.push(settingItem);
                                        });
                                    });
                                    collectData.topPagePartUseSettingDto = items;
                                    return collectData;
                                };
                                ScreenModel.prototype.convertPartType = function (partType) {
                                    switch (partType) {
                                        case 0: return "Widget";
                                        case 1: return "DashBoard";
                                        case 2: return "FolowMenu";
                                        default: return "Widget";
                                    }
                                };
                                ScreenModel.prototype.updateMyPageSetting = function () {
                                    var self = this;
                                    b.service.updateMyPageSetting(self.collectData()).done(function () {
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var MyPageSettingModel = (function () {
                                function MyPageSettingModel() {
                                    this.useMyPage = ko.observable(0);
                                    this.topPagePartSettingItems = ko.observableArray([new PartItemModel(0), new PartItemModel(1), new PartItemModel(2), new PartItemModel(3)]);
                                }
                                return MyPageSettingModel;
                            }());
                            viewmodel.MyPageSettingModel = MyPageSettingModel;
                            var PartItemModel = (function () {
                                function PartItemModel(partType) {
                                    this.partType = ko.observable(partType);
                                    this.usePart = ko.observable(0);
                                    this.settingItems = ko.observableArray([new SettingItemsModel("", "", 0)]);
                                }
                                return PartItemModel;
                            }());
                            viewmodel.PartItemModel = PartItemModel;
                            var SettingItemsModel = (function () {
                                function SettingItemsModel(itemCode, itemName, useItem) {
                                    this.itemCode = itemCode;
                                    this.itemName = itemName;
                                    this.useItem = ko.observable(useItem);
                                }
                                return SettingItemsModel;
                            }());
                            viewmodel.SettingItemsModel = SettingItemsModel;
                            var TopPagePartsType = (function () {
                                function TopPagePartsType() {
                                }
                                TopPagePartsType.Widget = "Widget";
                                TopPagePartsType.Dashboard = "DashBoard";
                                TopPagePartsType.FolowMenu = "FolowMenu";
                                return TopPagePartsType;
                            }());
                            viewmodel.TopPagePartsType = TopPagePartsType;
                            (function (TopPagePartsEnum) {
                                TopPagePartsEnum[TopPagePartsEnum["Widget"] = 0] = "Widget";
                                TopPagePartsEnum[TopPagePartsEnum["Dashboard"] = 1] = "Dashboard";
                                TopPagePartsEnum[TopPagePartsEnum["FolowMenu"] = 2] = "FolowMenu";
                            })(viewmodel.TopPagePartsEnum || (viewmodel.TopPagePartsEnum = {}));
                            var TopPagePartsEnum = viewmodel.TopPagePartsEnum;
                            (function (UseType) {
                                UseType[UseType["Use"] = 1] = "Use";
                                UseType[UseType["NotUse"] = 0] = "NotUse";
                            })(viewmodel.UseType || (viewmodel.UseType = {}));
                            var UseType = viewmodel.UseType;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = ccg015.b || (ccg015.b = {}));
                })(ccg015 = view.ccg015 || (view.ccg015 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ccg015.b.vm.js.map