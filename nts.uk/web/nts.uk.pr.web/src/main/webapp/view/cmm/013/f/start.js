var cmm013;
(function (cmm013) {
    var f;
    (function (f) {
        var viewmodel;
        (function (viewmodel) {
            var a;
            (function (a) {
                var start;
                (function (start) {
                    __viewContext.ready(function () {
                        var screenModel = new viewmodel.ScreenModel();
                        screenModel.startPage().done(function () {
                            __viewContext.bind(screenModel);
                        });
                    });
                })(start = a.start || (a.start = {}));
            })(a = viewmodel.a || (viewmodel.a = {}));
        })(viewmodel = f.viewmodel || (f.viewmodel = {}));
    })(f = cmm013.f || (cmm013.f = {}));
})(cmm013 || (cmm013 = {}));
