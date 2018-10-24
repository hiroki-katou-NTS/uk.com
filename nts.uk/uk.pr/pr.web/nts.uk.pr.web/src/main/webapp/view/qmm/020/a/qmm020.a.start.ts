module nts.uk.pr.view.qmm020.a {
    __viewContext.ready(function() {
        var viewmodelB = new nts.uk.pr.view.qmm020.b.viewmodel.ScreenModel();
        var viewmodelC = new nts.uk.pr.view.qmm020.c.viewmodel.ScreenModel();
        var viewmodelD = new nts.uk.pr.view.qmm020.d.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelB: viewmodelB,
            viewmodelC: viewmodelC,
            viewmodelD: viewmodelD
        };
        __viewContext.bind(__viewContext.viewModel);
    });
}