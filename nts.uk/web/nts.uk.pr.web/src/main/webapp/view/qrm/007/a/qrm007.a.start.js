var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            nts.uk.ui.confirmSave(screenModel.dirty);
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
//# sourceMappingURL=start.js.map