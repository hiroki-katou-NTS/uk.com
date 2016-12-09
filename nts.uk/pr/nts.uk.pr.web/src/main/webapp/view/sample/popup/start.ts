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
        },
        dismissible: false
    });
    
    $('#show-popup').click(function () {
        $('#popup-area').ntsPopup('show');
    });
    $('#toggle-popup').click(function () {
        $('#popup-area').ntsPopup('toggle');
    });
    $('.close-popup').click(function(){
        $('#popup-area').ntsPopup('hide');
    });
    $('#destroy-popup').click(function() {
       $('#popup-area').ntsPopup('destroy');
       $('#toggle-popup').prop("disabled",true);
       $('#show-popup').prop("disabled",true);
    });
    this.bind(new ScreenModel());
    
});