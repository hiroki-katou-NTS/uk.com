module nts.uk.at.view.kdm001.m {
    __viewContext.ready(function() {
        let screenModel =  new nts.uk.at.view.kdm001.m.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#M6_2').focus();
    });
}
