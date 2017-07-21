    __viewContext.ready(function() {
        let screenModel = new ksm002.d.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#startMonth").focus();
            }); 
        });

