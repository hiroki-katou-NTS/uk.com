module nts.uk.at.view.ksu001.s.sa {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
             $( "#swap-list" ).focus();
           });
    });
}