module nts.uk.pr.view.qmm041.b.viewmodel {

    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import model = nts.uk.pr.view.qmm041.share.model;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        takeoverMethod: KnockoutObservable<number>;
        takeoverItem: KnockoutObservableArray<>
        startDate: KnockoutObservable<number>;


        constructor() {
            let self = this;
            block.invisible();
            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");
            self.takeoverMethod = ko.observable(1);
            self.takeoverItem = ko.observableArray([]);
            self.startDate = ko.observable(null);

            let params = getShared("QMM041_A_PARAMS");
            if (params) {
                let period = params.period, displayLatestStartHistory = "";
                if (period && Object.keys(period).length > 0) {
                    let startYM = period.periodStartYm;
                    let endYM = period.periodEndYm;
                    displayLatestStartHistory = String(startYM).substring(0, 4) + "/" + String(startYM).substring(4, 6);
                    self.startDateString(startYM);
                    self.endDateString(endYM);
                    self.startDate(startYM);
                }
                if (params.historyID) {
                    self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.WITH_HISTORY, getText('QMM041_23', [displayLatestStartHistory])));
                    self.takeoverMethod(0);
                }
                self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.NO_HISTORY, getText('QMM041_23')));
            }
            block.clear();
        }

        addNewHistory() {
            let self = this;
            nts.uk.ui.errors.clearAll();
            $('.nts-input').trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            if (self.startDateString() >= self.startDate()) {
                dialog.alertError({messageId: "Msg_79"});
                return;
            }
            let historyID = getShared("QMM041_A_PARAMS").historyID;
            setShared('QMM041_B_RES_PARAMS', {
                historyID: historyID,
                periodStartYm: self.startDate(),
                periodEndYm: 999912,
                takeoverMethod: self.takeoverMethod()
            });
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        startPage(): JQueryPromise<any> {
            let dfd = $.Deferred();
            dfd.resolve();
            return dfd.promise();
        }


    }
}
