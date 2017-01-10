var qrm001;
(function (qrm001) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModel = new qrm001.b.viewmodel.ScreenModel();
            screenModel.startPage().done(function () {
                __viewContext.bind(screenModel);
            });
        });
    })(b = qrm001.b || (qrm001.b = {}));
})(qrm001 || (qrm001 = {}));
