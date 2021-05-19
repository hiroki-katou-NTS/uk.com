module nts.uk.at.view.kal001.b {  
    __viewContext.ready(function() {
        let param =  nts.uk.ui.windows.getShared("extractedAlarmData");
        let screenModel = new viewmodel.ScreenModel(param);

        screenModel.startPage().done(function() {
            $(".popup-area1").ntsPopup('init');
            __viewContext.bind(screenModel);
        });
    });
}