module nts.uk.at.view.kmr005.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kmr005.a.viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);       
        });
        
    });
}