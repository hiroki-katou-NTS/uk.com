module nts.uk.at.view.kml001.d {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kml001.d.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        if(screenModel.isUpdate()) {
            $('#startDateInput').focus();
        }
        $("* input").attr('tabindex', -1);
        $("#radioBox").attr('tabindex', 1);
        $("#startDateInput").attr('tabindex', 2);
        $("#functions-area-bottom > button:NTH-CHILD(1)").attr('tabindex', 3);
        $("#functions-area-bottom > button:NTH-CHILD(2)").attr('tabindex', 4);
    });
}
