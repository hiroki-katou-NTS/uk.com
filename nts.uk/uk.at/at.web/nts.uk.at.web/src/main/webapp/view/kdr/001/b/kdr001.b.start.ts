module nts.uk.at.view.kdr001.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function(self) {
                __viewContext.bind(screenModel);
                // focus
                self.setFocus();
            setTimeout(function() {self.setFocus(); }, 200);
            });
        });
}

