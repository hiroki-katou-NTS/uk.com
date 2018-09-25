module nts.uk.pr.view.qmm008.a {
    __viewContext.ready(function() {
        var viewmodelB = new nts.uk.pr.view.qmm008.b.viewmodel.ScreenModel();
        var viewmodelC = new nts.uk.pr.view.qmm008.c.viewmodel.ScreenModel();
        var viewmodelI = new nts.uk.pr.view.qmm008.i.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelB: viewmodelB,
            viewmodelC: viewmodelC
//            ,
//            viewmodelI: viewmodelI
        };
        __viewContext.bind(__viewContext.viewModel);
        viewmodelB.startPage().done(function() {
            $("#B1_5").focus();
            let isStartC, isStartI = false;
            $(".tab-b-sidebar").click(function() {
                setTimeout(function(){
                    $('#B1_5').focus();
                }, 50);
            });
            $(".tab-c-sidebar").click(function() {
                if (!isStartC){
                    viewmodelC.startPage().done(function() {
                        $('#C1_5').focus();
                        isStartC = true;
                    });
                }
                setTimeout(function(){
                    $('#C1_5').focus();
                }, 50);
            });
//            $(".tab-i").click(function() {
//                if (!isStartI){
//                    viewmodelI.startPage().done(function() {
//                        $('#emp-component').focus();
//                        isStartI = true;
//                    });
//                }
//            });
        });  
    });
}
