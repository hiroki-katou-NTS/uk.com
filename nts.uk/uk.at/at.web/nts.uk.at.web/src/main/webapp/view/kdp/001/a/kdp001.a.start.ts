module nts.uk.at.view.kdp001.a {
     let __viewContext: any = window['__viewContext'] || {};
    
     __viewContext.ready(() => {

        __viewContext['viewModel'] = new viewmodel.ScreenModel();
        __viewContext['viewModel'].start_page().done(() => {
            __viewContext.bind(__viewContext['viewModel']);
            
        });
    });
}