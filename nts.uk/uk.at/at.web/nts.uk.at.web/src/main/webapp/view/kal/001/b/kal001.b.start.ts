module nts.uk.at.view.kal001.b {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        let alarmCode =  nts.uk.ui.windows.getShared("alarmCode");
        console.log(alarmCode);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}