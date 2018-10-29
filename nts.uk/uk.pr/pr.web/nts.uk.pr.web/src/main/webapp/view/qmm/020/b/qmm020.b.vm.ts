module nts.uk.pr.view.qmm020.b.viewmodel {

    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    export class ScreenModel {

        listStateCorrelationHis: KnockoutObservableArray<ItemModel> =  ko.observableArray([]);
        currentSelect: KnockoutObservable<any> = ko.observable();
        specCode: KnockoutObservable<string> = ko.observable('TaiTT');
        specName: KnockoutObservable<string> = ko.observable('TaiTT');
        constructor(){
            let self = this;

            for(let i = 1; i < 100; i++) {
                self.listStateCorrelationHis.push(new ItemModel('00' + i, '基本給', i + 'TaiTT'));
            }
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