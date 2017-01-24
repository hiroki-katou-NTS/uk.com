var qpp007;
(function (qpp007) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(a = qpp007.a || (qpp007.a = {}));
})(qpp007|| (qpp007 = {}));
