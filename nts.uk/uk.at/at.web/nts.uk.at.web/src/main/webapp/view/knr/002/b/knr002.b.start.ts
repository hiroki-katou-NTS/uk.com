module knr002.b {    
    __viewContext.ready(function() {
        var screenModel = new knr002.b.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        }).then(() => {
            $('#B6_1').focus();
        });        
    });
}