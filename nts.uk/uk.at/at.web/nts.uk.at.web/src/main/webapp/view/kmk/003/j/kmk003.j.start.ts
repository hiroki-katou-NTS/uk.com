module nts.uk.at.view.kmk003.j {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.isFlow()) {
                _.defer(() => $('[tabindex=9]').focus());
            } else {
                _.defer(() => $('[tabindex=1]').focus());
            }
        });
    });
}