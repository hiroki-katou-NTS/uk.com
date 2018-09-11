module nts.uk.at.view.kal003.c {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kal003.c.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#conditionAtr-cbb").focus();
    });
}