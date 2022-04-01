module knr002.b {    
    __viewContext.ready(function() {
        var screenModel = new knr002.b.viewmodel.ScreenModel(); 
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        }).then(() => {
            $("#multi-list_sDate").attr('colspan',5);
            $('#B6_1').focus();
        });        
    });
}