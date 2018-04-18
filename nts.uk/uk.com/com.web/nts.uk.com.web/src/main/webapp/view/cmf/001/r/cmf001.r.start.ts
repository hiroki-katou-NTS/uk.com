module nts.uk.com.view.cmf001.r {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function(){
            __viewContext.bind(screenModel);
        });
         $('#BTN_CLOSE').focus();
    });
}