var qmm003;
(function (qmm003) {
    var a;
    (function (a) {
        var start;
        (function (start) {
            __viewContext.ready(function () {
                var screenModel = new qmm003.a.viewmodel.ScreenModel();
                screenModel.start(undefined).done(function () {
                    nts.uk.ui.confirmSave(screenModel.dirtyObject);
                    __viewContext.bind(screenModel);
                });
            });
        })(start = a.start || (a.start = {}));
    })(a = qmm003.a || (qmm003.a = {}));
})(qmm003 || (qmm003 = {}));
//# sourceMappingURL=qmm003.a.start.js.map