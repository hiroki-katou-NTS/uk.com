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
        code: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        startYearMonth: KnockoutObservable<number> = ko.observable();
        startLastYearMonth: KnockoutObservable<number> = ko.observable();
        end: KnockoutObservable<number> = ko.observable();
        historyAtr: KnockoutObservable<number> = ko.observable(1);
        startYearMonthDay: KnockoutObservable<string> = ko.observable('');
        startLastYearMonthDay: KnockoutObservable<number> = ko.observable();
        isFisrtHistory: KnockoutObservable<boolean> = ko.observable(true);

        constructor() {
            block.invisible()
            let self = this;
            let params = getShared('QMM001_PARAMS_TO_SCREEN_B');
            if(params) {
                self.code(params.code);
                self.name(params.name);
                self.historyAtr(params.historyAtr);
                if ( params.historyAtr == 1) {
                    self.startLastYearMonth(params.start);
                    self.historyAtr(params.historyAtr);
                    self.end(getText('QMM001_31', ['9999/12']));
                    if(params.start) {
                        self.listTakeOver()[0] = new model.ItemModel(0, getText('QMM001_33', [self.convertMonthYearToString(params.start)]));
                        self.isFisrtHistory(false);
                    }
                }
                if ( params.historyAtr == 0) {
                    self.startLastYearMonth(params.start);
                    self.end(getText('QMM001_31', ['9999/12/31']));
                    if(params.start) {
                        self.listTakeOver()[0] = new model.ItemModel(0, getText('QMM001_33', [params.start]));
                        self.isFisrtHistory(false);
                    }
                }
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

        validateYearMonthDay(){
            let self = this;
            if(!(moment.utc(self.startLastYearMonthDay(),'YYYY/MM/DD') < moment.utc(self.startYearMonthDay(), 'YYYY/MM/DD'))) {
                dialog.error({ messageId: "Msg_79"});
                return true;
            }
            return false;
        }

        convertMonthYearToString(yearMonth: any) {
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }


        cancel(){
            close();
        }

        register() {
            let self = this;
            if (self.historyAtr() == 1) {
                if (self.validateYearMonth()) {
                    return;
                }
                dialog.info({messageId: "Msg_15"}).then(() => {
                    setShared('QMM011_A', {
                        startYearMonth: self.startYearMonth(),
                        takeOver: self.takeOver()
                    });
                    close();
                });
            } else {
                if (self.validateYearMonthDay()) {
                    return;
                }
                dialog.info({messageId: "Msg_15"}).then(() => {
                    setShared('QMM011_A', {
                        startYearMonthDay: self.startYearMonthDay(),
                        takeOver: self.takeOver()
                    });
                    close();
                });
            }
        }
    }

    export enum PARAHISTORYATR {
        /*年月日履歴*/
        YMDHIST = 0,

        /*年月履歴*/
        YMHIST = 1
    }

    export function getListtakeOver(): Array<model.ItemModel> {
        return [
            new model.ItemModel(0, getText('QMM001_33')),
            new model.ItemModel(1, getText('QMM001_34'))
        ];
    }


}