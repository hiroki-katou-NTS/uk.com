var qmm006;
(function (qmm006) {
    var c;
    (function (c) {
        __viewContext.ready(function () {
            var screenModel = new qmm006.c.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(c = qmm006.c || (qmm006.c = {}));
})(qmm006 || (qmm006 = {}));
