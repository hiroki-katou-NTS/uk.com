module nts.uk.at.view.kmk012.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#selUseClassification").focus();
        }); 
    });
}