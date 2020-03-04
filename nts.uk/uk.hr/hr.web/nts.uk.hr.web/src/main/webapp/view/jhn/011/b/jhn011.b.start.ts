module jhn011.b {
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

$(window).on('resize', () => {
    const $content = $('#contents-area');

    if ($content.length) {
        const bound = $content.get(0).getBoundingClientRect();

        $content.css('height', `calc(100vh - ${bound.top + 14}px)`);
    }

    let layout = $('.ntsControl.layout-control.readonly').get(0),
        B221 = $('#B221').get(0);
    
    let panelHeightResize = window.innerHeight - layout.getBoundingClientRect().top,
        B221_panelResize = window.innerHeight - B221.getBoundingClientRect().top;
    
    if (panelHeightResize <= 90) {
        $(".drag-panel").attr(`style`, `max-height: 100px !important;height: 100px !important;`);
        $('#B221').attr(`style`, `max-height: 488px !important;height: 488px !important;` + `magin-bottom: 5px !important;`);
        $("#B221_4").igGrid("option", "height", 368);
        //$("#B221_4_container").attr(`style`, `max-height: ` + 368 + `px !important;` + `height: ` + 368 + `px !important;`);
    } else {
        let heighGridCal = ((B221_panelResize - 120) % 23) == 0 ? B221_panelResize - 120 : Math.floor((B221_panelResize - 120) / 23) * 23,
            heightGridSet = heighGridCal < 368 ? 368 : (heighGridCal - 23),
            dragPanelSet = heighGridCal < 368 ? 100 : (panelHeightResize - 43);

        $(".drag-panel").attr(`style`, `max-height: ` + dragPanelSet + `px !important;` + `height: ` + dragPanelSet + `px !important;`);
        $("#B221").attr(`style`, `max-height: ` + (heightGridSet + 120) + `px !important;` + `height: ` + (heightGridSet + 120) + `px !important;` + `magin-bottom: 5px !important;`);
        $("#B221_4").igGrid("option", "height", heightGridSet);
        //$("#B221_4_container").attr(`style`, `max-height: ` + heightGridSet + `px !important;` + `height: ` + heightGridSet + `px !important;`);
    }
});