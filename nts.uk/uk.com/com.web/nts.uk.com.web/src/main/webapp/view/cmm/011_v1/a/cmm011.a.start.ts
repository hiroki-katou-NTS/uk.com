module nts.uk.com.view.cmm011.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            // focus 
            if (screenModel.isNewMode()) {
                $('#wkpCd').focus();
            } else {
                $('#wkpName').focus();
            }
        });
    });
}