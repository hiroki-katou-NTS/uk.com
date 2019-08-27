module nts.uk.com.view.jmm018.a {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        let mainTab = new viewmodel.ScreenModel();

        blockUI.grayout();
        
        mainTab.start_page().done(function(screenModel){
            __viewContext.bind(screenModel);
            screenModel.onSelectTabB();
            
            //subcribe the change in the tree
            let lisTree = screenModel.eventManage().listEventId();
            _.forEach(lisTree, (value) => {
                if(value.listChild.length > 0){
                    let disable = _.map(value.listChild, 'key');
                    // enable/disable child
                    if(value.useEventOrMenu == 0){
                        $("#treegrid").ntsTreeView("disableRows", disable);
                    }
                }                            
            });
            
            
            $('#nodeText').focus();
            $('#nodeText').blur();
            
            $('#useEventOrMenu').focus();
            $('#useEventOrMenu').blur();
            
            $('#useNotice').focus();
            $('#useNotice').blur();
            
            $('#useApproval').focus();
            $('#useApproval').blur();
            
            blockUI.clear(); 
        });
    });
}