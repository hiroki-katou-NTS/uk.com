module nts.uk.pr.view.qsi003.a {
    __viewContext.ready(function() {
        let  screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {$('#A3_4').focus()});
    });
}