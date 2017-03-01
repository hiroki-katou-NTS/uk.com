var cmm015;
(function (cmm015) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(a = cmm015.a || (cmm015.a = {}));
})(cmm015 || (cmm015 = {}));
