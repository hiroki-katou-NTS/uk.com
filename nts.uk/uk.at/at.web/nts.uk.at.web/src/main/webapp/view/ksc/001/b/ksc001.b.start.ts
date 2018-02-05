module nts.uk.at.view.ksc001.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            __viewContext.bind(screenModel);

            // load ccg component
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.applyKCP005ContentSearch([]);
                $('.btn_showhide').attr('tabindex', '2');

                // Load employee list component
                $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption).done(function() {
                    $('#dateRangePickerPeriod').find('input').first().focus();
                    // clear block ui
                    nts.uk.ui.block.clear();
                });
            });
        });

    });
}
