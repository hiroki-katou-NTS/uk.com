module nts.uk.at.view.kmw005.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#actualLock-list_container').focus();
        }); 
    });
}