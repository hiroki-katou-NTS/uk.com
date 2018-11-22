module nts.uk.pr.view.qmm025.a {
    __viewContext.ready(function () {
        /*let dialogOptions: any = {
            forGrid: true,
            headers: [
                new nts.uk.ui.errors.ErrorHeader("rowId", "Row ID", "auto", true),
                new nts.uk.ui.errors.ErrorHeader("columnKey", "Column Key", "auto", true),
                new nts.uk.ui.errors.ErrorHeader("message", "Message", "auto", true),
                new nts.uk.ui.errors.ErrorHeader("ruleCode", "Rule code", "auto", true)
            ]
        };*/
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            $("#A2_3").focus();
        })
    });
}
