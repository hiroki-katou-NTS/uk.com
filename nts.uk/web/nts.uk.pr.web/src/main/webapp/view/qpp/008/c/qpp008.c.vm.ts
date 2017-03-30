module qpp008.c.viewmodel {
    export class ScreenModel {

        /*GridList*/
        //C_LST_001
        items: KnockoutObservableArray<ComparingFormHeader>;
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;
        currentItem: KnockoutObservable<ComparingFormHeader>;
        previousCurrentCode: string;

        //gridList2
        items2: KnockoutComputed<Array<ItemMaster>>;
        columns2: KnockoutObservableArray<any>;
        currentCode2: KnockoutObservable<any>;
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
        currentItemDirty: nts.uk.ui.DirtyChecker;
        items2Dirty: nts.uk.ui.DirtyChecker;
        constructor() {
            let self = this;
            self._initFormHeader();
            self._initSwap();
            self._initFormDetail();

            self.currentCode.subscribe(function(codeChanged) {
                if (nts.uk.text.isNullOrEmpty(codeChanged)) {
                    if (self.isUpdate()) {
                        self.refreshLayout();
                    }
                    return;
                }
                if (codeChanged === self.previousCurrentCode) {
                    return;
                }
                if (!self.currentItemDirty.isDirty() && !self.items2Dirty.isDirty()) {
                    self.processWhenCurrentCodeChange(codeChanged);
                    return;
                }
                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function() {
                    self.processWhenCurrentCodeChange(codeChanged);
                }).ifCancel(function() {
                    self.currentCode(self.previousCurrentCode);
                })

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
            self.previousCurrentCode = null;
        }

        _initSwap(): void {
            let self = this;
            self.itemsSwap = ko.observableArray([]);
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
            self.currentItemDirty = new nts.uk.ui.DirtyChecker(self.currentItem);
            self.items2Dirty = new nts.uk.ui.DirtyChecker(self.items2);
        }

        startPage(): JQueryPromise<any> {
            let self = this;
            return self.reload(true);
        }

        refreshLayout(): void {
            let self = this;
            self.currentItem(self.mappingFromJS(new ComparingFormHeader('', '')));
            self.currentCode(null);
            self.previousCurrentCode = null;
            self.allowEditCode(true);
            self.isUpdate(false);
            self.clearError();
            self.getComparingFormForTab(null).done(function() {
                self.currentItemDirty.reset();
                self.items2Dirty.reset();
            });
            $("#C_INP_002").focus();
        }

        createButtonClick(): void {
            let self = this;
            $('.save-error').ntsError('clear');
            if (!self.currentItemDirty.isDirty() && !self.items2Dirty.isDirty()) {
                self.refreshLayout();
                return;
            }
            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function() {
                self.refreshLayout();
            }).ifCancel(function() {
                return;
            });
        }

        processWhenCurrentCodeChange(codeChanged: any): void {
            let self = this;
            self.currentItem(self.mappingFromJS(self.getItem(codeChanged)));
            self.allowEditCode(false);
            self.isUpdate(true);
            self.getComparingFormForTab(codeChanged).done(function() {
                self.currentItemDirty.reset();
                self.items2Dirty.reset();
            });
            self.clearError();
            self.previousCurrentCode = codeChanged;
        }

        reload(isReload: boolean, reloadCode?: string) {
            let self = this;
            let dfd = $.Deferred();
            service.getListComparingFormHeader().done(function(data: Array<ComparingFormHeader>) {
                self.items([]);
                _.forEach(data, function(item) {
                    self.items.push(item);
                });
                if (self.items().length <= 0) {
                    self.refreshLayout();
                    dfd.resolve(data);
                    return;
                }
                if (isReload) {
                    self.currentCode(self.items()[0].formCode)
                } else if (!nts.uk.text.isNullOrEmpty(reloadCode)) {
                    self.currentCode(reloadCode)
                }
                dfd.resolve(data);
            }).fail(function(error) {
                alert(error.message);
            });
            return dfd.promise();
        }

        isError(): boolean {
            let self = this;
            $('#C_INP_002').ntsEditor("validate");
            $('#C_INP_003').ntsEditor("validate");
            if ($('.nts-editor').ntsError("hasError")) {
                return true;
            }
            return false;
        }

        clearError(): void {
            if ($('.nts-editor').ntsError("hasError")) {
                $('.save-error').ntsError('clear');
            }
        }

        insertUpdateData() {
            let self = this;
            let dfd = $.Deferred();
            if (self.isError()) {
                return;
            }
            let newformCode = ko.mapping.toJS(self.currentItem().formCode);
            let newformName = ko.mapping.toJS(self.currentItem().formName);
            let comparingFormDetailList = self.items2().map(function(item, i) {
                return new ComparingFormDetail(item.itemCode, item.categoryAtr, i);
            });
            let insertUpdateDataModel = new InsertUpdateDataModel(nts.uk.text.padLeft(newformCode, '0', 2), newformName, comparingFormDetailList);
            service.insertUpdateComparingForm(insertUpdateDataModel, self.isUpdate()).done(function() {
                self.reload(false, nts.uk.text.padLeft(newformCode, '0', 2));
                self.currentItemDirty.reset();
                if (self.isUpdate() === false) {
                    self.isUpdate(true);
                    self.allowEditCode(false);
                }
                dfd.resolve();
            }).fail(function(error) {
                if (error.message === '1') {
                    let _message = "入力した{0}は既に存在しています。\r\n {1}を確認してください。";
                    nts.uk.ui.dialog.alert(nts.uk.text.format(_message, 'コード', 'コード')).then(function() {
                        self.reload(true);
                    })
                } else if (error.message === '2') {
                    nts.uk.ui.dialog.alert("対象データがありません。").then(function() {
                        self.reload(true);
                    })
                }
            });
            return dfd.promise();
        }
        
         deleteConfirm() {
            let self = this;
            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                self.deleteData();
            })
        }

        deleteData() {
            let self = this;
            let deleteCode = ko.mapping.toJS(self.currentItem().formCode);
            service.deleteComparingForm(new DeleteFormHeaderModel(deleteCode)).done(function() {
                let indexItemDelete = _.findIndex(self.items(), function(item) { return item.formCode == deleteCode; });
                $.when(self.reload(false)).done(function() {
                    if (self.items().length === 0) {
                        self.refreshLayout();
                        return;
                    }
                    if (self.items().length == indexItemDelete) {
                        self.currentCode(self.items()[indexItemDelete - 1].formCode);
                        return;
                    }

                    if (self.items().length < indexItemDelete) {
                        self.currentCode(self.items()[0].formCode);
                        return;
                    }

                    if (self.items().length > indexItemDelete) {
                        self.currentCode(self.items()[indexItemDelete].formCode);
                        return;
                    }
                });

            }).fail(function(error) {
                if (error.message === '2') {
                    nts.uk.ui.dialog.alert("対象データがありません。").then(function() {
                        self.reload(true);
                    })
                }
            });
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
            self.itemsSwap([]);
            self.itemsSwap1([]);
            self.itemsSwap3([]);
            self.currentCodeListSwap([]);
            self.currentCodeListSwap1([]);
            self.currentCodeListSwap3([]);
            service.getComparingFormForTab(formCode).done(function(data: ItemTabModel) {
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

        getSwapUpDownList(lstSelectForTab: Array<string>, categoryAtr: number) {
            let self = this;
            if (categoryAtr === 0) {
                _.forEach(lstSelectForTab, function(value) {
                    _.forEach(self.itemsSwap(), function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.itemsSwap.remove(itemMaster);
                            self.currentCodeListSwap.push(itemMaster);
                            return false;
                        }
                    });
                });
            }

            if (categoryAtr === 1) {
                _.forEach(lstSelectForTab, function(value) {
                    _.forEach(self.itemsSwap1(), function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.itemsSwap1.remove(itemMaster);
                            self.currentCodeListSwap1.push(itemMaster);
                            return false;
                        }
                    });
                });
            }

            if (categoryAtr === 3) {
                _.forEach(lstSelectForTab, function(value) {
                    _.forEach(self.itemsSwap3(), function(itemMaster) {
                        if (value === itemMaster.itemCode) {
                            self.itemsSwap3.remove(itemMaster);
                            self.currentCodeListSwap3.push(itemMaster);
                            return false;
                        }
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

