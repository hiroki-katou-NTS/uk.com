module nts.uk.at.view.kdm001.h {
    __viewContext.ready(function() {
        let screenModel =  new nts.uk.at.view.kdm001.h.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#H6_4').focus();
    });
}
