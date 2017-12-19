module nts.uk.at.view.kmk013.d {
    __viewContext.ready(function() {
        var screenModel = new d.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
