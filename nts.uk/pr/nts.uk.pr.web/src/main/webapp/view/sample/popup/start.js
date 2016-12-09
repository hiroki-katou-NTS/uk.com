__viewContext.ready(function () {
    var ScreenModel = (function () {
        function ScreenModel() {
            var self = this;
        }
        return ScreenModel;
    }());
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
    $('.close-popup').click(function () {
        $('#popup-area').ntsPopup('hide');
    });
    this.bind(new ScreenModel());
});
