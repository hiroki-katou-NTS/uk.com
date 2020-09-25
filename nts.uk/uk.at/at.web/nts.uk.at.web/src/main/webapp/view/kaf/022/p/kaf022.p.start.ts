module nts.uk.at.view.kaf022.p {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.settings().length > 0) {
                $("#optItemAppTypeName").focus();
            } else {
                $("#optItemAppTypeCode").focus();
            }
        });
    });
}