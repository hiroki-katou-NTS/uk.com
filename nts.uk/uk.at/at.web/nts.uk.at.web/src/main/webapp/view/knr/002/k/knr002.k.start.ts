module knr002.k {    
    __viewContext.ready(function() {
        var screenModel = new knr002.k.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        }).then(() => {
            setTimeout(() => {
                $('#multi-list > tbody > tr:nth-child(1)').focus();
            }, 25);
        });        
    });
}