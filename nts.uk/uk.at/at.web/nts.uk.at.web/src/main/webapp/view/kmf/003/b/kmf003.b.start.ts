module nts.uk.at.view.kmf003.b {
    __viewContext.ready(function() {
        
        let dialogType = nts.uk.ui.windows.getShared("KMF003_DIALOG_TYPE");
        
        var screenModel = dialogType == "A" ? new nts.uk.at.view.kmf003.b.viewmodel.ScreenModel() : new nts.uk.at.view.kmf003.b1.viewmodel.ScreenModel();
        screenModel.dialogType(dialogType);
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            if (dialogType == "A") {
                setTimeout(function() { $('#b2_1').focus(); }, 200);
                nts.uk.ui.windows.getSelf().setSize(580, 900);
            } else {
                nts.uk.ui.windows.getSelf().setSize(520, 480);
            }           
        });
    });
}