module nts.uk.at.view.kdr002.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext['viewContext'] = screenModel;
        screenModel.startPage().done(function(self) {
            __viewContext.bind(self);
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.applyKCP005ContentSearch([]);
                // Load employee list component
                $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption).done(function() {
                });

                let $startDate = $("#ccg001-search-period .ntsStartDatePicker"),
                    $endDate = $("#ccg001-search-period .ntsEndDatePicker");
                if ($startDate) {
                    $startDate.change(function(data: any) {
                        if (data.currentTarget.value == screenModel.startDateString()) return;
                        screenModel.startDateString(data.currentTarget.value);
                    });
                }
                if ($endDate) {
                    $endDate.change(function(data: any) {
                        screenModel.endDateString(data.currentTarget.value);
                    });
                }

            });

            $('#date_type_group').focus();
        });
    });
}
