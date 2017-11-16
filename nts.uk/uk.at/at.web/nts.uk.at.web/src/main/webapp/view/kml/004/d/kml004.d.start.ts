module nts.uk.at.view.kml004.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#swap-list-grid1").igGrid("container").focus();
        });
    });  
}  