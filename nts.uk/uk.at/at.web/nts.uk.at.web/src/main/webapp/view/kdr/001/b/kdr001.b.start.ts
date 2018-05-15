module nts.uk.at.view.kdr001.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function(self) {
                __viewContext.bind(screenModel);
                setTimeout(function () { 
                    self.setFocus(); 
                    $("#rowSpecialHoliday > td > div > div > label").addClass("limited-label");
                    $("#rowSpecialHoliday > td > div > div > label > span.label").addClass("label-checkbox");
                }, 200);
            });
        });
}

