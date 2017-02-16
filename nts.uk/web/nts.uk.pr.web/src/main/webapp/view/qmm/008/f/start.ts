__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.f.viewmodel.ScreenModel( nts.uk.ui.windows.getShared("sendOfficeParentValue"),nts.uk.ui.windows.getShared("currentChildCode"));
    this.bind(screenModel);
});
