module nts.uk.com.view.jmm018.a {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        let mainTab = new viewmodel.ScreenModel();

        blockUI.grayout();
        
        mainTab.start_page().done(function(screenModel){
            __viewContext.bind(screenModel);
            screenModel.onSelectTabB();
            $('#nodeText').focus();
            $('#nodeText').blur();
            
            $('#useEventOrMenu').focus();
            $('#useEventOrMenu').blur();
            
            $('#useNotice').focus();
            $('#useNotice').blur();
            
            $('#useApproval').focus();
            $('#useApproval').blur();
            
            $('#treegrid_container').focus();
            blockUI.clear(); 
        });
    });
}