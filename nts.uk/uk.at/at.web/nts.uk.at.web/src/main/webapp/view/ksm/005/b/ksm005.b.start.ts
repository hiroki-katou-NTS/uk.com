module nts.uk.at.view.ksm005.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res) {
            __viewContext.bind(res);
        }).then(() => $(".ntsDatepicker.nts-input.ntsStartDatePicker.ntsDateRange_Component").focus());
    });
}