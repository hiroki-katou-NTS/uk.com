// TreeGrid Node
var qmm019;
(function (qmm019) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var NodeTest = (function () {
                function NodeTest(code, name, children) {
                    this.code = code;
                    this.name = name;
                    this.childs = children;
                }
                NodeTest.prototype.noteText = function () {
                    return this.code + ' ' + this.name;
                };
                return NodeTest;
            }());
            viewmodel.NodeTest = NodeTest;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm019.a || (qmm019.a = {}));
})(qmm019 || (qmm019 = {}));
