module nts.uk.at.view.qmm011.a {
    __viewContext.ready(function() {
        var viewmodelA = new nts.uk.com.view.qmm011.b.viewmodel.ScreenModel();
        var viewmodelB = new nts.uk.com.view.qmm011.c.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB
        };
        __viewContext.bind(__viewContext.viewModel);
        
        
    });
}