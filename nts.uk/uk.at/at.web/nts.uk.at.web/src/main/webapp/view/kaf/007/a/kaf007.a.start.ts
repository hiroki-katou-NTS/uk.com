module nts.uk.at.view.kaf007.a {
    __viewContext.ready(function() {
        var self = new nts.uk.at.view.kaf007.a.viewmodel.ScreenModel();
        self.kaf000_a = new nts.uk.at.view.kaf000.a.viewmodel.ScreenModel();
        //KAF000_A
           
            self.startPage().done(function() {
                __viewContext.bind(self);
                self.kaf000_a.start(self.employeeID, 1, 2, self.targetDate).done(function() {
                    nts.uk.ui.block.clear();
                });
            }).fail((res) => {
                nts.uk.ui.dialog.alertError({ messageId: res.messageId }).then(function() {
                    nts.uk.request.jump("com", "view/ccg/008/a/index.xhtml");
                });
                nts.uk.ui.block.clear();
            });
        
    });
}