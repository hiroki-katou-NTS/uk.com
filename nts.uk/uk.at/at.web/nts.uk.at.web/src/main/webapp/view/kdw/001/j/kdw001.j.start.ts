module nts.uk.at.view.kdw001.j {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
         __viewContext["viewmodel"] = screenModel;
        __viewContext.bind(screenModel);

        //When bind screen done, bind 2 component reference.
        screenModel.cScreenmodel.start();
        
        //Call start() function to getlist ケース別実行施内容
        screenModel.start();
    });
}