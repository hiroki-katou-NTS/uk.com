module nts.uk.at.view.ksm005.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start_page().done(function(){
           __viewContext.bind(screenModel);
           $('#ccgcomponent').ntsGroupComponent(screenModel.ccgcomponent);
            $('#component-items-list').ntsListComponent(screenModel.listComponentOption);
        });
    });
}