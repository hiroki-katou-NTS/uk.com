var qet001;
(function (qet001) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModel = new b.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
