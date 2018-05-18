module nts.uk.at.view.kwr001.c {
    __viewContext.ready(function() {
        var screenModel = new c.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (_.isEmpty(screenModel.currentCodeList()) || _.isNull(screenModel.currentCodeList())) {
                $('#C3_2').focus();
            } else {
                $('#C3_3').focus();    
            }
        });
    });
}
