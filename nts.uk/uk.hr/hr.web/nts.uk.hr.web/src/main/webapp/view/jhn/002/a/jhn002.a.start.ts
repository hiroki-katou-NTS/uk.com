module jhn002.a {
    let __viewContext: any = window['__viewContext'] || {};

    __viewContext.ready(function() {

        //get param url
        let url = $(location).attr('search');
        let reportId: number = url.split("=")[1];

        __viewContext['viewModel'] = new viewmodel.ViewModel(reportId);
        __viewContext.bind(__viewContext['viewModel']);
        $('.input-wrapper').addClass("notranslate");

        setTimeout(() => $(window).trigger('resize'), 100);
        
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
