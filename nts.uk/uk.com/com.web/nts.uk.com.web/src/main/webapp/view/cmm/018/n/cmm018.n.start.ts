module cmm018.n.start {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
            screenModel.start().done(function(){
                __viewContext.bind(screenModel);
                screenModel.loadGrid();
            });    
    });
}