module nts.uk.at.view.ktg031.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            setTimeout(function() {
                $("#button2").focus();
            }, 100);
        });
    });
}