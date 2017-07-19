module nts.uk.at.view.ksm005.f {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res){
            __viewContext.bind(res); 
        });
           
    });
}