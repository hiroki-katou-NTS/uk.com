/// <reference path="../qmm005.ts"/>
__viewContext.primitiveValueConstraints["processingYear"] = {
    min: 1970,
    max: 9999,
    required: true,
    charType: "Numeric",
    valueType: "Numeric"
};
var qmm005;
(function (qmm005) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            __viewContext["viewModel"] = new b.ViewModel();
            __viewContext.bind(__viewContext["viewModel"]);
        });
    })(b = qmm005.b || (qmm005.b = {}));
})(qmm005 || (qmm005 = {}));
//# sourceMappingURL=qmm005.b.start.js.map