module nts.uk.com.view.cps005.a {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
       __viewContext['screenModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            ko.computed(function() {
                __viewContext['screenModel'].isEnableButtonProceedA(nts.uk.ui._viewModel.errors.isEmpty() && __viewContext['screenModel'].currentData().isEnableButtonProceed());
            });
        });
    });
}