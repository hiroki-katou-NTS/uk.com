module nts.uk.at.view.kfp001.g {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
//            $("#E3_3").focus();
        });
    });
}
