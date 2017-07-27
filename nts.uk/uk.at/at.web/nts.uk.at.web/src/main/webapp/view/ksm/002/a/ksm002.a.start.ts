__viewContext.ready(function() {
    var viewModel = {
        viewModelA: new ksm002.a.viewmodel.ScreenModel(),
        viewModelB : new ksm002.b.viewmodel.ScreenModel()
    };
    viewModel.viewModelA.start().done(() => {
    __viewContext.bind(viewModel);
        //set tab index 
        $(".ntsCheckBox").attr("tabindex",5);
        //set focus
        $(".chkBox ").find("label").eq(0).focus();
    });
});