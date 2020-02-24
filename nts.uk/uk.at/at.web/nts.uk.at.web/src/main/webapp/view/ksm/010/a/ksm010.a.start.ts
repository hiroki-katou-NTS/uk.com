module nts.uk.at.view.ksm010.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.rankItems().length > 0 ? $('#rankSymbol').focus() : $('#rankCode').focus();;
        });

    });
}