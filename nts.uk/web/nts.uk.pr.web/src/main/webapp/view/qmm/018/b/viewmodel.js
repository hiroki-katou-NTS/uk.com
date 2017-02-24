var qmm018;
(function (qmm018) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.currentCodeListSwap = ko.observableArray([new ItemModel("0001", "支給1")]);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm018.b.service.qcamt_Item_SEL_3(nts.uk.ui.windows.getShared('categoryAtr')).done(function (data) {
                        if (!data.length) {
                            $("#label-span").ntsError('set', 'ER010');
                        }
                        else {
                            data.forEach(function (dataItem) {
                                self.items().push(new ItemModel(dataItem.itemCode, dataItem.itemAbName));
                            });
                            self.currentCodeListSwap.subscribe(function (value) {
                                if (!value.length)
                                    $("#label-span").ntsError('set', 'ER010');
                                else
                                    $("#label-span").ntsError('clear');
                            });
                        }
                        dfd.resolve();
                        self.currentCodeListSwap(nts.uk.ui.windows.getShared('selectedItemList')());
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.saveData = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('selectedItemList', self.currentCodeListSwap);
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeWindow = function () {
                    nts.uk.ui.windows.setShared('selectedItemList', ko.observableArray([]));
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ItemModel = (function () {
                function ItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ItemModel;
            }());
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm018.b || (qmm018.b = {}));
})(qmm018 || (qmm018 = {}));
