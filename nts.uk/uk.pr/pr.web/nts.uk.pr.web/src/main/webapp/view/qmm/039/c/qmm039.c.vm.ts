module nts.uk.pr.view.qmm039.c.viewmodel {
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import modal = nts.uk.ui.windows.sub.modal;
    import hasError = nts.uk.ui.errors.hasError;
    import model = nts.uk.pr.view.qmm039.share.model;

    export class ScreenModel {
        modifyMethod: KnockoutObservable<number> = ko.observable(1);
        modifyItem: KnockoutObservableArray<> = ko.observableArray([]);
        startMonth: KnockoutObservable<string> = ko.observable('');
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        selectedHistory: any = null;
        selectedEmployee: any = null;



        constructor() {
            var self = this;
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

            let params = getShared("QMM039_C_PARAMS");
            if (params) {
                block.invisible();
                //パラメータ.項目区分を確認する

                let selectedEmployee = params.employeeInfo;

                let period = params.period, displayLastestStartHistory = "";
                self.selectedHistory = params.period;
                self.selectedEmployee = params.employeeInfo;
                if (period && Object.keys(period).length > 0) {
                    let startYM = period.periodStartYm;
                    let endYM = period.periodEndYm;
                    displayLastestStartHistory = String(startYM).substring(0, 4) + "/" + String(startYM).substring(4, 6);
                    self.startDateString(startYM);
                    self.endDateString(endYM);
                }

                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.DELETE, getText('QMM039_36')));
                self.modifyItem.push(new model.EnumModel(model.MOFIDY_METHOD.UPDATE, getText('QMM039_37')));
            }
            block.clear();
        }

        editHistory() {
            let self = this;
            if (self.modifyMethod() == model.MOFIDY_METHOD.UPDATE) {
                self.updateHistory();
            } else {
                dialog.confirm({messageId: "Msg_18"}).ifYes(function () {
                    self.deleteHistory();
                });
            }
        }

        updateHistory() {
            let self = this;
            let newHistory = self.selectedHistory;
            newHistory.periodStartYM = parseInt(self.dateValue().startDate.replace('/', ''));
            newHistory.periodEndYM = parseInt(self.dateValue().endDate.replace('/', ''));
            let newEmployee = self.selectedEmployee;
            let command = {
                //emp history
                yearMonthHistoryItem: newHistory,
                //emp info data
                empId: newEmployee.empId,
                perValCode: newEmployee.personalValcode
            };

            service.editSalIndividualAmountHistory(command).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                    setShared('QMM039_C_RES_PARAMS', { modifyMethod: self.modifyMethod() });
                    nts.uk.ui.windows.close();
                });
            }).fail(function(err) {
                dialog.alertError(err.message);
            });
        }

        deleteHistory() {
            let self = this;
            let newHistory = self.selectedHistory;
            let newEmployee = self.selectedEmployee;
            let command = {
                //emp history
                yearMonthHistoryItem: newHistory,
                //emp info data
                empId: newEmployee.empId,
                perValCode: newEmployee.personalValcode
            };

            service.deleteSalIndividualAmountHistory(command).done(function() {
                nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                    setShared('QMM039_C_RES_PARAMS', { modifyMethod: self.modifyMethod() });
                    nts.uk.ui.windows.close();
                });
            }).fail(function(err) {
                dialog.alertError(err.message);
            });

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
