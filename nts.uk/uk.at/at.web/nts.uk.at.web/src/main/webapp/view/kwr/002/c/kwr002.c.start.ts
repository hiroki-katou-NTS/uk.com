module nts.uk.com.view.kwr002.c {
   import blockUI = nts.uk.ui.block;
   import viewModel = nts.uk.com.view.kwr002.c.viewmodel;
    __viewContext.ready(function() {
        let mainTab = new viewModel.ScreenModel();
    
        mainTab.start_page().done(function(screenModel:any){
            __viewContext.bind(mainTab); 
            $('#ui-id-1').focus();
        });
    });
}