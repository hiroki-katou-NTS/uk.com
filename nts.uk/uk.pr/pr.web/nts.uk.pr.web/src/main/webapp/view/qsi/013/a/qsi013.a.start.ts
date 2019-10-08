module nts.uk.pr.view.qsi013.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {$('#A2_4').focus()});
    });
}