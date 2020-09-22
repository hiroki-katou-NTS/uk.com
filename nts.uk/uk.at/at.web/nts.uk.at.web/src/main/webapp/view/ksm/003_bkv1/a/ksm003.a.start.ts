module nts.uk.at.view.ksm003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        }).then(function() {
            if (nts.uk.util.isNullOrEmpty(screenModel.itemLst())) {
                $("#inpCode").focus();
            }
        });
    });
}