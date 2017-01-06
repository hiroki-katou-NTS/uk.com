var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm002_1;
                (function (qmm002_1) {
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.lst_001 = ko.observableArray([]);
                                    self.filteredData = ko.observableArray([]);
                                    self.singleSelectedCode = ko.observable(null);
                                    self.selectedCodes = ko.observableArray([]);
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    a.service.getBankList().done(function (result) {
                                        self.lst_001(result);
                                        self.filteredData(nts.uk.util.flatArray(result, "childs"));
                                    });
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            var BankInfo = (function () {
                                function BankInfo(code, name, nameKata, memo) {
                                    var self = this;
                                    self.code = code;
                                    self.name = name;
                                    self.nameKata = nameKata;
                                    self.memo = memo;
                                }
                                return BankInfo;
                            }());
                            viewmodel.BankInfo = BankInfo;
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
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm002_1.a || (qmm002_1.a = {}));
                })(qmm002_1 = view.qmm002_1 || (view.qmm002_1 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
