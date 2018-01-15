module nts.uk.at.view.kdw001.a {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            
            //Get all 締め domain
            screenModel.start();
    });
}