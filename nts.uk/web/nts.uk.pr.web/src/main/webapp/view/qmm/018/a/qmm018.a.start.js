var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new qmm018.a.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                nts.uk.ui.confirmSave(screenModel.dirtyObject);
                __viewContext.bind(screenModel);
            });
        });
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
