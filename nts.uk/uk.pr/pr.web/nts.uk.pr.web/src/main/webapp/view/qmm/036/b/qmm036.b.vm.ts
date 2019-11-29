module nts.uk.pr.view.qmm036.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        takeoverMethod: KnockoutObservable<number> = ko.observable(1);
        takeoverItem: KnockoutObservableArray<> = ko.observableArray([]);
        yearMonthStart: KnockoutObservable<number> = ko.observable(null);
        isHaveOldHis: KnockoutObservable<boolean>;


        constructor() {
            let self = this;

            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");

            let params = getShared("QMM036_A_PARAMS");
            if (params) {

                let period = params.period, displayLastestStartHistory = "";
                if (period && Object.keys(period).length > 0) {
                    let startYM = period.periodStartYm;
                    let endYM = period.periodEndYm;
                    displayLastestStartHistory = String(startYM).substring(0, 4) + "/" + String(startYM).substring(4, 6);
                    self.startDateString(startYM);
                    self.endDateString(endYM);
                    self.yearMonthStart(startYM);
                }
                if (params.historyID) {
                    self.takeoverItem.push(new EnumModel(INHERITANCE_CLS.WITH_HISTORY, getText('QMM036_34', [displayLastestStartHistory])));
                    self.takeoverMethod(0);
                    self.isHaveOldHis = ko.observable(true);
                } else {
                    self.takeoverItem.push(new EnumModel(INHERITANCE_CLS.WITH_HISTORY, getText('QMM036_34', [""])));
                    self.takeoverMethod(1);
                    self.isHaveOldHis = ko.observable(false);
                }
                self.takeoverItem.push(new EnumModel(INHERITANCE_CLS.NO_HISTORY, getText('QMM036_35')));

            }

            $('#B1_3').focus();
            block.clear();
        }
        formatYM(intYM) {
            return intYM.toString().substr(0, 4) + '/' + intYM.toString().substr(4, intYM.length);
        }

        formatYMToInt(stringYM: string) {
            let arr = stringYM.split('/');
            return parseInt(arr[0]) * 100 + parseInt(arr[1]);
        }

        addNewHistory() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }

            if (self.startDateString() >= self.yearMonthStart()) {
                dialog.alertError({messageId: "Msg_79"});
                return;
            }
            let historyID = getShared("QMM036_A_PARAMS").historyID;
            let params = {};
            if(historyID){
                params = {
                    historyID: historyID,
                    periodStartYm: self.yearMonthStart(),
                    periodEndYm: 999912,
                    takeoverMethod: self.takeoverMethod()
                }
            }
            else{
                params = {
                    historyID: historyID,
                    periodStartYm: self.formatYMToInt(self.yearMonthStart()),
                    periodEndYm: 999912,
                    takeoverMethod: self.takeoverMethod()
                }
            }

            setShared('QMM036_B_RES_PARAMS', params);
            close();
        }

        cancel() {
            close();
        }

        startPage(params): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            self.startupScreen();
            dfd.resolve();
            return dfd.promise();
        }

        startupScreen() {
            var self = this;
        }


    }

    export enum INHERITANCE_CLS {
        WITH_HISTORY = 0,
        NO_HISTORY = 1
    }

    export class EnumModel {
        value: number;
        name: string;

        constructor(value, name) {
            this.value = value;
            this.name = name;
        }
    }
}
