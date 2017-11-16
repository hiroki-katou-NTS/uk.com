module nts.uk.at.view.kdw001.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext["viewmodel"] = screenModel;
        
        __viewContext.bind(screenModel);

        //When bind screen done, bind 2 component reference.
        screenModel.cScreenmodel.start();
    });
}