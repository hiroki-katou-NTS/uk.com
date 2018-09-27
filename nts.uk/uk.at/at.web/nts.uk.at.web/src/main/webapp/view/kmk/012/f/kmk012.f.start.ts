module nts.uk.at.view.kmk012.f {
    
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        blockUI.grayout();
        let screenModel  = new viewmodel.ScreenModel();
        screenModel .start_page().done(function(){
            __viewContext.bind(screenModel ); 
            $('#operationStartDate').focus();
            blockUI.clear();
        });
    });
}
