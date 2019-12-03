module jhn011.b {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        __viewContext['viewModel'] = new viewmodel.ViewModel();
        __viewContext.bind(__viewContext['viewModel']);
        
        
            let styles = '',  layout = $('.ntsControl.layout-control.readonly').get(0),
                              B221 = $('#B221').get(0),
                              contents_area = $('#contents-area').get(0);
            let panelHeight = window.innerHeight - layout.getBoundingClientRect().top,
                B221_panel =  window.innerHeight - B221.getBoundingClientRect().top,
                contentsAreaHeight = window.innerHeight - contents_area.getBoundingClientRect().top;
            if(panelHeight <= 70) {
                styles = '.drag-panel { max-height: 70px !important;height: 70px !important; }';   
            } else {
                styles = '.drag-panel { max-height: ' + panelHeight + 'px !important;' + 'height: ' + panelHeight + 'px !important;}';
            } 
            $( window ).resize(function() {
                let panelHeightResize = window.innerHeight - layout.getBoundingClientRect().top,
                    B221_panelResize =  window.innerHeight - B221.getBoundingClientRect().top,
                    contentsAreaHeightResize = window.innerHeight - contents_area.getBoundingClientRect().top;
                if (panelHeightResize <= 90) {
                    $(".drag-panel").attr(`style`, `max-height: 80px !important;height: 80px !important;`);
                    $('#B221').attr(`style`, `max-height: 525px !important;height: 525px !important;`+ `magin-bottom: 5px !important;`);
                    $("#B221_4").igGrid("option", "height",368);
                    $("#contents-area").attr(`style`, `height:` +`calc(100vh - `+ (contents_area.getBoundingClientRect().top + 14)+ `px);`);
                } else {
                    $(".drag-panel").attr(`style`, `max-height: ` + (panelHeightResize - 20) + `px !important;` + `height: ` + (panelHeightResize - 20) + `px !important;`);
                    $("#B221").attr(`style`, `max-height: ` + (B221_panelResize -10) + `px !important;` + `height: ` + (B221_panelResize - 10) + `px !important;` + `magin-bottom: 5px !important;`);
                    $("#B221_4").igGrid("option", "height", B221_panelResize - 140);
                }
            });
            let styleSheet = document.createElement("style");
            styleSheet.type = "text/css";
            styleSheet.innerText = styles;
            document.head.appendChild(styleSheet);
    });
}