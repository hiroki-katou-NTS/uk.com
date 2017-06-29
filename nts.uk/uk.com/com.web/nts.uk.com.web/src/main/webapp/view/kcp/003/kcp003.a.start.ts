module kcp003.a {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        // Load listComponent and initial Job Title List
            $('#component-items-list').ntsListComponent(screenModel.listComponentOption).done(function() {
                if (($('#component-items-list').getDataList() == undefined) || ($('#component-items-list').getDataList().length <= 0)) {
                    screenModel.hasSelectedJobTitle(false);
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_146" });
                }
                else {
                    // Job Title List
                    screenModel.jobTitleList($('#component-items-list').getDataList());
                }
                screenModel.jsonData(JSON.stringify($('#component-items-list').getDataList(), undefined, 10));
            });
    });
}