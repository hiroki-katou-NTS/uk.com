module nts.uk.at.view.kml001.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kml001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('th#dateRange-list_dateRange.ui-iggrid-header.ui-widget-header').css("display","none");
            if(screenModel.isInsert()){
                $("#startDateInput").focus();    
            } else {
                $("#memo").focus();    
            }    
            screenModel.setTabindex();
        });
    });
}
