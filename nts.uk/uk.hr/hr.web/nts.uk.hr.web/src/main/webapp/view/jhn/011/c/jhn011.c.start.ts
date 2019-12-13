module jhn011.c {
    let __viewContext: any = window['__viewContext'] || {};
    
    __viewContext.ready(function() {
        
        __viewContext['viewModel'] = new vm.ViewModel();
        
        __viewContext.bind(__viewContext['viewModel']);
        
        __viewContext['viewModel'].start();
        
        setTimeout(() => $(window).trigger('resize'), 100);

        $(document.body).on('click', '.nts-guide-link', () => {
            
            setTimeout(() => $(window).trigger('resize'), 5);
            
        });
    });
}

$(window).on('resize', () => {
    
    let $content = $(".drag-panel");
    
    if ($content.length) {
        
        var currentDialog = nts.uk.ui.windows.getSelf(),
            withResize = (currentDialog.$dialog.width() - $content[0].getBoundingClientRect().left - 90),
            widthDraggable = withResize + 300;

        $(".layout-control.dragable").attr("style", "max-width:" + widthDraggable + "px!important;" + "width:" + widthDraggable + " !important;");

        $(".drag-panel").attr("style", "max-width:" + (withResize) + "px!important;" + "width:" + (withResize) + " !important;");
    }
});