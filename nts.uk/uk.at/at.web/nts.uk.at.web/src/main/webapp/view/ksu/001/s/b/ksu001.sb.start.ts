module nts.uk.at.view.ksu001.s.sb {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
             $("#cancel").focus();
        });
    });
}