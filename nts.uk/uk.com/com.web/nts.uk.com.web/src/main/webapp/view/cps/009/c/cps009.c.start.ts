module nts.uk.com.view.cps009.c {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ViewModel();
        __viewContext.bind(__viewContext["viewModel"]);
        $("#codeInput").focus();
    });
}