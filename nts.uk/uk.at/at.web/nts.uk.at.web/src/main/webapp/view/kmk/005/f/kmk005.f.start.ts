module nts.uk.at.view.kmk005.f {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kmk005.f.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if(screenModel.isUpdate()) {
                $("#requiredName").focus();  
            } else {
                $("#requiredCode").focus();   
            }
            $("a#ui-id-1.ui-tabs-anchor").attr('tabindex', 9);
            $("a#ui-id-2.ui-tabs-anchor").attr('tabindex', 9);
        });
    });
}
