module nts.uk.pr.view.qmm007.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm007.share.model;

    export class ScreenModel {
        listTakeOver: KnockoutObservableArray<any> = ko.observableArray(getListtakeOver());
        takeOver: KnockoutObservable<number> = ko.observable(0);
        cId: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        startYearMonth: KnockoutObservable<number> = ko.observable();

        constructor() {
            let self = this;
            self.code('001');
            self.name('KKKK');
            self.startYearMonth(201802);
            self.startLastYearMonth(201801);
        }

        cancel(){
            close();
        }

        register(){
            let self =this;
            let data: any = {
                code: self.code,
                startYearMonth: self.startYearMonth(),
                endYearMonth:  self.endYearMonth()
            }
            service.register(data).done(() => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    close();
                });
            }).fail(function(res: any) {
                if (res)
                    dialog.alertError(res);
            }).always(() => {
                block.clear();
            });
        }
    }
    class PayrollUnitPriceHis{
        cId :string;
        code :string;
        startYearMonth:number;
        endYearMonth: number;

    }

    export function getListtakeOver(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText('QMM011_48')),
            new model.ItemModel(1, getText('QMM011_49'))
        ];
    }


}