module nts.uk.at.view.kdl014.a {
    __viewContext.ready(function() {
        var screenModel =  __viewContext.vm = new ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#btnClose').focus();
        });
    });
}
