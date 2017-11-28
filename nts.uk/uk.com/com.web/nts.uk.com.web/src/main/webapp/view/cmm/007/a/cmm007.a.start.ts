module nts.uk.com.view.cmm007.a {
    import viewModelTabB = nts.uk.com.view.cmm007.b.viewmodel;
    import viewModelTabC = nts.uk.com.view.cmm007.c.viewmodel;
    import viewModelTabD = nts.uk.com.view.cmm007.d.viewmodel;
    import viewModelTabE = nts.uk.com.view.cmm007.e.viewmodel;
    
    __viewContext.ready(function() {
        let tabB = new viewModelTabB.ScreenModel();
        let tabC = new viewModelTabC.ScreenModel();
        let tabD = new viewModelTabD.ScreenModel();
        let tabE = new viewModelTabE.ScreenModel();
        
        $.when(tabB.start_page(), tabC.start_page(), tabD.start_page(), tabE.start_page()).done(function(){
            let screenModel =  {
                systemDefine: tabB,
                temporaryAbsenceFr: tabC,
                planYearHolidayFr: tabD,
                overtimeWorkFr: tabE
            };
             __viewContext.bind(screenModel);
        }) 
    });
}
