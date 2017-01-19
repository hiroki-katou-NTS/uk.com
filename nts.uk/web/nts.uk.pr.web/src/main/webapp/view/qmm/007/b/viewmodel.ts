module nts.uk.pr.view.qmm007.b {
    export module viewmodel {

        export class ScreenModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;

            historyTakeOver: KnockoutObservable<string>;

            constructor() {
                var self = this;
                var unitPriceHistoryModel = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                self.code = ko.observable(unitPriceHistoryModel.unitPriceCode());
                self.name = ko.observable(unitPriceHistoryModel.unitPriceName());
                self.startMonth = ko.observable(unitPriceHistoryModel.startMonth());
                self.endMonth = ko.observable(unitPriceHistoryModel.endMonth());

                self.historyTakeOver = ko.observable('1');

            }

            btnApplyClicked() {
                nts.uk.ui.windows.close()
            }
            btnCancelClicked() {
                nts.uk.ui.windows.close()
            }

        }
    }
}