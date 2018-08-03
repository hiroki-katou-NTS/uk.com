module nts.uk.com.view.cli003.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            __viewContext.bind(self);
            $('#ccgcomponent').ntsGroupComponent(self.ccg001ComponentOption).done(function() {
                self.applyKCP005ContentSearchOperator([]);
                // Load employee list component
                $('#employeeSearchD').ntsListComponent(self.listComponentOptionOperator).done(function() {
                });
                
                $('#employeeSearch').ntsListComponent(self.listComponentOption).done(function() {
                });
            });
            $('#list-box_b').focus();

        });

    });
}