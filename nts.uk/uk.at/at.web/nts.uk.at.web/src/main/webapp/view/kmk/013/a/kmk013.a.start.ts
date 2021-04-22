module nts.uk.at.view.kmk013.a {
    __viewContext.ready(function() {
        var screenModel = new a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $( "#A1_4" ).focus();
        });
    });
}
