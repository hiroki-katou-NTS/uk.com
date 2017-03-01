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
                    var b;
                    (function (b) {
                        var ScreenModel = (function () {
                            function ScreenModel() {
                                var self = this;
                                self.init();
                                self.singleSelectedCode.subscribe(function (newValue) {
                                    self.curentNode(self.findByCode(self.filteredData(), newValue));
                                });
                            }
                            ScreenModel.prototype.findByCode = function (items, newValue) {
                                var self = this;
                                var _node;
                                _.find(items, function (_obj) {
                                    if (!_node) {
                                        if (_obj.code == newValue) {
                                            _node = _obj;
                                        }
                                    }
                                });
                                return _node;
                            };
                            ;
                            ScreenModel.prototype.clickButton = function () {
                                var self = this;
                                nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode(), true);
                                nts.uk.ui.windows.setShared('curentNode', self.curentNode(), true);
                                nts.uk.ui.windows.close();
                            };
                            ScreenModel.prototype.cancelButton = function () {
                                nts.uk.ui.windows.close();
                            };
                            ScreenModel.prototype.init = function () {
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
                                    new Node('5', '東海', []),
                                    new Node('6', '東海', [])
                                ]);
                                self.singleSelectedCode = ko.observable(null);
                                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                                self.curentNode = ko.observable(new Node("", "", []));
                            };
                            ScreenModel.prototype.register = function () {
                                // if()
                                //                         $("#A_INP_002").attr('disabled', 'true');
                                var inputSearch = $("#B_SCH_001").find("input.ntsSearchBox").val();
                                if (inputSearch == "") {
                                    $('#B_SCH_001').ntsError('set', 'inputSearch が入力されていません。');
                                }
                                else {
                                    $('#B_SCH_001').ntsError('clear');
                                }
                                // errror search
                                var error;
                                _.find(this.filteredData(), function (obj) {
                                    if (obj.code !== inputSearch) {
                                        error = true;
                                    }
                                });
                                if (error = true) {
                                    $('#B_SCH_001').ntsError('set', '対象データがありません。');
                                }
                                else {
                                    $('#B_SCH_001').ntsError('clear');
                                }
                            };
                            return ScreenModel;
                        }());
                        b.ScreenModel = ScreenModel;
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
                        b.Node = Node;
                    })(b = qmm003.b || (qmm003.b = {}));
                })(qmm003 = view.qmm003 || (view.qmm003 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
;
