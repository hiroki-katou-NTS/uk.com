module qpp008.c.viewmodel {
    export class ScreenModel {

        /*GridList*/
        //C_LST_001
        items: KnockoutObservableArray<ComparingFormHeader>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentItem: KnockoutObservable<ComparingFormHeader>;

        //gridList2
        items2: KnockoutComputed<Array<ComparingFormDeltail>>;
        columns2: KnockoutObservableArray<any>;
        currentCode2: KnockoutObservable<any>;
        currentItem2: KnockoutObservable<any>;


        /*SwapList*/
        //swapList1
        itemsSwap: KnockoutObservableArray<ItemMaster>;
        columnsSwap: KnockoutObservableArray<any>;
        currentCodeListSwap: KnockoutObservableArray<ItemMaster>;
        //swapList2
        itemsSwap1: KnockoutObservableArray<ItemMaster>;
        columnsSwap1: KnockoutObservableArray<any>
        currentCodeListSwap1: KnockoutObservableArray<ItemMaster>;
        //swapList3
        itemsSwap3: KnockoutObservableArray<ItemMaster>;
        columnsSwap3: KnockoutObservableArray<any>
        currentCodeListSwap3: KnockoutObservableArray<ItemMaster>;
        /*TabPanel*/
        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;

        nameValue: KnockoutObservable<string>;
        codeValue: KnockoutObservable<any>;

        /*TextEditer*/
        cInp002Code: KnockoutObservable<boolean>;
        allowEditCode: KnockoutObservable<boolean> = ko.observable(false);
        isUpdate: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            let self = this;
            self._initFormHeader();
            self._initSwap();
            self._initFormDetail();

            self.cInp002Code = ko.observable(false);
            self.currentCode.subscribe(function(codeChanged) {
                if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                    self.currentItem(self.mappingFromJS(self.getItem(codeChanged)));
                    self.cInp002Code(false);
                    self.allowEditCode(true);
                    self.getComparingFormForTab(codeChanged);

                }
            });

        }

        _initFormHeader(): void {
            let self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: 'コード', prop: 'formCode', width: 60 },
                { headerText: '名称', prop: 'formName', width: 120 },
            ]);
            self.currentItem = ko.observable(new ComparingFormHeader('', ''));
            self.currentCode = ko.observable();
        }

        _initSwap(): void {
            let self = this;
            self.itemsSwap = ko.observableArray([]);
            let array = [];
            let array1 = [];
            let array2 = [];

            self.columnsSwap = ko.observableArray([
                { headerText: 'コード', prop: 'itemCode', width: 60 },
                { headerText: '名称', prop: 'itemName', width: 120 }
            ]);

            self.currentCodeListSwap = ko.observableArray([]);

            //swapList2
            self.itemsSwap1 = ko.observableArray([]);
            self.columnsSwap1 = ko.observableArray([
                { headerText: 'コード', prop: 'itemCode', width: 60 },
                { headerText: '名称', prop: 'itemName', width: 120 }
            ]);
            self.currentCodeListSwap1 = ko.observableArray([]);

            //swapList3
            self.itemsSwap3 = ko.observableArray([]);
            self.columnsSwap3 = ko.observableArray([
                { headerText: 'コード', prop: 'itemCode', width: 60 },
                { headerText: '名称', prop: 'itemName', width: 120 }
            ]);
            self.currentCodeListSwap3 = ko.observableArray([]);

            /*TabPanel*/
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: '支給', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: '控除', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-3', title: '記事', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
        }

        _initFormDetail(): void {
            let self = this;
            self.items2 = ko.computed(function() {
                let itemsDetail = [];
                itemsDetail = itemsDetail.concat(self.currentCodeListSwap());
                itemsDetail = itemsDetail.concat(self.currentCodeListSwap1());
                itemsDetail = itemsDetail.concat(self.currentCodeListSwap3());
                return itemsDetail;
            }, self).extend({ deferred: true });

            self.columns2 = ko.observableArray([
                { headerText: '区分', prop: 'categoryAtrName', width: 60 },
                { headerText: 'コード', prop: 'itemCode', width: 60 },
                { headerText: '名称', prop: 'itemName', width: 120 },

            ]);
            self.currentCode2 = ko.observable();
        }

        mappingFromJS(data) {
            return ko.mapping.fromJS(data);
        }

        refreshLayout(): void {
            let self = this;
            self.currentItem(self.mappingFromJS(new ComparingFormHeader('', '')));
            self.currentCode();
            self.allowEditCode(true);
            self.cInp002Code(true);

        }

        createButtonClick(): void {
            let self = this;
            self.refreshLayout();
        }

        insertData() {
            let self = this;
            let newData = ko.toJS(self.currentItem());
            if (self.cInp002Code()) {
                self.items.push(newData);
            } else {
                let updateIndex = _.findIndex(self.items(), function(item) { return item.formCode == newData.code; });
                self.items.splice(updateIndex, 1, newData);
            }
        }

        deleteData() {
            let self = this;
            let newDelData = ko.toJS(self.currentItem());
            let deleData = _.findIndex(self.items(), function(item) { return item.formCode == newDelData.code; });
            self.items.splice(deleData, 1);
        }

        getItem(codeNew): ComparingFormHeader {
            let self = this;
            let currentItem: ComparingFormHeader = _.find(self.items(), function(item) {
                return item.formCode === codeNew;
            });
            return currentItem;
        }

        //        findDuplicateSwaps(codeNew) {
        //            let self = this;
        //            let value;
        //            let checkItemSwap = _.find(self.items2(), function(item) {
        //                return item.code == codeNew
        //            });
        //            if (checkItemSwap == undefined) {
        //                value = false;
        //            }
        //            else {
        //                value = true;
        //            }
        //            return value;
        //        }

        addItem() {
            this.items.push(new ComparingFormHeader('9', '基本給'));
        }

        removeItem() {
            this.items.shift();
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getListComparingFormHeader().done(function(data: Array<ComparingFormHeader>) {
                self.adDataToItemsList(data);
                self.currentItem(_.first(self.items()));
                self.currentCode(self.currentItem().formCode);
                dfd.resolve(data);
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
        }

        getComparingFormForTab(formCode: string) {
            let self = this;
            let dfd = $.Deferred();
            service.getComparingFormForTab(formCode).done(function(data: ItemTabModel) {
                self.itemsSwap([]);
                self.itemsSwap1([]);
                self.itemsSwap3([]);
                self.itemsSwap(data.lstItemMasterForTab_0);
                self.itemsSwap1(data.lstItemMasterForTab_1);
                self.itemsSwap3(data.lstItemMasterForTab_3);
                self.getSwapUpDownList(data.lstSelectForTab_0, 0);
                self.getSwapUpDownList(data.lstSelectForTab_1, 1);
                self.getSwapUpDownList(data.lstSelectForTab_3, 3);
                dfd.resolve(data);
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
        }

        closeDialog(): any {
            nts.uk.ui.windows.close();
        }

        adDataToItemsList(data: Array<ComparingFormHeader>): void {
            let self = this;
            self.items([]);
            _.forEach(data, function(value) {
                self.items.push(value);
            });
        }

        getSwapUpDownList(lstSelectForTab: Array<string>, categoryAtr: number) {
            let self = this;
            if (categoryAtr === 0) {
                _.forEach(lstSelectForTab, function(value) {
                    self.itemsSwap.remove(function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.currentCodeListSwap.push(new ItemMaster(itemMaster.itemCode, itemMaster.itemName, "支"));
                        }
                        return value === itemMaster.itemCode;
                    });
                });
            }

            if (categoryAtr === 1) {
                _.forEach(lstSelectForTab, function(value) {
                    self.itemsSwap1.remove(function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.currentCodeListSwap1.push(new ItemMaster(itemMaster.itemCode, itemMaster.itemName, "控"));
                        }
                        return value === itemMaster.itemCode;
                    });
                });
            }

            if (categoryAtr === 3) {
                _.forEach(lstSelectForTab, function(value) {
                    self.itemsSwap3.remove(function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.currentCodeListSwap3.push(new ItemMaster(itemMaster.itemCode, itemMaster.itemName, "記"));
                        }
                        return value === itemMaster.itemCode;
                    });
                });
            }
        }
    }

    export class ComparingFormHeader {
        formCode: string;
        formName: string;
        constructor(formCode: string, formName: string) {
            this.formCode = formCode;
            this.formName = formName;
        }
    }

    export class ItemMaster {
        itemCode: string;
        itemName: string;
        categoryAtrName: string;
        constructor(itemCode: string, itemName: string, categoryAtrName: string) {
            this.itemCode = itemCode;
            this.itemName = itemName;
            this.categoryAtrName = categoryAtrName;
        }
    }

    export class ItemTabModel {
        lstItemMasterForTab_0: Array<ItemMaster>;
        lstItemMasterForTab_1: Array<ItemMaster>;
        lstItemMasterForTab_3: Array<ItemMaster>;
        lstSelectForTab_0: Array<string>;
        lstSelectForTab_1: Array<string>;
        lstSelectForTab_3: Array<string>;
        constructor(lstItemMasterForTab_0: Array<ItemMaster>, lstItemMasterForTab_1: Array<ItemMaster>,
            lstItemMasterForTab_3: Array<ItemMaster>, lstSelectForTab_0: Array<String>,
            lstSelectForTab_1: Array<String>, lstSelectForTab_3: Array<String>) {
            this.lstItemMasterForTab_0 = lstItemMasterForTab_0;
            this.lstItemMasterForTab_1 = lstItemMasterForTab_0;
            this.lstItemMasterForTab_3 = lstItemMasterForTab_1;
            this.lstSelectForTab_0 = lstSelectForTab_0;
            this.lstSelectForTab_1 = lstSelectForTab_1;
            this.lstSelectForTab_3 = lstSelectForTab_3;
        }
    }

    export class InsertUpdateFormHeaderModel {
        formCode: string;
        formName: string;
        constructor(formCode: string, formName: string) {
            this.formCode = formCode;
            this.formName = formName;
        }
    }

    export class DeleteFormHeaderModel {
        formCode: string;
        constructor(formCode: string) {
            this.formCode = formCode;
        }
    }
}

