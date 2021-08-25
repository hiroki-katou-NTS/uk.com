module nts.uk.at.view.knr002.l {
    
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.knr002.l.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });    
    });
}