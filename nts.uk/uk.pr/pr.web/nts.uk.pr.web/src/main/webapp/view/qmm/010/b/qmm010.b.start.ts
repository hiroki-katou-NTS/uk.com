module nts.uk.pr.view.qmm010.b{
    __viewContext.ready(function() {
        var viewmodel = new nts.uk.pr.view.qmm010.b.viewmodel.ScreenModel();
        __viewContext.viewModel = viewmodel;

        viewmodel.startPage().done(function() {
            __viewContext.bind(__viewContext.viewModel);
        });
    });
}
