module nts.uk.pr.view.qmm039.b.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import modal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;
    import model = nts.uk.pr.view.qmm039.share.model;
    import dialog = nts.uk.ui.dialog;

    export class ScreenModel {
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        takeoverMethod: KnockoutObservable<number> = ko.observable(1);
        takeoverItem: KnockoutObservableArray<> = ko.observableArray([]);


        constructor() {
            let self = this;

            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");
            self.dateValue = ko.observable({});

            self.startDateString.subscribe(function (value) {
                self.dateValue().startDate = value;
                self.dateValue.valueHasMutated();
            });

            self.endDateString.subscribe(function (value) {
                self.dateValue().endDate = value;
                self.dateValue.valueHasMutated();
            });

            let params = getShared("QMM039_A_PARAMS");
            if (params) {

                let period = params.period, displayLastestStartHistory = "";
                if (period && Object.keys(period).length > 0) {
                    let startYM = period.periodStartYm;
                    let endYM = period.periodEndYm;
                    displayLastestStartHistory = String(startYM).substring(0, 4) + "/" + String(startYM).substring(4, 6);
                    self.startDateString(startYM);
                    self.endDateString(endYM);
                }

                if (params.historyID) {
                    self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.WITH_HISTORY, getText('QMM039_29', [displayLastestStartHistory])));
                    self.takeoverMethod(0);
                }
                self.takeoverItem.push(new model.EnumModel(model.INHERITANCE_CLS.NO_HISTORY, getText('QMM039_30')));

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
            if (self.takeoverMethod() == 0) {
                let startValidPeriod = self.dateValue().startDate;

                if (startValidPeriod <= self.startDateString().toString() || startValidPeriod <= self.endDateString().toString()) {
                    dialog.alertError({messageId: "Msg_79"});
                    return;
                }

            }
            let historyID = getShared("QMM039_A_PARAMS").historyID;
            setShared('QMM039_B_RES_PARAMS', {
                historyID: historyID,
                periodStartYm: self.dateValue().startDate,
                periodEndYm: self.dateValue().endDate,
                takeoverMethod: self.takeoverMethod()
            });
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
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
}
