var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new qmm018.a.viewmodel.ScreenModel();
            nts.uk.ui.confirmSave(screenModel.dirty);
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
//# sourceMappingURL=qmm018.a.start.js.map