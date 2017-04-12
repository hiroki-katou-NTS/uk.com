var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var p;
                    (function (p) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel(data) {
                                    var self = this;
                                    self.subject = "基準金額に設定する、" + data.subject + "を";
                                    self.items = ko.observableArray(data.itemList);
                                    self.columns = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 50 },
                                        { headerText: '名称', prop: 'name', width: 145 }
                                    ]);
                                    self.currentCodeList = ko.observableArray(data.selectedItems);
                                }
                                ScreenModel.prototype.closeAndReturnData = function () {
                                    var self = this;
                                    var baseAmountListItem = self.currentCodeList();
                                    nts.uk.ui.windows.setShared('baseAmountListItem', baseAmountListItem);
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.closeDialog = function () {
                                    nts.uk.ui.windows.close();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = p.viewmodel || (p.viewmodel = {}));
                        var ItemModel = (function () {
                            function ItemModel(code, name) {
                                this.code = code;
                                this.name = name;
                            }
                            return ItemModel;
                        }());
                    })(p = qmm017.p || (qmm017.p = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
