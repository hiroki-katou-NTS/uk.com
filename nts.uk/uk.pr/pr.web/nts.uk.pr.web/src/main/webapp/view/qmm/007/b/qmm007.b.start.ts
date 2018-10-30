module nts.uk.pr.view.qmm007.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {$('#B1_6').focus()});
    });
}
