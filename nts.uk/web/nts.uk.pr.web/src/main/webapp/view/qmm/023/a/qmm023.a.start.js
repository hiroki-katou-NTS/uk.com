var qmm023;
(function (qmm023) {
    var a;
    (function (a) {
        var start;
        (function (start) {
            __viewContext.ready(function () {
                var screenModel = new qmm023.a.viewmodel.ScreenModel();
                screenModel.startPage().done(function () {
                    nts.uk.ui.confirmSave(screenModel.currentTaxDirty);
                    __viewContext.bind(screenModel);
                });
            });
        })(start = a.start || (a.start = {}));
    })(a = qmm023.a || (qmm023.a = {}));
})(qmm023 || (qmm023 = {}));
//# sourceMappingURL=qmm023.a.start.js.map