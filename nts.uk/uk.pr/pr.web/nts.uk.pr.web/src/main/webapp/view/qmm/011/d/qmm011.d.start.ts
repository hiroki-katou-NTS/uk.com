module nts.uk.com.view.qmm011.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        if (!screenModel.businessType1().toUse()){
           _.defer(() => {$('#D2_25').focus()});
        } else {
           _.defer(() => {$('#D2_3').focus()});
        } 
    });
}