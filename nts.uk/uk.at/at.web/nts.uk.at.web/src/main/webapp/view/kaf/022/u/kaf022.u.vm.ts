module nts.uk.at.view.kaf022.u.viewmodel {
    let __viewContext: any = window["__viewContext"] || {};
    import isNullOrEmpty = nts.uk.text.isNullOrEmpty;
    import dialogInfo = nts.uk.ui.dialog.info;
    import dialogConfirm =  nts.uk.ui.dialog.confirm;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        listApptype: KnockoutObservableArray<any> = ko.observableArray([]);
        listSelect: KnockoutObservableArray<number> = ko.observableArray([]);
        columns: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor() {
            let self = this;
            self.initData();
        }
        
        initData(){
            let self = this;
            
            self.columns = ko.observableArray([
                { headerText: getText("KAF022_443"), key: 'value', width: 250, hidden: true },
                { headerText: getText("KAF022_449"), key: 'name', width: 250, formatter: _.escape }
            ]);
        }


        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            self.listApptype(__viewContext.enums.ApplicationType);
            self.listSelect(nts.uk.ui.windows.getShared('shareApptypeToUDialog'));
            dfd.resolve();
            return dfd.promise();
        }


        // close dialog
        closeDialog() {
            nts.uk.ui.windows.setShared('shareApptypeToKAF022A', nts.uk.ui.windows.getShared('shareApptypeToUDialog'));
            nts.uk.ui.windows.close();
        }

        /** update or insert data when click button register **/
        register() {
            let self = this;
            let dfd = $.Deferred();
            nts.uk.ui.windows.setShared('shareApptypeToKAF022A', self.listSelect());
            nts.uk.ui.windows.close();
        }
}

    export interface IProxy {
        appType : number;
    }

    export class Proxy {
        appType: KnockoutObservable<number>;
        constructor(param: IProxy) {
            let self = this;
            self.appType = ko.observable(param.appType);
        }
    }
}




