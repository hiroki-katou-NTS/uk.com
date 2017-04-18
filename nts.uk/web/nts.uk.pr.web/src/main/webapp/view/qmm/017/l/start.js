__viewContext.ready(function () {
    var param = nts.uk.ui.windows.getShared('paramFromScreenC');
    var screenModel = new nts.uk.pr.view.qmm017.l.viewmodel.ScreenModel(param);
    screenModel.start().done(function () {
        __viewContext.bind(screenModel);
    });
});
//# sourceMappingURL=start.js.map