module nts.uk.at.view.kal003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#check-condition-table").ntsFixedTable();
        });
    });
}