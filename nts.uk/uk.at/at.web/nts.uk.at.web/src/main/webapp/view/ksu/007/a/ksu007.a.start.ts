module nts.uk.at.view.ksu007.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(self) {
            __viewContext.bind(self);
            $('#ccgcomponent').ntsGroupComponent(self.ccgcomponent).done(function() {
                self.employeeList = ko.observableArray<UnitModel>([]);
                self.applyKCP005ContentSearch([]);
                // Load employee list component
                $('#employeeSearch').ntsListComponent(self.lstPersonComponentOption).done(function() {
                    $('#employeeSearch').focus();
                    if(self.employeeList().length <= 0){
                        $('#ccg001-btn-search-drawer').trigger('click');  
                    }
                });
            });
            //Load ScheduleBatchCorrect
            self.findScheduleBatchCorrectSetting().done(function(e) {
                 if(!nts.uk.util.isNullOrUndefined(e)){                 
                    self.periodDate({
                        startDate: e.startDate,
                        endDate: e.endDate
                    });
                    self.workTypeInfo(e.worktypeCode);                  
                    self.workTimeInfo(e.worktimeCode);
                    self.workTypeCode(e.worktypeCode.split(" ")[0]);
                    self.workTimeCode(e.worktimeCode.split(" ")[0]);
                }                       
             });
        });
    });
}
