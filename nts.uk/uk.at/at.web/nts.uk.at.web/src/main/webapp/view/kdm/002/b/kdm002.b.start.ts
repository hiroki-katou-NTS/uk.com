module nts.uk.at.view.kdm002.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(){
           __viewContext.bind(screenModel);
        });
        $('#BTN_STOP').focus();
    });
}