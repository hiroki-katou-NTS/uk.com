__viewContext.ready(function() {
    let screenModel = new kaf000.a.viewmodel.ScreenModel();
    screenModel.start().done(function(){
       __viewContext.bind(screenModel); 
    });
});

