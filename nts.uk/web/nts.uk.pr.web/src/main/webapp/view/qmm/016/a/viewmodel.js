var qmm016;
(function (qmm016) {
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
                    self.custom = 'Random' + new Date().getTime();
                }
                return Node;
            }());
            viewmodel.Node = Node;
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.dataSource = ko.observableArray([new Node('0001', 'あああああ', []),
                        new Node('0002', 'いいいいい', []),
                        new Node('0003', 'おおおおお', [new Node('0004', 'Washington US', []), new Node('0005', 'Newyork US', [])])]);
                    self.tabs = ko.observableArray([
                        { id: 'tab-1', title: '基本情報', content: '#tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                        { id: 'tab-2', title: '賃金テーブルの情報', content: '#tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
                    ]);
                    self.selectedTab = ko.observable('tab-1');
                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "childs"));
                    self.singleSelectedCode = ko.observable(null);
                    self.selectedCodes = ko.observableArray([]);
                    self.index = 0;
                    self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                    self.enabled = ko.observable(false);
                    self.selected = ko.observable('');
                    self.sel_002 = ko.observableArray([
                        { code: '1', name: 'å¯¾è±¡' },
                        { code: '2', name: 'å¯¾è±¡å¤–' }
                    ]);
                }
                ScreenModel.prototype.goToB = function () {
                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { dialogClass: 'no-close', height: 380, width: 400 }).setTitle('å±¥æ­´ã�®è¿½åŠ ');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm016.a || (qmm016.a = {}));
})(qmm016 || (qmm016 = {}));
