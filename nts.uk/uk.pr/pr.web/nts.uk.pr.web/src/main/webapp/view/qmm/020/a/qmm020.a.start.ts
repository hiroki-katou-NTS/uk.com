module nts.uk.pr.view.qmm020.a {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    __viewContext.ready(function() {
        block.invisible();
        let viewmodelA = new nts.uk.pr.view.qmm020.a.viewmodel.ScreenModel();
        let viewmodelB = new nts.uk.pr.view.qmm020.b.viewmodel.ScreenModel();
        let viewmodelC = new nts.uk.pr.view.qmm020.c.viewmodel.ScreenModel();
        let viewmodelD = new nts.uk.pr.view.qmm020.d.viewmodel.ScreenModel();
        let viewmodelE = new nts.uk.pr.view.qmm020.e.viewmodel.ScreenModel();
        let viewmodelF = new nts.uk.pr.view.qmm020.f.viewmodel.ScreenModel();
        let viewmodelG = new nts.uk.pr.view.qmm020.g.viewmodel.ScreenModel();
        let viewmodelH = new nts.uk.pr.view.qmm020.h.viewmodel.ScreenModel();
        let viewmodelI = new nts.uk.pr.view.qmm020.i.viewmodel.ScreenModel();

        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB,
            viewmodelC: viewmodelC,
            viewmodelD: viewmodelD,
            viewmodelE: viewmodelE,
            viewmodelF: viewmodelF,
            viewmodelG: viewmodelG,
            viewmodelH: viewmodelH,
            viewmodelI: viewmodelI
        };
        __viewContext.bind(__viewContext.viewModel);
        viewmodelA.startPage().done(()=>{
            viewmodelA.onSelectTabB();
        }).fail((err)=>{
            if(err) dialog.alertError(err);
        }).always(()=>{
            block.clear();
        });
    });
}