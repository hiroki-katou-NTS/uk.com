module nts.uk.pr.view.qmm012.h {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => { $("#H2_2").focus(); });
        });
    });
}