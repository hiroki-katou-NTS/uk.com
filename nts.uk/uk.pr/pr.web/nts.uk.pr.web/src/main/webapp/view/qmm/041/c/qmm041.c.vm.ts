module nts.uk.pr.view.qmm041.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import model = nts.uk.pr.view.qmm041.share.model;

    export class ScreenModel {
        modifyMethod: KnockoutObservable<number> = ko.observable(1);
        modifyItem: KnockoutObservableArray<> = ko.observableArray([]);
        startMonth: KnockoutObservable<string> = ko.observable('');
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        selectedHistory: any = null;
        selectedEmployee: any = null;
        cateIndicator: KnockoutObservable<number> = ko.observable(0);
        salBonusCate: KnockoutObservable<number> = ko.observable(0);
        yearMonthStart: KnockoutObservable<number> = ko.observable(201812);
        yearMonthEnd: KnockoutObservable<string> = ko.observable('');
        isDeletable: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;
            block.invisible();
            self.startDateString = ko.observable("");
            self.endDateString = ko.observable("");
            self.dateValue = ko.observable({});

            self.startDateString.subscribe( (value) => {
                self.dateValue().startDate = value;
                self.dateValue.valueHasMutated();
            });

            self.endDateString.subscribe((value) => {
                self.dateValue().endDate = value;
                self.dateValue.valueHasMutated();
            });

            let params = getShared("QMM041_C_PARAMS");
            if (params) {
                block.invisible();
                let period = params.period;
                self.selectedHistory = params.period;
                self.selectedEmployee = params.employeeInfo;
                if (period && Object.keys(period).length > 0) {
                    let startYM = period.periodStartYm;
                    let endYM = period.periodEndYm;
                    self.startDateString(startYM);
                    self.endDateString(endYM);
                    self.yearMonthStart(startYM);
                    self.yearMonthEnd(nts.uk.time.parseYearMonth(endYM).format());
                    if (endYM == 999912) {
                        self.isDeletable(true);
                    }
                }
                self.modifyItem.push(new model.EnumModel(model.MODIFY_METHOD.DELETE, getText('QMM041_30')));
                self.modifyItem.push(new model.EnumModel(model.MODIFY_METHOD.UPDATE, getText('QMM041_31')));
            }
            block.clear();
        }

        editHistory() {
            let self = this;
            if (self.modifyMethod() == model.MODIFY_METHOD.UPDATE) {
                self.updateHistory();
            } else {
                dialog.confirm({messageId: "Msg_18"}).ifYes(() => {
                    self.deleteHistory();
                });
            }
        }

        updateHistory() {
            let self = this;
            let params = getShared("QMM041_C_PARAMS");
            let newHistory = self.selectedHistory;
            newHistory.startMonth = self.yearMonthStart();
            newHistory.endMonth = params.period.periodEndYm;

            let newEmployee = self.selectedEmployee;
            newEmployee.cateIndicator = self.cateIndicator();
            newEmployee.salBonusCate = self.salBonusCate();


            if (self.yearMonthStart() <= params.lastPeriodStartYm || self.yearMonthStart() > params.period.periodEndYm) {
                nts.uk.ui.dialog.info({messageId: "Msg_107"});
                return;
            }

            let command = {
                //emp history
                yearMonthHistoryItem: [newHistory],
                //emp info data
                cateIndicator: newEmployee.cateIndicator,
                salBonusCate: newEmployee.salBonusCate,
                empId: newEmployee.empId,
                perValCode: newEmployee.personalValcode,
                lastHistoryId: params.lastHistoryId,
            };

            service.editEmpSalUnitPriceHis(command).done(() => {
                nts.uk.ui.dialog.info({messageId: "Msg_15"}).then(() => {
                    setShared('QMM041_C_RES_PARAMS', {modifyMethod: self.modifyMethod()});
                    nts.uk.ui.windows.close();
                });
            }).fail(function (err) {
                dialog.alertError(err.message);
            });
        }

        deleteHistory() {
            let self = this;
            let params = getShared("QMM041_C_PARAMS");
            let newHistory = self.selectedHistory;
            newHistory.startMonth = self.dateValue().startDate;
            newHistory.endMonth = self.dateValue().endDate;

            let newEmployee = self.selectedEmployee;
            newEmployee.cateIndicator = self.cateIndicator();
            newEmployee.salBonusCate = self.salBonusCate();
            let command = {
                //emp history
                yearMonthHistoryItem: [newHistory],
                //emp info data
                cateIndicator: newEmployee.cateIndicator,
                salBonusCate: newEmployee.salBonusCate,
                empId: newEmployee.empId,
                perValCode: newEmployee.personalValcode,
                lastHistoryId: params.lastHistoryId,
            };

            service.deleteEmpSalUnitPriceHis(command).done(() => {
                nts.uk.ui.dialog.info({messageId: "Msg_16"}).then(() => {
                    setShared('QMM041_C_RES_PARAMS', {modifyMethod: self.modifyMethod()});
                    nts.uk.ui.windows.close();
                });
            }).fail((err) => {
                dialog.alertError(err.message);
            });

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
