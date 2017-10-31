module nts.uk.at.view.kdw001.f {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
        
        $("#contents-area").on("click", ".open-dialog-i", function() {
            console.log($(this).data("id"));
            nts.uk.ui._viewModel.content.openDialogI.call(nts.uk.ui._viewModel.content);
        });
        
    });
}