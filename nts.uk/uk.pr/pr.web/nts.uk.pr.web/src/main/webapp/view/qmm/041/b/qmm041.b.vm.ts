module nts.uk.pr.view.qmm041.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import hasError = nts.uk.ui.errors.hasError;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm041.share.model;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        startDateString: KnockoutObservable<string> = ko.observable(null);
        endDateString: KnockoutObservable<string> = ko.observable(null);
        takeoverMethod: KnockoutObservable<number> = ko.observable(0);
        takeoverItem: KnockoutObservableArray<number> = ko.observableArray([]);
        yearMonthStart: KnockoutObservable<number> = ko.observable(null);


        constructor() {
            let self = this;
            block.invisible();
            let params = getShared("QMM041_A_PARAMS");
            if (params) {
                let period = params.period, displayLatestStartHistory = "";
                if (period && Object.keys(period).length > 0) {
                    let startYM = period.periodStartYm;
                    let endYM = period.periodEndYm;
                    displayLatestStartHistory = self.formatYM(startYM);
                    self.startDateString(startYM);
                    self.endDateString(endYM);
                    self.yearMonthStart(startYM);
                }
                if (params.historyId) {
                    self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.WITH_HISTORY, getText('QMM041_23', [displayLatestStartHistory])));
                    self.takeoverMethod(1);
                }
                self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.NO_HISTORY, getText('QMM041_23')));
            }
            block.clear();
        }

        formatYM(intYM) {
            return nts.uk.time.parseYearMonth(intYM).format();
        }

        addNewHistory() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').trigger("validate");
            if (hasError()) {
                return;
            }
            if (self.startDateString() >= self.yearMonthStart()) {
                dialog.alertError({messageId: "Msg_79"});
                return;
            }
            let historyId = getShared("QMM041_A_PARAMS").historyId;
            setShared('QMM041_B_RES_PARAMS', {
                historyId: historyId,
                periodStartYm: self.yearMonthStart(),
                periodEndYm: 999912,
                takeoverMethod: self.takeoverMethod()
            });
            close();
        }

        cancel() {
            close();
        }

        startPage(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }


    }
}
