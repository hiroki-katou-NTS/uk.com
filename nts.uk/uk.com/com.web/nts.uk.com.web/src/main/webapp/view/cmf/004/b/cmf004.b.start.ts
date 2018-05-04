module nts.uk.com.view.cmf004.b {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function(self) {
            __viewContext.bind(__viewContext['screenModel']);
            $('#ccg001component').ntsGroupComponent(self.ccg001ComponentOption);
            $('#kcp005component').ntsListComponent(self.kcp005ComponentOption);
        });
    });
}