module nts.uk.at.view.kal004.a {
    __viewContext.ready(function() {
        let screenModel = new model.ScreenModel();
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);            
        });
    });
}