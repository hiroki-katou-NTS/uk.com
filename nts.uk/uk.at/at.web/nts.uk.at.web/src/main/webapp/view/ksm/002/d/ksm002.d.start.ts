    __viewContext.ready(function() {
        let screenModel = new ksm002.d.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $(".ntsDatepicker.nts-input.ntsStartDatePicker.ntsDateRange_Component").focus();
            }); 
        });

