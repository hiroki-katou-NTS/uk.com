var qmm034;
(function (qmm034) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                nts.uk.ui.confirmSave(screenModel.dirtyObject);
                __viewContext.bind(screenModel);
                screenModel.dirtyObject.reset();
            });
        });
    })(a = qmm034.a || (qmm034.a = {}));
})(qmm034 || (qmm034 = {}));
//# sourceMappingURL=start.js.map