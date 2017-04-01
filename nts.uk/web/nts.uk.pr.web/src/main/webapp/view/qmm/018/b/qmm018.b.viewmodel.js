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
                    self.currentCodeListSwap = ko.observableArray([]);
                    self.unselectedCodeListSwap = ko.observableArray([]);
                    self.oldCurrentCodeListSwap = ko.observableArray([]);
                    self.oldUnselectedCodeListSwap = ko.observableArray([]);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm018.b.service.itemSelect(nts.uk.ui.windows.getShared('categoryAtr')).done(function (data) {
                        if (!data.length) {
                            $("#label-span").ntsError('set', Error.ER010);
                        }
                        else {
                            data.forEach(function (dataItem) {
                                self.items().push(new ItemModel(dataItem.itemCode, dataItem.itemAbName));
                            });
                            self.currentCodeListSwap.subscribe(function (value) {
                                self.unselectedCodeListSwap(_.difference(self.items(), self.currentCodeListSwap()));
                                if (!value.length)
                                    $("#label-span").ntsError('set', Error.ER007);
                                else
                                    $("#label-span").ntsError('clear');
                            });
                        }
                        dfd.resolve();
                        self.currentCodeListSwap(nts.uk.ui.windows.getShared('selectedItemList'));
                        self.oldCurrentCodeListSwap(nts.uk.ui.windows.getShared('selectedItemList'));
                        self.oldUnselectedCodeListSwap(_.differenceBy(self.items(), self.oldCurrentCodeListSwap(), "code"));
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.submitData = function () {
                    // return new data
                    var self = this;
                    nts.uk.ui.windows.setShared('selectedItemList', self.currentCodeListSwap());
                    nts.uk.ui.windows.setShared('unSelectedItemList', self.unselectedCodeListSwap());
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.closeWindow = function () {
                    // return old data
                    var self = this;
                    nts.uk.ui.windows.setShared('selectedItemList', self.oldCurrentCodeListSwap());
                    nts.uk.ui.windows.setShared('unSelectedItemList', self.oldUnselectedCodeListSwap());
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
            var Error;
            (function (Error) {
                Error[Error["ER001"] = "＊が入力されていません。"] = "ER001";
                Error[Error["ER007"] = "＊が選択されていません。"] = "ER007";
                Error[Error["ER010"] = "対象データがありません。"] = "ER010";
            })(Error || (Error = {}));
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm018.b || (qmm018.b = {}));
})(qmm018 || (qmm018 = {}));
//# sourceMappingURL=qmm018.b.viewmodel.js.map