module nts.uk.com.view.cdl002.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        // Load Component
        $('#emp-component').ntsListComponent(screenModel.listComponentOption).done(function() {
            $('#emp-component').focusComponent();
        });
    });
}
