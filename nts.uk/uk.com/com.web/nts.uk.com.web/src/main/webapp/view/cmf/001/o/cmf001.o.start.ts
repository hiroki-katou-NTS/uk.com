module nts.uk.com.view.cmf001.o {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            __viewContext.bind(screenModel);
        })
    });
}