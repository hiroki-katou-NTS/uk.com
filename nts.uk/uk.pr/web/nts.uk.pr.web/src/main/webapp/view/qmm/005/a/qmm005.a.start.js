/// <reference path="../qmm005.ts"/>
var qmm005;
(function (qmm005) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            __viewContext["viewModel"] = new a.ViewModel();
            __viewContext.bind(__viewContext["viewModel"]);
            nts.uk.ui.confirmSave(__viewContext["viewModel"].dirty);
        });
    })(a = qmm005.a || (qmm005.a = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.a.start.js.map