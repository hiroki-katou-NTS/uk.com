module nts.uk.at.view.kmk003.sample {
    __viewContext.ready(function() {
        var screenModel = new viewModel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#fixed-table').focus();
            document.getElementById('fixed-table').addEventListener('timerangedatachange', function(event) {
                console.log('changed');
            })
        });
    });
}