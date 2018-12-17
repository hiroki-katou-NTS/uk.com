module nts.uk.pr.view.qmm016.a {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext["viewModel"].startPage().done(() => {
            __viewContext.bind(__viewContext["viewModel"]);
            if (!__viewContext["viewModel"].updateMode())
                $("#A5_2").focus();
            else
                $("#A5_3").focus();
        });
    });
}