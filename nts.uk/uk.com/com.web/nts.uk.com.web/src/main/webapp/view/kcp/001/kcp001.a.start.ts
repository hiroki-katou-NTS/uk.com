module kcp001.a {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        // Load Component
        $('#empt-list-setting').ntsListComponent(screenModel.listComponentOption).done(function() {
                if (($('#empt-list-setting').getDataList() == undefined) || ($('#empt-list-setting').getDataList().length <= 0)) {
                    screenModel.hasSelectedEmp(false);
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_146" });
                }
                else {
                    // Employment List
                    screenModel.employmentList($('#empt-list-setting').getDataList());
                }
            screenModel.jsonData(JSON.stringify($('#empt-list-setting').getDataList(), undefined, 10));
            });
    });
}