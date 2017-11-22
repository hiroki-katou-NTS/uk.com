module nts.uk.com.view.cmm007.a {
    import viewModelTabB = nts.uk.com.view.cmm007.b.viewmodel;
    import viewModelTabC = nts.uk.com.view.cmm007.c.viewmodel;
    
    __viewContext.ready(function() {
        let tabB = new viewModelTabB.ScreenModel();
        let tabC = new viewModelTabC.ScreenModel();
        
        $.when(tabB.start_page(), tabC.start_page()).done(function(){
            let screenModel =  {
                systemDefine: tabB,
                temporaryAbsenceFr: tabC
            };
             __viewContext.bind(screenModel);
        }) 
    });
}
