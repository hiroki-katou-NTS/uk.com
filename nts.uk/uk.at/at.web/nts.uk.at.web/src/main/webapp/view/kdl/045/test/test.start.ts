module nts.uk.at.view.test {
    __viewContext.ready(function() {
        var screenModel = new vm.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.executeComponent().done(() => {
            });
        });
    });
}