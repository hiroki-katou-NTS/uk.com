module nts.uk.hr.view.jmm017.a {
    __viewContext.ready(function() {
        var screenModel =  __viewContext.vm = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}