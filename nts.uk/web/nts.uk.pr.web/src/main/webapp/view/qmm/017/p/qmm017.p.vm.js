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
                            class ScreenModel {
                                constructor(data) {
                                    var self = this;
                                    self.subject = "基準金額に設定する、" + data.subject + "を";
                                    self.items = ko.observableArray(data.itemList);
                                    self.columns = ko.observableArray([
                                        { headerText: 'コード', prop: 'code', width: 50 },
                                        { headerText: '名称', prop: 'name', width: 145 }
                                    ]);
                                    self.currentCodeList = ko.observableArray(data.selectedItems);
                                }
                                closeAndReturnData() {
                                    var self = this;
                                    let baseAmountListItem = self.currentCodeList();
                                    nts.uk.ui.windows.setShared('baseAmountListItem', baseAmountListItem);
                                    nts.uk.ui.windows.close();
                                }
                                closeDialog() {
                                    nts.uk.ui.windows.close();
                                }
                            }
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = p.viewmodel || (p.viewmodel = {}));
                        class ItemModel {
                            constructor(code, name) {
                                this.code = code;
                                this.name = name;
                            }
                        }
                    })(p = qmm017.p || (qmm017.p = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
