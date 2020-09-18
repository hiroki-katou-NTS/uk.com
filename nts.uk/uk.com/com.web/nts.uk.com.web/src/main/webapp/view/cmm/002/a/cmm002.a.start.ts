__viewContext.ready(function() {
    var screenModel = new cmm002.a.ViewModel();
    screenModel.start().done(function(){
        __viewContext.bind(screenModel);
    });
});
   