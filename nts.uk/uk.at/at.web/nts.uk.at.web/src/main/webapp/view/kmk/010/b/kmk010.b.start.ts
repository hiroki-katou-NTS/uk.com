module nts.uk.at.view.kmk010.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res){
            __viewContext.bind(res); 
            $("#overtimeNo_1").focus();
        });
           
    });
}