var cmm013;
(function (cmm013) {
    var test;
    (function (test) {
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
        })(viewmodel = test.viewmodel || (test.viewmodel = {}));
    })(test = cmm013.test || (cmm013.test = {}));
})(cmm013 || (cmm013 = {}));
