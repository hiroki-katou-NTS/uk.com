var cmm013;
(function (cmm013) {
    var c;
    (function (c) {
        __viewContext.ready(function () {
            var screenModel = new c.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(c = cmm013.c || (cmm013.c = {}));
})(cmm013 || (cmm013 = {}));
