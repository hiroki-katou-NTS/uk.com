module nts.uk.pr.view.ksu006.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#dateRangePickerLog').find('.ntsStartDatePicker').focus();
            $(document).delegate('#single-list', "iggridrowsrendered", function() {
//                if (screenModel.isFilterData) {
                    screenModel.eventClick(screenModel.dataLog());
//                }
            });
        });
    });
}