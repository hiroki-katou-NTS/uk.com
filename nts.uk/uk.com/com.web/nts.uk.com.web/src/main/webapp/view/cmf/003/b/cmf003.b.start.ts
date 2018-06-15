module nts.uk.com.view.cmf003.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            __viewContext.bind(self);
            $("#B4_2").focus();
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                self.applyKCP005ContentSearch([]);
                // Load employee list component
                $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption).done(function() {
                    $('#dateRangePickerPeriod').find('input').first().focus();
                });
            });
        });
    });
}
