module nts.uk.at.view.kmk002.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#searchbox-attd-item').find('button').focus();
        });
    });
}
