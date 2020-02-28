module nts.uk.pr.view.qui001.c.start {
    __viewContext.ready(function() {
        let  screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#emp-component').focus();
    });
}