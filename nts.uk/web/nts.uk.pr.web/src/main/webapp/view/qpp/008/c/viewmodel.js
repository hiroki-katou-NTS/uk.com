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
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    /*SwapList*/
                    //swapList1
                    self.itemsSwap = ko.observableArray([]);
                    var array = [];
                    var array1 = [];
                    var array2 = [];
                    for (var i = 0; i < 10000; i++) {
                        array.push(new ItemModel("testz" + i, '基本給', "description"));
                    }
                    self.itemsSwap(array);
                    self.columnsSwap = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 116 }
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
                        { headerText: '名称', prop: 'name', width: 116 }
                    ]);
                    self.currentCodeListSwap2 = ko.observableArray([]);
                    //swapList3
                    self.itemsSwap3 = ko.observableArray([]);
                    for (var i = 0; i < 10000; i++) {
                        array2.push(new ItemModel("testy" + i, '基本給', "description"));
                    }
                    self.itemsSwap3(array2);
                    self.columnsSwap3 = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 116 }
                    ]);
                    self.currentCodeListSwap3 = ko.observableArray([]);
                    /*TextEditer*/
                    self.cInp002Code = ko.observable(false);
                    /*TabPanel*/
                    self.tabs = ko.observableArray([
                        { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                    ]);
                    self.selectedTab = ko.observable('tab-2');
                    /*gridList*/
                    //gridList1
                    self.items = ko.observableArray([]);
                    var str = ['a0', 'b0', 'c0', 'd0'];
                    for (var j = 0; j < 4; j++) {
                        for (var i = 1; i < 51; i++) {
                            var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                            this.items.push(new ItemModel(code, code, code, code));
                        }
                    }
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 115 },
                    ]);
                    //get event when hover on table by subcribe
                    self.currentCode = ko.observable();
                    self.currentItem = ko.observable(ko.mapping.fromJS(_.first(self.items())));
                    self.currentCode.subscribe(function (codeChanged) {
                        self.currentItem(ko.mapping.fromJS(self.getItem(codeChanged)));
                        self.cInp002Code(false);
                    });
                    self.items2 = ko.computed(function () {
                        var x = [];
                        x = x.concat(self.currentCodeListSwap());
                        x = x.concat(self.currentCodeListSwap2());
                        x = x.concat(self.currentCodeListSwap3());
                        return x;
                    }, self).extend({ deferred: true });
                    //            self.currentCodeListSwap2.subscribe(function(swapItems) {
                    //                self.items2.removeAll();
                    //                _.forEach(swapItems, function(value) {
                    //                    console.log(value)
                    //                    if (self.findDuplicateSwaps(value.code) == false) {
                    //                        self.items2.push(value);
                    //                    }
                    //
                    //                });
                    //
                    //            });
                    //gridList2
                    //            self.items2 = ko.observableArray([]);
                    //            var str = ['a0', 'b0', 'c0', 'd0'];
                    //            for (var j = 0; j < 4; j++) {
                    //                for (var i = 1; i < 51; i++) {
                    //                    var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                    //                    this.items2.push(new ItemModel(code, code, code, code));
                    //                }
                    //            }
                    self.columns2 = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 60 },
                        { headerText: '名称', prop: 'name', width: 120 },
                    ]);
                    self.currentCode2 = ko.observable();
                }
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    //self.allowEditCode(true);
                    self.cInp002Code(true);
                    self.currentItem(ko.mapping.fromJS(new ItemModel('', '', '', '', '')));
                    //self.currentCode() = ko.observable();
                    // self.currentItem=ko.observable(new ItemModel('', '', '','',''));
                    //            self.currentCode = ko.observable();
                    //            self.currentItem = ko.observable(ko.mapping.fromJS(_.first(self.items())));
                    //elf.cInp002Code = ko.observable(false);
                };
                ScreenModel.prototype.insertData = function () {
                    var self = this;
                    var newData = ko.toJS(self.currentItem());
                    if (self.cInp002Code()) {
                        self.items.push(newData);
                    }
                    else {
                        var updateIndex = _.findIndex(self.items(), function (item) { return item.code == newData.code; });
                        self.items.splice(updateIndex, 1, newData);
                    }
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var newDelData = ko.toJS(self.currentItem());
                    var deleData = _.findIndex(self.items(), function (item) { return item.code == newDelData.code; });
                    self.items.splice(deleData, 1);
                };
                ScreenModel.prototype.getItem = function (codeNew) {
                    var self = this;
                    var currentItem = _.find(self.items(), function (item) {
                        return item.code === codeNew;
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
                    this.items.push(new ItemModel('999', '基本給', "description 1", "other1"));
                    this.items2.push(new ItemModel('999', '基本給', "description 1", "other1"));
                };
                ScreenModel.prototype.removeItem = function () {
                    this.items.shift();
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    c.service.getPaymentDateProcessingList().done(function (data) {
                        // self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
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
