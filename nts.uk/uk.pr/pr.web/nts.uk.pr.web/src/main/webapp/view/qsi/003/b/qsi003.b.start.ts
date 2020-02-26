module nts.uk.pr.view.qsi003.b {
    __viewContext.ready(function() {
        let  screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#emp-component").focus();
    });
}