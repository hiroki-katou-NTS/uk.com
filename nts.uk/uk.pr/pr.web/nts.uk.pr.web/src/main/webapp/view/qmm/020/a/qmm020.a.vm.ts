module nts.uk.pr.view.qmm020.a.viewmodel {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        masterUse: KnockoutObservable<number> = ko.observable();
        individualUse: KnockoutObservable<number> = ko.observable();
        usageMaster: KnockoutObservable<number> = ko.observable();
        constructor(){
            block.invisible();
            let self = this;
        }

        startPage (): JQueryPromise<any>{
            block.invisible();
            let self = this;
            let dfd = $.Deferred();
            service.getStateUseUnitSettingById().done((data:any)=>{
                if(data){
                    self.masterUse(data.masterUse);
                    self.individualUse(data.individualUse);
                    self.usageMaster(data.usageMaster);
                }
                dfd.resolve(data);
            }).fail((err)=>{
                dfd.reject();
                if (err)
                    dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });
            return dfd.promise();
        }


    }
}