module qpp008.c.viewmodel {
    export class ScreenModel {

        /*GridList*/
        //C_LST_001
        items: KnockoutObservableArray<ComparingFormHeader>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentItem: KnockoutObservable<ComparingFormHeader>;

        //gridList2
        items2: KnockoutComputed<Array<ItemMaster>>;
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

        /*Other*/
        allowEditCode: KnockoutObservable<boolean>;
        isUpdate: KnockoutObservable<boolean>;

        constructor() {
            let self = this;
            self._initFormHeader();
            self._initSwap();
            self._initFormDetail();

            self.currentCode.subscribe(function(codeChanged) {
                if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                    self.currentItem(self.mappingFromJS(self.getItem(codeChanged)));
                    self.allowEditCode(false);
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
            let array = new Array();
            let array1 = new Array();
            let array2 = new Array();

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
                let itemsDetail = new Array();
                itemsDetail = itemsDetail.concat(self.mappingItemMasterToFormDetail(self.currentCodeListSwap(), 0));
                itemsDetail = itemsDetail.concat(self.mappingItemMasterToFormDetail(self.currentCodeListSwap1(), 1));
                itemsDetail = itemsDetail.concat(self.mappingItemMasterToFormDetail(self.currentCodeListSwap3(), 3));
                return itemsDetail;
            }, self).extend({ deferred: true });

            self.columns2 = ko.observableArray([
                { headerText: '区分', prop: 'categoryAtrName', width: 60 },
                { headerText: 'コード', prop: 'itemCode', width: 60 },
                { headerText: '名称', prop: 'itemName', width: 120 },
            ]);
            self.currentCode2 = ko.observable();

            self.allowEditCode = ko.observable(false);
            self.isUpdate = ko.observable(true);
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

        refreshLayout(): void {
            let self = this;
            self.currentItem(self.mappingFromJS(new ComparingFormHeader('', '')));
            self.currentCode();
            self.allowEditCode(true);
            self.isUpdate(false);
            self.getComparingFormForTab(null);
        }

        createButtonClick(): void {
            let self = this;
            self.isUpdate(false);
            self.refreshLayout();
        }

        insertUpdateData() {
            let self = this;
            let dfd = $.Deferred();           
            let newformCode = ko.mapping.toJS(self.currentItem().formCode);
            let newformName = ko.mapping.toJS(self.currentItem().formName);
            if (nts.uk.text.isNullOrEmpty(newformCode)) {
                $('#INP_002').ntsError('set', nts.uk.text.format('{0}が入力されていません。', 'コード'));
                return;
            }
            if (nts.uk.text.isNullOrEmpty(newformName)) {
                $('#INP_003').ntsError('set', nts.uk.text.format('{0}が入力されていません。', '名称'));
                return;
            }
            
            let comparingFormDetailList = new Array<ComparingFormDetail>();
             comparingFormDetailList = self.items2().map(function(item, i) {
                return new ComparingFormDetail(item.itemCode,item.categoryAtr, i);
            });
            let insertUpdateDataModel = new InsertUpdateDataModel(self.currentItem().formCode, self.currentItem().formName, comparingFormDetailList);
             service.insertUpdateComparingForm(insertUpdateDataModel, self.isUpdate()).done(function() {
                 alert("insert Ok");
             });
            

        }

        deleteData() {
            let self = this;
            let newDelData = ko.toJS(self.currentItem());
            let deleData = _.findIndex(self.items(), function(item) { return item.formCode == newDelData.code; });
            self.items.splice(deleData, 1);
        }

        getItem(codeNew: string): ComparingFormHeader {
            let self = this;
            let currentItem: ComparingFormHeader = _.find(self.items(), function(item) {
                return item.formCode === codeNew;
            });
            return currentItem;
        }

        mappingFromJS(data: any) {
            return ko.mapping.fromJS(data);
        }

        getComparingFormForTab(formCode: string) {
            let self = this;
            let dfd = $.Deferred();
            service.getComparingFormForTab(formCode).done(function(data: ItemTabModel) {
                self.itemsSwap([]);
                self.itemsSwap1([]);
                self.itemsSwap3([]);
                self.currentCodeListSwap([]);
                self.currentCodeListSwap1([]);
                self.currentCodeListSwap3([]);
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

        mappingItemMasterToFormDetail(selectList: Array<ItemMaster>, categoryAtr: number) {
            return _.map(selectList, function(item) {
                let newMapping = new ItemMaster(item.itemCode, item.itemName, "支", categoryAtr);
                if (categoryAtr === 1) {
                    newMapping = new ItemMaster(item.itemCode, item.itemName, "控", categoryAtr);
                } else if (categoryAtr === 3) {
                    newMapping = new ItemMaster(item.itemCode, item.itemName, "記", categoryAtr);
                }
                return newMapping;
            });
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
                            self.currentCodeListSwap.push(itemMaster);
                        }
                        return value === itemMaster.itemCode;
                    });
                });
            }

            if (categoryAtr === 1) {
                _.forEach(lstSelectForTab, function(value) {
                    self.itemsSwap1.remove(function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.currentCodeListSwap1.push(itemMaster);
                        }
                        return value === itemMaster.itemCode;
                    });
                });
            }

            if (categoryAtr === 3) {
                _.forEach(lstSelectForTab, function(value) {
                    self.itemsSwap3.remove(function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.currentCodeListSwap3.push(itemMaster);
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
        categoryAtr: number;
        constructor(itemCode: string, itemName: string, categoryAtrName: string, categoryAtr: number) {
            this.itemCode = itemCode;
            this.itemName = itemName;
            this.categoryAtrName = categoryAtrName;
            this.categoryAtr = categoryAtr;
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
            lstItemMasterForTab_3: Array<ItemMaster>, lstSelectForTab_0: Array<string>,
            lstSelectForTab_1: Array<string>, lstSelectForTab_3: Array<string>) {
            this.lstItemMasterForTab_0 = lstItemMasterForTab_0;
            this.lstItemMasterForTab_1 = lstItemMasterForTab_0;
            this.lstItemMasterForTab_3 = lstItemMasterForTab_1;
            this.lstSelectForTab_0 = lstSelectForTab_0;
            this.lstSelectForTab_1 = lstSelectForTab_1;
            this.lstSelectForTab_3 = lstSelectForTab_3;
        }
    }

    export class InsertUpdateDataModel {
        formCode: string;
        formName: string;
        comparingFormDetailList: Array<ComparingFormDetail>;
        constructor(formCode: string, formName: string, comparingFormDetailList: Array<ComparingFormDetail>) {
            this.formCode = formCode;
            this.formName = formName;
            this.comparingFormDetailList = comparingFormDetailList;
        }
    }

    export class ComparingFormDetail {
        itemCode: string;
        categoryAtr: number;
        dispOrder: number;
        constructor(itemCode: string, categoryAtr: number, dispOrder: number) {
            this.itemCode = itemCode;
            this.categoryAtr = categoryAtr;
            this.dispOrder = dispOrder;
        }
    }

    export class DeleteFormHeaderModel {
        formCode: string;
        constructor(formCode: string) {
            this.formCode = formCode;
        }
    }
}

