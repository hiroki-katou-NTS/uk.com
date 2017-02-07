var qmm002;
(function (qmm002) {
    var c;
    (function (c) {
        __viewContext.ready(function () {
            var screenModel = new c.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(c = qmm002.c || (qmm002.c = {}));
})(qmm002 || (qmm002 = {}));
