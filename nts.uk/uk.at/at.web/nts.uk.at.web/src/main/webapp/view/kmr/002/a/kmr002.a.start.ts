module nts.uk.at.view.kmr002.a {
    __viewContext.ready(function() {
        let screenModel = new model.ScreenModel();
        __viewContext["viewmodel"] = screenModel;
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);
//            $('#component-employee-list').ntsListComponent(screenModel.listComponentOption);  
//            $('#ccgcomponent').ntsGroupComponent(screenModel.ccg001ComponentOption);               
        });
        
    });
}