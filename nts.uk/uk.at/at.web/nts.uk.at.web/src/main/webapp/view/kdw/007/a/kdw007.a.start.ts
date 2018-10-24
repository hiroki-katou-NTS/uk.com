module nts.uk.at.view.kdw007.a {
    __viewContext.ready(function() {
        let isDaily = null;
        this.transferred.ifPresent(param => {
           isDaily = param.ShareObject;
        });
        var screenModel = new viewmodel.ScreenModel(isDaily);
        screenModel.startPage(null).done(function() {
            __viewContext.bind(screenModel);
            setTimeout(() => {
                if (screenModel.lstFilteredData().length > 0) {
					$("#errorAlarmWorkRecordName").focus();
				} else {
					$("#errorAlarmWorkRecordCode").focus();
				}
            }, 1);
        });
    });
}