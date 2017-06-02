var qmm006;
(function (qmm006) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new qmm006.a.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                nts.uk.ui.confirmSave(screenModel.dirty);
                __viewContext.bind(screenModel);
            });
        });
    })(a = qmm006.a || (qmm006.a = {}));
})(qmm006 || (qmm006 = {}));
//# sourceMappingURL=qmm006.a.start.js.map