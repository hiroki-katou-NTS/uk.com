module nts.uk.pr.view.kmf001.h {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $( "#company-manage" ).focus();
        });
    });
}