module nts.uk.at.view.kdm001.k {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.kdm001.k.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            $("#multi-list_container").focus();
    });
}
