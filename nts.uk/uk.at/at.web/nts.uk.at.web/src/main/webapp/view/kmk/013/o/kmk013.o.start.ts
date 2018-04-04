module nts.uk.at.view.kmk013.o {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res) {
            __viewContext.bind(screenModel);
        });
    });
}