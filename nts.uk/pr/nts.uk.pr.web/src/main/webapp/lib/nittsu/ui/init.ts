module nts.uk.ui.init {
    
    var start: any;
    
    __viewContext.ready = function (callback: () => void) {
        start = callback;
    };
    
    $(function () {
        start();
    });
}