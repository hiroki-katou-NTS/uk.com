module nts.uk.com.view.cps005.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            ko.computed(function() {
                screenModel.isEnableButtonProceedA(nts.uk.ui._viewModel.errors.isEmpty() && screenModel.currentData().isEnableButtonProceed());
            });
        });
    });
}