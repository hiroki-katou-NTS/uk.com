var qpp008;
(function (qpp008) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self._initFormHeader();
                    self._initSwap();
                    self._initFormDetail();
                    self.currentCode.subscribe(function (codeChanged) {
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
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                            self.processWhenCurrentCodeChange(codeChanged);
                        }).ifCancel(function () {
                            self.currentCode(self.previousCurrentCode);
                        });
                    });
                }
                ScreenModel.prototype._initFormHeader = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'formCode', width: 60 },
                        { headerText: '名称', prop: 'formName', width: 120 },
                    ]);
                    self.currentItem = ko.observable(new ComparingFormHeader('', ''));
                    self.currentCode = ko.observable();
                    self.previousCurrentCode = null;
                };
                ScreenModel.prototype._initSwap = function () {
                    var self = this;
                    self.itemsSwap = ko.observableArray([]);
                    self.columnsSwap = ko.observableArray([
                        { headerText: 'コード', prop: 'itemCode', width: 60 },
                        { headerText: '名称', prop: 'itemName', width: 120 }
                    ]);
                    self.currentCodeListSwap = ko.observableArray([]);
                    self.itemsSwap1 = ko.observableArray([]);
                    self.columnsSwap1 = ko.observableArray([
                        { headerText: 'コード', prop: 'itemCode', width: 60 },
                        { headerText: '名称', prop: 'itemName', width: 120 }
                    ]);
                    self.currentCodeListSwap1 = ko.observableArray([]);
                    self.itemsSwap3 = ko.observableArray([]);
                    self.columnsSwap3 = ko.observableArray([
                        { headerText: 'コード', prop: 'itemCode', width: 60 },
                        { headerText: '名称', prop: 'itemName', width: 120 }
                    ]);
                    self.currentCodeListSwap3 = ko.observableArray([]);
                    self.tabs = ko.observableArray([
                        { id: 'tab-1', title: '支給', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-2', title: '控除', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-3', title: '記事', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                    ]);
                    self.selectedTab = ko.observable('tab-1');
                };
                ScreenModel.prototype._initFormDetail = function () {
                    var self = this;
                    self.items2 = ko.computed(function () {
                        var itemsDetail = new Array();
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
                    self.allowEditCode = ko.observable(false);
                    self.isUpdate = ko.observable(true);
                    self.isEnableDeleteBtn = ko.observable(true);
                    self.currentItemDirty = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.items2Dirty = new nts.uk.ui.DirtyChecker(self.items2);
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    return self.reload(true);
                };
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    self.currentItem(self.mappingFromJS(new ComparingFormHeader('', '')));
                    self.currentCode(null);
                    self.previousCurrentCode = null;
                    self.allowEditCode(true);
                    self.isUpdate(false);
                    self.isEnableDeleteBtn(false);
                    self.clearError();
                    self.getComparingFormForTab(null).done(function () {
                        self.currentItemDirty.reset();
                        self.items2Dirty.reset();
                    });
                    $("#C_INP_002").focus();
                };
                ScreenModel.prototype.createButtonClick = function () {
                    var self = this;
                    if (!self.currentItemDirty.isDirty() && !self.items2Dirty.isDirty()) {
                        self.refreshLayout();
                        return;
                    }
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                        self.refreshLayout();
                    }).ifCancel(function () {
                        return;
                    });
                };
                ScreenModel.prototype.processWhenCurrentCodeChange = function (codeChanged) {
                    var self = this;
                    self.currentItem(self.mappingFromJS(self.getItem(codeChanged)));
                    self.allowEditCode(false);
                    self.isUpdate(true);
                    self.getComparingFormForTab(codeChanged).done(function () {
                        self.currentItemDirty.reset();
                        self.items2Dirty.reset();
                    });
                    self.clearError();
                    self.previousCurrentCode = codeChanged;
                };
                ScreenModel.prototype.reload = function (isReload, reloadCode) {
                    var self = this;
                    var dfd = $.Deferred();
                    c.service.getListComparingFormHeader().done(function (data) {
                        self.items([]);
                        _.forEach(data, function (item) {
                            self.items.push(item);
                        });
                        if (self.items().length <= 0) {
                            self.refreshLayout();
                            dfd.resolve(data);
                            return;
                        }
                        self.isEnableDeleteBtn(true);
                        self.items2Dirty.reset();
                        if (isReload) {
                            self.currentCode(self.items()[0].formCode);
                        }
                        else if (!nts.uk.text.isNullOrEmpty(reloadCode)) {
                            self.currentCode(reloadCode);
                        }
                        dfd.resolve(data);
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.isError = function () {
                    var self = this;
                    $('#C_INP_002').ntsEditor("validate");
                    $('#C_INP_003').ntsEditor("validate");
                    if ($('.nts-editor').ntsError("hasError")) {
                        return true;
                    }
                    return false;
                };
                ScreenModel.prototype.clearError = function () {
                    if ($('.nts-editor').ntsError("hasError")) {
                        $('.save-error').ntsError('clear');
                    }
                };
                ScreenModel.prototype.insertUpdateData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.isError()) {
                        return;
                    }
                    var newformCode = ko.mapping.toJS(self.currentItem().formCode);
                    var newformName = ko.mapping.toJS(self.currentItem().formName);
                    var comparingFormDetailList = self.items2().map(function (item, i) {
                        return new ComparingFormDetail(item.itemCode, item.categoryAtr, i);
                    });
                    var insertUpdateDataModel = new InsertUpdateDataModel(nts.uk.text.padLeft(newformCode, '0', 2), newformName, comparingFormDetailList);
                    c.service.insertUpdateComparingForm(insertUpdateDataModel, self.isUpdate()).done(function () {
                        self.reload(false, nts.uk.text.padLeft(newformCode, '0', 2));
                        self.currentItemDirty.reset();
                        if (self.isUpdate() === false) {
                            self.isUpdate(true);
                            self.allowEditCode(false);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        if (error.message === '1') {
                            var _message = "入力した{0}は既に存在しています。\r\n {1}を確認してください。";
                            nts.uk.ui.dialog.alert(nts.uk.text.format(_message, 'コード', 'コード'));
                        }
                        else if (error.message === '2') {
                            nts.uk.ui.dialog.alert("対象データがありません。").then(function () {
                                self.reload(true);
                            });
                        }
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.deleteConfirm = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                        self.deleteData();
                    });
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var deleteCode = ko.mapping.toJS(self.currentItem().formCode);
                    c.service.deleteComparingForm(new DeleteFormHeaderModel(deleteCode)).done(function () {
                        var indexItemDelete = _.findIndex(self.items(), function (item) { return item.formCode == deleteCode; });
                        $.when(self.reload(false)).done(function () {
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
                    }).fail(function (error) {
                        if (error.message === '2') {
                            nts.uk.ui.dialog.alert("対象データがありません。").then(function () {
                                self.reload(true);
                            });
                        }
                    });
                };
                ScreenModel.prototype.getItem = function (codeNew) {
                    var self = this;
                    var currentItem = _.find(self.items(), function (item) {
                        return item.formCode === codeNew;
                    });
                    return currentItem;
                };
                ScreenModel.prototype.mappingFromJS = function (data) {
                    return ko.mapping.fromJS(data);
                };
                ScreenModel.prototype.getComparingFormForTab = function (formCode) {
                    var self = this;
                    var dfd = $.Deferred();
                    self.itemsSwap([]);
                    self.itemsSwap1([]);
                    self.itemsSwap3([]);
                    self.currentCodeListSwap([]);
                    self.currentCodeListSwap1([]);
                    self.currentCodeListSwap3([]);
                    c.service.getComparingFormForTab(formCode).done(function (data) {
                        self.itemsSwap(data.lstItemMasterForTab_0);
                        self.itemsSwap1(data.lstItemMasterForTab_1);
                        self.itemsSwap3(data.lstItemMasterForTab_3);
                        var t0 = performance.now();
                        self.getSwapUpDownList(data.lstSelectForTab_0, 0);
                        var t1 = performance.now();
                        console.log("Call to doSomething took " + (t1 - t0) + " milliseconds.");
                        self.getSwapUpDownList(data.lstSelectForTab_1, 1);
                        self.getSwapUpDownList(data.lstSelectForTab_3, 3);
                        dfd.resolve(data);
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.getSwapUpDownList = function (lstSelectForTab, categoryAtr) {
                    var self = this;
                    if (categoryAtr === 0) {
                        _.forEach(lstSelectForTab, function (value) {
                            _.forEach(self.itemsSwap(), function (itemMaster) {
                                if (value === itemMaster.itemCode) {
                                    self.itemsSwap.remove(itemMaster);
                                    itemMaster.categoryAtrName = "支";
                                    itemMaster.categoryAtr = 0;
                                    self.currentCodeListSwap.push(itemMaster);
                                    return false;
                                }
                            });
                        });
                    }
                    if (categoryAtr === 1) {
                        _.forEach(lstSelectForTab, function (value) {
                            _.forEach(self.itemsSwap1(), function (itemMaster) {
                                if (value === itemMaster.itemCode) {
                                    self.itemsSwap1.remove(itemMaster);
                                    itemMaster.categoryAtrName = "控";
                                    itemMaster.categoryAtr = 1;
                                    self.currentCodeListSwap1.push(itemMaster);
                                    return false;
                                }
                            });
                        });
                    }
                    if (categoryAtr === 3) {
                        _.forEach(lstSelectForTab, function (value) {
                            _.forEach(self.itemsSwap3(), function (itemMaster) {
                                if (value === itemMaster.itemCode) {
                                    self.itemsSwap3.remove(itemMaster);
                                    itemMaster.categoryAtrName = "記";
                                    itemMaster.categoryAtr = 3;
                                    self.currentCodeListSwap3.push(itemMaster);
                                    return false;
                                }
                            });
                        });
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ComparingFormHeader = (function () {
                function ComparingFormHeader(formCode, formName) {
                    this.formCode = formCode;
                    this.formName = formName;
                }
                return ComparingFormHeader;
            }());
            viewmodel.ComparingFormHeader = ComparingFormHeader;
            var ItemMaster = (function () {
                function ItemMaster(itemCode, itemName, categoryAtrName, categoryAtr) {
                    this.itemCode = itemCode;
                    this.itemName = itemName;
                    this.categoryAtrName = categoryAtrName;
                    this.categoryAtr = categoryAtr;
                }
                return ItemMaster;
            }());
            viewmodel.ItemMaster = ItemMaster;
            var ItemTabModel = (function () {
                function ItemTabModel(lstItemMasterForTab_0, lstItemMasterForTab_1, lstItemMasterForTab_3, lstSelectForTab_0, lstSelectForTab_1, lstSelectForTab_3) {
                    this.lstItemMasterForTab_0 = lstItemMasterForTab_0;
                    this.lstItemMasterForTab_1 = lstItemMasterForTab_0;
                    this.lstItemMasterForTab_3 = lstItemMasterForTab_1;
                    this.lstSelectForTab_0 = lstSelectForTab_0;
                    this.lstSelectForTab_1 = lstSelectForTab_1;
                    this.lstSelectForTab_3 = lstSelectForTab_3;
                }
                return ItemTabModel;
            }());
            viewmodel.ItemTabModel = ItemTabModel;
            var InsertUpdateDataModel = (function () {
                function InsertUpdateDataModel(formCode, formName, comparingFormDetailList) {
                    this.formCode = formCode;
                    this.formName = formName;
                    this.comparingFormDetailList = comparingFormDetailList;
                }
                return InsertUpdateDataModel;
            }());
            viewmodel.InsertUpdateDataModel = InsertUpdateDataModel;
            var ComparingFormDetail = (function () {
                function ComparingFormDetail(itemCode, categoryAtr, dispOrder) {
                    this.itemCode = itemCode;
                    this.categoryAtr = categoryAtr;
                    this.dispOrder = dispOrder;
                }
                return ComparingFormDetail;
            }());
            viewmodel.ComparingFormDetail = ComparingFormDetail;
            var DeleteFormHeaderModel = (function () {
                function DeleteFormHeaderModel(formCode) {
                    this.formCode = formCode;
                }
                return DeleteFormHeaderModel;
            }());
            viewmodel.DeleteFormHeaderModel = DeleteFormHeaderModel;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qpp008.c || (qpp008.c = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=qpp008.c.vm.js.map