var qmm020;
(function (qmm020) {
    var c;
    (function (c) {
        __viewContext.ready(function () {
            var screenModel = new qmm020.c.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(c = qmm020.c || (qmm020.c = {}));
})(qmm020 || (qmm020 = {}));
