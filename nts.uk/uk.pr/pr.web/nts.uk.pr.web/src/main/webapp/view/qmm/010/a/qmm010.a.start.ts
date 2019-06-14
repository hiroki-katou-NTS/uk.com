module nts.uk.pr.view.qmm010.a {
    __viewContext.ready(function() {
        var viewmodel = new nts.uk.pr.view.qmm010.a.viewmodel.ScreenModel();
        __viewContext.viewModel = viewmodel;

        viewmodel.startPage().done(function() {
            __viewContext.bind(__viewContext.viewModel);
        });
    });
}
