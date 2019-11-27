module nts.uk.pr.view.qmm011.a {
    __viewContext.ready(function() {
        var viewmodelA = new nts.uk.pr.view.qmm011.a.viewmodel.ScreenModel();
        var viewmodelB = new nts.uk.pr.view.qmm011.b.viewmodel.ScreenModel();
        var viewmodelC = new nts.uk.pr.view.qmm011.c.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB,
            viewmodelC: viewmodelC
        };
        __viewContext.bind(__viewContext.viewModel);
        _.defer(() => {$('#B1_4_container').focus()});
    });
}