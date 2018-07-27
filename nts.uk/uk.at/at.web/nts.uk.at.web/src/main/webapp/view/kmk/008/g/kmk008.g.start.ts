module nts.uk.at.view.kmk008.g {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#ccgcomponent').ntsGroupComponent(screenModel.ccg001ComponentOption);
            $('#component-items-list').ntsListComponent(screenModel.listComponentOption);
            
            //
        });
    });
}