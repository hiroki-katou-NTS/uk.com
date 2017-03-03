__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.c.viewmodel.ScreenModel();
    var self= this;
    $.when(screenModel.start()).done(function() {
        self.bind(screenModel);
    });
});
