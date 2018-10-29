module nts.uk.pr.view.qmm020.a.viewmodel {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {
        masterUse: KnockoutObservable<number> = ko.observable();
        individualUse: KnockoutObservable<number> = ko.observable();
        usageMaster: KnockoutObservable<number> = ko.observable();
        viewmodelC: any;
        viewmodelD: any;
        constructor(){
            block.invisible();
            let self = this;
            service.getStateUseUnitSettingById().done((data:any)=>{
                if(data){
                    self.masterUse(data.masterUse);
                    self.individualUse(data.individualUse);
                    self.usageMaster(data.usageMaster);
                }
            }).fail((err)=>{
                if (err)
                    dialog.alertError(err);
            });
            block.clear();
        }

        initScreenC(){
            block.invisible();
            let self = this;
            self.viewmodelC = new nts.uk.pr.view.qmm020.c.viewmodel.ScreenModel();
            __viewContext.viewModel.viewmodelC = self.viewmodelC;
            block.clear();
            return true;
        }

        initScreenD(){
            block.invisible();
            let self = this;
            self.viewmodelD = new nts.uk.pr.view.qmm020.d.viewmodel.ScreenModel();
            __viewContext.viewModel.viewmodelD = self.viewmodelD;
            block.clear();
            return true;
        }
    }
}