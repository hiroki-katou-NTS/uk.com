__viewContext.ready(function() {
    let screenModel = new kaf000.b.viewmodel.ScreenModel();
    screenModel.start().done(function(){
       __viewContext.bind(screenModel); 
    });

});

