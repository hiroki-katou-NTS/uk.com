module nts.uk.pr.view.qmm020.b.viewmodel {

    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        currentSelect: KnockoutObservable<any> = ko.observable();
        specCode: KnockoutObservable<string> = ko.observable('TaiTT');
        specName: KnockoutObservable<string> = ko.observable('TaiTT');
        to : KnockoutObservable<string> = ko.observable(' ～ ');
        constructor(){
            block.invisible()
            let self = this;
            service.getStateCorrelationHisCompanyById().done((data) =>{
                console.dir(data);
                _.forEach(data,(o)=>{
                    self.listStateCorrelationHis.push(new ItemModel(o.historyID, '', self.convertYearMonthToDisplayYearMonth(o.startYearMonth) + self.to() + self.convertYearMonthToDisplayYearMonth(o.endYearMonth)));
                });
            }).fail((err)=>{
                if(err)
                    dialog.alertError(err);
            }).always(()=>{
                block.clear();
            });
            self.currentSelect.subscribe((data)=>{
                service.getStateLinkSettingCompanyById(data).done(()=>{

                }).fail((err) =>{
                    if(err)
                        dialog.alertError(err);
                }).always(()=>{

                });
            });
        }

        test(){
            modal("com","/view/qmm/020/j/index.xhtml");
        }
        register(){
            block.invisible();
            let self = this;
            let data: any = {
                historyID: '1',
                salaryCode: '01',
                bonusCode: '02'
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