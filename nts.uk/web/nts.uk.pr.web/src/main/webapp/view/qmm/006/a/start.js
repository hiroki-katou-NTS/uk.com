var qmm006;
(function (qmm006) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new qmm006.a.viewmodel.ScreenModel();
            nts.uk.ui.confirmSave(screenModel.dirty);
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(a = qmm006.a || (qmm006.a = {}));
})(qmm006 || (qmm006 = {}));
