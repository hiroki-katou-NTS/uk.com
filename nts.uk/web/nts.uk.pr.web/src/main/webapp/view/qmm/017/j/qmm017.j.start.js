__viewContext.ready(function () {
    var param = nts.uk.ui.windows.getShared('paramFromScreenA');
    var screenModel = new nts.uk.pr.view.qmm017.j.viewmodel.ScreenModelJScreen(param);
    this.bind(screenModel);
});
