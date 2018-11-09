module nts.uk.pr.view.qmm020.a {
    __viewContext.ready(function() {
        var viewmodelA = new nts.uk.pr.view.qmm020.a.viewmodel.ScreenModel();
        var viewmodelB = new nts.uk.pr.view.qmm020.b.viewmodel.ScreenModel();
        var viewmodelC = new nts.uk.pr.view.qmm020.c.viewmodel.ScreenModel();
        var viewmodelD = new nts.uk.pr.view.qmm020.d.viewmodel.ScreenModel();
        var viewmodelE = new nts.uk.pr.view.qmm020.e.viewmodel.ScreenModel();
        var viewmodelF = new nts.uk.pr.view.qmm020.f.viewmodel.ScreenModel();
        var viewmodelG = new nts.uk.pr.view.qmm020.g.viewmodel.ScreenModel();
        var viewmodelH = new nts.uk.pr.view.qmm020.h.viewmodel.ScreenModel();


        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB,
            viewmodelC: viewmodelC,
            viewmodelD: viewmodelD,
            viewmodelE: viewmodelE,
            viewmodelF: viewmodelF,
            viewmodelG: viewmodelG,
            viewmodelH: viewmodelH
        };
        __viewContext.bind(__viewContext.viewModel);
        viewmodelA.startPage().done((data)=>{
            viewmodelB.startPage().done(function() {
                nts.uk.ui.errors.clearAll();
            });
            $(".tab-b-sidebar").click(function() {
                viewmodelB.startPage().done(function() {
                    nts.uk.ui.errors.clearAll();
                    viewmodelB.enableEditHisButton(true);
                    viewmodelB.enableAddHisButton(true);
                });
            });

            $(".tab-2-sidebar").click(function() {
                if(data.masterUse === 1 && data.usageMaster === 0){
                    viewmodelC.startPage().done(function() {
                        nts.uk.ui.errors.clearAll();
                        viewmodelC.enableEditHisButton(true);
                        viewmodelC.enableAddHisButton(true);
                    });
                }else if(data.masterUse === 1 && data.usageMaster === 1){
                    viewmodelD.startPage().done(function() {
                        nts.uk.ui.errors.clearAll();
                        viewmodelD.enableEditHisButton(true);
                        viewmodelD.enableAddHisButton(true);
                    });
                }

            });
            /*$(".tab-b-sidebar").click(function() {
                viewmodelB.startPage().done(function() {
                    nts.uk.ui.errors.clearAll();
                    viewmodelB.enableEditHisButton(true);
                    viewmodelB.enableAddHisButton(true);
                });
            });

            $(".tab-c-sidebar").click(function() {
                viewmodelC.startPage().done(function() {
                    nts.uk.ui.errors.clearAll();
                    viewmodelC.enableEditHisButton(true);
                    viewmodelC.enableAddHisButton(true);
                });
            });

            $(".tab-d-sidebar").click(function() {
                viewmodelD.startPage().done(function() {
                    nts.uk.ui.errors.clearAll();
                    viewmodelD.enableEditHisButton(true);
                    viewmodelD.enableAddHisButton(true);
                });
            });*/

        });


    });
}