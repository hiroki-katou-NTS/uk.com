module jhn001.c {
    let __viewContext: any = window['__viewContext'] || {};
    
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new viewmodel.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);
        
        setTimeout(() => $(window).trigger('resize'), 1000);
        
        $(document.body).on('click', '.nts-guide-link', () => {
            setTimeout(() => $(window).trigger('resize'), 5);
        });
    });
}


$(window).on('resize', () => {
    const $content = $('#contents-area');

    if ($content.length) {
        const bound = $content.get(0).getBoundingClientRect();

        $content.css('height', `calc(100vh - ${bound.top + 20}px)`);
    }
});