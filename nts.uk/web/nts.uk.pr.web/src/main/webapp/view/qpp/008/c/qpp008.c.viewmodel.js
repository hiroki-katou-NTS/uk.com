var qpp008;
(function (qpp008) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.allowEditCode = ko.observable(false);
                    this.isUpdate = ko.observable(true);
                    var self = this;
                    self._initFormHeader();
                    self._initSwap();
                    self._initFormDetail();
                    self.cInp002Code = ko.observable(false);
                    self.currentCode.subscribe(function (codeChanged) {
                        if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                            self.currentItem(ko.mapping.toJS(self.getItem(codeChanged)));
                            self.cInp002Code(false);
                            self.allowEditCode(true);
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
                    self.currentItem = ko.observable(new c.service.model.ComparingFormHeader('', ''));
                    self.currentCode = ko.observable();
                };
                ScreenModel.prototype._initSwap = function () {
                    var self = this;
                    self.itemsSwap = ko.observableArray([]);
                    var array = [];
                    var array1 = [];
                    var array2 = [];
                    for (var i_1 = 0; i_1 < 10000; i_1++) {
                        array.push(new ItemModel("testz" + i_1, '基本給', "description"));
                    }
                    self.itemsSwap(array);
                    self.columnsSwap = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 120 }
                    ]);
                    self.currentCodeListSwap = ko.observableArray([]);
                    //swapList2
                    self.itemsSwap2 = ko.observableArray([]);
                    for (var i = 0; i < 10000; i++) {
                        array1.push(new ItemModel("testx" + i, '基本給', "description"));
                    }
                    self.itemsSwap2(array1);
                    self.columnsSwap2 = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 120 }
                    ]);
                    self.currentCodeListSwap2 = ko.observableArray([]);
                    //swapList3
                    self.itemsSwap3 = ko.observableArray([]);
                    for (var i_2 = 0; i_2 < 10000; i_2++) {
                        array2.push(new ItemModel("testy" + i_2, '基本給', "description"));
                    }
                    self.itemsSwap3(array2);
                    self.columnsSwap3 = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 120 }
                    ]);
                    self.currentCodeListSwap3 = ko.observableArray([]);
                    /*TabPanel*/
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
                        var x = [];
                        x = x.concat(self.currentCodeListSwap());
                        x = x.concat(self.currentCodeListSwap2());
                        x = x.concat(self.currentCodeListSwap3());
                        return x;
                    }, self).extend({ deferred: true });
                    self.columns2 = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 120 },
                    ]);
                    self.currentCode2 = ko.observable();
                };
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    self.currentItem(new c.service.model.ComparingFormHeader('', ''));
                    self.currentCode();
                    self.allowEditCode(true);
                    self.cInp002Code(true);
                };
                ScreenModel.prototype.createButtonClick = function () {
                    var self = this;
                    self.refreshLayout();
                };
                ScreenModel.prototype.insertData = function () {
                    var self = this;
                    var newData = ko.toJS(self.currentItem());
                    if (self.cInp002Code()) {
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
                ScreenModel.prototype.findDuplicateSwaps = function (codeNew) {
                    var self = this;
                    var value;
                    var checkItemSwap = _.find(self.items2(), function (item) {
                        return item.code == codeNew;
                    });
                    if (checkItemSwap == undefined) {
                        value = false;
                    }
                    else {
                        value = true;
                    }
                    return value;
                };
                ScreenModel.prototype.addItem = function () {
                    this.items.push(new c.service.model.ComparingFormHeader('9', '基本給'));
                };
                ScreenModel.prototype.removeItem = function () {
                    this.items.shift();
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
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.adDataToItemsList = function (data) {
                    var self = this;
                    self.items([]);
                    _.forEach(data, function (value) {
                        self.items.push(value);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name, description, other1, other2) {
                    this.code = code;
                    this.name = name;
                    this.description = description;
                    this.other1 = other1;
                    this.other2 = other2 || other1;
                }
                return ItemModel;
            }());
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qpp008.c || (qpp008.c = {}));
})(qpp008 || (qpp008 = {}));
