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
                    var d;
                    (function (d) {
                        var ScreenModel = (function () {
                            function ScreenModel() {
                                this.editMode = true;
                                var self = this;
                                self.init();
                                self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.items(), "childs"));
                                self.selectedCode.subscribe(function (newValue) {
                                    self.arrayCode(newValue);
                                });
                            }
                            ScreenModel.prototype.findByName = function (items) {
                                var self = this;
                                var node;
                                _.find(items, function (obj) {
                                    if (!node) {
                                        if (obj.name == self.curentNode().name) {
                                            node = obj;
                                        }
                                    }
                                });
                                return node;
                            };
                            ;
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
                                for (var i = 0; i < _.size(self.arrayCode()); i++) {
                                    self.removeNodeByCode(self.items(), self.arrayCode()[i]);
                                    self.item1s(self.items());
                                    self.items([]);
                                    self.items(self.item1s());
                                }
                                nts.uk.ui.windows.setShared("items", self.items(), true);
                                nts.uk.ui.windows.close();
                            };
                            ScreenModel.prototype.cancelButton = function () {
                                nts.uk.ui.windows.close();
                            };
                            ScreenModel.prototype.removeNodeByCode = function (items, newValue) {
                                var self = this;
                                _.remove(items, function (obj) {
                                    if (obj.code == newValue) {
                                        return obj.code == newValue;
                                    }
                                    else {
                                        return self.removeNodeByCode(obj.childs, newValue);
                                    }
                                });
                            };
                            ;
                            ScreenModel.prototype.removeData1 = function (items) {
                                _.remove(items, function (value) {
                                    return _.size(value) == 1;
                                });
                            };
                            ScreenModel.prototype.removeData2 = function (items) {
                                _.remove(items, function (value) {
                                    return _.size(value) == 2;
                                });
                            };
                            ScreenModel.prototype.removeData3 = function (items) {
                                _.remove(items, function (value) {
                                    return _.size(value) == 3;
                                });
                            };
                            ScreenModel.prototype.delete = function (items) {
                                var self = this;
                                _.each(self.filteredData(), function (obj) {
                                    if (_.size(obj.code) == 1) {
                                        self.removeData1(self.filteredData());
                                    }
                                });
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
                                    new Node('5', '東海', [])
                                ]);
                                self.item1s = ko.observableArray([]);
                                self.singleSelectedCode = ko.observable("11");
                                self.curentNode = ko.observable(new Node("", "", []));
                                self.index = 0;
                                self.selectedCode = ko.observableArray([]);
                                self.arrayCode = ko.observableArray([]);
                            };
                            ;
                            ScreenModel.prototype.register = function () {
                                var inputSearch = $("#D_SCH_001").find("input.ntsSearchBox").val();
                                if (inputSearch == "") {
                                    $('#D_SCH_001').ntsError('set', 'inputSearch が入力されていません。');
                                }
                                else {
                                    $('#D_SCH_001').ntsError('clear');
                                }
                                var error;
                                _.find(this.filteredData(), function (obj) {
                                    if (obj.code !== inputSearch) {
                                        error = true;
                                    }
                                });
                                if (error = true) {
                                    $('#D_SCH_001').ntsError('set', '対象データがありません。');
                                }
                                else {
                                    $('#D_SCH_001').ntsError('clear');
                                }
                            };
                            return ScreenModel;
                        }());
                        d.ScreenModel = ScreenModel;
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
                        d.Node = Node;
                    })(d = qmm003.d || (qmm003.d = {}));
                })(qmm003 = view.qmm003 || (view.qmm003 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map