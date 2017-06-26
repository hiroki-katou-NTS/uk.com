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
            $("* input").attr('tabindex', -1);
            $("* button").attr('tabindex', -1);
            $("#dateRange-list-container").attr('tabindex', -1);
            $("#dateRange-list-container *").attr('tabindex', -1);
            $("#functions-area > button:NTH-CHILD(1)").attr('tabindex', 1);
            $("#functions-area > button:NTH-CHILD(2)").attr('tabindex', 2);
            $(".dateControlBtn:NTH-CHILD(1)").attr('tabindex', 3);
            $(".dateControlBtn:NTH-CHILD(2)").attr('tabindex', 4);
            $("#dateRange-list").attr('tabindex', 5);
            $("#startDateInput").attr('tabindex', 6);
            $("#combo-box").attr('tabindex', 7);
            $("#memo").attr('tabindex', 8);
            $("#premium-set-tbl > tbody > tr > td:NTH-CHILD(2) input").each(function (i) { $(this).attr('tabindex', i*2 + 9); });
            $("#premium-set-tbl > tbody > tr > td:NTH-CHILD(3) button").each(function (i) { $(this).attr('tabindex', i*2 + 10); });
        });
    });
}
