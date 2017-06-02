var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                nts.uk.ui.confirmSave(screenModel.dirty);
                __viewContext.bind(screenModel);
            });
        });
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
//# sourceMappingURL=cmm014.a.start.js.map