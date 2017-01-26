module qmm012.b {
    __viewContext.ready(function() {
        let screenModelB = new viewmodel.ScreenModel();
        let screenModelC = new c.viewmodel.ScreenModel();
        let screenModelD = new d.viewmodel.ScreenModel();
        let screenModelE = new e.viewmodel.ScreenModel();
        let screenModelF = new f.viewmodel.ScreenModel();
        let screenModel = {
            screenB: screenModelB,
            screenC: screenModelC,
            screenD: screenModelD,
            screenE: screenModelE,
            screenF: screenModelF
        }
        __viewContext.bind(screenModel);
        screenModel.screenB.start();
        screenModel.screenC.start();
        screenModel.screenD.start();
        screenModel.screenE.start();
        screenModel.screenF.start();
    });
}
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
