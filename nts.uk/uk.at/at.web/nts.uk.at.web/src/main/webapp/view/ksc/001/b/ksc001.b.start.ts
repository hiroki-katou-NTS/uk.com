module nts.uk.at.view.ksc001.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#ccgcomponent').ntsGroupComponent(screenModel.ccgcomponent).done(function() {
            screenModel.employeeList = ko.observableArray<UnitModel>([]);
            screenModel.applyKCP005ContentSearch([]);
            // Load employee list component
            $('#employeeSearch').ntsListComponent(screenModel.lstPersonComponentOption).done(function() {
            });
        });
    });
}
