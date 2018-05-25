module nts.uk.at.view.kdm001.a {
    __viewContext.ready(function() {
        var screenModelA = new nts.uk.at.view.kdm001.a.viewmodel.ScreenModel();
        var screenModelB = new nts.uk.at.view.kdm001.b.viewmodel.ScreenModel();
        screenModelA.startPage().done(function() {
            __viewContext.viewModel = {
                viewmodelA: screenModelA,
                viewmodelA: screenModelB
            };
            __viewContext.bind(__viewContext.viewModel);
            $('#emp-component').focus();
        });
        
        screenModelB.startPage().done(function() {
            $('#B2_1').focus();
        });
    });
}
