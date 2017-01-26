var qmm012;
(function (qmm012) {
    var b;
    (function (b) {
        __viewContext.ready(function () {
            var screenModelB = new b.viewmodel.ScreenModel();
            var screenModelC = new qmm012.c.viewmodel.ScreenModel();
            var screenModelD = new qmm012.d.viewmodel.ScreenModel();
            var screenModelE = new qmm012.e.viewmodel.ScreenModel();
            var screenModelF = new qmm012.f.viewmodel.ScreenModel();
            var screenModel = {
                screenB: screenModelB,
                screenC: screenModelC,
                screenD: screenModelD,
                screenE: screenModelE,
                screenF: screenModelF
            };
            __viewContext.bind(screenModel);
            screenModel.screenB.start();
            screenModel.screenC.start();
            screenModel.screenD.start();
            screenModel.screenE.start();
            screenModel.screenF.start();
        });
    })(b = qmm012.b || (qmm012.b = {}));
})(qmm012 || (qmm012 = {}));
//module qmm012.c {
//    __viewContext.ready(function() {
//        var screenModel = new viewmodel.ScreenModel();
//        __viewContext.bind(screenModel);
//        screenModel.start();
//    });
//}
function closeDialog() {
    nts.uk.ui.windows.close();
}
