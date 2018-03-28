module nts.uk.com.view.cmf001.b {
    __viewContext.ready(function() {
            var screenModel = new viewmodel.ScreenModel(this.transferred.value);
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
            });
    });
}