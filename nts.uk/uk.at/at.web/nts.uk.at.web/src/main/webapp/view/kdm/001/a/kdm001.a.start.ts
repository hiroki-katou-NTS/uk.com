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
                viewmodelA.updateDataList(true);
            });
            $(".tab-b").click(() => {
                if (viewmodelB.isOnStartUp) {
                    viewmodelB.startPage().done(() => {
                        $('#emp-component').focus();
                        nts.uk.ui.block.clear();
                    });
                } else {
                    viewmodelB.getSubstituteDataList(viewmodelB.getSearchCondition(), true).done(() => {
                        $('#emp-component').focus();
                        nts.uk.ui.block.clear();
                    });
                }
            });
        });
    });
}
