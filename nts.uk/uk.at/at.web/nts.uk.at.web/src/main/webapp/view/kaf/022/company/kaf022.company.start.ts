module nts.uk.at.view.kaf022.company {
    __viewContext.ready(function() {
        nts.uk.ui.block.grayout();
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext["viewModel"].start().done(() => {
            __viewContext.bind(__viewContext["viewModel"]);
        });
    });
}