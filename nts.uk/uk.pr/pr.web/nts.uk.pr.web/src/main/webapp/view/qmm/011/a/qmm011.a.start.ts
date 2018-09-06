module nts.uk.at.view.qmm011.a {
    __viewContext.ready(function() {
        var viewmodelA = new nts.uk.at.view.qmm011.a.viewmodel.ScreenModel();
        var viewmodelB = new nts.uk.at.view.qmm011.a.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB
        };
        __viewContext.bind(__viewContext.viewModel);
        
        
    });
}