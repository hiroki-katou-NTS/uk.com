module nts.uk.at.view.kfp001.f {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#F5_2").focus();
        });
    });
}
