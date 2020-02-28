module jhn001.c {
    let __viewContext: any = window['__viewContext'] || {};
    
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new viewmodel.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);
        
        setTimeout(() => $(window).trigger('resize'), 100);
        
        $(document.body).on('click', '.nts-guide-link', () => {
            setTimeout(() => $(window).trigger('resize'), 5);
        });
    });
}
