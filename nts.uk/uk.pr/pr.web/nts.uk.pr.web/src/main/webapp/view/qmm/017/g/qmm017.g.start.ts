module nts.uk.pr.view.qmm017.g {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        setTimeout(function(){
            $('#G1_12').attr('tabindex', '3');
        }, 100)
    });
}