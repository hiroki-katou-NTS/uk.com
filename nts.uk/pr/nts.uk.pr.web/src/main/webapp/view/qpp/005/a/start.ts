__viewContext.ready(function() {
    var screenModel = new nts.uk.pr.view.qpp005.viewmodel.ScreenModel();
    screenModel.startPage().done(function() {
        __viewContext.bind(screenModel);
    });
});
