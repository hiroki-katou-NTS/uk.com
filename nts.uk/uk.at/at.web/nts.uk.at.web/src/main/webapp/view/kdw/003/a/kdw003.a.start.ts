module nts.uk.at.view.kdw003.a {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdw003.a.viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            let dialogOptions: any = {
                forGrid: true,
                headers: [
                    new nts.uk.ui.errors.ErrorHeader("message", "エラー内容", "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("errorCode", "エラーコード", "auto", true)
//                    new nts.uk.ui.errors.ErrorHeader("employeeCode", nts.uk.resource.getText("KDW003_32"), "auto", true),
//                    new nts.uk.ui.errors.ErrorHeader("employeeName", nts.uk.resource.getText("KDW003_33"), "auto", true),
//                    new nts.uk.ui.errors.ErrorHeader("date",nts.uk.resource.getText('KDW003_34'), "auto", true),
//                    new nts.uk.ui.errors.ErrorHeader("message", nts.uk.resource.getText('KDW003_36'), "auto", true)
                ]
            };
            //this.bind(screenModel, dialogOptions);
            //cursor move direction 
            screenModel.selectedDirection.subscribe((value) => {
                if (value == 0) {
                    $("#dpGrid").ntsGrid("directEnter", "below");
                } else {
                    $("#dpGrid").ntsGrid("directEnter", "right");
                }
            });
            screenModel.loadKcp009();
            __viewContext.bind(screenModel, dialogOptions);
        });
    });
}