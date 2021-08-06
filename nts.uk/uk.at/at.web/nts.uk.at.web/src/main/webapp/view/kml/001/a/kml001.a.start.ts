module nts.uk.at.view.kml001.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kml001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.isInsert()){
                $("#startDateInput").focus();    
            } else {
                $("#A4_10").focus();    
            }
            _.defer(() => {
                $("#dateRange-list").igGrid("option", "showHeader", false);
                $("#dateRange-list").igGrid("option", "height", 265);
            });
        });
    });
}
