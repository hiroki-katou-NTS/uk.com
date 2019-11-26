module nts.uk.pr.view.qmm011.e {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            _.defer(() => {$('#E1_5').focus()});
    });
}
