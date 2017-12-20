module nts.uk.at.view.kmw005.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        screenModel.startPage().done(function() {
            $('#actualLock-list_container').focus();
        }); 
    });
}