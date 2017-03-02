__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.b.viewmodel.ScreenModel(nts.uk.ui.windows.getShared("sendOfficeParentValue"),nts.uk.ui.windows.getShared("dataParentValue"),nts.uk.ui.windows.getShared("isHealthParentValue"));
    this.bind(screenModel);
});
