module nts.uk.pr.view.qmm036.c.viewmodel {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog;
    import alertError = nts.uk.ui.dialog.alertError;

    export class ScreenModel {
        modifyMethod: KnockoutObservable<number> = ko.observable(1);
        modifyItem: KnockoutObservableArray<> = ko.observableArray([]);
        startMonth: KnockoutObservable<string> = ko.observable('');
        dateValue: KnockoutObservable<any>;
        startDateString: KnockoutObservable<string>;
        endDateString: KnockoutObservable<string>;
        selectedHistory: any = null;

        cateIndicator: KnockoutObservable<number> = ko.observable(0);
        salBonusCate: KnockoutObservable<number> = ko.observable(0);


        yearMonthStart: KnockoutObservable<number> = ko.observable(201812);
        yearMonthEnd: KnockoutObservable<string> = ko.observable('');


        canDelete: KnockoutObservable<boolean> = ko.observable(false);


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

            let params = getShared("QMM036_C_PARAMS");
            if (params) {
                block.invisible();
                //パラメータ.項目区分を確認する

                let selectedEmployee = params.employeeInfo;

                let period = params.period;
                self.selectedHistory = params.period;
                if (period && Object.keys(period).length > 0) {
                    let startYM = period.periodStartYm;
                    let endYM = period.periodEndYm;
                    self.startDateString(startYM);
                    self.endDateString(endYM);
                    self.yearMonthStart(startYM);
                    self.yearMonthEnd(nts.uk.time.parseYearMonth(endYM).format());
                    if (endYM == 999912) {
                        self.canDelete(true);
                    }
                }

                self.modifyItem.push(new EnumModel(MOFIDY_METHOD.DELETE, getText('QMM036_41')));
                self.modifyItem.push(new EnumModel(MOFIDY_METHOD.UPDATE, getText('QMM036_42')));
            }
            $('#C1_3').focus();
            block.clear();
        }

        editHistory() {
            let self = this;
            if (self.modifyMethod() == MOFIDY_METHOD.UPDATE) {
                self.updateHistory();
            } else {
                dialog.confirm({messageId: "Msg_18"}).ifYes(function () {
                    self.deleteHistory();
                });
            }
        }

        updateHistory() {
            let self = this;
            let params = getShared("QMM036_C_PARAMS");

            if (((params.period.preHisStartYm != null) && (self.yearMonthStart() <= params.period.preHisStartYm)) || (self.yearMonthStart() > params.period.periodEndYm)) {
                nts.uk.ui.dialog.info({messageId: "Msg_107"});
                return;
            }
            let lstPeriod: Array = [];
            let period = {
                historyId: params.period.historyId,
                startMonth: self.yearMonthStart(),
                endMonth: params.period.periodEndYm
            };
            lstPeriod.push(period);
            let lstBreakdownAmount: Array = [];

            let command = {
                lastHistoryId: params.period.lastHistoryId,
                categoryAtr: params.period.categoryAtr,
                itemNameCd: params.period.itemNameCd,
                employeeId: params.period.employeeID,
                salaryBonusAtr: params.period.salaryBonusAtr,
                period: lstPeriod,
                breakdownAmountList: lstBreakdownAmount,
                histCheck: null
            };
            service.updateBreakdownAmountHis(ko.toJS(command)).done(() => {
                dialog.info({messageId: "Msg_15"}).then(() => {
                    nts.uk.ui.windows.close();
                });
            }).fail(function (error) {
                alertError(error);
            });

        }

        deleteHistory() {
            let self = this;
            let params = getShared("QMM036_C_PARAMS");

            let lstPeriod: Array = [];
            let period = {
                historyId: params.period.historyId,
                startMonth: self.yearMonthStart(),
                endMonth: params.period.periodEndYm
            };

            lstPeriod.push(period);
            let lstBreakdownAmount: Array = [];
            let command = {
                lastHistoryId: params.period.lastHistoryId,
                categoryAtr: params.period.categoryAtr,
                itemNameCd: params.period.itemNameCd,
                employeeId: params.period.employeeID,
                salaryBonusAtr: params.period.salaryBonusAtr,
                period: lstPeriod,
                breakdownAmountList: lstBreakdownAmount,
                histCheck: null
            };

            service.removeBreakdownItemSet(command).done(function () {
                nts.uk.ui.dialog.info({messageId: "Msg_16"}).then(function () {
                    nts.uk.ui.windows.close();
                });
            }).fail(function (err) {
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

    export enum MOFIDY_METHOD {
        DELETE = 0,
        UPDATE = 1
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
