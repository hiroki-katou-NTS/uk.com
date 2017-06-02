var kml001;
(function (kml001) {
    var a;
    (function (a) {
        __viewContext.ready(function () {
            var screenModel = new a.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
                if (screenModel.isInsert()) {
                    $("#startDateInput").focus();
                }
                else {
                    $("#memo").focus();
                }
            });
        });
    })(a = kml001.a || (kml001.a = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.a.start.js.map