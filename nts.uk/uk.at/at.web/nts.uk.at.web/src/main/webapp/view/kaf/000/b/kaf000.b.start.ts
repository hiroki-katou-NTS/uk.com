import kaf002 = nts.uk.at.view.kaf002;
//import kaf009 = nts.uk.at.view.kaf009;

__viewContext.ready(function() {
    var appType: number = Number(__viewContext.transferred.value.appType);
    var screenModel: any = {};
    //debugger;
    if (appType == 7) {
        screenModel = new kaf002.c.viewmodel.ScreenModel(appType);
    } else if (appType == 9) {
        screenModel = new nts.uk.at.view.kaf004.e.viewmodel.ScreenModel(appType);
    } else if (appType == 4) {
        screenModel = new nts.uk.at.view.kaf009.b.viewmodel.ScreenModel(appType);     
    }
    
    screenModel.start(appType, moment(new Date()).format("YYYY/MM/DD")).done(function() {
        __viewContext.bind(screenModel);
    });
});





