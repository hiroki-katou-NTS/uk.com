__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm016.a.viewmodel.ScreenModel();
    screenModel.startPage().done(function () {
        __viewContext.bind(screenModel);
    });
});
