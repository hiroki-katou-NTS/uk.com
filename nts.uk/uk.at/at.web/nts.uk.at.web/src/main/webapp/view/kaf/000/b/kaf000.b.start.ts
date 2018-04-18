import kaf002 = nts.uk.at.view.kaf002;
//import kaf009 = nts.uk.at.view.kaf009;
import model = nts.uk.at.view.kaf000.shr.model;

function initScreen(screenModel: any, listAppMeta: Array<model.ApplicationMetadata>, currentApp: model.ApplicationMetadata): void {
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
    } else if (currentApp.appType == 6) {
        screenModel = new nts.uk.at.view.kaf010.b.viewmodel.ScreenModel(listAppMeta, currentApp);
    } else if (currentApp.appType == 1) {
        screenModel = new nts.uk.at.view.kaf006.b.viewmodel.ScreenModel(listAppMeta, currentApp);
    } else if (currentApp.appType == 10) {
        screenModel = new nts.uk.at.view.kaf011.b.viewmodel.ScreenModel(listAppMeta, currentApp);
    }
    __viewContext['viewModel'] = screenModel;
    screenModel.start(moment.utc().format("YYYY/MM/DD")).done(function() {
        __viewContext.bind(screenModel);
    });
}

__viewContext.ready(function() {
    var listAppMeta: Array<model.ApplicationMetadata>;
    var currentApp: model.ApplicationMetadata;
    var screenModel: any = {};
    var appID = __viewContext.transferred.value.appID;
    if (nts.uk.util.isNullOrUndefined(appID)) {
        listAppMeta = __viewContext.transferred.value.listAppMeta;
        currentApp = __viewContext.transferred.value.currentApp;
        initScreen(screenModel, listAppMeta, currentApp);
    } else {
        nts.uk.at.view.kaf000.b.service.getAppByID(appID)
            .done((data) => {
                var appInfo = new model.ApplicationMetadata(data.appID, data.appType, data.appDate);
                listAppMeta = [appInfo];
                currentApp = appInfo;
                initScreen(screenModel, listAppMeta, currentApp);
            }).fail((res) =>{
                nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(function() {
                              nts.uk.request.jump("/view/cmm/045/a/index.xhtml");
                });
            });
    }

});







