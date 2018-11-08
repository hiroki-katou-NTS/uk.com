module nts.uk.pr.view.qmm020.a {
    import share = qmm020.share.model;
    __viewContext.ready(function() {
        var viewmodelA = new nts.uk.pr.view.qmm020.a.viewmodel.ScreenModel();
        var viewmodelB = new nts.uk.pr.view.qmm020.b.viewmodel.ScreenModel();
        var viewmodelC = new nts.uk.pr.view.qmm020.c.viewmodel.ScreenModel();
        var viewmodelD = new nts.uk.pr.view.qmm020.d.viewmodel.ScreenModel();;


        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB,
            viewmodelC: viewmodelC,
            viewmodelD: viewmodelD
        };
        __viewContext.bind(__viewContext.viewModel);
        viewmodelA.startPage().done((data)=>{
            viewmodelB.startPage().done(function() {
                nts.uk.ui.errors.clearAll();
            });
            if(data.masterUse == 1 && data.usageMaster == 0){
                viewmodelC.startPage().done(function() {
                    nts.uk.ui.errors.clearAll();
                });
            }


        });


    });
}