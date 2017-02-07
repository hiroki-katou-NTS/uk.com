var cmm009;
(function (cmm009) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModel = new b.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(b = cmm009.b || (cmm009.b = {}));
})(cmm009 || (cmm009 = {}));
