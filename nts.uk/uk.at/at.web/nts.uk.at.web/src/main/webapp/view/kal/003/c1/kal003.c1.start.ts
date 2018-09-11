module nts.uk.at.view.kal003.c1 {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kal003.c1.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#conditionAtr-cbb").focus();
    });
}