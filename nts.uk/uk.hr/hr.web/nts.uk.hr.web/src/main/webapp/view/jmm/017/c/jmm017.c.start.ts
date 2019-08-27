module nts.uk.com.view.jmm017.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $( ".inputTextarea" ).focus();
        });
    });
}