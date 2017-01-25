var qmm012;
(function (qmm012) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModel = new b.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            screenModel.start();
        });
    })(b = qmm012.b || (qmm012.b = {}));
})(qmm012 || (qmm012 = {}));
function OpenModeSubWindow(url, option) {
    nts.uk.ui.windows.sub.modal(url, option);
}
function closeDialog() {
    nts.uk.ui.windows.close();
}
