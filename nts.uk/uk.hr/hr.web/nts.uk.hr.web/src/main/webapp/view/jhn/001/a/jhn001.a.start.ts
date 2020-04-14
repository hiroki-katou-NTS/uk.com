module jhn001.a {
    let __viewContext: any = window['__viewContext'] || {};

    __viewContext.ready(function() {

        //get param url
        let url = $(location).attr('search');
        let reportId: number = url.split("=")[1];

        __viewContext['viewModel'] = new viewmodel.ViewModel(reportId);
        __viewContext.bind(__viewContext['viewModel']);
        $('.input-wrapper').addClass("notranslate");

        setTimeout(() => $(window).trigger('resize'), 300);
        
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
    
    let layout = $('.ntsControl.layout-control.readonly').get(0),
        B221 = $('#B221').get(0);
    
    let panelHeightResize = window.innerHeight - layout.getBoundingClientRect().top,
        B221_panelResize = window.innerHeight - B221.getBoundingClientRect().top;
    
    if (panelHeightResize <= 215) {
        $('#B221').attr(`style`, `max-height: 488px !important;height: 488px !important;` + `magin-bottom: 5px !important;`);
        $("#B221_4").igGrid("option", "height", 368);
    } else {
        let heighGridCal = ((B221_panelResize - 93) % 23) == 0 ? B221_panelResize - 100 : Math.floor((B221_panelResize - 93) / 23) * 23,
            heightGridSet = heighGridCal < 368 ? 368 : (heighGridCal - 23),
            dragPanelSet = heighGridCal < 368 ? 100 : (panelHeightResize - 170);

        $("#B221").attr(`style`, `max-height: ` + (heightGridSet + 95) + `px !important;` + `height: ` + (heightGridSet + 95) + `px !important;` + `magin-bottom: 5px !important;`);
        $("#B221_4").igGrid("option", "height", heightGridSet);
    }
   
});
