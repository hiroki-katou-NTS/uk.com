module nts.uk.pr.view.qmm016.a {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext["viewModel"].startPage().done(() => {
            __viewContext.bind(__viewContext["viewModel"]);
            if (__viewContext["viewModel"].updateMode()) {
                __viewContext["viewModel"].selectedTab('tab-2');
                $("#ui-id-2").parent().focus();
            } else {
                __viewContext["viewModel"].tabs()[1].enable(false);
                $("#A5_2").focus();
            }
        });
    });
}