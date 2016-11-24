// TreeGrid Node
var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var ScreenModel = (function () {
            function ScreenModel() {
                var self = this;
                self.itemList = ko.observableArray([
                    new NodeTest('0001', 'サービス部', [
                        new NodeTest('0001-1', 'サービス部1', []),
                        new NodeTest('0001-2', 'サービス部2', []),
                        new NodeTest('0001-3', 'サービス部3', []),
                        new NodeTest('0001-4', 'サービス部4', [])]),
                    new NodeTest('0002', '開発部', [])
                ]);
                self.singleSelectedCode = ko.observable(null);
            }
            // start function
            ScreenModel.prototype.start = function () {
                var self = this;
                var dfd = $.Deferred();
                //            service.getAllLayout("1").done(function(layouts: Array<service.model.LayoutMasterDto>) {
                //                self.layouts(layouts);
                dfd.resolve(null);
                //            }).fail(function(res) {
                //                // Alert message
                //                alert(res);
                //            });
                // Return.
                return dfd.promise();
            };
            return ScreenModel;
        }());
        a.ScreenModel = ScreenModel;
        var NodeTest = (function () {
            function NodeTest(code, name, children) {
                this.code = code;
                this.name = name;
                this.childs = children;
                this.nodeText = this.code + ' ' + this.name;
            }
            return NodeTest;
        }());
        a.NodeTest = NodeTest;
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
