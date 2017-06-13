module nts.uk.at.view.kmk012.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#itemname").focus();
        }); 
    });
}