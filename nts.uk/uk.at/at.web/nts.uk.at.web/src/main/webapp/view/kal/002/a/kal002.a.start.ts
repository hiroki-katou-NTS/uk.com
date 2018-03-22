module nts.uk.at.view.kal002.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#checkCondition").focus();
        });
    });
}