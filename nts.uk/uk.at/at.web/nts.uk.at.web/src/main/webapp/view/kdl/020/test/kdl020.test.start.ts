__viewContext.ready(function() {
        var screenModel = new kdl020.test.viewmodel.ScreenModel();
        //screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);   
        //});
    });