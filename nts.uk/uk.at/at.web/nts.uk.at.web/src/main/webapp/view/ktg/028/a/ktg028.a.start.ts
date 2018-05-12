module nts.uk.at.view.ktg028.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.items_A2().length == 0) {
                $("#code").focus();
            } else {
                setTimeout(function() { $("#name").focus(); }, 500);
            }
        });
    });
}