module nts.uk.com.view.kfp001.h {
    __viewContext.ready(function() {
            var screenModel = new viewmodel.ScreenModel();
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
                screenModel.bindLinkClick();
                $(".ntsStartDatePicker").focus();
                $('#list_container').attr('tabindex', '4');
            });
    });
}