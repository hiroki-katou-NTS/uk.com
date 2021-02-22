module nts.uk.at.view.kaf006.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.KAF006CViewModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}
