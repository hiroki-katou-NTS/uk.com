module nts.uk.at.view.kmw006.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => {
                $("#A1_1").ntsFixedTable({});
                $("#A1_10").ntsFixedTable({});
                $("#A1_14").ntsFixedTable({});
            });
        });
    });
}