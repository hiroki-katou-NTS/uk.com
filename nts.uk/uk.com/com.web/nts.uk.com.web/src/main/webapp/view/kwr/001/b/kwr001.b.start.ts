module nts.uk.at.view.kwr001.b {
    __viewContext.ready(function() {
        var screenModel = new b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            _.defer(function() {
                __viewContext.bind(screenModel);
                $("#multi-list-div").focus();
            },1000);
        });
    });
}
