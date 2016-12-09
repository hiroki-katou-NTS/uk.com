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
        },
        dismissible: false
    });
    $('#show-popup').click(function () {
        $('#popup-area').ntsPopup('show');
    });
    $('.close-popup').click(function () {
        $('#popup-area').ntsPopup('hide');
    });
    $('#destroy-popup').click(function () {
        $('#popup-area').ntsPopup('destroy');
        $('#show-popup').prop("disabled", true);
    });
    this.bind(new ScreenModel());
});
