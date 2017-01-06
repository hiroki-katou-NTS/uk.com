var qmm007;
(function (qmm007) {
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
                    self.dataSource = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                        new Node('0003', 'Bangkok Thailand', []),
                        new Node('0004', 'Tokyo Japan', []),
                        new Node('0005', 'Jakarta Indonesia', []),
                        new Node('0002', 'Seoul Korea', []),
                        new Node('0006', 'Paris France', []),
                        new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                        new Node('0010', 'Beijing China', []),
                        new Node('0011', 'London United Kingdom', []),
                        new Node('0012', '', [])]);
                    self.filteredData = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "childs"));
                    self.singleSelectedCode = ko.observable(null);
                    self.selectedCodes = ko.observableArray([]);
                    self.index = 0;
                    self.headers = ko.observableArray(["Item Value Header", "Item Text Header", "Auto generated Field"]);
                    self.enabled = ko.observable(false);
                    self.selected = ko.observable('');
                    self.sel_002 = ko.observableArray([
                        { code: '1', name: '対象' },
                        { code: '2', name: '対象外' }
                    ]);
                }
                ScreenModel.prototype.goToB = function () {
                    nts.uk.ui.windows.sub.modal('/view/qmm/007/b/index.xhtml', { dialogClass: 'no-close', height: 380, width: 400 }).setTitle('履歴の追加');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm007.a || (qmm007.a = {}));
})(qmm007 || (qmm007 = {}));
