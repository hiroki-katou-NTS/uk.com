module nts.uk.at.view.ksm005.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res) {
            __viewContext.bind(res);
            screenModel.isBuild = true;
        });

    });
}