module nts.uk.at.view.kmk010.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#selectMethodOutsideOT").focus();
            screenModel.addViewLanguage();
            service.initTooltip();
            $('.rate').each(function(index, element) {
                $(element).attr('tabindex', $(element).data('tab'));
            });
            
            $('.selectUnit').each(function(index, element) {
                $(element).attr('tabindex', $(element).data('tab'));
            });
            
            $('.selectRounding').each(function(index, element) {
                $(element).attr('tabindex', $(element).data('tab'));
            });
        }); 
    });
}