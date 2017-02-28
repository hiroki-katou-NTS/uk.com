module nts.uk.pr.view.qmm007.c {
    export module viewmodel {
        import service = nts.uk.pr.view.qmm007.a.service;
        import UnitPriceHistoryDto = nts.uk.pr.view.qmm007.a.service.model.UnitPriceHistoryDto;

        export class ScreenModel {
            unitPriceHistoryModel: KnockoutObservable<any>;

            edittingMethod: KnockoutObservable<string>;
            isEditMode: KnockoutObservable<boolean>;

            constructor() {
                var self = this;
                var unitPriceHistoryDto: UnitPriceHistoryDto = nts.uk.ui.windows.getShared('unitPriceHistoryModel');
                self.unitPriceHistoryModel = ko.mapping.fromJS(unitPriceHistoryDto);
                self.edittingMethod = ko.observable('Edit');
                self.isEditMode = ko.observable(true);

                self.edittingMethod.subscribe(val => {
                    val == 'Edit' ? self.isEditMode(true) : self.isEditMode(false);
                });

            }

            btnApplyClicked() {
                var self = this;
                if (self.isEditMode()) {
                    service.update(ko.toJS(self.unitPriceHistoryModel)).done(() => {
                        nts.uk.ui.windows.close();
                    });
                } else {
                    service.remove(self.unitPriceHistoryModel.id(), self.unitPriceHistoryModel.unitPriceCode()).done(() => {
                        nts.uk.ui.windows.close();
                    });
                }
            }
            btnCancelClicked() {
                nts.uk.ui.windows.close()
            }
        }
    }
}