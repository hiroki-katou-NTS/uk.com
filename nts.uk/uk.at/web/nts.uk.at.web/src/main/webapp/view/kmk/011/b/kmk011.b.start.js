var kmk011;
(function (kmk011) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModel = new b.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
                $("#inpCode").focus();
            });
        });
    })(b = kmk011.b || (kmk011.b = {}));
})(kmk011 || (kmk011 = {}));
//# sourceMappingURL=kmk011.b.start.js.map