module nts.uk.com.view.kwr002.b {
    __viewContext.ready(function() { 
        let screenModel = new ScreenModel(); 
        screenModel.start().done(()=>
            __viewContext.bind(screenModel)
        );
    }); 
}
