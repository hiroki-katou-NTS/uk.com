module nts.uk.pr.view.qmm041.a {
    __viewContext.ready(() => {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            $('#com-ccg001').ntsGroupComponent(screenModel.ccgComponent);
            $('#emp-component').ntsLoadListComponent(screenModel.listComponentOption);
            $('#emp-component').focus();
        });
    });
}