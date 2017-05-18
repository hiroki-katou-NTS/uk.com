module nts.uk.pr.view.ccg015.b {
    export module viewmodel {
        import MyPageSettingDto = nts.uk.pr.view.ccg015.b.service.model.MyPageSettingDto;
        import TopPagePartUseSettingItemDto = nts.uk.pr.view.ccg015.b.service.model.TopPagePartUseSettingItemDto;
        export class ScreenModel {
            useDivisionOptions: KnockoutObservableArray<any>;
            permissionDivisionOptions: KnockoutObservableArray<any>;
            selectedUsingMyPage　: KnockoutObservable<number>;

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            myPageSettingModel: KnockoutObservable<MyPageSettingModel>;
            columns: KnockoutObservable<any>;
            currentCode: KnockoutObservable<any>;
            data: KnockoutObservable<MyPageSettingDto>;
            constructor() {
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
                    { headerText: "コード", width: "70px", key: 'itemCode', dataType: "string", hidden: false },
                    { headerText: "名称", width: "200px", key: 'itemName', dataType: "string" },
                    { headerText: "マイページ利用設定", key: 'useItem', width: "300px", controlType: 'switch' }
                ]);
                this.currentCode = ko.observable("w1");
                self.data = ko.observable(null);
                self.selectedTab.subscribe(function() {
                    self.data(self.collectData());
                    self.setData(self.data());
                });
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }
            initData() {
                var self = this;
                var companyId: string;
                service.loadMyPageSetting().done(function(data: MyPageSettingDto) {
                    if (data) {
                        self.data(data);
                        self.loadDataToScreen(data).done(function() {
                            self.setData(data);
                        });
                    } else {
                        service.loadDefaultMyPageSetting().done(function(dataDefault: MyPageSettingDto) {
                            self.data(dataDefault);
                            self.loadDataToScreen(dataDefault).done(function() {
                                self.setData(dataDefault);
                            });
                        });
                    }
                    self.selectedTab("tab_dash_board");
                    self.selectedTab("tab_flow_menu");
                    self.selectedTab("tab_widget");
                });
            }
            private loadDataToScreen(data: MyPageSettingDto): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                //reset item
                self.myPageSettingModel().topPagePartSettingItems()[0].settingItems([]);
                self.myPageSettingModel().topPagePartSettingItems()[1].settingItems([]);
                self.myPageSettingModel().topPagePartSettingItems()[2].settingItems([]);

                self.myPageSettingModel().useMyPage(data.useMyPage);
                self.myPageSettingModel().topPagePartSettingItems()[0].usePart(data.useWidget);
                self.myPageSettingModel().topPagePartSettingItems()[1].usePart(data.useDashboard);
                self.myPageSettingModel().topPagePartSettingItems()[2].usePart(data.useFlowMenu);
                self.myPageSettingModel().topPagePartSettingItems()[3].usePart(data.externalUrlPermission);

                data.topPagePartUseSettingDto.forEach(function(item, index) {
                    if (item.partType == TopPagePartsType.Widget) {
                        self.myPageSettingModel().topPagePartSettingItems()[0].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision,item.topPagePartId));
                    }
                    if (item.partType == TopPagePartsType.Dashboard) {
                        self.myPageSettingModel().topPagePartSettingItems()[1].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision,item.topPagePartId));
                    }
                    if (item.partType == TopPagePartsType.FolowMenu) {
                        self.myPageSettingModel().topPagePartSettingItems()[2].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision,item.topPagePartId));
                    }
                });
                dfd.resolve();
                return dfd.promise();
            }
            private setData(data: MyPageSettingDto) {
                data.topPagePartUseSettingDto.forEach(function(item, index) {
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
            }

            private collectData(): MyPageSettingDto {
                var self = this;
                var items: Array<TopPagePartUseSettingItemDto> = [];

                var collectData: MyPageSettingDto = {
                    companyId: "",
                    useMyPage: self.myPageSettingModel().useMyPage(),
                    useWidget: self.myPageSettingModel().topPagePartSettingItems()[0].usePart(),
                    useDashboard: self.myPageSettingModel().topPagePartSettingItems()[1].usePart(),
                    useFlowMenu: self.myPageSettingModel().topPagePartSettingItems()[2].usePart(),
                    externalUrlPermission: self.myPageSettingModel().topPagePartSettingItems()[3].usePart(),
                    topPagePartUseSettingDto: []
                }
                self.myPageSettingModel().topPagePartSettingItems().forEach(function(item, index) {
                    item.settingItems().forEach(function(item2, index2) {
                        if (item2.useItem != UseType.Use && item2.useItem != UseType.NotUse) {
                            var settingItem: TopPagePartUseSettingItemDto = {
                                companyId: "",
                                partItemCode: item2.itemCode,
                                partItemName: item2.itemName,
                                useDivision: item2.useItem(),
                                partType: self.convertPartType(item.partType()),
                                topPagePartId: item2.topPagePartId
                            }
                        }
                        else {
                            var settingItem: TopPagePartUseSettingItemDto = {
                                companyId: "",
                                partItemCode: item2.itemCode,
                                partItemName: item2.itemName,
                                useDivision: item2.useItem,
                                partType: self.convertPartType(item.partType()),
                                topPagePartId: item2.topPagePartId
                            }
                        }
                        if (settingItem.partType != TopPagePartsType.ExternalUrl) {
                            items.push(settingItem);
                        }
                    });
                });
                collectData.topPagePartUseSettingDto = items;
                return collectData;
            }
            private convertPartType(partType: number) {
                switch (partType) {
                    case 0: return "Widget";
                    case 1: return "DashBoard";
                    case 2: return "FolowMenu";
                    case 3: return "ExternalUrl";
                    default: return "Widget";
                }
            }
            private updateMyPageSetting() {
                var self = this;
                service.updateMyPageSetting(self.collectData()).done(function() {
                    nts.uk.ui.dialog.alert("登録しました。").then(function() {
                        nts.uk.ui.windows.close();
                    });
                });
            }
        }
        export class MyPageSettingModel {
            useMyPage: KnockoutObservable<number>;
            topPagePartSettingItems: KnockoutObservableArray<PartItemModel>;
            constructor() {
                this.useMyPage = ko.observable(0);
                this.topPagePartSettingItems = ko.observableArray<PartItemModel>([new PartItemModel(0), new PartItemModel(1), new PartItemModel(2), new PartItemModel(3)]);
            }
        }
        export class PartItemModel {
            partType: KnockoutObservable<number>;
            usePart: KnockoutObservable<number>;
            settingItems: KnockoutObservableArray<SettingItemsModel>;
            constructor(partType: number) {
                this.partType = ko.observable(partType);
                this.usePart = ko.observable(0);
                this.settingItems = ko.observableArray<SettingItemsModel>([new SettingItemsModel("", "", 0)]);
            }
        }
        export class SettingItemsModel {
            itemCode: string;
            itemName: string;
            useItem: KnockoutObservable<number>;
            topPagePartId: string;
            constructor(itemCode: string, itemName: string, useItem: number, topPagePartId: string) {
                this.itemCode = itemCode;
                this.itemName = itemName;
                this.useItem = ko.observable(useItem);
                this.topPagePartId = topPagePartId;
            }
        }
        export class TopPagePartsType {
            static Widget = "Widget";
            static Dashboard = "DashBoard";
            static FolowMenu = "FolowMenu";
            static ExternalUrl = "ExternalUrl";
        }
        export enum TopPagePartsEnum {
            Widget = 0,
            Dashboard = 1,
            FolowMenu = 2
        }
        export enum UseType {
            Use = 1,
            NotUse = 0,
        }
    }
}