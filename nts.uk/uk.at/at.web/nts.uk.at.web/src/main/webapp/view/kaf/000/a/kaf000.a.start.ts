__viewContext.ready(function() {
    let screenModel = new nts.uk.at.view.kaf000.a.viewmodel.ScreenModel();
    screenModel.start().done(function(){
       __viewContext.bind(screenModel); 
    });
});

