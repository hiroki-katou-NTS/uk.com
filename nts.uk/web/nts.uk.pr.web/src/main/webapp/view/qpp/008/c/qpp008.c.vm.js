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
                        if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                            self.currentItem(self.mappingFromJS(self.getItem(codeChanged)));
                            self.allowEditCode(false);
                            self.getComparingFormForTab(codeChanged);
                        }
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
                };
                ScreenModel.prototype._initSwap = function () {
                    var self = this;
                    self.itemsSwap = ko.observableArray([]);
                    var array = new Array();
                    var array1 = new Array();
                    var array2 = new Array();
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
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    c.service.getListComparingFormHeader().done(function (data) {
                        self.adDataToItemsList(data);
                        self.currentItem(_.first(self.items()));
                        self.currentCode(self.currentItem().formCode);
                        dfd.resolve(data);
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    self.currentItem(self.mappingFromJS(new ComparingFormHeader('', '')));
                    self.currentCode();
                    self.allowEditCode(true);
                    self.isUpdate(false);
                    self.getComparingFormForTab(null);
                };
                ScreenModel.prototype.createButtonClick = function () {
                    var self = this;
                    self.isUpdate(false);
                    self.refreshLayout();
                };
                ScreenModel.prototype.insertUpdateData = function () {
                    var self = this;
                    var newData = ko.toJS(self.currentItem());
                    if (self.isUpdate()) {
                        self.items.push(newData);
                    }
                    else {
                        var updateIndex = _.findIndex(self.items(), function (item) { return item.formCode == newData.code; });
                        self.items.splice(updateIndex, 1, newData);
                    }
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var newDelData = ko.toJS(self.currentItem());
                    var deleData = _.findIndex(self.items(), function (item) { return item.formCode == newDelData.code; });
                    self.items.splice(deleData, 1);
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
                    c.service.getComparingFormForTab(formCode).done(function (data) {
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
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.mappingItemMasterToFormDetail = function (selectList, categoryAtr) {
                    return _.map(selectList, function (item) {
                        var newMapping = new ItemMaster(item.itemCode, item.itemName, "支");
                        if (categoryAtr === 1) {
                            newMapping = new ItemMaster(item.itemCode, item.itemName, "控");
                        }
                        else if (categoryAtr === 3) {
                            newMapping = new ItemMaster(item.itemCode, item.itemName, "記");
                        }
                        return newMapping;
                    });
                };
                ScreenModel.prototype.adDataToItemsList = function (data) {
                    var self = this;
                    self.items([]);
                    _.forEach(data, function (value) {
                        self.items.push(value);
                    });
                };
                ScreenModel.prototype.getSwapUpDownList = function (lstSelectForTab, categoryAtr) {
                    var self = this;
                    if (categoryAtr === 0) {
                        _.forEach(lstSelectForTab, function (value) {
                            self.itemsSwap.remove(function (itemMaster) {
                                if (value === itemMaster.itemCode) {
                                    self.currentCodeListSwap.push(itemMaster);
                                }
                                return value === itemMaster.itemCode;
                            });
                        });
                    }
                    if (categoryAtr === 1) {
                        _.forEach(lstSelectForTab, function (value) {
                            self.itemsSwap1.remove(function (itemMaster) {
                                if (value === itemMaster.itemCode) {
                                    self.currentCodeListSwap1.push(itemMaster);
                                }
                                return value === itemMaster.itemCode;
                            });
                        });
                    }
                    if (categoryAtr === 3) {
                        _.forEach(lstSelectForTab, function (value) {
                            self.itemsSwap3.remove(function (itemMaster) {
                                if (value === itemMaster.itemCode) {
                                    self.currentCodeListSwap3.push(itemMaster);
                                }
                                return value === itemMaster.itemCode;
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
                function ItemMaster(itemCode, itemName, categoryAtrName) {
                    this.itemCode = itemCode;
                    this.itemName = itemName;
                    this.categoryAtrName = categoryAtrName;
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
