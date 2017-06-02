var kml001;
(function (kml001) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModel = new b.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
                if (screenModel.allUse() != 0) {
                    for (var i = 0; i < 10; i++) {
                        if (screenModel.premiumItemList()[i].useAtr() != 0) {
                            $('.premiumName:eq(' + i + ')').focus();
                            break;
                        }
                    }
                }
            });
        });
    })(b = kml001.b || (kml001.b = {}));
})(kml001 || (kml001 = {}));
//# sourceMappingURL=kml001.b.start.js.map