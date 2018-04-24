module nts.uk.at.view.kdr001.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            __viewContext.bind(self);
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.applyKCP005ContentSearch([]);
                // Load employee list component
                $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption).done(function() {
                });
            });
            let user: any = __viewContext.user;
            nts.uk.characteristics.restore("UserSpecific_" + user.employeeId).done(function(userSpecific) {
                if (userSpecific) {
                    self.holidayRemainingSelectedCd(userSpecific.outputItemSettingCode);
                    self.selectedCode(userSpecific.pageBreakAtr);
                }
            });
        });
    });
}
