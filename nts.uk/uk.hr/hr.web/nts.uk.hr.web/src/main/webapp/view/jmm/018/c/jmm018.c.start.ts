module nts.uk.at.view.jmm018.c {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#check-box').focus();
        });
    });
}