module nts.uk.at.view.kml001.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kml001.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.isInsert()){
                $("#startDateInput").focus();    
            } else {
                $("#memo").focus();    
            }    
            $("#premium-set-tbl > tbody > tr > td:NTH-CHILD(2) input").each(function (i) { $(this).attr('tabindex', i + 8); });
            $("#premium-set-tbl > tbody > tr > td:NTH-CHILD(3) button").each(function (i) { $(this).attr('tabindex', i + 9); });
        });
    });
}
