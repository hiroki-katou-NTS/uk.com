module nts.uk.pr.view.kmf001.l {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.selectedMaxManageSemiVacation.subscribe(function(value) {
                $('#max-number-company').ntsError('clear');
            });
            screenModel.selectedManageUpperLimitDayVacation.subscribe(function(value) {
                $('#time-max-day-company').ntsError('clear');
            });
        });
    });
}