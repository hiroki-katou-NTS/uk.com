module nts.uk.at.view.ksm005.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start_page().done(function(){
           __viewContext.bind(screenModel);
           _.defer(() => {
                $('#ccgcomponent').ntsGroupComponent(screenModel.ccgcomponent).done(function() {
                    $('#component-items-list').ntsListComponent(screenModel.listComponentOption);
                });
            });
        });
    });
}