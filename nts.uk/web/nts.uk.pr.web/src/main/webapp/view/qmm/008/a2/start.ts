__viewContext.ready(function() {
    var screenModel = new nts.uk.pr.view.qmm008.a2.viewmodel.ScreenModel();
    // load stress check result list.
    $.when(screenModel.startPage()).done(function() {
        // Binding screen Model
        $(".draggable").draggable({});
        __viewContext.bind(screenModel);
    });
});