var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var start;
        (function (start) {
            __viewContext.ready(function () {
                var screenModel = new qmm003.a.viewmodel.ScreenModel();
                screenModel.start().done(function () {
                    __viewContext.bind(screenModel);
                });
            });
        })(start = a.start || (a.start = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
