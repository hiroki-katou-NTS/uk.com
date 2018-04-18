module nts.uk.at.view.kmk004_Old.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#companyYearPicker').focus();
            screenModel.initNextTabFeature();
        });
    });
}