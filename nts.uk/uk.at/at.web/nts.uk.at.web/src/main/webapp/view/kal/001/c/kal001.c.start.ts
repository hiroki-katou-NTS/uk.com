module nts.uk.at.view.kal001.c {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
         let shareEmployee : Array<model.ShareEmployee> =  nts.uk.ui.windows.getShared("shareEmployee");
        screenModel.shareEmployee = ko.observableArray(shareEmployee);
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel); 
        });
    });
}