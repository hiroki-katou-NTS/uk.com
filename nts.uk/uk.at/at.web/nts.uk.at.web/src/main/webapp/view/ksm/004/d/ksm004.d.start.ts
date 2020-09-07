module ksm004.d {
    __viewContext.ready(function() {
        var screenModel = new ksm004.d.viewmodel.ScreenModel();

        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        }).then(function() {
            //forcus start range date picker
            $(".ntsDatepicker.nts-input.ntsStartDatePicker.ntsDateRange_Component").focus();
        });


    });
}   