module nts.uk.pr.view.qmm041.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm041.share.model;
    import dialog = nts.uk.ui.dialog;
    import HISTORY_STATUS = nts.uk.pr.view.qmm041.share.model.HISTORY_STATUS;
    import INHERITANCE = nts.uk.pr.view.qmm041.share.model.INHERITANCE;

    export class ScreenModel {
        takeOverMethod: KnockoutObservable<number> = ko.observable(0);
        takeOverItem: KnockoutObservableArray<number> = ko.observableArray([]);
        startYm: KnockoutObservable<number> = ko.observable(null);
        baseYm: number = 0;
        historyStatus: number = 0;

        constructor() {
            let self = this;
            block.invisible();
            let params = getShared("QMM041_A_PARAMS");
            if (params) {
                self.baseYm = params.startYm;
                self.startYm(params.startYm);
                self.historyStatus = params.historyStatus;
                if (params.historyStatus == HISTORY_STATUS.WITH_HISTORY) {
                    self.takeOverItem.push(new model.EnumModel(INHERITANCE.YES, getText('QMM041_23', self.formatYM(self.startYm()))));
                    self.takeOverMethod(1);
                }
                self.takeOverItem.push(new model.EnumModel(INHERITANCE.NO, getText('QMM041_23')));
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
            if (self.historyStatus == HISTORY_STATUS.WITH_HISTORY && self.baseYm > self.startYm()) {
                dialog.alertError({messageId: "Msg_79"});
                return;
            }
            let historyId = nts.uk.util.randomId();
            setShared('QMM041_B_RES_PARAMS', {
                historyId: historyId,
                periodStartYm: self.startYm(),
                takeOverMethod: self.takeOverMethod()
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
