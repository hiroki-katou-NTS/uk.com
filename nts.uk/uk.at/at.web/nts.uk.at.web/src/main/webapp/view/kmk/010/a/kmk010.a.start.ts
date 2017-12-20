module nts.uk.at.view.kmk010.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#selectMethodOutsideOT").focus();
            screenModel.addViewLanguage();
            service.initTooltip();
        }); 
    });
}