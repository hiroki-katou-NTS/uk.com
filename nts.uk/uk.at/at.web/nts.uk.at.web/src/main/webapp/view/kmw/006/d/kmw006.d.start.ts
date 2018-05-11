module nts.uk.at.view.kmw006.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#D3_2").focus();
        });
    });
}
