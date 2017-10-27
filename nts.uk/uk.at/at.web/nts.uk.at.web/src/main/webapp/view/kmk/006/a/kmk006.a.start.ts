module nts.uk.at.view.kmk006.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res) {
            __viewContext.bind(res);
        });
    });
}