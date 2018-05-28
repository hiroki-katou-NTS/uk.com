module nts.uk.com.view.kwr002.e {
   import viewModel = nts.uk.com.view.kwr002.e.viewmodel;
    __viewContext.ready(function() {
        let mainTab = new viewModel.ScreenModel();
    
        mainTab.start().done(function(screenModel:any){
            __viewContext.bind(mainTab); 
             $('#attendance_name').focus();
        });
    });
}