module nts.uk.at.view.kaf022.o {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModelO();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}