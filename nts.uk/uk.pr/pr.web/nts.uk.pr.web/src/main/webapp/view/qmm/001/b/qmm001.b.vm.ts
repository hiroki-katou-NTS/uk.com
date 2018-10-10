module nts.uk.pr.view.qmm001.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import dialog  = nts.uk.ui.dialog;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = qmm001.share.model;

    export class ScreenModel {
        listTakeOver: KnockoutObservableArray<any> = ko.observableArray(getListtakeOver());
        takeOver: KnockoutObservable<number> = ko.observable(0);
        cId: KnockoutObservable<string> = ko.observable('');
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        startYearMonth: KnockoutObservable<number> = ko.observable();
        startLastYearMonth: KnockoutObservable<number> = ko.observable();
        endYearMonth: KnockoutObservable<number> = ko.observable(999912);
        monthlyCalendar: KnockoutObservable<string> = ko.observable('');

        constructor() {
            block.invisible()
            let self = this;
            self.monthlyCalendar(getText('qmm001_12', []));
            let params = getShared('qmm001_PARAMS_TO_SCREEN_B');
            self.startYearMonth.subscribe((data) => {
                self.monthlyCalendar(getText('qmm001_12', [nts.uk.time.yearmonthInJapanEmpire(data).toString().split(' ').join('')]));
            });
            if(params) {
                self.startLastYearMonth(params.startYearMonth);
                self.code(params.code);
                self.name(params.name);
                self.listTakeOver()[0] = new model.ItemModel(0,getText('qmm001_34', [self.convertMonthYearToString(params.startYearMonth)]));
            }
            block.clear();
        }

        validateYearMonth(){
            let self = this;
            if(!(self.startLastYearMonth() < self.startYearMonth())) {
                dialog.error({ messageId: "Msg_79"});
                return true;
            }
            return false;
        }

        convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }

        cancel(){
            close();
        }

        register(){
            let self =this;
            if(self.validateYearMonth()) {
                return;
            }
            let data: any = {
                code: self.code(),
                startYearMonth: self.startYearMonth(),
                endYearMonth:  self.endYearMonth()
            }
            service.addPayrollUnitPriceHis(data).done((historyId: any) => {
                dialog.info({ messageId: "Msg_15" }).then(() => {
                    setShared('qmm001_B_PARAMS_OUTPUT', {
                        hisId: historyId,
                        startYearMonth: self.startYearMonth(),
                        takeOver: self.takeOver()
                    });
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
        hisId: string;
        startYearMonth:number;
        endYearMonth: number;

    }

    export function getListtakeOver(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText('QMM001_33')),
            new model.ItemModel(1, getText('QMM001_34'))
        ];
    }


}