__viewContext.ready(function () {
    var param = nts.uk.ui.windows.getShared('paramFromScreenC');
    var screenModel = new nts.uk.pr.view.qmm017.l.viewmodel.ScreenModel(param);
    this.bind(screenModel);
});
