__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm016.a.viewmodel.ScreenModel();
    screenModel.startPage().done(function () {
        nts.uk.ui.confirmSave(screenModel.headDirtyChecker);
        nts.uk.ui.confirmSave(screenModel.settingDirtyChecker);
        __viewContext.bind(screenModel);
    });
});
//# sourceMappingURL=qmm016.a.start.js.map