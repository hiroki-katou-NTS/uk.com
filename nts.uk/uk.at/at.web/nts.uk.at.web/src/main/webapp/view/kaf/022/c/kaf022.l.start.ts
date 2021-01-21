module nts.uk.at.view.kaf022.l {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext.bind(__viewContext["viewModel"]);
        __viewContext["viewModel"].start().done(() => {

        });
    });
}