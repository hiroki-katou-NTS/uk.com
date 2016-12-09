__viewContext.ready(function () {
    class ScreenModel {
    
        constructor() {
            var self = this;
        }
    }
    
    $('#popup-area').ntsPopup({
        position: {
            my: 'left top',
            at: 'left bottom',
            of: $('#show-popup')
        }
    });
    
    $('#show-popup').click(function () {
        $('#popup-area').closest('show');
    });
    
    $('.close-popup').click(function(){
        $('#popup-area').ntsPopup('hide');
    });
    
    this.bind(new ScreenModel());
    
});