module nts.uk.pr.view.qmm011.f {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            _.defer(() => {$('#F1_6').focus()});
    });
}
