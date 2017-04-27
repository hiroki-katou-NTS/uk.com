module nts.uk.pr.view.ccg015.b {
    export module viewmodel {
        import MyPageSettingDto = nts.uk.pr.view.ccg015.b.service.model.MyPageSettingDto;
        export class ScreenModel {
            useDivisionOptions: KnockoutObservableArray<any>;
            permissionDivisionOptions: KnockoutObservableArray<any>;
            selectedUsingMyPage　: KnockoutObservable<number>;

            tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
            selectedTab: KnockoutObservable<string>;
            myPageSettingModel: KnockoutObservable<MyPageSettingModel>;
            columns: KnockoutObservable<any>;
            currentCode: KnockoutObservable<any>;
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
                    { headerText: "コード", width: "50px", key: 'itemCode', dataType: "string", hidden: false },
                    { headerText: "名称", width: "100px", key: 'itemName', dataType: "string" },
                    { headerText: "マイページ利用設定", key: 'useItem', width: "200px", controlType: 'switch' }
                ]);
                this.currentCode = ko.observable("w1");
            }
            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                var companyId: string;
                service.loadMyPageSetting(companyId).done(function(data: MyPageSettingDto) {
                    //TODO convert data to Model
                    self.loadDataToScreen(data);
                    dfd.resolve();
                });
                return dfd.promise();
            }
            private loadDataToScreen(data: MyPageSettingDto) {
                var self = this;
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
                        self.myPageSettingModel().topPagePartSettingItems()[0].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision));
                    }
                    if (item.partType == TopPagePartsType.Dashboard) {
                        self.myPageSettingModel().topPagePartSettingItems()[1].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision));
                    }
                    if (item.partType == TopPagePartsType.FolowMenu) {
                        self.myPageSettingModel().topPagePartSettingItems()[2].settingItems.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision));
                    }
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
            constructor(itemCode: string, itemName: string, useItem: number) {
                this.itemCode = itemCode;
                this.itemName = itemName;
                this.useItem = ko.observable(useItem);
            }
        }
        export class TopPagePartsType {
            static Widget = "Widget";
            static Dashboard = "DashBoard";
            static FolowMenu = "FolowMenu";
        }
    }
}