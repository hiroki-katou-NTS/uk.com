module nts.uk.com.view.cmf005.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        if (screenModel.rdSelected == 1) {
            $("#C20_1").focus();
        } else {
            //focus  B3_4
        }
    });
}