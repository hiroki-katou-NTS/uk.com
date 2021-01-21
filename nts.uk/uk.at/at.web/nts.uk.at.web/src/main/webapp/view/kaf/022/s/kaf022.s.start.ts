module nts.uk.at.view.kaf022.s {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.listReasonByAppType().length > 0) {
                $("#reasonTemp").focus();
            } else {
                $("#reasonCode").focus();
            }
        });
    });
}