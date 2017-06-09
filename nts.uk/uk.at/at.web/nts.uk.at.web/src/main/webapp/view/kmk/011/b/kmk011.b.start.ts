module kmk011.b {
    __viewContext.ready(function(){
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(self.checkModel()!=true){
                $("#inpCode").focus();
            }else{
                $("#inpReason").focus();
            }
        });
    });
}