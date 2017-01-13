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
                                    console.log(nts.uk.ui.windows.getShared('listItem'));
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    b.service.getBankList().done(function (result) {
                                        self.lst_001(result);
                                    });
                                };
                                ScreenModel.prototype.excute = function () {
                                    var self = this;
                                    nts.uk.ui.windows.setShared("data", self.selectedCodes());
                                    nts.uk.ui.windows.close();
                                };
                                ScreenModel.prototype.close = function () {
                                    var self = this;
                                    nts.uk.ui.windows.close();
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
