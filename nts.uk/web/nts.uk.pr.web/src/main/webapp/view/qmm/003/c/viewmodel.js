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
                    var c;
                    (function (c) {
                        var ScreenModel = (function () {
                            function ScreenModel() {
                                var self = this;
                                self.init();
                                self.singleSelectedCode.subscribe(function (newValue) {
                                    self.labelSubsub(self.findByCode(self.filteredData(), newValue));
                                    console.log(self.labelSubsub());
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
                                self.singleSelectedCode = ko.observable("11");
                                self.labelSubsub = ko.observable(new Node("", "", []));
                                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                            };
                            ScreenModel.prototype.clickButton = function () {
                                var self = this;
                                nts.uk.ui.windows.setShared('singleSelectedCode', self.singleSelectedCode(), true);
                                nts.uk.ui.windows.setShared('labelSubsub', self.labelSubsub(), true);
                                nts.uk.ui.windows.close();
                            };
                            ScreenModel.prototype.cancelButton = function () {
                                nts.uk.ui.windows.close();
                            };
                            ScreenModel.prototype.register = function () {
                                // if()
                                //                         $("#A_INP_002").attr('disabled', 'true');
                                var inputSearch = $("#C_SCH_001").find("input.ntsSearchBox").val();
                                if (inputSearch == "") {
                                    $('#C_SCH_001').ntsError('set', 'inputSearch が入力されていません。');
                                }
                                else {
                                    $('#C_SCH_001').ntsError('clear');
                                }
                                // errror search
                                var error;
                                _.find(this.filteredData(), function (obj) {
                                    if (obj.code !== inputSearch) {
                                        error = true;
                                    }
                                });
                                if (error = true) {
                                    $('#C_SCH_001').ntsError('set', '対象データがありません。');
                                }
                                else {
                                    $('#C_SCH_001').ntsError('clear');
                                }
                            };
                            return ScreenModel;
                        }());
                        c.ScreenModel = ScreenModel;
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
                        c.Node = Node;
                    })(c = qmm003.c || (qmm003.c = {}));
                })(qmm003 = view.qmm003 || (view.qmm003 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
