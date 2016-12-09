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
    $('#toggle-popup').click(function () {
        $('#popup-area').ntsPopup('toggle');
    });
    $('.close-popup').click(function () {
        $('#popup-area').ntsPopup('hide');
    });
    $('#destroy-popup').click(function () {
        $('#popup-area').ntsPopup('destroy');
        $('#toggle-popup').prop("disabled", true);
        $('#show-popup').prop("disabled", true);
    });
    $('#reset-popup').click(function () {
        $('#popup-area').ntsPopup({
            position: {
                my: 'left top',
                at: 'left bottom',
                of: $('#show-popup')
            },
            dismissible: false
        });
        $('#toggle-popup').prop("disabled", false);
        $('#show-popup').prop("disabled", false);
    });
    this.bind(new ScreenModel());
});
