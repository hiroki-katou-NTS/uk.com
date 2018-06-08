module nts.uk.at.view.kdm001.e {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.kdm001.e.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        screenModel.initScreen();
        $("#multi-list_container").focus();
    });
}
