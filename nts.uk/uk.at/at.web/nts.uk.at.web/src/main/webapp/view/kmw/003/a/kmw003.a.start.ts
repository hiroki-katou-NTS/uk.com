module nts.uk.at.view.kmw003.a {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let data = __viewContext.transferred.value, value,
        param = null;
        // if(value == undefined || value == null){
        //    value = false; 
        // } else {
        //    value =  value.isKmwCall;
        // }

        if (data == undefined || data == null) {
            value = false;             
        } else {
            value =  data.isKmwCall;
            param = {
                closureId: data.closureId,
                yearMonth: data.yearMonth
            };
        }
        let screenModel = __viewContext.vm = new nts.uk.at.view.kmw003.a.viewmodel.ScreenModel(value);
        // set pg-name if pg-name = ''
        if ($("#pg-name").text() == '') $("#pg-name").text("KMW003A " + nts.uk.resource.getText("KMW003_1"));
        screenModel.startPage(param).done(() => {
            
            let dialogOptions = {
               forGrid: true,
                headers: [
                    new nts.uk.ui.errors.ErrorHeader("employeeCode", "社員コード", "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("employeeName", "社員名", "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("columnName", "対象項目", "auto", true),
                    new nts.uk.ui.errors.ErrorHeader("message", "エラー内容", "auto", true)
                ] 
            }
            __viewContext.bind(screenModel, dialogOptions);
                screenModel.isStartScreen(false);
        });
    });
}