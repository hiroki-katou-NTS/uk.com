module nts.uk.pr.view.qmm007.c {
    export module viewmodel {
        import service = nts.uk.pr.view.qmm007.a.service;

        export class ScreenModel {
            code: KnockoutObservable<string>;
            name: KnockoutObservable<string>;
            startMonth: KnockoutObservable<string>;
            endMonth: KnockoutObservable<string>;
            edittingMethod: KnockoutObservable<string>;
            isEditMode: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                var unitPriceHistoryModel = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                self.code = ko.observable(unitPriceHistoryModel.unitPriceCode());
                self.name = ko.observable(unitPriceHistoryModel.unitPriceName());
                self.startMonth = ko.observable(unitPriceHistoryModel.startMonth());
                self.endMonth = ko.observable(unitPriceHistoryModel.endMonth());
                self.edittingMethod = ko.observable('Edit');
                self.isEditMode = ko.observable(true);

                self.edittingMethod.subscribe(val => {
                    val == 'Edit' ? self.isEditMode(true) : self.isEditMode(false);
                });

            }
            btnApplyClicked() {
                var self = this;
                var unitPriceHistoryModel = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                if (self.isEditMode()) {
                    unitPriceHistoryModel.startMonth(self.startMonth());
                    service.update(service.collectData(nts.uk.ui.windows.getShared('unitPriceHistoryModel')));
                } else {
                    service.remove(nts.uk.ui.windows.getShared('unitPriceHistoryModel').id);
                }
                nts.uk.ui.windows.close()
            }
            btnCancelClicked() {
                nts.uk.ui.windows.close()
            }
        }
    }
}