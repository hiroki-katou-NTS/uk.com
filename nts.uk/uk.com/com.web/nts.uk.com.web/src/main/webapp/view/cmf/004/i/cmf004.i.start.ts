module nts.uk.com.view.cmf004.i {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(()=>{
            __viewContext.bind(screenModel);           
        });
        $('#F5_1').focus();
    });
}