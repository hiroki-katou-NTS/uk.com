module nts.uk.at.view.kdm002.b {
    __viewContext.ready(function() {
        __viewContext["viewModel"] = new viewmodel.ScreenModel();
        __viewContext["viewModel"].startPage().done(function(){
            __viewContext.bind(__viewContext["viewModel"]);
        });
        $('#BTN_STOP').focus();
    });
}