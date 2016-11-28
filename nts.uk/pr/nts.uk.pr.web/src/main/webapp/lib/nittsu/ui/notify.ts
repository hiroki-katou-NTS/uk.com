module nts.uk.ui.notify {

    
    
    module error {
        
        ui.documentReady.add(() => {
            $('#func-notifier-errors').position({ my: 'left+5 top-5', at: 'left bottom', of: $('#functions-area') });
        });
        
    }
}