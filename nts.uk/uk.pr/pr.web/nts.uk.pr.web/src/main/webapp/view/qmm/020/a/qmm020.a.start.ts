module nts.uk.pr.view.qmm020.a {
    import model = qmm020.share.model;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;


    __viewContext.ready(function() {
        block.invisible();
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
        block.clear();
        __viewContext.bind(__viewContext.viewModel);
        viewmodelA.startPage().done((data)=>{
            $(".tab-b-sidebar").click(function() {
                viewmodelB.startPage().done(function() {
                    nts.uk.ui.errors.clearAll();
                    if(viewmodelB.mode()  === model.MODE.NO_REGIS){
                        viewmodelB.enableEditHisButton(false);
                        viewmodelB.enableAddHisButton(true);
                        viewmodelB.enableRegisterButton(false);
                        viewmodelB.openScreenJ();
                    }else{
                        viewmodelB.enableEditHisButton(true);
                        viewmodelB.enableAddHisButton(true);
                        viewmodelB.enableRegisterButton(true);
                    }
                    viewmodelB.newHistoryId(null);
                    $("#B1_5").focus();
                });
            });

            $(".tab-2-sidebar").click(function() {
                if(data.masterUse === 1 && data.usageMaster === 0){
                    viewmodelC.startPage().done(function() {
                        nts.uk.ui.errors.clearAll();
                        if(viewmodelC.mode()  === model.MODE.NO_REGIS){
                            viewmodelC.enableEditHisButton(false);
                            viewmodelC.enableAddHisButton(true);
                            viewmodelC.enableRegisterButton(false);
                        }else{
                            viewmodelC.enableEditHisButton(true);
                            viewmodelC.enableAddHisButton(true);
                            viewmodelC.enableRegisterButton(true);
                        }
                        viewmodelC.newHistoryId(null);
                        $("#C1_5").focus();
                    });
                }else if(data.masterUse === 1 && data.usageMaster === 1){
                    viewmodelD.startPage().done(function() {
                        nts.uk.ui.errors.clearAll();
                        viewmodelD.enableEditHisButton(true);
                        viewmodelD.enableAddHisButton(true);
                    });
                }else if(data.masterUse === 1 && data.usageMaster === 2){
                    viewmodelE.initScreen(null);
                }else if(data.masterUse === 1 && data.usageMaster === 3){
                    viewmodelF.initScreen(null);
                }else if(data.masterUse === 1 && data.usageMaster === 4){
                    viewmodelG.initScreen(null);
                }
            });
            $(".tab-h-sidebar").click(function() {
                viewmodelH.initScreen().done(() => {
                    viewmodelH.loadCCG001();
                });
            });


        }).fail((err)=>{
            if(err) dialog.alertError(err);
        }).always(()=>{
            block.clear();
            $(".tab-b-sidebar").click();
        });


    });
}