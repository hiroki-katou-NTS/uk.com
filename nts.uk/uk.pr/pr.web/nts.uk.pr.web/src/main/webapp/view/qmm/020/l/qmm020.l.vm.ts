module nts.uk.pr.view.qmm020.l.viewmodel {
    import dialog = nts.uk.ui.dialog;
    import getText = nts.uk.resource.getText;
    import close = nts.uk.ui.windows.close;
    import block = nts.uk.ui.block;
    export class ScreenModel {
        masterUse: KnockoutObservableArray<any>;
        selectedMasterUse: KnockoutObservable<number> = ko.observable();
        individualUse: KnockoutObservableArray<any>;
        selectedIndividualUse : KnockoutObservable<number> = ko.observable();
        usageMaster: KnockoutObservableArray<any>;
        selectedUsageMaster: KnockoutObservable<number> = ko.observable();
        constructor() {
            block.invisible();
            var self = this;
            self.init();
            service.getStateUseUnitSettingById().done((data)=>{
                if(data){
                    self.selectedMasterUse(data.masterUse);
                    self.selectedIndividualUse(data.individualUse);
                    self.selectedUsageMaster(data.usageMaster);
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
            });

        }

        init(){
            let self = this;
            self.masterUse = ko.observableArray([
                { code: '1', name: getText('QMM020_74') },
                { code: '0', name: getText('QMM020_77') }
            ]);

            self.individualUse = ko.observableArray([
                { code: '1', name: getText('QMM020_74') },
                { code: '0', name: getText('QMM020_77') }
            ]);

            self.usageMaster = ko.observableArray([
                new BoxModel(0, getText('QMM020_6')),
                new BoxModel(1, getText('QMM020_78')),
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
                usageMaster: self.selectedUsageMaster()
            }

            service.update(data).done(()=>{
                dialog.info({ messageId: "Msg_15" }).then(() => {
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