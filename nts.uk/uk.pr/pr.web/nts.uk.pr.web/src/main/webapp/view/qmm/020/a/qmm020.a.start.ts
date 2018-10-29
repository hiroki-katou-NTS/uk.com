module nts.uk.pr.view.qmm020.a {
    __viewContext.ready(function() {
        var viewmodelE = new nts.uk.pr.view.qmm020.e.viewmodel.ScreenModel();
       /* var viewmodelF = new nts.uk.pr.view.qmm020.f.viewmodel.ScreenModel();*/
        var viewmodelG = new nts.uk.pr.view.qmm020.g.viewmodel.ScreenModel();
        var viewmodelH = new nts.uk.pr.view.qmm020.h.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelE: viewmodelE,
            viewmodelG: viewmodelG,
            viewmodelH: viewmodelH
        };
        __viewContext.bind(__viewContext.viewModel);
    });
}