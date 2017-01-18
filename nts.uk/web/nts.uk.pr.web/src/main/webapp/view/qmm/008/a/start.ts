__viewContext.ready(function() {
    var self= this;
    var screenModel = new nts.uk.pr.view.qmm008.a.viewmodel.ScreenModel();
    // load stress check result list.
    $.when(screenModel.start()).done(function() {
        // Binding screen Model
        $(".draggable").draggable({});
        self.bind(screenModel);
    });
});