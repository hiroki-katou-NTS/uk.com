var qmm020;
(function (qmm020) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModel = new qmm020.b.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(b = qmm020.b || (qmm020.b = {}));
})(qmm020 || (qmm020 = {}));
