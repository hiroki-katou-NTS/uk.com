var qpp008;
(function (qpp008) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    /*SwapList*/
                    self.itemsSwap = ko.observableArray([]);
                    var array = [];
                    for (var i = 0; i < 10000; i++) {
                        array.push(new ItemModel("test" + i, '基本給', "description"));
                    }
                    self.itemsSwap(array);
                    self.columnsSwap = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 80 },
                        { headerText: '名称', prop: 'name', width: 120 }
                    ]);
                    this.currentCodeListSwap = ko.observableArray([]);
                    /*TabPanel*/
                    self.tabs = ko.observableArray([
                        { id: 'tab-1', title: 'Tab Title 1', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-2', title: 'Tab Title 2', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-3', title: 'Tab Title 3', content: '.tab-content-3', enable: ko.observable(true), visible: ko.observable(true) }
                    ]);
                    self.selectedTab = ko.observable('tab-2');
                    /*gridList*/
                    self.items = ko.observableArray([]);
                    var str = ['a0', 'b0', 'c0', 'd0'];
                    for (var j = 0; j < 4; j++) {
                        for (var i = 1; i < 51; i++) {
                            var code = i < 10 ? str[j] + '0' + i : str[j] + i;
                            this.items.push(new ItemModel(code, code, code, code));
                        }
                    }
                    self.columns = ko.observableArray([
                        { headerText: 'コード', prop: 'code', width: 30 },
                        { headerText: '名称', prop: 'name', width: 90 },
                    ]);
                    //get event when hover on table by subcribe
                    self.currentCode = ko.observable();
                    self.currentItem = ko.observable(ko.mapping.fromJS(_.first(self.items())));
                    self.currentCode.subscribe(function (codeChanged) {
                        self.currentItem(ko.mapping.fromJS(self.getItem(codeChanged)));
                    });
                    /*TextEditer*/
                    self.enableC_INP_002 = ko.observable(false);
                }
                ScreenModel.prototype.getItem = function (codeNew) {
                    var self = this;
                    var currentItem = _.find(self.items(), function (item) {
                        return item.code === codeNew;
                    });
                    return currentItem;
                };
                ScreenModel.prototype.addItem = function () {
                    this.items.push(new ItemModel('999', '基本給', "description 1", "other1"));
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
