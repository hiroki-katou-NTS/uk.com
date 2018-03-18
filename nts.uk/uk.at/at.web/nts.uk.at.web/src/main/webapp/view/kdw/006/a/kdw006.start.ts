module nts.uk.at.view.kdw006 { 
 
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        let mode = nts.uk.ui.windows.getShared('mode'); 
        if (mode == 'MONTHLY'){
            $('.navigator li a#MONTHLY').trigger('click');
        }else if (mode == 'DAILY'){
            $('.navigator li a#DAILY').trigger('click');
        } else{
            $('.navigator li a#COMMON').trigger('click');
        }
        $('#button1').focus();
    });
}