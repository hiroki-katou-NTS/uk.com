module nts.uk.pr.view.qmm020.l.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import block = nts.uk.ui.block;
    import model = qmm020.share.model;
    import setShared = nts.uk.ui.windows.setShared;
    export class ScreenModel {
        masterUse: KnockoutObservableArray<any>;
        selectedMasterUse: KnockoutObservable<number> = ko.observable();
        individualUse: KnockoutObservableArray<any>;
        selectedIndividualUse : KnockoutObservable<number> = ko.observable();
        usageMaster: KnockoutObservableArray<any>;
        selectedUsageMaster: KnockoutObservable<number> = ko.observable();
        currentSelectedUsageMaster: KnockoutObservable<number> = ko.observable();
        constructor() {
            block.invisible();
            var self = this;
            self.init();
            service.getStateUseUnitSettingById().done((data)=>{
                if(data){
                    self.selectedMasterUse(data.masterUse);
                    self.selectedIndividualUse(data.individualUse);
                    self.selectedUsageMaster(data.usageMaster === null ? 0 : data.usageMaster);
                    self.currentSelectedUsageMaster(data.usageMaster);
                }else{
                    self.selectedMasterUse(1);
                    self.selectedIndividualUse(1);
                    self.selectedUsageMaster(0);
                }

            }).fail((err)=>{
                if(err)
                    dialog.alertError(err)

            }).always(()=>{
                block.clear();
                $("#L1_6").focus();
            });

        }

        init(){
            let self = this;
            //L1_7, L1_8
            self.masterUse = ko.observableArray([
                { code: 1, name: getText('QMM020_74') },
                { code: 0, name: getText('QMM020_77') }
            ]);
            //L1_17, L1_18
            self.individualUse = ko.observableArray([
                { code: 1, name: getText('QMM020_74') },
                { code: 0, name: getText('QMM020_77') }
            ]);
            //L1_10, L1_11, L1_12, L1_13, L1_14
            self.usageMaster = ko.observableArray([
                new BoxModel(0, getText('QMM020_6')),
                new BoxModel(1, getText('QMM020_7')),
                new BoxModel(2, getText('QMM020_8')),
                new BoxModel(3, getText('QMM020_9')),
                new BoxModel(4, getText('QMM020_10')),
            ]);
        }

        cancel(){
            close();
        }

        update(){
            block.invisible();
            let self  = this;
            let data: any = {
                companyID: '',
                masterUse: self.selectedMasterUse(),
                individualUse: self.selectedIndividualUse(),
                usageMaster: self.selectedMasterUse() === 1 ? self.selectedUsageMaster() : self.currentSelectedUsageMaster()
            }

            service.update(data).done(()=>{
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    setShared(model.PARAMETERS_SCREEN_L.OUTPUT, {
                        isSubmit: true
                    });
                    close();
                });
            }).fail((err)=>{
                if(err)
                    dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });
        }
    }

    class BoxModel {
        id: number;
        name: string;
        constructor(id, name){
            var self = this;
            self.id = id;
            self.name = name;
        }
    }
}