module nts.uk.at.view.kmw003.a {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        var screenModel = __viewContext.vm = new nts.uk.at.view.kmw003.a.viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            //this.bind(screenModel, dialogOptions);
            //cursor move direction 
            screenModel.selectedDirection.subscribe((value) => {
                if (value == 0) {
                    $("#dpGrid").ntsGrid("directEnter", "below", "");
                } else {
                    $("#dpGrid").ntsGrid("directEnter", "right", "");
                }
            });
            
            let dialogOptions = {
               forGrid: true,
                headers: [
                    new nts.uk.ui.errors.ErrorHeader("employeeCode", "Employee code", "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("employeeName", "Employee name", "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("columnName", "columnName", "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("message", "Message", "auto", true)
                ] 
            }
            __viewContext.bind(screenModel, dialogOptions);
        });
    });
}