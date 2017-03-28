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
                    var a;
                    (function (a) {
                        var viewmodel;
                        (function (viewmodel) {
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
                            var TreeGrid = (function () {
                                function TreeGrid() {
                                    var self = this;
                                    self.items = ko.observableArray([new Node('0001', 'サービス部', [
                                            new Node('0001-1', 'サービス部1', []),
                                            new Node('0001-2', 'サービス部2', []),
                                            new Node('0001-3', 'サービス部3', [])
                                        ]), new Node('0002', '開発部', [])]);
                                    self.selectedCode = ko.observableArray([]);
                                    self.singleSelectedCode = ko.observable(null);
                                    self.index = 0;
                                }
                                return TreeGrid;
                            }());
                            viewmodel.TreeGrid = TreeGrid;
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.lst_001 = ko.observable(new TreeGrid());
                                    self.tabs = ko.observableArray([
                                        { id: 'tab-1', title: '基本情報', content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                                        { id: 'tab-2', title: '計算式の設定', content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                                    ]);
                                    self.selectedTab = ko.observable('tab-1');
                                }
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
                    })(a = qmm017.a || (qmm017.a = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
