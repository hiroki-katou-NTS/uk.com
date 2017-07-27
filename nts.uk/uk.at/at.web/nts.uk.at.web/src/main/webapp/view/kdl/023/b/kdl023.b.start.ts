module nts.uk.at.view.kdl023.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);

            // Set focus control
            $('#cbb-daily-work-pattern').focus();
        });
    });
}