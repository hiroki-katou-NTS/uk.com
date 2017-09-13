module nts.uk.com.view.cmm018.a {
    import cmm = nts.uk.com.view.cmm018;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        
        __viewContext.viewModel = {
            viewmodelA: new nts.uk.com.view.cmm018.a.viewmodelA.ScreenModel(),
            viewmodelB: new nts.uk.com.view.cmm018.a.viewmodelB.ScreenModel()
        };
        __viewContext.bind(__viewContext.viewModel);
    });
}
