import kaf002 = nts.uk.at.view.kaf002;
//import kaf009 = nts.uk.at.view.kaf009;
import model = nts.uk.at.view.kaf000.b.viewmodel.model;

__viewContext.ready(function() {
    var listAppMeta: Array<model.ApplicationMetadata> = __viewContext.transferred.value.listAppMeta;
    var currentApp: model.ApplicationMetadata = __viewContext.transferred.value.currentApp;
    var screenModel: any = {};
    if (currentApp.appType == 7) {
        screenModel = new kaf002.c.viewmodel.ScreenModel(listAppMeta, currentApp);
    } else if (currentApp.appType == 9) {
        screenModel = new nts.uk.at.view.kaf004.e.viewmodel.ScreenModel(listAppMeta, currentApp);
    } else if (currentApp.appType == 4) {
        screenModel = new nts.uk.at.view.kaf009.b.viewmodel.ScreenModel(listAppMeta, currentApp);     
    } else if (currentApp.appType == 0) {
        screenModel = new nts.uk.at.view.kaf005.b.viewmodel.ScreenModel(listAppMeta, currentApp);     
    } else if (currentApp.appType == 2) {
        screenModel = new nts.uk.at.view.kaf007.b.viewmodel.ScreenModel(listAppMeta, currentApp);     
    }
    
    screenModel.start(moment.utc().format("YYYY/MM/DD")).done(function() {
        __viewContext.bind(screenModel);
    });
});





