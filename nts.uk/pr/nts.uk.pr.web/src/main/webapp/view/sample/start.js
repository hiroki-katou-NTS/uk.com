__viewContext.ready(function () {
    $('#popup-area').ntsPopup({
        position: {
            my: 'left top',
            at: 'left bottom',
            of: $('#show-popup')
        }
    });
    $('#show-popup').click(function () {
        $('#popup-area').ntsPopup('show');
    });
    var vm = {}; // developer's view model
    this.bind(vm);
});
