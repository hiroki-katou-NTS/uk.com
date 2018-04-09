module nts.uk.at.view.kdl034.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdl034.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#combo-box").focus();
        });
    });
}
