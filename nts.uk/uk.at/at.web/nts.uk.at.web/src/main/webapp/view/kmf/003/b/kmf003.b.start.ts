module nts.uk.at.view.kmf003.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            
            setTimeout(function() { $('#b2_1').focus(); }, 200);            
        });
    });
}