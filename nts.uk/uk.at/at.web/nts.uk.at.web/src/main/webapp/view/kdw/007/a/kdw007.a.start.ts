module nts.uk.at.view.kdw007.a {
    import ScreenMode = nts.uk.at.view.kdw007.a.viewmodel.ScreenMode;
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        this.transferred.ifPresent(param => {
           screenModel.screenMode(param.screenMode);
        });
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