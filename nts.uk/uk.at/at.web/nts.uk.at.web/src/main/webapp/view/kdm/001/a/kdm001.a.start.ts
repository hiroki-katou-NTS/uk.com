module nts.uk.at.view.kdm001.a {
    __viewContext.ready(() => {
        var viewmodelA = new nts.uk.at.view.kdm001.a.viewmodel.ScreenModel();
        var viewmodelB = new nts.uk.at.view.kdm001.b.viewmodel.ScreenModel();
        __viewContext.viewModel = {
            viewmodelA: viewmodelA,
            viewmodelB: viewmodelB
        };
        __viewContext.bind(__viewContext.viewModel);
        
        viewmodelA.startPage().done(() => {
            $('#ccgcomponentA').ntsGroupComponent(viewmodelA.ccgcomponent);
            $('#emp-componentA').focus();
            nts.uk.ui.block.clear();
            $(".tab-a").click(() => {
                viewmodelA.updateDataList(false);
            });
            $(".tab-b").click(() => {
                viewmodelB.startPage().done(() => {
                    $('#emp-component').focus();
                    nts.uk.ui.block.clear();
                });
            });
        });
    });
}
