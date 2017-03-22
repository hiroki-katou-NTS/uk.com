__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.c.viewmodel.ScreenModel();
    // load stress check result list.
    $.when(screenModel.startPage()).done(function () {
        // Binding screen Model
        $(".draggable").draggable({});
        __viewContext.bind(screenModel);
    });
});
//# sourceMappingURL=qmm008.c.start.js.map