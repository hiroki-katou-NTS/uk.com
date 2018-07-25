module nts.uk.at.view.kal004.g {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#start-tab1").focus();
            $("#combo-1 input").focus();
            $("#start-tab-2 input").focus();
            $("#start-tab-3 input").focus();
            $("#edit-3").focus();
            $("#start-tab-4 input").focus();
            $("#edit-4").focus();
            $("#start-tab-5").focus();
        });
    });
}