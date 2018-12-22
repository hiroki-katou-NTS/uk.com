module nts.uk.pr.view.qmm017.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext['screenModel'] = screenModel;
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            __viewContext['screenModel'].initScreenDTabData();
            __viewContext['screenModel'].screenDViewModel.showHintBox();
            setTimeout(function () {
                $('body').on('click', '.switch-button button', function() {
                    $(this).parent().parent().next().find('input').ntsError('clear')
                });
            }, 200)

        });
    });
}