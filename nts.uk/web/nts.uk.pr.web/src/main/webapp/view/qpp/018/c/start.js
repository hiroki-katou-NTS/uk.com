var qpp018;
(function (qpp018) {
    var c;
    (function (c) {
        __viewContext.ready(function () {
            var screenModel = new c.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(c = qpp018.c || (qpp018.c = {}));
})(qpp018 || (qpp018 = {}));
