module nts.uk.pr.view.qmm001.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {  screenModel.setFocus(); });
    });
}
