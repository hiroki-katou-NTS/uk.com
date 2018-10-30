module nts.uk.pr.view.qmm020.b.viewmodel {

    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        currentSelect: KnockoutObservable<any> = ko.observable();
        salaryCode: KnockoutObservable<string> = ko.observable();
        bonusCode: KnockoutObservable<string> = ko.observable();
        specName: KnockoutObservable<string> = ko.observable('TaiTT');
        to : KnockoutObservable<string> = ko.observable(' ～ ');
        constructor(){
            block.invisible()
            let self = this;
            service.getStateCorrelationHisCompanyById().done((data) =>{
                if(data){
                    _.forEach(data,(o)=>{
                        self.listStateCorrelationHis.push(new ItemModel(o.historyID, '', self.convertYearMonthToDisplayYearMonth(o.startYearMonth) + self.to() + self.convertYearMonthToDisplayYearMonth(o.endYearMonth)));
                    });
                }
            }).fail((err)=>{
                if(err)
                    dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });
            self.currentSelect.subscribe((hisID)=>{
                service.getStateLinkSettingCompanyById(hisID).done((data)=>{
                    if(data){
                        self.salaryCode(data.salaryCode);
                        self.bonusCode(data.bonusCode);
                    }
                }).fail((err) =>{
                    if(err)
                        dialog.alertError(err);
                }).always(()=>{

                });
            });
        }

        test(){
            let self = this;
            console.dir(self.currentSelect());
        }
        register(){
            block.invisible();
            let self = this;
            let historyID = nts.uk.util.randomId();
            let data: any = {
                stateCorrelationHisCompanyCommand: {
                    cid: '',
                    historyID: historyID,
                    startYearMonth: 201809,
                    endYearMonth: 999912
                },
                stateLinkSettingCompanyCommand : {
                    historyID: historyID,
                    salaryCode: self.salaryCode(),
                    bonusCode: self.bonusCode()
                }

            }
            service.register(data).done((data)=>{
                dialog.info({ messageId: "Msg_15" }).then(() => {

                });
            }).fail((err) =>{
                if(err){
                    dialog.alertError(err);
                }
            }).always(()=>{
                block.clear();
            });

        }

        openScreenL(){
            modal("/view/qmm/020/l/index.xhtml");
        }

        convertYearMonthToDisplayYearMonth(yearMonth) {
            return nts.uk.time.formatYearMonth(yearMonth);
        }

    }
    export class ItemModel {
        code: string;
        name: string;
        display: string;
        constructor(code: string, name: string, display: string) {
            this.code = code;
            this.name = name;
            this.display = display;
        }
    }

}