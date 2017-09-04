module nts.uk.at.view.ksm006.a {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.ksm006.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#companyBWWorkingDayBtn').focus();
        });
    });
}