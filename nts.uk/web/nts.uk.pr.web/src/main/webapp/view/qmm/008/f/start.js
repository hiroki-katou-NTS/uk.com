__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.f.viewmodel.ScreenModel();
    $("#parentInstruct").text("My parent say: " + nts.uk.ui.windows.getShared("addHistoryParentValue"));
    this.bind(screenModel);
});
