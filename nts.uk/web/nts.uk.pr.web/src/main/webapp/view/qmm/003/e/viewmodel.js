var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm003;
                (function (qmm003) {
                    var e;
                    (function (e) {
                        var ScreenModel = (function () {
                            function ScreenModel() {
                                this.editMode = true; // true là mode thêm mới, false là mode sửa 
                                var self = this;
                                self.items = ko.observableArray([
                                    new Node('1', '東北', [
                                        new Node('11', '青森県', [
                                            new Node('022012', '青森市', []),
                                            new Node('052019', '秋田市', [])
                                        ]),
                                        new Node('12', '東北', [
                                            new Node('062014', '山形市', [])
                                        ]),
                                        new Node('13', '福島県', [
                                            new Node('062015', '福島市', [])
                                        ])
                                    ]),
                                    new Node('2', '北海道', []),
                                    new Node('3', '東海', []),
                                    new Node('4', '関東', [
                                        new Node('41', '茨城県', [
                                            new Node('062016', '水戸市', []),
                                        ]),
                                        new Node('42', '栃木県', [
                                            new Node('062017', '宇都宮市', [])
                                        ]),
                                        new Node('43', '埼玉県', [
                                            new Node('062019', '川越市', []),
                                            new Node('062020', '熊谷市', []),
                                            new Node('062022', '浦和市', []),
                                        ])
                                    ]),
                                    new Node('5', '東海', [])
                                ]);
                                self.singleSelectedCode = ko.observable("11");
                                self.curentNode = ko.observable(new Node("", "", []));
                                self.index = 0;
                                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                                self.selectedCodes = ko.observableArray([]);
                                //Init();
                                self.singleSelectedCode.subscribe(function (newValue) {
                                });
                            }
                            ScreenModel.prototype.clickButton = function () {
                                nts.uk.ui.windows.close();
                            };
                            ;
                            ScreenModel.prototype.cancelButton = function () {
                                nts.uk.ui.windows.close();
                            };
                            ;
                            return ScreenModel;
                        }());
                        e.ScreenModel = ScreenModel;
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
                        e.Node = Node;
                    })(e = qmm003.e || (qmm003.e = {}));
                })(qmm003 = view.qmm003 || (view.qmm003 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
