__viewContext.ready(function() {
    let param = nts.uk.ui.windows.getShared('paramFromScreenA');
    var screenModel = new nts.uk.pr.view.qmm017.k.viewmodel.ScreenModel(param);
    this.bind(screenModel);
});