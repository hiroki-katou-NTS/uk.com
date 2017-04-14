var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm002;
                (function (qmm002) {
                    var b;
                    (function (b) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.lst_001 = ko.observableArray([]);
                                    self.selectedCodes = ko.observableArray([]);
                                    self.selectedCodes.subscribe(function (val) {
                                        console.log(val);
                                    });
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var list = nts.uk.ui.windows.getShared('listItem');
                                    self.lst_001(list);
                                };
                                ScreenModel.prototype.close = function () {
                                    var self = this;
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.btn_001 = function () {
                                    var self = this;
                                    var keyBank = [];
                                    _.forEach(self.selectedCodes(), function (item) {
                                        var code = item.split('-');
                                        var bankCode = code[0];
                                        var branchCode = code[1];
                                        keyBank.push({
                                            bankCode: bankCode,
                                            branchCode: branchCode
                                        });
                                    });
                                    var data = {
                                        bank: keyBank,
                                    };
                                    b.service.removeBank(data).done(function () {
                                        self.close();
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var Node = (function () {
                                function Node(code, name, childs) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.nodeText = self.code + ' ' + self.name;
                                    self.childs = childs;
                                }
                                return Node;
                            }());
                            viewmodel.Node = Node;
                        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
                    })(b = qmm002.b || (qmm002.b = {}));
                })(qmm002 = view.qmm002 || (view.qmm002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map