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
                    { code: '1', name: nts.uk.resource.getText("CCG015_22") },
                    { code: '0', name: nts.uk.resource.getText("CCG015_23") }
                ]);
                self.permissionDivisionOptions = ko.observableArray([
                    { code: '1', name: nts.uk.resource.getText("CCG015_33") },
                    { code: '0', name: nts.uk.resource.getText("CCG015_34") }
                ]);
                self.selectedUsingMyPage = ko.observable(0);
                self.tabs = ko.observableArray([
                    { id: 'tab_standar_widget', title: nts.uk.resource.getText("Enum_TopPagePartType_StandardWidget"), content: '#standar_widget', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab_optional_widget', title: nts.uk.resource.getText("Enum_TopPagePartType_OptionalWidget"), content: '#optional_widget', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab_dash_board', title: nts.uk.resource.getText("Enum_TopPagePartType_DashBoard"), content: '#dash_board', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab_flow_menu', title: nts.uk.resource.getText("Enum_TopPagePartType_FlowMenu"), content: '#flow_menu', enable: ko.observable(true), visible: ko.observable(true) },
                    { id: 'tab_url', title: nts.uk.resource.getText("Enum_TopPagePartType_ExternalUrl"), content: '#url', enable: ko.observable(true), visible: ko.observable(true) }
                ]);
                self.selectedTab = ko.observable('tab_standar_widget');
                self.myPageSettingModel = ko.observable(new MyPageSettingModel());
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CCG015_11"), width: "70px", key: 'itemCode', dataType: "string", hidden: false },
                    { headerText: nts.uk.resource.getText("CCG015_12"), width: "320px", key: 'itemName', dataType: "string", formatter: _.escape },
                    { headerText: nts.uk.resource.getText("CCG015_29"), key: 'useItem', width: "200px", controlType: 'switch' }
                ]);
                this.currentCode = ko.observable("w1");
                self.data = ko.observable(null);
            }
            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.loadMyPageSetting().done(function(data: MyPageSettingDto) {
                    if (data) {
                        self.data(data);
                        self.loadDataToScreen(data);
                        self.setData(data);
                        dfd.resolve();
                    } else {
                        service.loadDefaultMyPageSetting().done(function(dataDefault: MyPageSettingDto) {
                            self.data(dataDefault);
                            self.loadDataToScreen(dataDefault);
                            self.setData(dataDefault);
                            dfd.resolve();
                        });
                    }
                });
                return dfd.promise();
            }
            private loadDataToScreen(data: MyPageSettingDto) {
                var self = this;
                var dataSort = _.sortBy(data.topPagePartUseSettingDto, ['partType', 'partItemCode', 'partItemName']);
                //reset item
                self.myPageSettingModel().topPagePartSettingItems()[0].settingItems([]);
                self.myPageSettingModel().topPagePartSettingItems()[1].settingItems([]);
                self.myPageSettingModel().topPagePartSettingItems()[2].settingItems([]);
                self.myPageSettingModel().topPagePartSettingItems()[3].settingItems([]);
                self.myPageSettingModel().topPagePartSettingItems()[4].settingItems([]);

                self.myPageSettingModel().useMyPage(data.useMyPage);
                self.myPageSettingModel().topPagePartSettingItems()[0].usePart(data.useStandarWidget);
                self.myPageSettingModel().topPagePartSettingItems()[1].usePart(data.useOptionalWidget);
                self.myPageSettingModel().topPagePartSettingItems()[2].usePart(data.useDashboard);
                self.myPageSettingModel().topPagePartSettingItems()[3].usePart(data.useFlowMenu);
                self.myPageSettingModel().topPagePartSettingItems()[4].usePart(data.externalUrlPermission);
                var StandarWidget = [], 
                    OptionalWidget = [], 
                    Dashboard = [], 
                    FlowMenu = [];
                
                dataSort.forEach(function(item, index) {
                    if (item.partType == TopPagePartsEnum.StandarWidget) {
                        StandarWidget.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision,item.topPagePartId));
                    }
                    if (item.partType == TopPagePartsEnum.OptionalWidget) {
                        OptionalWidget.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision,item.topPagePartId));
                    }
                    if (item.partType == TopPagePartsEnum.Dashboard) {
                        Dashboard.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision,item.topPagePartId));
                    }
                    if (item.partType == TopPagePartsEnum.FlowMenu) {
                        FlowMenu.push(new SettingItemsModel(item.partItemCode, item.partItemName, item.useDivision,item.topPagePartId));
                    }
                });
                self.myPageSettingModel().topPagePartSettingItems()[0].settingItems(StandarWidget);
                self.myPageSettingModel().topPagePartSettingItems()[1].settingItems(OptionalWidget);
                self.myPageSettingModel().topPagePartSettingItems()[2].settingItems(Dashboard);
                self.myPageSettingModel().topPagePartSettingItems()[3].settingItems(FlowMenu);
            }
            private setData(data: MyPageSettingDto) {
                data.topPagePartUseSettingDto.forEach(function(item, index) {
                    if (item.partType == TopPagePartsEnum.StandarWidget) {
                        $("#standarWidget-list").ntsGridListFeature('switch', 'setValue', item.partItemCode, 'useItem', item.useDivision);
                    }
                    if (item.partType == TopPagePartsEnum.OptionalWidget) {
                        $("#optionalWidget-list").ntsGridListFeature('switch', 'setValue', item.partItemCode, 'useItem', item.useDivision);
                    }
                    if (item.partType == TopPagePartsEnum.Dashboard) {
                        $("#dashboard-list").ntsGridListFeature('switch', 'setValue', item.partItemCode, 'useItem', item.useDivision);
                    }
                    if (item.partType == TopPagePartsEnum.FlowMenu) {
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
                    useStandarWidget: self.myPageSettingModel().topPagePartSettingItems()[0].usePart(),
                    useOptionalWidget: self.myPageSettingModel().topPagePartSettingItems()[1].usePart(),
                    useDashboard: self.myPageSettingModel().topPagePartSettingItems()[2].usePart(),
                    useFlowMenu: self.myPageSettingModel().topPagePartSettingItems()[3].usePart(),
                    externalUrlPermission: self.myPageSettingModel().topPagePartSettingItems()[4].usePart(),
                    topPagePartUseSettingDto: []
                }
                self.myPageSettingModel().topPagePartSettingItems().forEach(function(item, index) {
                    item.settingItems().forEach(function(item2, index2) {
                        if (item2.useItem != UseType.Use && item2.useItem != UseType.NotUse) {
                            var settingItem: TopPagePartUseSettingItemDto = {
                                companyId: "",
                                partItemCode: item2.itemCode,
                                partItemName: item2.itemName,
                                useDivision: item2.useItem,
                                partType: item.partType(),
                                topPagePartId: item2.topPagePartId
                            }
                        }
                        else {
                            var settingItem: TopPagePartUseSettingItemDto = {
                                companyId: "",
                                partItemCode: item2.itemCode,
                                partItemName: item2.itemName,
                                useDivision: item2.useItem,
                                partType: item.partType(),
                                topPagePartId: item2.topPagePartId
                            }
                        }
                        if (settingItem.partType != TopPagePartsEnum.ExternalUrl) {
                            items.push(settingItem);
                        }
                    });
                });
                collectData.topPagePartUseSettingDto = items;
                return collectData;
            }
            
            private updateMyPageSetting() {
                var self = this;
                service.updateMyPageSetting(self.collectData()).done(function() {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
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
                this.topPagePartSettingItems = ko.observableArray<PartItemModel>([new PartItemModel(0), new PartItemModel(1), new PartItemModel(2), new PartItemModel(3), new PartItemModel(4)]);
            }
        }
        export class PartItemModel {
            partType: KnockoutObservable<number>;
            usePart: KnockoutObservable<number>;
            settingItems: KnockoutObservableArray<SettingItemsModel>;
            constructor(partType: number) {
                this.partType = ko.observable(partType);
                this.usePart = ko.observable(0);
                this.settingItems = ko.observableArray<SettingItemsModel>([new SettingItemsModel("", "", 0, "")]);
            }
        }
        export class SettingItemsModel {
            itemCode: string;
            itemName: string;
            useItem: number;
            topPagePartId: string;
            constructor(itemCode: string, itemName: string, useItem: number, topPagePartId: string) {
                this.itemCode = itemCode;
                this.itemName = itemName;
                this.useItem = useItem;
                this.topPagePartId = topPagePartId;
            }
        }
        
        export enum TopPagePartsEnum {
            StandarWidget = 0,
            OptionalWidget = 1,
            Dashboard = 2,
            FlowMenu = 3,
            ExternalUrl = 4
        }
        export enum UseType {
            Use = 1,
            NotUse = 0,
        }
    }
}