module nts.uk.pr.view.ccg007.c {
    __viewContext.ready(function() {
        if ($('#contents-area').data('loaded')) {
            $('[id=contents-area]:eq(1)').remove();
            return;
        }
        $('#contents-area').data('loaded', true);
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#company-code-inp').focus();
        });
    });
}