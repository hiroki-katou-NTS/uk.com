module nts.uk.at.view.kmk009.a {
    __viewContext.ready(function() {
        var screenModel = new kmk009.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            // Focus grid list
            $('#single-list-dataSource_container').focus();
        }); 
    });
}