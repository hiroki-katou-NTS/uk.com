module nts.uk.at.view.kmf002.a {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        let mainTab = new viewmodel.ScreenModel();

        
        mainTab.start_page(1).done(function(screenModel){
            __viewContext.bind(mainTab);
//            screenModel.onSelectTabA(); 
        });
    });
}
