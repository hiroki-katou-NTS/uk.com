var cmm009;
(function (cmm009) {
    var d;
    (function (d) {
        __viewContext.ready(function () {
            var screenModel = new d.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(d = cmm009.d || (cmm009.d = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=cmm009.d.start.js.map