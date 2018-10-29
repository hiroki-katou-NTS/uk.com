module nts.uk.pr.view.qmm020.a {
    __viewContext.ready(function() {
        var viewmodelA = new nts.uk.pr.view.qmm020.a.viewmodel.ScreenModel();
        var viewmodelB = new nts.uk.pr.view.qmm020.b.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB
        };
        __viewContext.bind(__viewContext.viewModel);
    });
}