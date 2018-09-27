module nts.uk.at.view.kdw002.a {  
    __viewContext.ready(function() {
        let dataShare:any;
         this.transferred.ifPresent(data => {
            console.log(data);
             dataShare = data;
        });
        
        let screenModel = new viewmodel.ScreenModel(dataShare);
            __viewContext.bind(screenModel);
    });
}

$(document).on('click', '[data-prevent-click="true"]', evt => {
    $.blockUI({
        message: null,
        overlayCSS: { opacity: 0 },
    });
}).ajaxStop(() => {
    setTimeout(() => {
        $.unblockUI();
    }, 500);
});

