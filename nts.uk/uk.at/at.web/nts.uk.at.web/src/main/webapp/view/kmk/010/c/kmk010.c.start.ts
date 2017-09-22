module nts.uk.at.view.kmk010.c {
    
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res){
            __viewContext.bind(res); 
            $("#breakdownItemNo_1").focus();
        });
           
    });
}