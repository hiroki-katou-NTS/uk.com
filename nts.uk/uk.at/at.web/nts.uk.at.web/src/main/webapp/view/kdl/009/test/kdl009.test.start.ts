module nts.uk.at.view.kdl009.test {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdl009.test.viewmodel.ScreenModel();
        //screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);  
        //});
    });
}
