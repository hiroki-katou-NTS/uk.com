module knr002.d {    
    __viewContext.ready(function() {
        var screenModel = new knr002.d.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        }).then(() => {
            $('#D5_1').focus();	
        });        
    });
}