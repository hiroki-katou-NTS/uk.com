module nts.uk.at.view.kdw007.b {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdw007.b.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#conditionAtr-cbb").focus();
    });
}