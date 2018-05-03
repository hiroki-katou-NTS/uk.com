module nts.uk.at.view.cmf003.d {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            __viewContext.bind(self);
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.applyKCP005ContentSearch([]);
                // Load employee list component
                $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption).done(function() {
                    $('#dateRangePickerPeriod').find('input').first().focus();
                });
            });
        });
    });
}
