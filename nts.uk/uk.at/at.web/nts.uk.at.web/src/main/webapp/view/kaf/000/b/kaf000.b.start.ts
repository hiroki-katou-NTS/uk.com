import kaf002 = nts.uk.at.view.kaf002;
__viewContext.ready(function() {
    var appType: number = Number(__viewContext.transferred.value.appType);
    var screenModel: any = {};
    if (appType == 7) {
        screenModel = new nts.uk.at.view.kaf002.cm.viewmodel.ScreenModel(appType);
    } else if (appType == 9) {
        screenModel = new nts.uk.at.view.kaf004.e.viewmodel.ScreenModel(appType);
    }

    screenModel.start().done(function() {
        __viewContext.bind(screenModel);
    });
});

