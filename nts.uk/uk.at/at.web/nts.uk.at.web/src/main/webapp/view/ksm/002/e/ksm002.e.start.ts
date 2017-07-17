    __viewContext.ready(function() {
        var screenModel = new ksm002.e.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#startDateInput').focus();
        $("* input").attr('tabindex', -1);
        $("#startDateInput").attr('tabindex', 1);
        $("#radioBox").attr('tabindex', 2);
        $("#functions-area-bottom > button:NTH-CHILD(1)").attr('tabindex', 3);
        $("#functions-area-bottom > button:NTH-CHILD(2)").attr('tabindex', 4);
    });
