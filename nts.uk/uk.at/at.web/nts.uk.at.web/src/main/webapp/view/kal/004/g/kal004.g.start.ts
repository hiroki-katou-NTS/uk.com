module nts.uk.at.view.kal004.g {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#start-tab1").focus();
            $("#combo-1 input").focus();
        });
    });
}