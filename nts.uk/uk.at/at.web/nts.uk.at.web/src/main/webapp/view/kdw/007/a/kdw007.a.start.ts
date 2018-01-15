module nts.uk.at.view.kdw007.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage(null).done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.lstErrorAlarm().length > 0) {
                $("#errorAlarmWorkRecordName").focus();
            } else {
                $("#errorAlarmWorkRecordCode").focus();
            }
        });
    });
}