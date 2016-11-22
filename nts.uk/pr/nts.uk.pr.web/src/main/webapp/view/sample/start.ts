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

    var vm = {
        hoge: ko.observable('aaa')
    }; // developer's view model
    this.bind(vm);
    
});