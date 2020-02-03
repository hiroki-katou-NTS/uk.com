module nts.uk.hr.view.jhn001.d.viewmodel {
    import text = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import alertError = nts.uk.ui.dialog.alertError;
    import close = nts.uk.ui.windows.close;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import showDialog = nts.uk.ui.dialog;
    import permision = service.getCurrentEmpPermision;
    let __viewContext: any = window['__viewContext'] || {},
        block = window["nts"]["uk"]["ui"]["block"]["grayout"],
        unblock = window["nts"]["uk"]["ui"]["block"]["clear"],
        invisible = window["nts"]["uk"]["ui"]["block"]["invisible"];
    
    export class ScreenModel {
        listBack: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedBack: KnockoutObservable<any>  = ko.observable(null);
        backComment: KnockoutObservable<any>  = ko.observable(null);
        constructor() {
                
        }
        
        start(): JQueryPromise<any> {
            let self = this,
            dfd = $.Deferred();

            block();
//            $.when(dfdGetData).done((datafile: Array<IReportFileManagement>) => {
                unblock();
                dfd.resolve();
//            });
            return dfd.promise();
        }

        close() {
            close();
        }        

    }
}