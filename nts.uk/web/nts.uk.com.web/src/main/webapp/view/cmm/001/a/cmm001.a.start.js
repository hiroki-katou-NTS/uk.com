__viewContext.ready(function () {
    var screenModel = new cmm001.a.ViewModel();
    nts.uk.ui.confirmSave(screenModel.dirtyObject);
    screenModel.start(undefined).done(function () {
        __viewContext.bind(screenModel);
    });
});
//# sourceMappingURL=cmm001.a.start.js.map