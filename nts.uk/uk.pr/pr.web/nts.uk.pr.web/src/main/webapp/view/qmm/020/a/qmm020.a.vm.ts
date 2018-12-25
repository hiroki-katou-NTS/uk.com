module nts.uk.pr.view.qmm020.a.viewmodel {
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import model = qmm020.share.model;
    export class ScreenModel {
        masterUse: KnockoutObservable<number> = ko.observable();
        individualUse: KnockoutObservable<number> = ko.observable();
        usageMaster: KnockoutObservable<number> = ko.observable();
        constructor(){

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

        eventClick(data){
            $(".tab-2-sidebar").click(function() {
                if(data.masterUse === 1 && data.usageMaster === 0){
                    __viewContext.viewModel.viewmodelC.startPage().done(function() {
                        nts.uk.ui.errors.clearAll();
                        if(__viewContext.viewModel.viewmodelC.mode()  === model.MODE.NO_REGIS){
                            __viewContext.viewModel.viewmodelC.enableEditHisButton(false);
                            __viewContext.viewModel.viewmodelC.enableAddHisButton(true);
                            __viewContext.viewModel.viewmodelC.enableRegisterButton(false);
                        }else{
                            __viewContext.viewModel.viewmodelC.enableEditHisButton(true);
                            __viewContext.viewModel.viewmodelC.enableAddHisButton(true);
                            __viewContext.viewModel.viewmodelC.enableRegisterButton(true);
                        }
                        __viewContext.viewModel.viewmodelC.newHistoryId(null);
                        $("#C1_5").focus();
                    });
                }else if(data.masterUse === 1 && data.usageMaster === 1){
                    __viewContext.viewModel.viewmodelD.startPage().done(function() {
                        nts.uk.ui.errors.clearAll();
                        __viewContext.viewModel.viewmodelD.enableEditHisButton(true);
                        __viewContext.viewModel.viewmodelD.enableAddHisButton(true);
                    });
                }else if(data.masterUse === 1 && data.usageMaster === 2){
                    __viewContext.viewModel.viewmodelE.initScreen(null);
                }else if(data.masterUse === 1 && data.usageMaster === 3){
                    __viewContext.viewModel.viewmodelF.initScreen(null);
                }else if(data.masterUse === 1 && data.usageMaster === 4){
                    __viewContext.viewModel.viewmodelG.initScreen(null);
                }
            });

        }

        openScreenL(){
            let self = __viewContext.viewModel.viewmodelA;
            let isClick = false;
            modal("/view/qmm/020/l/index.xhtml").onClosed(()=>{
                let params = getShared(model.PARAMETERS_SCREEN_L.OUTPUT);
                service.getStateUseUnitSettingById().done((data:any)=>{
                    if($(".tab-2-sidebar")[0].className.indexOf('active') > 0){
                        isClick = true;
                    }
                    if(data){
                        self.masterUse(data.masterUse);
                        self.individualUse(data.individualUse);
                        self.usageMaster(data.usageMaster);
                    }
                    self.eventClick(data);
                }).fail((err)=>{
                    if (err)
                        dialog.alertError(err);
                }).always(()=>{
                    if(isClick){
                        $(".tab-2-sidebar").click();
                    }
                    block.clear();
                });
            });
        }

    }
}