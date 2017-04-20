var cmm009;
(function (cmm009) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(a = cmm009.a || (cmm009.a = {}));
})(cmm009 || (cmm009 = {}));
