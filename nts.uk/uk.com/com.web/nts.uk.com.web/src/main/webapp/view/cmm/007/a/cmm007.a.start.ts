module nts.uk.com.view.cmm007.a {
    import viewModelTabB = nts.uk.com.view.cmm007.b.viewmodel;
    import viewModelTabC = nts.uk.com.view.cmm007.c.viewmodel;
    import viewModelTabD = nts.uk.com.view.cmm007.d.viewmodel;
    
    __viewContext.ready(function() {
        let tabB = new viewModelTabB.ScreenModel();
        let tabC = new viewModelTabC.ScreenModel();
        let tabD = new viewModelTabD.ScreenModel();
        
        $.when(tabB.start_page(), tabC.start_page(), tabD.start_page()).done(function(){
            let screenModel =  {
                systemDefine: tabB,
                temporaryAbsenceFr: tabC,
                planYearHolidayFr: tabD
            };
             __viewContext.bind(screenModel);
        }) 
    });
}
