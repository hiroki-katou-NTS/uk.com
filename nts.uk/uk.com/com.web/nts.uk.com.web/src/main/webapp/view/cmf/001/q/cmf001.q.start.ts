module nts.uk.com.view.cmf001.q {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function(){
           __viewContext.bind(screenModel);
        });
        $('#BTN_STOP').focus();
    });
}