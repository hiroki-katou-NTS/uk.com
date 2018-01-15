module nts.uk.at.view.kdl001.test {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdl001.test.viewmodel.ScreenModel();
        //screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);  
        //});
    });
}
