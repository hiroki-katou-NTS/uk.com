module nts.uk.at.view.kfp001.f {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
//            $("#D3_2").focus();
        });
    });
}
