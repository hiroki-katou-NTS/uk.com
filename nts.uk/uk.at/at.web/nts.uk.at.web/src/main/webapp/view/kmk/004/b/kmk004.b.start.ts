module nts.uk.at.view.kmk004.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            
            let self = screenModel;
            
            _.defer(() => {
                $("#sidebar").ntsSideBar("active", 3);
                $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                    self.employeeList = ko.observableArray<UnitModel>([]);
                    self.applyKCP005ContentSearch([]);
                    // Load employee list component
                    $('#list-employee').ntsListComponent(self.lstPersonComponentOption).done(function() {
                        if(self.employeeList().length <= 0){
                            $('#ccg001-btn-search-drawer').trigger('click');  
                        }                                    
                    });
                });
            });
        });
    });
}