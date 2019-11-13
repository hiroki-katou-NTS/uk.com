module nts.uk.pr.view.qui002.b {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {
            $("#" + screenModel.listEmp()[0].employeeId ).focus();
        });
    });
}