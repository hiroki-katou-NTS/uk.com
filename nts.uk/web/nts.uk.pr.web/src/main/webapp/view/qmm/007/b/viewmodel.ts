module nts.uk.pr.view.qmm007.b {
    export module viewmodel {
        import service = nts.uk.pr.view.qmm007.a.service;

        export class ScreenModel {
            code: string;
            name: string;
            endMonth: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;

            historyTakeOver: KnockoutObservable<string>;

            constructor() {
                var self = this;
                var unitPriceHistoryModel = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                self.code = unitPriceHistoryModel.unitPriceCode();
                self.name = unitPriceHistoryModel.unitPriceName();
                self.startMonth = ko.observable(unitPriceHistoryModel.startMonth());
                self.endMonth = ko.observable(unitPriceHistoryModel.endMonth());

                self.historyTakeOver = ko.observable('1');

            }

            btnApplyClicked() {
                service.create(service.collectData(nts.uk.ui.windows.getShared('unitPriceHistoryModel')));
                nts.uk.ui.windows.close()
            }
            btnCancelClicked() {
                nts.uk.ui.windows.close()
            }

        }
    }
}