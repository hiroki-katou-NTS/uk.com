module nts.uk.at.view.kmf002.f {  
    
    __viewContext.ready(function() {
        let mainTab = new viewmodel.ScreenModel();
        
        mainTab.startPage().done(function(screenModel){
            __viewContext.bind(mainTab); 
			$( "#selectUnitCheck" ).focus();
        });
    });
}
