module nts.uk.at.view.kdm001.a {
    __viewContext.ready(function() {
        var screenModelA = new nts.uk.at.view.kdm001.a.viewmodel.ScreenModel();
        var screenModelB = new nts.uk.at.view.kdm001.b.viewmodel.ScreenModel();
        screenModelA.startPage().done(function() {
            screenModelB.startPage().done(function() {
                __viewContext.viewModel = {
                    viewmodelA: screenModelA,
                    viewmodelB: screenModelB
                };
                __viewContext.bind(__viewContext.viewModel);
                
                $('#B2_1').focus();
            });
            
            $('#emp-component').focus();
        });
    });
}
