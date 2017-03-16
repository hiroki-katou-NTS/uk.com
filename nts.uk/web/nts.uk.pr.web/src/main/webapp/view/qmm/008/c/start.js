__viewContext.ready(function () {
    var screenModel = new nts.uk.pr.view.qmm008.c.viewmodel.ScreenModel();
    $.when(screenModel.start()).done(function () {
        __viewContext.bind(screenModel);
        screenModel.selectedOfficeCode(screenModel.officeItems()[0].code);
    });
});
