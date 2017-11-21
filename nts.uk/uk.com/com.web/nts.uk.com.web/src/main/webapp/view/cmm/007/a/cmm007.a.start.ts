module nts.uk.com.view.cmm007.a {
    import viewModelTabB = nts.uk.com.view.cmm007.b.viewmodel;
    import viewModelTabC = nts.uk.com.view.cmm007.c.viewmodel;
    
    __viewContext.ready(function() {
        let tabB = new viewModelTabB.ScreenModel();
        let tabC = new viewModelTabC.ScreenModel();
        let screenModel =  {
            systemDefine: tabB,
            profile: tabC
        };
         __viewContext.bind(screenModel);
    });
}
