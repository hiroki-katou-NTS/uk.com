module nts.uk.com.view.cmf002.x {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel(this.transferred.value);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}