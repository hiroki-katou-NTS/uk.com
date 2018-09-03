module nts.uk.at.view.kwr006.d {
    __viewContext.ready(function() {
        var screenModel = new d.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#combo-box-scrD').focus(); 
        });
    });
}
