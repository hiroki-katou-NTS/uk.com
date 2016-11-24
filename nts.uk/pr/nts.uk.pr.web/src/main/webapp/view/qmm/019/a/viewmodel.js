// TreeGrid Node
var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                //            self.itemList = ko.observableArray([
                //                new NodeTest('0001', 'サービス部', [
                //                    new NodeTest('0001-1', 'サービス部1', []),
                //                    new NodeTest('0001-2', 'サービス部2', []),
                //                    new NodeTest('0001-3', 'サービス部3', []),
                //                    new NodeTest('0001-4', 'サービス部4', [])]),
                //                new NodeTest('0002', '開発部', [])
                //            ]);
                self.itemList = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.layouts = ko.observableArray([]);
                self.layoutsMax = ko.observableArray([]);
            }
            // start function
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                a.service.getAllLayout().done(function (layouts) {
                    self.layouts(layouts);
                    a.service.getLayoutsWithMaxStartYm().done(function (layoutsMax) {
                        self.layoutsMax(layoutsMax);
                        self.buildTreeDataSource();
                        dfd.resolve();
                    });
                }).fail(function (res) {
                    // Alert message
                    alert(res);
                });
                // Return.
                return dfd.promise();
            };
            ScreenModel.prototype.buildTreeDataSource = function () {
                var self = this;
                self.itemList.removeAll();
                _.forEach(self.layoutsMax(), function (layoutMax) {
                    var children = [];
                    var childLayouts = _.filter(self.layouts(), function (layout) {
                        return layout.stmtCode === layoutMax.stmtCode;
                    });
                    _.forEach(childLayouts, function (child) {
                        children.push(new NodeTest(child.stmtCode + 1, child.stmtName, [], child.startYm + " ~ " + child.endYM));
                    });
                    self.itemList.push(new NodeTest(layoutMax.stmtCode, layoutMax.stmtName, children, layoutMax.stmtCode + " " + layoutMax.stmtName));
                });
            };
            return ScreenModel;
        }());
        a.ScreenModel = ScreenModel;
        var NodeTest = (function () {
            function NodeTest(code, name, children, nodeText) {
                this.code = code;
                this.name = name;
                this.childs = children;
                this.nodeText = nodeText;
            }
            return NodeTest;
        }());
        a.NodeTest = NodeTest;
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
