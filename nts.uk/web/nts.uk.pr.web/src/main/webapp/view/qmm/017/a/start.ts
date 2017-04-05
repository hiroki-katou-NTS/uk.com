module nts.qmm017 {
__viewContext.ready(function() {
        var screenModel = new nts.qmm017.ScreenModel();
        screenModel.start().done(function(){
            __viewContext.bind(screenModel);
        });
        
});}