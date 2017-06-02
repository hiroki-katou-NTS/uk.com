var cmm011;
(function (cmm011) {
    var d;
    (function (d) {
        __viewContext.ready(function () {
            var screenModel = new d.viewmodel.ScreenModel();
            screenModel.start().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(d = cmm011.d || (cmm011.d = {}));
})(cmm011 || (cmm011 = {}));
//# sourceMappingURL=cmm011.d.start.js.map