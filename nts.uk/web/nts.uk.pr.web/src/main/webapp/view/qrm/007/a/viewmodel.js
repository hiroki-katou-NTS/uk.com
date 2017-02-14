var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.retirementPayItemList = ko.observableArray([new RetirementPayItemModel(null, null, null, null)]);
                    self.currentCode = ko.observable();
                    self.currentItem = ko.observable(new RetirementPayItemModel(null, null, null, null));
                    self.currentCode.subscribe(function (newValue) {
                        self.currentItem(RetirementPayItemModel.converToObject(_.find(self.retirementPayItemList(), function (item) { return item.itemCode == newValue; })));
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qrm007.a.service.getRetirementPayItemList().done(function (data) {
                        self.retirementPayItemList.removeAll();
                        if (data.length) {
                            data.forEach(function (dataItem) {
                                self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                            });
                            self.currentCode(1);
                            self.currentItem(RetirementPayItemModel.converToObject(_.first(self.retirementPayItemList())));
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        //self.retirementPayItemList.removeAll();
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.updateRetirementPayItemList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var command = ko.mapping.toJS(self.currentItem());
                    console.log(self.currentItem());
                    qrm007.a.service.updateRetirementPayItem(command).done(function (data) {
                        qrm007.a.service.getRetirementPayItemList().done(function (data) {
                            self.retirementPayItemList.removeAll();
                            if (data.length) {
                                data.forEach(function (dataItem) {
                                    self.retirementPayItemList.push(ko.mapping.toJS(new RetirementPayItemModel(dataItem.itemCode, dataItem.itemName, dataItem.printName, dataItem.memo)));
                                });
                            }
                            dfd.resolve();
                        }).fail(function (res) {
                            //self.retirementPayItemList.removeAll();
                        });
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var RetirementPayItemModel = (function () {
                function RetirementPayItemModel(code, name, printName, memo) {
                    var self = this;
                    self.itemCode = ko.observable(code);
                    self.itemName = ko.observable(name);
                    self.printName = ko.observable(printName);
                    self.memo = ko.observable(memo);
                }
                RetirementPayItemModel.converToObject = function (object) {
                    return new RetirementPayItemModel(object.itemCode, object.itemName, object.printName, object.memo);
                };
                return RetirementPayItemModel;
            }());
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
