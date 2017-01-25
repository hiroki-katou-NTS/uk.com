__viewContext.ready(function() {
    let screenModel = new nts.uk.pr.view.qmm005.e.viewmodel.viewModel();
    this.bind(screenModel);

    $('div[data-help]').each((i, element) => {
        $(element).find('.ntsCheckBox').append($('<span>', {
            'class': 'label helper',
            'text': $(element).data('help')
        }));
    });
});