module nts.uk.at.view.kmw006.f {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => {
                $("#fixed-table").ntsFixedTable({ height: 150 });
            });
        });
    });
}
