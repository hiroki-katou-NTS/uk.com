module ksm004.d {
    __viewContext.ready(function() {
        var screenModel = new ksm004.d.viewmodel.ScreenModel();

            __viewContext.bind(screenModel);
            //forcus start range date picker
            $(".ntsDatepicker.nts-input.ntsStartDatePicker.ntsDateRange_Component").focus();

    });
}   