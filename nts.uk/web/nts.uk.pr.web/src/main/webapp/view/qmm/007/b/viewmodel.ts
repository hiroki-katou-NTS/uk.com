module nts.uk.pr.view.qmm007.b {
    export module viewmodel {
        import service = nts.uk.pr.view.qmm007.a.service;
        import UnitPriceHistoryModel = nts.uk.pr.view.qmm007.a.viewmodel.UnitPriceHistoryModel;

        export class ScreenModel {
            unitPriceHistoryModel: UnitPriceHistoryModel;

            historyTakeOver: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.unitPriceHistoryModel = ko.mapping.fromJS(nts.uk.ui.windows.getShared('unitPriceHistoryModel'));
                self.historyTakeOver = ko.observable('latest');

            }

            /**
             * Start page.
             */
            public startPage(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                dfd.resolve();
                return dfd.promise();
            }

            /**
             * Create history and then dialog.
             */
            private btnApplyClicked(): void {
                var self = this;
                // Reset data if historyTakeOver = beginning
                if (self.historyTakeOver() == 'beginning') {
                    self.unitPriceHistoryModel.budget(0);
                    self.unitPriceHistoryModel.fixPaySettingType('Company');
                    self.unitPriceHistoryModel.fixPayAtr('NotApply');
                    self.unitPriceHistoryModel.fixPayAtrMonthly('NotApply');
                    self.unitPriceHistoryModel.fixPayAtrDayMonth('NotApply');
                    self.unitPriceHistoryModel.fixPayAtrDaily('NotApply');
                    self.unitPriceHistoryModel.fixPayAtrHourly('NotApply');
                    self.unitPriceHistoryModel.memo('');
                }
                service.create(ko.toJS(self.unitPriceHistoryModel)).done(() => {
                    nts.uk.ui.windows.setShared('isCreated', true);
                    nts.uk.ui.windows.close();
                });
            }

            /**
             * Close dialog.
             */
            private btnCancelClicked(): void {
                nts.uk.ui.windows.close();
            }

        }
    }
}