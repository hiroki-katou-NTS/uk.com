var qmm002;
(function (qmm002) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
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
                    self.singleSelectedCode = ko.observable();
                    self.selectedCodes = ko.observableArray([]);
                    self.dataSource2 = ko.observableArray([new Node('0001', 'Hanoi Vietnam', []),
                        new Node('0003', 'Bangkok Thailand', []),
                        new Node('0004', 'Tokyo Japan', []),
                        new Node('0005', 'Jakarta Indonesia', []),
                        new Node('0002', 'Seoul Korea', []),
                        new Node('0006', 'Paris France', []),
                        new Node('0007', 'United States', [new Node('0008', 'Washington US', []), new Node('0009', 'Newyork US', [])]),
                        new Node('0010', 'Beijing China', []),
                        new Node('0011', 'London United Kingdom', []),
                        new Node('0012', '', [])]);
                    self.singleSelectedCode2 = ko.observable(null);
                    self.selectedCodes2 = ko.observableArray([]);
                    self.headers = ko.observableArray(["Item Value Header", "コード", "名称", "口座区分", "口座番号"]);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm002.c.service.getPaymentDateProcessingList().done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
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
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = qmm002.c || (qmm002.c = {}));
})(qmm002 || (qmm002 = {}));
;
