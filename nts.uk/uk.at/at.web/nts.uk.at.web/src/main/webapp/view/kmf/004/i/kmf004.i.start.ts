module nts.uk.at.view.kmf004.i {
    __viewContext.ready(function() {

        let vm = __viewContext['viewModel'] = new  viewmodel.ScreenModel();
        __viewContext['viewModel'].startPage().done(function() {
            __viewContext.bind(__viewContext['viewModel']);

        });
    });
} 