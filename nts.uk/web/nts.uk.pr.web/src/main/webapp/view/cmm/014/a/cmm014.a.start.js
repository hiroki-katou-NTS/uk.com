var cmm014;
(function (cmm014) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            nts.uk.ui.confirmSave(screenModel.dirty_1);
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(a = cmm014.a || (cmm014.a = {}));
})(cmm014 || (cmm014 = {}));
