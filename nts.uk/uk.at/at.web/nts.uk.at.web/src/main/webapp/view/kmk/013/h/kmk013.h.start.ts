module nts.uk.at.view.kmk013.h {
    __viewContext.ready(function() {
        var screenModel = new h.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#h3_2").focus();
        });
    });
}
