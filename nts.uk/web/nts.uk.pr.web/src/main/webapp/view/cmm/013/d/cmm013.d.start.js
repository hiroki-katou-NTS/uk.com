var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        __viewContext.ready(function () {
            var screenModel = new d.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
