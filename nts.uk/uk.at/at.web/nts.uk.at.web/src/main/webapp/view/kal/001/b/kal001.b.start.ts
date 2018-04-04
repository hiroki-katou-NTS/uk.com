module nts.uk.at.view.kal001.b {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        let extractedAlarmData : Array<model.ValueExtractAlarmDto> =  nts.uk.ui.windows.getShared("extractedAlarmData");
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);            
            $("#grid").igGrid("option", "dataSource", extractedAlarmData);
            screenModel.dataSource= extractedAlarmData;
        });
    });
}