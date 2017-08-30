module kcp009.a {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        // Load listComponent
            $('#emp-component').ntsLoadListComponent(screenModel.listComponentOption);
    });
}