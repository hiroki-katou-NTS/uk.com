module nts.uk.at.view.kfp001.h {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);

            screenModel.start();
    });
}