__viewContext.ready(function() {
        var screenModel = new kdl029.test.viewmodel.ScreenModel();
        //screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);   
        //});
    });