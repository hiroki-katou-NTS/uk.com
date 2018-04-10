module nts.uk.at.view.kmk013.j {
    __viewContext.ready(function() {
        var screenModel = new j.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
