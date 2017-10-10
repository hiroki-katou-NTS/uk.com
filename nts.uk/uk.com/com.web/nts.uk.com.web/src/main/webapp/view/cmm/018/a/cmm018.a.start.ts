module nts.uk.com.view.cmm018.a.start {
    import cmm018 = nts.uk.com.view.cmm018;
    let transferData = {screen: 'Menu'};
    let __viewContext: any = window["__viewContext"] || {};
    
    __viewContext.ready(function() {
        if(__viewContext.transferred.value != null){
            transferData = __viewContext.transferred.value;
        }
        __viewContext.viewModel = {
            viewmodelA: new cmm018.a.viewmodelA.ScreenModel(transferData),
            viewmodelB: new cmm018.a.viewmodelB.ScreenModel()
        };
             __viewContext.bind(__viewContext.viewModel);
    });
}
