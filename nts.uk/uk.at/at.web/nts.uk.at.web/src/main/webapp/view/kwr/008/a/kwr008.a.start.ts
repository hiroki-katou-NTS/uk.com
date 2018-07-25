module nts.uk.at.view.kwr008.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            $('#A1_1').focus();
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
