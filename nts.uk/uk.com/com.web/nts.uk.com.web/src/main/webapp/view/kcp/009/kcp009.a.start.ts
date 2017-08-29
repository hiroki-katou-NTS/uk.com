module kcp009.a {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        // Load listComponent
            $('#emp-list').ntsLoadListComponent(screenModel.listComponentOption);
    });
}