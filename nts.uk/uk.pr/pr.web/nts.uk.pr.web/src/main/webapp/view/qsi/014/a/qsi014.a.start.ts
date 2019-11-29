module nts.uk.pr.view.qsi014.a {
    __viewContext.ready(function() {
        let  screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {$('#A222_4').focus()});
    });
}