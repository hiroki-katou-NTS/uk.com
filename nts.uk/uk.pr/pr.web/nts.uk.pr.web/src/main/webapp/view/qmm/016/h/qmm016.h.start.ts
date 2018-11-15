module nts.uk.pr.view.qmm016.h {
    __viewContext.ready(function() {
        __viewContext.viewModel = new nts.uk.pr.view.qmm016.h.viewmodel.ScreenModel();
        __viewContext.viewModel.startPage().done(()=>{
            __viewContext.bind(__viewContext.viewModel);
        })
    });
}
