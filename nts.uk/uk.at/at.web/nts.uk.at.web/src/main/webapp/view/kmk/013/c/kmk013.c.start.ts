module nts.uk.at.view.kmk013.c {
    __viewContext.ready(function() {
        var screenModel = new c.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#C2_3').focus();
        });
    });
}
