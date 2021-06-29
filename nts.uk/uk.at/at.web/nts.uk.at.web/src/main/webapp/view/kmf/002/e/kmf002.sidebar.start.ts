module nts.uk.at.view.kmf002.e.sidebar {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        let mainTab = new viewmodel.SidebarScreenModel();
        mainTab.startPage(0).done(function(screenModel){
            __viewContext.bind(screenModel);
            $ ("#managePubHD").focus();
        });
    });
}
