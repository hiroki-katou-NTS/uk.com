module nts.uk.at.view.kmk008.b {
    __viewContext.ready(function() {
        let screenModels = new kmk008.b.viewmodel.ScreenModel();

        screenModels.startPage().done(function() {
            __viewContext.bind(screenModels);
        });
    });
}