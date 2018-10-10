module nts.uk.pr.view.qmm008.a {
    __viewContext.ready(function() {
        var viewmodelB = new nts.uk.pr.view.qmm008.b.viewmodel.ScreenModel();
        var viewmodelC = new nts.uk.pr.view.qmm008.c.viewmodel.ScreenModel();
        var viewmodelI = new nts.uk.pr.view.qmm008.i.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelB: viewmodelB,
            viewmodelC: viewmodelC,
            viewmodelI: viewmodelI
        };
        __viewContext.bind(__viewContext.viewModel);
        viewmodelB.startPage().done(function() {
            $("#B1_5").focus();
            $(".tab-b-sidebar").click(function() {
                viewmodelB.isSelectFirstOfficeAndHistory = true;
                viewmodelB.startPage().done(function() {
                    $('#B1_5').focus();
                    nts.uk.ui.errors.clearAll();
                });
            });
            $(".tab-c-sidebar").click(function() {
                viewmodelC.isSelectFirstOfficeAndHistory = true;
                viewmodelC.startPage().done(function() {
                    $('#C1_5').focus();
                    nts.uk.ui.errors.clearAll();
                });
            });
            $(".tab-i-sidebar").click(function() {
                viewmodelI.isOnStartUp = true;
                viewmodelI.startPage().done(function() {
                    $("#I1_5").focus();
                    nts.uk.ui.errors.clearAll();
                });
            });
        });
    });
}
