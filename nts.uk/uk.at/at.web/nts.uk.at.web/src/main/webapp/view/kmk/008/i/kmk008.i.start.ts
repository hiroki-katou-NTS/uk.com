module nts.uk.at.view.kmk008.i {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#combo-box-month-setting").focus(); 
        });
    });
}