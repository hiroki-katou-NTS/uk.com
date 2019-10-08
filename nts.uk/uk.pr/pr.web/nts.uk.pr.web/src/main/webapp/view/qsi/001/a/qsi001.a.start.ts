module nts.uk.pr.view.qsi001.a {
    __viewContext.ready(function() {
        let  screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {$('#A2_4').focus()});
    });
}